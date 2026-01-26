package com.example.imirly.data.repository

import com.example.imirly.data.model.AnuncioDto
import com.example.imirly.data.remote.RetrofitClient

class AnunciosRemoteRepository {

    suspend fun getAnuncios(): List<AnuncioDto> {
        return RetrofitClient.api.getAnuncios()
    }
}
