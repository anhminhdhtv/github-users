package com.example.githubuser.feature.detail.di

import com.example.githubuser.feature.detail.viewmodel.DetailViewModelImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailFeatureModule = module {
    viewModel{
        DetailViewModelImpl(get())
    }
}