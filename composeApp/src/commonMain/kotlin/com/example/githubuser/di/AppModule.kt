package com.example.githubuser.di

import com.example.githubusers.core.di.coreModule
import com.example.githubuser.data.di.dataModule
import com.example.githubuser.domain.di.domainModule
import com.example.githubuser.feature.detail.di.detailFeatureModule
import com.example.githubuser.feature.users.di.usersFeatureModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModule)
    }

val appModule = module {
    includes(coreModule, dataModule, domainModule, usersFeatureModule, detailFeatureModule)
}
