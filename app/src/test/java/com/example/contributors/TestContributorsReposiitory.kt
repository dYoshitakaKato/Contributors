package com.example.contributors

import com.example.contributors.repository.ContributorRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class TestContributorsReposiitory {
    @Test
    fun fetchAllSuccessTest() = runBlocking {
        val repository = ContributorRepository()
        val response = repository.createService().fetchAll()
        if (!response.isSuccessful) {
            Assert.fail("応答エラー")
            return@runBlocking
        }
        val body = response.body()
        Assert.assertNotNull("Null取得エラー", body)
        val contributors = requireNotNull(response.body())
        Assert.assertFalse("取得データ空エラー", contributors.isEmpty())
        Assert.assertEquals("取得件数エラー", contributors.size, 30)
    }

    @Test
    fun fetchDetailSuccessTest() = runBlocking {
        val repository = ContributorRepository()
        val login = "ianhanniballake"
        val response = repository.createService().fetchDetail(login)
        if (!response.isSuccessful) {
            Assert.fail("応答エラー")
            return@runBlocking
        }
        val body = response.body()
        Assert.assertNotNull("Null取得エラー", body)
        val detail = requireNotNull(response.body())
        Assert.assertEquals("取得データ不一致エラー", detail.login, login)
    }

    @Test
    fun fetchDetailFailTest() = runBlocking {
        val repository = ContributorRepository()
        val login = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
        val response = repository.createService().fetchDetail(login)
        if (response.isSuccessful) {
            Assert.fail("応答エラー")
            return@runBlocking
        }
        Assert.assertNull("取得エラー", response.body())
    }
}