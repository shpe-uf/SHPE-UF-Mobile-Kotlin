package com.example.shpe_uf_mobile_kotlin.profile_tests

import com.example.shpe_uf_mobile_kotlin.GetUserQuery
import com.example.shpe_uf_mobile_kotlin.apolloClient
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

private suspend fun getUserInfo(id: String): Boolean{
   val response = apolloClient.query(GetUserQuery(id)).execute()

    if(!response.hasErrors()){
        val result = response.data?.getUser
        println(result)
        return true
    }
    else
        return false
}

class ProfileQueryTests {
    // Testing profile query with SHPE ID.
    @Test
    fun testGetUser() = runTest{
        val id = "64ee138ec736670014d47e48"

        val result = getUserInfo(id)

        assertEquals(result, true)
    }
}