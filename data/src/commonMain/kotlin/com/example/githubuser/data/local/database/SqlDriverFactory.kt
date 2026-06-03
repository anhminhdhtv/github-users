package com.example.githubuser.data.local.database

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope
import com.example.githubuser.data.cache.MyDatabase1

expect fun Scope.sqlDriverFactory() : SqlDriver

fun createDatabase(driver: SqlDriver): MyDatabase1 {
    return MyDatabase1(
        driver = driver,
    )
}