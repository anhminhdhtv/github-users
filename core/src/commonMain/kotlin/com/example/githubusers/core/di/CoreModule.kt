package com.example.githubusers.core.di
import com.example.githubusers.core.dispatchers.QzDispatchers
import com.example.githubusers.core.dispatchers.QzDispatchersImpl
import org.koin.dsl.module

val coreModule = module {
    single<QzDispatchers> { QzDispatchersImpl() }
}
