package com.example.githubuser.domain.usecase

import com.example.githubuser.domain.manage.UserManager
import dev.mokkery.answering.returns
import dev.mokkery.mock
import dev.mokkery.every
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode.Companion.exactly
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class RemoveAllUserUseCaseTest {

    private val userManager: UserManager = mock()
    private val removeAllUserUseCase = RemoveAllUserUseCase(userManager)

    @Test
    fun `invoke should call userManager removeALlUsers`() = runTest {
        // Given
        every { userManager.removeALlUsers() } returns Unit

        // When
        removeAllUserUseCase(Unit)

        // Then
        verify(exactly(1)) { userManager.removeALlUsers() }
    }
}
