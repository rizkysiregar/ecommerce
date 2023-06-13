package com.rizkysiregar.ecommerce

import android.app.Application
import com.google.firebase.FirebaseApp
import com.rizkysiregar.ecommerce.data.di.databaseModule
import com.rizkysiregar.ecommerce.data.di.firebaseAnalyticsModule
import com.rizkysiregar.ecommerce.data.di.networkModule
import com.rizkysiregar.ecommerce.data.di.repositoryModule
import com.rizkysiregar.ecommerce.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    viewModelModule,
                    repositoryModule,
                    databaseModule,
                    firebaseAnalyticsModule
                )
            )
        }
    }
}