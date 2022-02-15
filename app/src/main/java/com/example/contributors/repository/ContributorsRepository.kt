package com.example.contributors.repository

import com.example.contributors.model.Contributor
import com.example.contributors.model.Response
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityRetainedComponent::class)
class ContributorsRepository @Inject constructor() {

    @Inject
    lateinit var composite: ContributorRepository

    suspend fun get(): Response<List<Contributor>> {
        val response = composite.createService().fetchAll()
        if (!response.isSuccessful) {
            return Response(false, emptyList())
        }
        return response(response)
    }

    private fun response(response: retrofit2.Response<List<Contributor>>): Response<List<Contributor>> {
        val body = response.body() ?: return Response(
            false,
            emptyList()
        )
        return Response(true, body)
    }
}
