package com.example.petadoptionapp.network

sealed class Failure(val message: String) {

    //TODO adding network errors

    class NetworkConnectionFailure(message: String) : Failure(message)
    class ApiErrorFailure(message: String) : Failure(message)
    class InvalidCredentialsErrorFailure(message: String) : Failure(message)
    class ServerFailure(message: String) : Failure(message)
    class ResourceNotAvailableFailure(message: String) : Failure(message)
    class ResourceParseFailure(message: String) : Failure(message)

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(message: String) : Failure(message)

}