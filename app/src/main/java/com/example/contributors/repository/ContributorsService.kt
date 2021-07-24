package com.example.contributors.repository

import com.example.contributors.model.Contributor
import retrofit2.Call
import retrofit2.http.GET

interface ContributorsService {
    @GET("repositories/90792131/contributors")
    fun fetch(): Call<List<Contributor>>
}