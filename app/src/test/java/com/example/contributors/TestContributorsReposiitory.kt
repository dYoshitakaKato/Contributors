package com.example.contributors

import com.example.contributors.model.Contributor
import com.example.contributors.repository.ContributorsRepository
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class TestContributorsReposiitory {
    @Test
    fun fetchSuccessTest() {
        val repository = ContributorsRepository()
        val countDownLatch = CountDownLatch(1)
        var contributors: List<Contributor> = ArrayList<Contributor>()
        repository.createService().fetch().enqueue(object : Callback<List<Contributor>> {
            override fun onResponse(call: Call<List<Contributor>>, response: Response<List<Contributor>>) {
                if (!response.isSuccessful) {
                    countDownLatch.countDown()
                    return
                }
                response.body()?.let {
                    contributors = it
                    countDownLatch.countDown()
                }
            }
            override fun onFailure(call: Call<List<Contributor>>, t: Throwable) {
                Assert.fail("Fetch失敗")
                return
            }
        })
        Assert.assertTrue("非同期タイムアウト", countDownLatch.await(1, TimeUnit.SECONDS))
        Assert.assertFalse("取得エラー", contributors.isEmpty())
        Assert.assertEquals("取得件数エラー", contributors.size, 30)
    }
}