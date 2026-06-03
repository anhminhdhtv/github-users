package com.example.githubuser.domain.usecase

import com.example.githubuser.domain.manage.UserManager
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RemoveAllUserUseCaseTest {

    private val userManager: UserManager = mockk()
    private val removeAllUserUseCase = RemoveAllUserUseCase(userManager)

    @Test
    fun `invoke should call userManager removeALlUsers`() = runTest {
        // Given
        every { userManager.removeALlUsers() } just runs

        // When
        removeAllUserUseCase(Unit)

        // Then
        verify(exactly = 1) { userManager.removeALlUsers() }
    }
}
