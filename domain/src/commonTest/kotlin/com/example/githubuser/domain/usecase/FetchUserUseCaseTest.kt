package com.example.githubuser.domain.usecase

import com.example.githubuser.domain.manage.UserManager
import com.example.githubusers.core.model.ListDataStruct
import com.example.githubusers.core.model.User
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.exactly
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FetchUserUseCaseTest {

    private val userManager: UserManager = mock()
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
        everySuspend { userManager.fetchUser(20, 0) } returns Result.success(expectedResult)

        // When
        val result = fetchUserUseCase(config)

        // Then
        assertEquals(Result.success(expectedResult), result)
        verifySuspend(exactly(1)) { userManager.fetchUser(20, 0) }
    }
}
