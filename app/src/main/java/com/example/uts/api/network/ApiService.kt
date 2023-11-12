package com.example.uts.api.network

import com.example.uts.api.model.Hero
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("data.php")
    fun getAllHeros():Call<Hero>
}