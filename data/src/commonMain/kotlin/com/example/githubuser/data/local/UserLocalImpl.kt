package com.example.githubuser.data.local

import com.example.githubusers.core.model.User
import com.example.githubuser.data.cache.MyDatabase
import com.example.githubuser.data.cache.UserDb

class UserLocalImpl(db: MyDatabase) : IUserLocal {
    private val query = db.myDatabaseQueries

    override fun getAllUsers(): List<UserDb> {
        return query.selectAll().executeAsList().sortedBy { it.id }
    }

    override fun getUser(id: Int): UserDb? {
        return query.selectById(id.toString()).executeAsOneOrNull()
    }

    override fun getUser(userName: String): UserDb? {
        return query.selectByUserName(userName).executeAsOneOrNull()
    }

    override fun saveUser(user: User, inDetail: Boolean) {
        query.insert(user.toUserDb(inDetail))
    }

    override fun removeAllUsers() {
        query.deleteAll()
    }

    /**
     * Extension function to map User to UserDb.
     *
     * @param inDetail Flag to check if user is in detail.
     *
     * @return UserDb
     */
    private fun User.toUserDb(inDetail: Boolean = false): UserDb {
        return UserDb(
            id = this.id.toString(),
            username = this.username,
            avatarUrl = this.avatarUrl,
            htmlUrl = this.htmlUrl,
            location = this.location,
            followers = this.followers,
            following = this.following,
            blog = this.blog,
            inDetail = inDetail
        )
    }
}
