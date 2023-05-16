package com.example.petadoptionapp.repository.mapper

interface ModelMapper<in M, out E> {

    fun map(model: M): E

}