package com.example.githubuser.domain.manage

import com.example.githubuser.data.repo.local.IUserLocalRepo
import com.example.githubuser.data.repo.remote.IUserRemoteRepo
import com.example.githubusers.core.model.User
import dev.mokkery.answering.returns
import dev.mokkery.mock
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import dev.mokkery.matcher.any
import dev.mokkery.verify.VerifyMode.Companion.exactly
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserManagerImplTest {

    private val userRemoteRepo: IUserRemoteRepo = mock()
    private val userLocalRepo: IUserLocalRepo = mock()
    private lateinit var userManager: UserManagerImpl

    @BeforeTest
    fun setUp() {
        userManager = UserManagerImpl(userRemoteRepo, userLocalRepo)
    }

    @Test
    fun `removeAllUsers should delegate to userLocalRepo`() {
        // Given
        every { userLocalRepo.removeAllUsers() } returns Unit

        // When
        userManager.removeALlUsers()

        // Then
        verify(exactly(1)) { userLocalRepo.removeAllUsers() }
    }

    @Test
    fun `fetchUser should return local data when local cache is not empty on first call`() = runTest {
        // Given
        val localUsers = listOf(User(id = 1, username = "local_user"))
        every { userLocalRepo.getAllUsers() } returns localUsers

        // When
        val result = userManager.fetchUser(itemPerPage = 20, since = 0)

        // Then
        assertTrue(result.isSuccess)
        val dataStruct = result.getOrNull()
        assertEquals(localUsers, dataStruct?.dataList)
        assertEquals(20, dataStruct?.itemPerPage)
        verify(exactly(1)) { userLocalRepo.getAllUsers() }
        verifySuspend(exactly(0)) { userRemoteRepo.fetchUser(any(), any()) }
    }

    @Test
    fun `fetchUser should fetch from remote and save to local when local cache is empty`() = runTest {
        // Given
        every { userLocalRepo.getAllUsers() } returns emptyList()
        val remoteUsers = arrayOf(User(id = 2, username = "remote_user"))
        everySuspend { userRemoteRepo.fetchUser(20, 0) } returns Result.success(remoteUsers)
        every { userLocalRepo.saveUser(any()) } returns Unit

        // When
        val result = userManager.fetchUser(itemPerPage = 20, since = 0)

        // Then
        assertTrue(result.isSuccess)
        val dataStruct = result.getOrNull()
        assertEquals(remoteUsers.toList(), dataStruct?.dataList)
        verify(exactly(1)) { userLocalRepo.getAllUsers() }
        verifySuspend(exactly(1)) { userRemoteRepo.fetchUser(20, 0) }
        verify(exactly(1)) { userLocalRepo.saveUser(remoteUsers[0]) }
    }

    @Test
    fun `fetchUserDetail should return local user if user exists and is in detail`() = runTest {
        // Given
        val localUser = User(id = 1, username = "john", isInDetail = true)
        every { userLocalRepo.getUser("john") } returns localUser

        // When
        val result = userManager.fetchUserDetail("john")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(localUser, result.getOrNull())
        verifySuspend(exactly(0)) { userRemoteRepo.fetchUserDetail(any()) }
    }

    @Test
    fun `fetchUserDetail should fetch from remote and save locally if local user is not in detail`() = runTest {
        // Given
        val localUser = User(id = 1, username = "john", isInDetail = false)
        val remoteUserDetail = User(id = 1, username = "john", isInDetail = true, location = "San Francisco")
        every { userLocalRepo.getUser("john") } returns localUser
        everySuspend { userRemoteRepo.fetchUserDetail("john") } returns Result.success(remoteUserDetail)
        every { userLocalRepo.saveUser(remoteUserDetail, true) } returns Unit

        // When
        val result = userManager.fetchUserDetail("john")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(remoteUserDetail, result.getOrNull())
        verifySuspend(exactly(1)) { userRemoteRepo.fetchUserDetail("john") }
        verify(exactly(1)) { userLocalRepo.saveUser(remoteUserDetail, true) }
    }
}
