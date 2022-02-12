package com.example.contributors.model

data class Response<T> (
    val isSuccess: Boolean,
    val responseData: T,
)
