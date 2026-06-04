package com.example.githubuser.data.local.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.core.scope.Scope
import org.koin.android.ext.koin.androidContext
import com.example.githubuser.data.cache.MyDatabase

actual fun Scope.sqlDriverFactory(): SqlDriver {
    return AndroidSqliteDriver(
        schema = MyDatabase.Schema,
        context = androidContext(),
        name = "MyDatabase.cache"
    )
}