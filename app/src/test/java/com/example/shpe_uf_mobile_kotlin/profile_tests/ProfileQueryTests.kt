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

        if(result != null){
            val name = result.firstName + " " + result.lastName

            assertEquals(name, "Andrei Ursu")
            assertEquals(result.username, "andreiursu")
            assertEquals(result.email, "ursuandrei@ufl.edu")
            assertEquals(result.sex, "Male")
            assertEquals(result.ethnicity, "White")
            assertEquals(result.country, "United States")
            assertEquals(result.year, "3rd Year")
            assertEquals(result.graduating, "2025")
            assertEquals(result.classes?.size, 0)
            assertEquals(result.internships?.size, 0)
            assertEquals(result.socialMedia?.size, 0)
        }

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
        val id = "64ee138ec736670014d47e48" // Andrei's shpe id, use for testing purposes.
        val result = getUserInfo(id)
        assertEquals(result, true)
    }
}