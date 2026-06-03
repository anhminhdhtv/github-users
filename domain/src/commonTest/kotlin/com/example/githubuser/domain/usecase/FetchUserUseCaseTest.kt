package com.example.githubuser.domain.usecase

import com.example.githubuser.domain.manage.UserManager
import com.example.githubusers.core.model.ListDataStruct
import com.example.githubusers.core.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FetchUserUseCaseTest {

    private val userManager: UserManager = mockk()
    private val fetchUserUseCase = FetchUserUseCase(userManager)

    @Test
    fun `invoke should call userManager fetchUser and return result`() = runTest {
        // Given
        val config = FetchUserConfig(itemPerPage = 20, since = 0)
        val expectedResult = ListDataStruct<User>(
            dataCapacity = 100L,
            dataList = listOf(User(id = 1, username = "user1")),
            itemPerPage = 20
        )
        coEvery { userManager.fetchUser(20, 0) } returns expectedResult

        // When
        val result = fetchUserUseCase(config)

        // Then
        assertEquals(expectedResult, result)
        coVerify(exactly = 1) { userManager.fetchUser(20, 0) }
    }
}
