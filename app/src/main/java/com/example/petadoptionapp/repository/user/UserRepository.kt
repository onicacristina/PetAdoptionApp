package com.example.petadoptionapp.repository.user

import com.example.petadoptionapp.network.models.User
import com.example.petadoptionapp.network.services.user.UserApiInterface
import com.example.petadoptionapp.repository.mapper.requests.UserRequestMapper
import com.example.petadoptionapp.repository.mapper.responses.UserResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiUserInterface: UserApiInterface
): UserRepositoryInterface {
    override suspend fun getUserById(id: String): Result<User> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val result = apiUserInterface.getUserById(id)
                UserResponseMapper().map(result)
            }
        }
    }

    override suspend fun editUser(id: String, data: User): Result<Any> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val params = UserRequestMapper().map(data)
                apiUserInterface.editUser(id, params)
            }
        }
    }
}