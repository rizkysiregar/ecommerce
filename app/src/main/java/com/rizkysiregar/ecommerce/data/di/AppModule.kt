package com.rizkysiregar.ecommerce.data.di

import com.rizkysiregar.ecommerce.MainActivityViewModel
import com.rizkysiregar.ecommerce.ui.cart.CartViewModel
import com.rizkysiregar.ecommerce.ui.checkout.CheckoutViewModel
import com.rizkysiregar.ecommerce.ui.detail.DetailProductViewModel
import com.rizkysiregar.ecommerce.ui.login.LoginViewModel
import com.rizkysiregar.ecommerce.ui.payment.PaymentViewModel
import com.rizkysiregar.ecommerce.ui.profile.ProfileViewModel
import com.rizkysiregar.ecommerce.ui.register.RegisterViewModel
import com.rizkysiregar.ecommerce.ui.review.ReviewViewModel
import com.rizkysiregar.ecommerce.ui.search.SearchViewModel
import com.rizkysiregar.ecommerce.ui.status.StatusViewModel
import com.rizkysiregar.ecommerce.ui.store.StoreViewModel
import com.rizkysiregar.ecommerce.ui.transaction.TransactionViewModel
import com.rizkysiregar.ecommerce.ui.wishlist.WishlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { StoreViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailProductViewModel(get()) }
    viewModel { ReviewViewModel(get()) }
    viewModel { WishlistViewModel(get()) }
    viewModel { CartViewModel(get()) }
    viewModel { PaymentViewModel(get()) }
    viewModel { CheckoutViewModel(get()) }
    viewModel { StatusViewModel(get()) }
    viewModel { TransactionViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
}