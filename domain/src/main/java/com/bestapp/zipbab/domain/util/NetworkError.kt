package com.bestapp.zipbab.domain.util

enum class NetworkError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN,
    NOT_AVAILABLE,
    NOT_FOUND,
}
