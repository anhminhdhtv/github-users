package com.example.githubuser.data.repo.local

import com.example.githubuser.data.cache.UserDb
import com.example.githubusers.core.model.User
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UserLocalRepoImplTest {

    private val fakeUserLocal = FakeUserLocal()
    private val repo = UserLocalRepoImpl(fakeUserLocal)

    @Test
    fun testGetAllUsers() {
        val userDb = UserDb(
            id = "1",
            username = "john",
            avatarUrl = "url",
            htmlUrl = "html",
            location = "loc",
            followers = 10L,
            following = 5L,
            blog = "blog",
            inDetail = true
        )
        fakeUserLocal.getAllUsersResult = listOf(userDb)

        val result = repo.getAllUsers()
        assertEquals(1, result.size)
        val user = result.first()
        assertEquals(1, user.id)
        assertEquals("john", user.username)
        assertEquals("url", user.avatarUrl)
        assertEquals("html", user.htmlUrl)
        assertEquals("loc", user.location)
        assertEquals(10L, user.followers)
        assertEquals(5L, user.following)
        assertEquals("blog", user.blog)
        assertTrue(user.isInDetail)
    }

    @Test
    fun testGetUserById() {
        val userDb = UserDb(
            id = "1",
            username = "john",
            avatarUrl = "url",
            htmlUrl = "html",
            location = "loc",
            followers = 10L,
            following = 5L,
            blog = "blog",
            inDetail = true
        )
        fakeUserLocal.getUserResult = userDb

        val result = repo.getUser(1)
        assertEquals(1, result?.id)
        assertEquals("john", result?.username)
    }

    @Test
    fun testGetUserByIdNotFound() {
        fakeUserLocal.getUserResult = null
        val result = repo.getUser(1)
        assertNull(result)
    }

    @Test
    fun testGetUserByUsername() {
        val userDb = UserDb(
            id = "1",
            username = "john",
            avatarUrl = "url",
            htmlUrl = "html",
            location = "loc",
            followers = 10L,
            following = 5L,
            blog = "blog",
            inDetail = true
        )
        fakeUserLocal.getUserByNameResult = userDb

        val result = repo.getUser("john")
        assertEquals(1, result?.id)
        assertEquals("john", result?.username)
    }

    @Test
    fun testSaveUser() {
        val user = User(id = 1, username = "john")
        repo.saveUser(user, true)

        assertEquals(user, fakeUserLocal.savedUser)
        assertEquals(true, fakeUserLocal.savedInDetail)
    }

    @Test
    fun testRemoveAllUsers() {
        repo.removeAllUsers()
        assertTrue(fakeUserLocal.removeAllUsersCalled)
    }
}
