package com.example.githubuser.data.local.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.koin.core.scope.Scope
import com.example.githubuser.data.cache.MyDatabase
actual fun Scope.sqlDriverFactory(): SqlDriver {
    return NativeSqliteDriver(MyDatabase.Schema, "MyDatabase.cache")
}
