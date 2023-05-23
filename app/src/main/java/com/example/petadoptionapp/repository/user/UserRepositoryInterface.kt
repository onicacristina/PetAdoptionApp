package com.example.petadoptionapp.repository.user

import com.example.petadoptionapp.network.models.User

interface UserRepositoryInterface {
    suspend fun getUserById(id: String): Result<User>
    suspend fun editUser(id: String, data: User): Result<Any>
}