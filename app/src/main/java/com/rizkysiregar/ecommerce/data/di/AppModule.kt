package com.rizkysiregar.ecommerce.data.di

import com.rizkysiregar.ecommerce.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {RegisterViewModel(get())}
}