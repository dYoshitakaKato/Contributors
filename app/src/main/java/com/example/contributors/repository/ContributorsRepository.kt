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
            return Response(false, ArrayList())
        }
        val body = response.body() ?: return Response(
            false,
            ArrayList()
        )
        return Response(true, body)
    }
}
