package com.example.contributors.repository

import com.example.contributors.model.Contributor
import com.example.contributors.model.ContributorDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ContributorService {
    @GET("repositories/90792131/contributors")
    suspend fun fetchAll(): Response<List<Contributor>>

    @GET("users/{login}")
    suspend fun fetchDetail(@Path("login") login: String): Response<ContributorDetail>
}