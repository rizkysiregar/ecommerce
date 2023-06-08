package com.rizkysiregar.ecommerce.data.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.GsonBuilder
import com.rizkysiregar.ecommerce.BuildConfig
import com.rizkysiregar.ecommerce.data.local.db.AppExecutors
import com.rizkysiregar.ecommerce.data.local.db.EcommerceDatabase
import com.rizkysiregar.ecommerce.data.network.api.ApiService
import com.rizkysiregar.ecommerce.data.network.api.ApiServiceHeadersInterceptor
import com.rizkysiregar.ecommerce.data.network.api.TokenAuthenticator
import com.rizkysiregar.ecommerce.data.network.api.baseUrl
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponseInstanceCreator
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import com.rizkysiregar.ecommerce.data.repository.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<EcommerceDatabase>().ecommerceDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            EcommerceDatabase::class.java, "ecommerce.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val networkModule = module {
    single {
        // chucker
        val chuckerCollector = ChuckerCollector(
            context = get(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        val chuckerInterceptor = ChuckerInterceptor.Builder(get())
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .build()

        // logging interceptor
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        // Okhttp
        OkHttpClient.Builder()
            .authenticator(TokenAuthenticator(get()))
            .addInterceptor(ApiServiceHeadersInterceptor(get()))
            .addInterceptor(loggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    single {
        // gson for centralization
        val gson = GsonBuilder()
            .registerTypeAdapter(RegisterResponse::class.java, RegisterResponseInstanceCreator())
            .create()

        // retrofit
        val retrofitAuthService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(get())
            .build()
        retrofitAuthService.create(ApiService::class.java)
    }

}

val repositoryModule = module {
    factory { AppExecutors() }
    single { UserRepository(get()) }
    single { ContentRepository(get(), get()) }
}