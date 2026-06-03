package com.example.githubuser.domain.manage

import com.example.githubuser.data.repo.local.IUserLocalRepo
import com.example.githubuser.data.repo.remote.IUserRemoteRepo
import com.example.githubusers.core.model.User
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.Test

class UserManagerImplTest {

    private val userRemoteRepo: IUserRemoteRepo = mockk()
    private val userLocalRepo: IUserLocalRepo = mockk()
    private lateinit var userManager: UserManagerImpl

    @Before
    fun setUp() {
        userManager = UserManagerImpl(userRemoteRepo, userLocalRepo)
    }

    @Test
    fun `removeAllUsers should delegate to userLocalRepo`() {
        // Given
        every { userLocalRepo.removeAllUsers() } just runs

        // When
        userManager.removeALlUsers()

        // Then
        verify(exactly = 1) { userLocalRepo.removeAllUsers() }
    }

    @Test
    fun `fetchUser should return local data when local cache is not empty on first call`() = runTest {
        // Given
        val localUsers = listOf(User(id = 1, username = "local_user"))
        every { userLocalRepo.getAllUsers() } returns localUsers

        // When
        val result = userManager.fetchUser(itemPerPage = 20, since = 0)

        // Then
        assertEquals(localUsers, result.dataList)
        assertEquals(20, result.itemPerPage)
        coVerify(exactly = 1) { userLocalRepo.getAllUsers() }
        coVerify(exactly = 0) { userRemoteRepo.fetchUser(any(), any()) }
    }

    @Test
    fun `fetchUser should fetch from remote and save to local when local cache is empty`() = runTest {
        // Given
        every { userLocalRepo.getAllUsers() } returns emptyList()
        val remoteUsers = arrayOf(User(id = 2, username = "remote_user"))
        coEvery { userRemoteRepo.fetchUser(20, 0) } returns remoteUsers
        every { userLocalRepo.saveUser(any()) } just runs

        // When
        val result = userManager.fetchUser(itemPerPage = 20, since = 0)

        // Then
        assertEquals(remoteUsers.toList(), result.dataList)
        coVerify(exactly = 1) { userLocalRepo.getAllUsers() }
        coVerify(exactly = 1) { userRemoteRepo.fetchUser(20, 0) }
        verify(exactly = 1) { userLocalRepo.saveUser(remoteUsers[0]) }
    }

    @Test
    fun `fetchUserDetail should return local user if user exists and is in detail`() = runTest {
        // Given
        val localUser = User(id = 1, username = "john", isInDetail = true)
        every { userLocalRepo.getUser("john") } returns localUser

        // When
        val result = userManager.fetchUserDetail("john")

        // Then
        assertEquals(localUser, result)
        coVerify(exactly = 0) { userRemoteRepo.fetchUserDetail(any()) }
    }

    @Test
    fun `fetchUserDetail should fetch from remote and save locally if local user is not in detail`() = runTest {
        // Given
        val localUser = User(id = 1, username = "john", isInDetail = false)
        val remoteUserDetail = User(id = 1, username = "john", isInDetail = true, location = "San Francisco")
        every { userLocalRepo.getUser("john") } returns localUser
        coEvery { userRemoteRepo.fetchUserDetail("john") } returns remoteUserDetail
        every { userLocalRepo.saveUser(remoteUserDetail, true) } just runs

        // When
        val result = userManager.fetchUserDetail("john")

        // Then
        assertEquals(remoteUserDetail, result)
        coVerify(exactly = 1) { userRemoteRepo.fetchUserDetail("john") }
        verify(exactly = 1) { userLocalRepo.saveUser(remoteUserDetail, true) }
    }
}
