package com.example.petadoptionapp.network.interceptor

import com.example.petadoptionapp.network.refresh_token.RefreshTokenRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class JwtTokenInterceptor(private val tokenRepository: RefreshTokenRepository) : Interceptor {

    companion object {
        const val NO_AUTHENTICATION = "No-Authentication: true"
        private const val NO_AUTHENTICATION_KEY = "No-Authentication"

        const val AUTHORIZATION_KEY = "Authorization"
        const val AUTHORIZATION_VALUE = "Bearer %s"
    }

    /**
     * This method is used to add the authentication header. It uses a dummy marker header with with
     * the [@Header] value [NO_AUTHENTICATION], if this header is present than we don't need to authenticate
     * this request, otherwise the saved jwt token is added to the request.
     * @see [AuthApiService] for example
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        /**
         * To prepare an authenticated request we need to add the saved Jwt token as and
         * authorization header
         */
        fun prepareAuthenticatedRequest(request: Request): Request {
            val jwtToken = tokenRepository.getAccessToken()
            return request.newBuilder()
                .header(AUTHORIZATION_KEY, AUTHORIZATION_VALUE.format(jwtToken))
                .build()
        }

        /**
         * To prepare an unauthenticated request we just remove the dummy marker header that shows
         * us that the token should not be added tot this request
         */
        fun prepareUnauthenticatedRequest(request: Request): Request =
            request.newBuilder()
                .removeHeader(NO_AUTHENTICATION_KEY)

                .build()


        val request = with(chain.request()) {
            when (this.header(NO_AUTHENTICATION_KEY) != null) {
                true -> prepareUnauthenticatedRequest(this)
                false -> prepareAuthenticatedRequest(this)
            }
        }

        return chain.proceed(request)

    }

}