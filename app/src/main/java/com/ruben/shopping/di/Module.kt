package com.ruben.shopping.di

import com.ruben.shopping.api.ApiClient
import com.ruben.shopping.appservice.PreferenceService
import com.ruben.shopping.appservice.PreferenceServiceImpl
import com.ruben.shopping.menu.recipe.page1.RecipeRepo
import com.ruben.shopping.menu.recipe.page1.RecipeViewModel
import com.ruben.shopping.menu.refrigerator.RefrigeratorRepo
import com.ruben.shopping.menu.refrigerator.RefrigeratorViewModel
import com.ruben.shopping.menu.shop.ShopRepo
import com.ruben.shopping.menu.shop.ShopViewModel
import com.ruben.shopping.utils.AppURLs
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val appRepo = module {
    single { RecipeRepo(get(), get()) }
    single { ShopRepo(get(), get()) }
    single { RefrigeratorRepo(get()) }
    single<PreferenceService> { PreferenceServiceImpl(context = get()) }
}

val networkModule = module {
    factory { provideOkHttpClient() }
    factory { provideApiClient(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(AppURLs.BASE_URL).client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()
}

fun provideApiClient(retrofit: Retrofit): ApiClient = retrofit.create(ApiClient::class.java)

val appViewModels = module {
    viewModel { RecipeViewModel(get()) }
    viewModel {ShopViewModel(get())}
    viewModel {RefrigeratorViewModel(get())}
}

