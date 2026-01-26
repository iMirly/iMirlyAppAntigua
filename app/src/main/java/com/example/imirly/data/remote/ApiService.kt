package com.example.imirly.data.remote

import com.example.imirly.data.model.AnuncioDto
import retrofit2.http.GET

interface ApiService {

    @GET("anuncios")
    suspend fun getAnuncios(): List<AnuncioDto>
}
