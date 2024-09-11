package com.example.shpe_uf_mobile_kotlin.login_tests

import apolloClient
import com.example.shpe_uf_mobile_kotlin.LoginMutation
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*


// Validates all login functions.
private fun validateUsername(username: String): Boolean {
    return !username.isNullOrBlank()
}

private fun validatePassword(password: String): Boolean {
    return !password.isNullOrBlank()
}

// It's defined as a suspend function b/c it uses a network request which could take some time, and we don't want to pause the UI while the mutation is run.
private suspend fun loginUser(username: String, password: String): Boolean {
    // Mutation for logging in, returns the user's id on success.
    val response = apolloClient.mutation(LoginMutation(username, password, "true")).execute()

    // If the response doesn't throw an error, then it successfully logged in.
    if (!response.hasErrors()) {
        val id = response.data?.login?.id
        println("GraphQL: $id")
        return true
    } else { // Else, the user provided incorrect credentials.
        println("GraphQL: Could not login.")
        return false
    }
}

// Test names are self explanatory.
class ValidationTests {
    @Test
    fun testEmptyUsernameOnly() {
        val result = validateUsername("")
        assertEquals(false, result)
    }

    @Test
    fun testNonEmptyUsernameOnly() {
        val result = validateUsername("andreiursu")
        assertEquals(true, result)
    }

    @Test
    fun testEmptyPasswordOnly() {
        val result = validatePassword("")
        assertEquals(false, result)
    }

    @Test
    fun testNonEmptyPasswordOnly() {
        val result = validatePassword("Password123!")
        assertEquals(true, result)
    }

    @Test
    fun testBothEmptyUsernamePassword() {
        val res1 = validateUsername("")
        val res2 = validatePassword("")

        assertEquals(false, res1)
        assertEquals(false, res2)
    }

    @Test
    fun testEmptyUsernameNonEmptyPassword() {
        val res1 = validateUsername("")
        val res2 = validatePassword("Password123!")

        assertEquals(false, res1)
        assertEquals(true, res2)
    }

    @Test
    fun testNonEmptyUsernameEmptyPassword() {
        val res1 = validateUsername("andreiursu")
        val res2 = validatePassword("")

        assertEquals(true, res1)
        assertEquals(false, res2)
    }

    @Test
    fun testFilledUsernameAndPassword() {
        val res1 = validateUsername("andreiursu")
        val res2 = validatePassword("Password123!")

        assertEquals(true, res1)
        assertEquals(true, res2)
    }

    // Testing invalid login with wrong credentials.
    @Test
    fun testInvalidLogin() = runTest {
        val result = loginUser("theandreiursu", "r0seBud1!")
        assertEquals(false, result)
    }

    // Testing valid login with correct credentials.
    @Test
    fun testValidLogin() = runTest {
        val result = loginUser("andreiursu", "Password1!")
        assertEquals(true, result)
    }
}
