package com.example.githubuser.domain.usecase

import com.example.githubuser.domain.manage.UserManager
import com.example.githubusers.core.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FetchUserDetailUseCaseTest {

    private val userManager: UserManager = mockk()
    private val fetchUserDetailUseCase = FetchUserDetailUseCase(userManager)

    @Test
    fun `invoke should call userManager fetchUserDetail and return result`() = runTest {
        // Given
        val username = "john_doe"
        val expectedUser = User(id = 1, username = username, isInDetail = true)
        coEvery { userManager.fetchUserDetail(username) } returns Result.success(expectedUser)

        // When
        val result = fetchUserDetailUseCase(username)

        // Then
        assertEquals(Result.success(expectedUser), result)
        coVerify(exactly = 1) { userManager.fetchUserDetail(username) }
    }
}
