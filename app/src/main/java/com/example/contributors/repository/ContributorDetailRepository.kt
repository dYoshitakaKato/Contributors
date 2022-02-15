package com.example.contributors.repository

import com.example.contributors.model.ContributorDetail
import com.example.contributors.model.Response
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityRetainedComponent::class)
class ContributorDetailRepository @Inject constructor() {
    @Inject
    lateinit var composite: ContributorRepository

    suspend fun get(id: String): Response<ContributorDetail?> {
        val response = composite.createService().fetchDetail(id)
        if (!response.isSuccessful) {
            return Response(false, null)
        }
        if (response.body() == null) {
            return Response(false, null)
        }
        return Response(true, response.body())
    }
}
