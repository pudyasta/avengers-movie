package com.example.uts.api.network.movies

import com.example.uts.model.Movies
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("movies")
    fun getAllMovies(): Call<Movies>
}