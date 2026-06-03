package com.example.githubuser.feature.users.di

import com.example.githubuser.feature.users.viewmodel.UsersViewModelImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val usersFeatureModule = module {
    viewModel {
        UsersViewModelImpl(get(), get())
    }
}