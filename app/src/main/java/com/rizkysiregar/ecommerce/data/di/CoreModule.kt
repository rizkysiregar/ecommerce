package com.rizkysiregar.ecommerce.data.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.GsonBuilder
import com.rizkysiregar.ecommerce.BuildConfig
import com.rizkysiregar.ecommerce.data.network.api.AuthHeadersInterceptor
import com.rizkysiregar.ecommerce.data.network.api.AuthService
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponse
import com.rizkysiregar.ecommerce.data.network.response.RegisterResponseInstanceCreator
import com.rizkysiregar.ecommerce.data.repository.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        val authClient = OkHttpClient.Builder()
            .addInterceptor(AuthHeadersInterceptor())
            .addInterceptor(loggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(RegisterResponse::class.java, RegisterResponseInstanceCreator())
            .create()

        val retrofitAuthService = Retrofit.Builder()
            .baseUrl("http://172.17.20.210:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(authClient)
            .build()
        retrofitAuthService.create(AuthService::class.java)
    }

}

val repositoryModule = module {
    single { UserRepository(get()) }
}