package com.example.contributors.repository

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
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

    private fun httpBuilder(timeoutValue: Long): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder().addInterceptor(
            Interceptor {
                val original = it.request()
                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method, original.body)
                    .build()
                return@Interceptor it.proceed(request)
            }
        ).readTimeout(timeoutValue, java.util.concurrent.TimeUnit.SECONDS)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)
        return httpClient
    }

    private val baseUrl = "https://api.github.com/"

    fun createService(timeoutValue: Long = 30): ContributorService {
        val client = httpBuilder(timeoutValue).build()
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit.create(ContributorService::class.java)
    }
}
