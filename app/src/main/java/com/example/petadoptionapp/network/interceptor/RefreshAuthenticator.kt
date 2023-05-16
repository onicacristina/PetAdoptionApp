package com.example.petadoptionapp.network.interceptor

import android.content.Context
import android.content.Intent
import com.example.petadoptionapp.network.refresh_token.AUTHENTICATION_REQUIRED
import com.example.petadoptionapp.network.refresh_token.RefreshTokenEndpoint
import com.example.petadoptionapp.presentation.ui.main.MainActivity
import com.example.petadoptionapp.presentation.utils.Constants.REFRESH_TOKEN_EXPIRED
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber

class RefreshAuthenticator(
    private val refreshTokenEndpoint: RefreshTokenEndpoint,
    private val applicationContext: Context
) : Authenticator {

    // according to https://objectpartners.com/2018/06/08/okhttp-authenticator-selectively-reauthorizing-requests/
    override fun authenticate(route: Route?, response: Response): Request? {
        Timber.d("Detected authentication error ${response.code} on ${response.request.url}")

        if (hasTokenAuthorizationHeader(response)) {
            val previousRetryCount = retryCount(response)

            return reAuthenticateRequestUsingRefreshToken(
                response.request,
                previousRetryCount + 1
            )

        }
        // if the old request did not had authorization header we will not make the refresh token request, just return null
        return null
    }

    // checks if the request had a bearer authorization!
    private fun hasTokenAuthorizationHeader(response: Response?): Boolean {
        response?.let {
            val authorizationHeader = it.request.header("Authorization")
            return authorizationHeader?.startsWith("Bearer ") ?: false
        }
        Timber.d("The failed request does not have auth header")
        return false
    }

    private fun retryCount(response: Response?): Int {
        return response?.request?.header("xObidanRetryCount")?.toInt() ?: 0
    }

    @Synchronized
    // We synchronize this request, so that multiple concurrent failures
    // don't all try to use the same refresh token!
    private fun reAuthenticateRequestUsingRefreshToken(
        staleRequest: Request?,
        retryCount: Int
    ): Request? {

        // See if we have gone too far:
        if (retryCount > 1) {
            // Yup!
            Timber.d("Retry count exceeded! Giving up.")
            // Don't try to re-authenticate any more.
            return null
        }

        // try to get the new refreshed token
        val refreshedToken = refreshTokenEndpoint.refreshToken()

        return if (refreshedToken?.isEmpty() == true || refreshedToken == AUTHENTICATION_REQUIRED) {
            Timber.d("Failed to retrieve new token, unable to re-authenticate!")
            openLoginScreen()
            null
        } else {
            Timber.d("Retrieving new token was success, retry the failed request with $refreshedToken")
            // recreates the old request with the new refreshed token as header for authorization
            rewriteRequest(staleRequest, retryCount, refreshedToken)
        }
    }

    private fun rewriteRequest(
        staleRequest: Request?, retryCount: Int, authToken: String?
    ): Request? {
        return staleRequest?.newBuilder()
            ?.header(
                JwtTokenInterceptor.AUTHORIZATION_KEY,
                JwtTokenInterceptor.AUTHORIZATION_VALUE.format(authToken)
            )
            ?.header(
                "xObidanRetryCount",
                "$retryCount"
            )
            ?.build()
    }

    private fun openLoginScreen() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra(REFRESH_TOKEN_EXPIRED, true)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(intent)
    }
}