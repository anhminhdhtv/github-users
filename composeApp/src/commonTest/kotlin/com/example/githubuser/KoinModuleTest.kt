package com.example.githubuser

import com.example.githubuser.di.appModule
import com.example.githubuser.feature.users.viewmodel.UsersViewModelImpl
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class KoinModuleTest {

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun verifyKoinAppModule() {
        val koinApp = startKoin {
            modules(appModule)
        }
        val usersViewModel = koinApp.koin.get<UsersViewModelImpl>()
        assertNotNull(usersViewModel)
    }
}
