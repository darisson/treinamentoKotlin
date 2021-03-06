package com.example.darisson.treinamentokotlin.modules.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseNetwork {

    companion object {
        const val BASE_URL = "https://api-agenda-unifor.herokuapp.com/"
    }

    fun getRetrofit(url: String = BASE_URL): Retrofit {

        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}