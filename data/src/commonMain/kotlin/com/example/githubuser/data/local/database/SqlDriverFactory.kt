package com.example.githubuser.data.local.database

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope
import com.example.githubuser.data.cache.MyDatabase

expect fun Scope.sqlDriverFactory(): SqlDriver

fun createDatabase(driver: SqlDriver): MyDatabase {
    return MyDatabase(
        driver = driver,
    )
}
