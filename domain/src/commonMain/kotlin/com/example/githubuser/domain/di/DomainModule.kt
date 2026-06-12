package com.example.githubuser.domain.di

import com.example.githubuser.domain.manage.UserManager
import com.example.githubuser.domain.manage.UserManagerImpl
import com.example.githubuser.domain.usecase.FetchUserDetailUseCase
import com.example.githubuser.domain.usecase.FetchUserUseCase
import com.example.githubuser.domain.usecase.RemoveAllUserUseCase
import org.koin.dsl.module

val domainModule = module {
    single<UserManager> { UserManagerImpl(get(), get()) }
    factory<FetchUserUseCase> { FetchUserUseCase(get()) }
    factory<RemoveAllUserUseCase> { RemoveAllUserUseCase(get()) }
    factory<FetchUserDetailUseCase> { FetchUserDetailUseCase(get()) }
}
