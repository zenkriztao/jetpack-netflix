package com.zenkriztao.netflix.ui.profile

import com.zenkriztao.netflix.util.ViewModelTest
import com.zenkriztao.netflix.util.client.FakePersonClient
import com.zenkriztao.netflix.util.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException

class ProfileViewModelTest : ViewModelTest() {

    private val personId = 1337
    private val personService = FakePersonClient()
    private val profileMapper = ProfileMapper()

    private lateinit var profileViewModel: ProfileViewModel

    @Test
    fun `fetch profile success`() = runTest {
        profileViewModel = createViewModel()

        val stateValues = profileViewModel.uiState.test()

        stateValues.last() shouldBe ProfileViewModel.ProfileUiState(
            profileMapper.map(personService.profileResponse),
            loading = false,
        )
    }

    @Test
    fun `fetch profile error`() = runTest {
        personService.fetchProfileException = IOException()

        profileViewModel = createViewModel()
        val stateValues = profileViewModel.uiState.test()

        stateValues.last().error.shouldBeInstanceOf<IOException>()
        stateValues.last().profile shouldBe null
    }

    private fun createViewModel() = ProfileViewModel(personId, personService, profileMapper)
}
