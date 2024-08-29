package com.example.shpe_uf_mobile_kotlin.registration_tests

//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import apolloClient
//import com.apollographql.apollo3.api.Optional
//import com.example.shpe_uf_mobile_kotlin.RegisterMutation
//import com.example.shpe_uf_mobile_kotlin.type.RegisterInput
import junit.framework.Assert
//import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apolloClient
import com.apollographql.apollo3.api.Optional
import com.example.shpe_uf_mobile_kotlin.RegisterMutation
import com.example.shpe_uf_mobile_kotlin.type.RegisterInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private suspend fun registerUser(registerInput: Optional<RegisterInput?>): Boolean {
    val response = apolloClient.mutation(RegisterMutation(registerInput)).execute()

    if (!response.hasErrors()) {
        val id = response.data?.register?.id
        println("GraphQL: $id")
        return true
    } else { // Else, the user provided incorrect credentials.
        println("GraphQL: Could not register.")
        return false
    }
}




class RegisterGraphQLTest {
    @Test
    fun test1GraphQLRegisterMutation() = runTest{
        val registerInput = RegisterInput(
            firstName = "Anthony",
            lastName = "Zurita",
            username = "AntAndrei23",
            email = "testAntAndrei23@ufl.edu",
            password = "12344321zZ$",
            confirmPassword = "12344321zZ$",
            sex = "Male",
            ethnicity = "Hispanic/Latino",
            country = "United States",
            major = "Mechanical Engineering",
            year = "Freshman",
            graduating = "2027",
            listServ = "false"
    )
//        registerUser(Optional.presentIfNotNull(registerInput))


        Assert.assertEquals(true, registerUser(Optional.presentIfNotNull(registerInput)))
    }

}