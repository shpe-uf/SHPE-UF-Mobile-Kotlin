// kotlin
package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.profile

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileViewModelTest {

    @Test
    fun `isUserAdmin returns true for admin permission`() {
        val vm = ProfileViewModel()
        vm.setPermissionForTesting("member-admin-super")
        assertTrue(vm.isUserAdmin())
    }

    @Test
    fun `isUserAdmin returns false for normal user`() {
        val vm = ProfileViewModel()
        vm.setPermissionForTesting("member")
        assertFalse(vm.isUserAdmin())
    }

    @Test
    fun `isUserAdmin handles null permission`() {
        val vm = ProfileViewModel()
        vm.setPermissionForTesting(null)
        assertFalse(vm.isUserAdmin())
    }
}
