package com.example.contributors.repository

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@Module
@InstallIn(ActivityRetainedComponent::class)
class ContributorRepository @Inject constructor() {
    private val httpBuilder: OkHttpClient.Builder get() {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Accept", "application/json")
                .method(original.method, original.body)
                .build()

            return@Interceptor chain.proceed(request)
        }).readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        return httpClient
    }

    private val baseUrl = "https://api.github.com/"

    fun createService(): ContributorService {
        val client = httpBuilder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ContributorService::class.java)
    }
}