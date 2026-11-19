package com.shpeuf.shpe_uf_mobile_kotlin

import com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.register.RegisterPage1ViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class RegisterPageViewModelTest {

    private lateinit var viewModel: RegisterPage1ViewModel

    @Before
    fun setUp() {
        viewModel = RegisterPage1ViewModel()
    }

    @Test
    fun page1_emailValidation() {
        // Blank email
        viewModel.onEmailChanged("")
        assertEquals("Email is required.", viewModel.uiState.value.emailErrorMessage)

        // Invalid email format
        viewModel.onEmailChanged("not-an-email")
        assertEquals("Invalid email address.", viewModel.uiState.value.emailErrorMessage)

        // Non-UF / Non-SF email domain
        viewModel.onEmailChanged("user@gmail.com")
        assertEquals("University of Florida or Santa Fe College email required.", viewModel.uiState.value.emailErrorMessage)

        // Valid UF email
        viewModel.onEmailChanged("albert@ufl.edu")
        assertNull(viewModel.uiState.value.emailErrorMessage)

        // Valid SF College email
        viewModel.onEmailChanged("student@sfcollege.edu")
        assertNull(viewModel.uiState.value.emailErrorMessage)
    }

    @Test
    fun page1_usernameValidation() {
        // Blank username
        viewModel.onUsernameChanged("")
        assertEquals("Username is required.", viewModel.uiState.value.usernameErrorMessage)

        // Invalid length (too short)
        viewModel.onUsernameChanged("abc")
        assertEquals("6-20 characters, '.' or '_'", viewModel.uiState.value.usernameErrorMessage)

        // Invalid characters
        viewModel.onUsernameChanged("invalid@user!")
        assertEquals("6-20 characters, '.' or '_'", viewModel.uiState.value.usernameErrorMessage)

        // Valid username
        viewModel.onUsernameChanged("gator_shpe2025")
        assertNull(viewModel.uiState.value.usernameErrorMessage)
    }

    @Test
    fun page1_passwordAndConfirmPasswordValidation() {
        // Blank password
        viewModel.onPasswordChanged("")
        assertEquals("Password is required.", viewModel.uiState.value.passwordErrorMessage)

        // Weak password (<8 chars or missing special char/uppercase/number)
        viewModel.onPasswordChanged("weakpass")
        assertEquals("8+ characters, lowercase, uppercase, number & special character", viewModel.uiState.value.passwordErrorMessage)

        // Valid password
        viewModel.onPasswordChanged("StrongGator123!")
        assertNull(viewModel.uiState.value.passwordErrorMessage)

        // Password mismatch
        viewModel.onConfirmPasswordChanged("Different123!")
        assertEquals("Passwords must match.", viewModel.uiState.value.confirmPasswordErrorMessage)

        // Matching confirm password
        viewModel.onConfirmPasswordChanged("StrongGator123!")
        assertNull(viewModel.uiState.value.confirmPasswordErrorMessage)
    }

    @Test
    fun page1_validateRegisterPage1Fields() {
        // Initially invalid
        assertFalse(viewModel.validateRegisterPage1Fields())

        // Fill valid fields
        viewModel.onEmailChanged("gator@ufl.edu")
        viewModel.onUsernameChanged("shpe_albert_2025")
        viewModel.onPasswordChanged("GatorPass123!")
        viewModel.onConfirmPasswordChanged("GatorPass123!")

        assertTrue(viewModel.validateRegisterPage1Fields())
    }

    @Test
    fun page2_personalDetailsValidation() {
        // First Name
        viewModel.onFirstNameChanged("")
        assertEquals("First name is required.", viewModel.uiState.value.firstNameErrorMessage)

        viewModel.onFirstNameChanged("Al") // < 3 chars
        assertNotNull(viewModel.uiState.value.firstNameErrorMessage)

        viewModel.onFirstNameChanged("Albert")
        assertNull(viewModel.uiState.value.firstNameErrorMessage)

        // Last Name
        viewModel.onLastNameChanged("")
        assertEquals("Last name is required.", viewModel.uiState.value.lastNameErrorMessage)

        viewModel.onLastNameChanged("Gator")
        assertNull(viewModel.uiState.value.lastNameErrorMessage)

        // Gender
        viewModel.onGenderChanged("")
        assertEquals("Gender is required.", viewModel.uiState.value.genderErrorMessage)

        viewModel.onGenderChanged("Male")
        assertNull(viewModel.uiState.value.genderErrorMessage)

        // Ethnicity
        viewModel.onEthnicityChanged("")
        assertEquals("Ethnicity is required.", viewModel.uiState.value.ethnicityErrorMessage)

        viewModel.onEthnicityChanged("Hispanic/Latino")
        assertNull(viewModel.uiState.value.ethnicityErrorMessage)

        // Country of Origin
        viewModel.onCountryOriginChanged("")
        assertEquals("Country of origin is required.", viewModel.uiState.value.countryOriginErrorMessage)

        viewModel.onCountryOriginChanged("United States")
        assertNull(viewModel.uiState.value.countryOriginErrorMessage)

        assertTrue(viewModel.validateRegisterPage2Fields())
    }

    @Test
    fun page3_academicDetailsValidation() {
        // Major
        viewModel.onMajorChanged("")
        assertEquals("Major is required.", viewModel.uiState.value.majorErrorMessage)

        viewModel.onMajorChanged("Computer Science")
        assertNull(viewModel.uiState.value.majorErrorMessage)

        // Class Year
        viewModel.onYearChanged("")
        assertEquals("Year is required.", viewModel.uiState.value.yearErrorMessage)

        viewModel.onYearChanged("3rd Year")
        assertNull(viewModel.uiState.value.yearErrorMessage)

        // Graduation Year
        viewModel.onGraduationYearChanged("")
        assertEquals("Graduation year is required.", viewModel.uiState.value.graduationYearErrorMessage)

        viewModel.onGraduationYearChanged("2026")
        assertNull(viewModel.uiState.value.graduationYearErrorMessage)

        assertTrue(viewModel.validateRegisterPage3Fields())
    }

    @Test
    fun statePersistenceAcrossSteps() {
        // Enter Page 1 data
        viewModel.onEmailChanged("shpeito@ufl.edu")
        viewModel.onUsernameChanged("shpeito_2025")
        viewModel.onPasswordChanged("SecurePass123!")
        viewModel.onConfirmPasswordChanged("SecurePass123!")

        // Enter Page 2 data
        viewModel.onFirstNameChanged("Albert")
        viewModel.onLastNameChanged("Gator")
        viewModel.onGenderChanged("Male")
        viewModel.onEthnicityChanged("Hispanic/Latino")
        viewModel.onCountryOriginChanged("United States")

        // Enter Page 3 data
        viewModel.onMajorChanged("Computer Engineering")
        viewModel.onYearChanged("4th Year")
        viewModel.onGraduationYearChanged("2025")

        // Verify state retains all values simultaneously
        val state = viewModel.uiState.value

        assertEquals("shpeito@ufl.edu", state.email)
        assertEquals("shpeito_2025", state.username)
        assertEquals("SecurePass123!", state.password)
        assertEquals("SecurePass123!", state.confirmPassword)
        assertEquals("Albert", state.firstName)
        assertEquals("Gator", state.lastName)
        assertEquals("Male", state.gender)
        assertEquals("Hispanic/Latino", state.ethnicity)
        assertEquals("United States", state.countryOrigin)
        assertEquals("Computer Engineering", state.major)
        assertEquals("4th Year", state.year)
        assertEquals("2025", state.graduationYear)
    }

    @Test
    fun passwordVisibilityToggles() {
        assertFalse(viewModel.uiState.value.isPasswordVisible)
        viewModel.togglePasswordVisibility()
        assertTrue(viewModel.uiState.value.isPasswordVisible)

        assertFalse(viewModel.uiState.value.isConfirmPasswordVisible)
        viewModel.toggleConfirmPasswordVisibility()
        assertTrue(viewModel.uiState.value.isConfirmPasswordVisible)
    }
}
