package com.example.shpe_uf_mobile_kotlin.registration_tests



import org.junit.Test

import org.junit.Assert.*



private fun validateFirstName(name: String): String? {
    if (name.isBlank()) return "First name is required."

    val nameValidator = "^[a-zA-Z ',.-]{3,20}$".toRegex()
    return if (name.matches(nameValidator)) null else "First Name must be at least 3 characters, max 20. No special characters or numbers."
}


private fun validateLastName(name: String): String? {
    if (name.isBlank()) return "Last name is required."

    val nameValidator = "^[a-zA-Z ',.-]{3,20}$".toRegex()
    return if (name.matches(nameValidator)) null else "Last name must be at least 3 characters, max 20. No special characters or numbers."
}


private fun validateEmail(email: String): String? {
    if (email.isBlank()) return "Email is required."

    val emailValidator = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,12})\$".toRegex()

    if(!email.matches(emailValidator)) return "Invalid email address."

    val domains = listOf("@ufl.edu", "@sfcollege.edu")

    return if (domains.contains("@${email.substringAfterLast('@')}")) null else "University of Florida or Santa Fe College email required."
}


private fun validatePassword(password: String): String? {
    if (password.isBlank()) return "Password is required."

    val passwordValidator = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-.]).{8,}$".toRegex()

    return if (password.matches(passwordValidator)) null else "Password must be at least 8 characters, include one lowercase, one uppercase, one number, and one special character."
}


private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
    return if (password == confirmPassword) null else "Passwords must match."
}


class ValidationTests {

    @Test
    fun testEmptyInputForFirstNameField()
    {
        val result = validateFirstName("")
        assertEquals("First name is required.", result)
    }
    @Test
    fun testLessThanThreeCharsForFirstNameField()
    {
        val result = validateFirstName("An")
        assertEquals("First Name must be at least 3 characters, max 20. No special characters or numbers.", result)
    }
    @Test
    fun testMoreThanTwentyCharsForFirstNameField()
    {
        val result = validateFirstName("AnthonyZuritaDiegoBatista")
        assertEquals("First Name must be at least 3 characters, max 20. No special characters or numbers.", result)
    }
    @Test
    fun testSpecialCharsInFirstNameField()
    {
        val result = validateFirstName("An#$")
        assertEquals("First Name must be at least 3 characters, max 20. No special characters or numbers.", result)
    }
    @Test
    fun testNumbersInFirstNameField()
    {
        val result = validateFirstName("An1hon3")
        assertEquals("First Name must be at least 3 characters, max 20. No special characters or numbers.", result)
    }

    @Test
    fun testEmptyInputForLastNameField()
    {
        val result = validateLastName("")
        assertEquals("Last name is required.", result)
    }

    @Test
    fun testLessThanThreeCharsForLastNameField()
    {
        val result = validateLastName("Al")
        assertEquals("Last name must be at least 3 characters, max 20. No special characters or numbers.", result)
    }
    @Test
    fun testMoreThanTwentyCharsForLastNameField()
    {
        val result = validateLastName("ZuritaAnthonyBatistaDiego")
        assertEquals("Last name must be at least 3 characters, max 20. No special characters or numbers.", result)
    }
    @Test
    fun testSpecialCharsInLastNameField()
    {
        val result = validateLastName("B$@o")
        assertEquals("Last name must be at least 3 characters, max 20. No special characters or numbers.", result)
    }
    @Test
    fun testNumbersInLastNameField()
    {
        val result = validateLastName("Z407ita")
        assertEquals("Last name must be at least 3 characters, max 20. No special characters or numbers.", result)
    }

    @Test
    fun testEmptyInputForEmailField()
    {
        val result = validateEmail("")
        assertEquals("Email is required.", result)
    }

    @Test
    fun testInvalidDomainForEmailField()
    {
        val result = validateEmail("anthony.zurita@gmail.com")
        assertEquals("University of Florida or Santa Fe College email required.", result)
    }

    @Test
    fun testInvalidEmailForEmailField()
    {
        val result = validateEmail("a%*h.onzu!a@ufl.edu")
        assertEquals("Invalid email address.", result)
    }

    @Test
    fun testNoInputForPasswordField()
    {
        val result = validatePassword("")
        assertEquals("Password is required.", result)
    }
    @Test
    fun testLessThan8CharsForPasswordField()
    {
        val result = validatePassword("aZ8#")
        assertEquals("Password must be at least 8 characters, include one lowercase, one uppercase, one number, and one special character.", result)
    }
    @Test
    fun testMissingLowerCaseForPasswordField()
    {
        val result = validatePassword("ANTHONY12$")
        assertEquals("Password must be at least 8 characters, include one lowercase, one uppercase, one number, and one special character.", result)
    }
    @Test
    fun testMissingUpperCaseForPasswordField()
    {
        val result = validatePassword("anthony12$")
        assertEquals("Password must be at least 8 characters, include one lowercase, one uppercase, one number, and one special character.", result)
    }
    @Test
    fun testMissingNumberForPasswordField()
    {
        val result = validatePassword("anthonyZur$")
        assertEquals("Password must be at least 8 characters, include one lowercase, one uppercase, one number, and one special character.", result)
    }
    @Test
    fun testMissingSpecialCharForPasswordField()
    {
        val result = validatePassword("anthonyZur98")
        assertEquals("Password must be at least 8 characters, include one lowercase, one uppercase, one number, and one special character.", result)
    }

    @Test
    fun testPasswordsNotMatching()
    {
        val result = validateConfirmPassword("anthonyZ#9", "anthonyZ#6")
        assertEquals("Passwords must match.", result)
    }




}