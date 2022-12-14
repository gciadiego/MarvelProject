package com.example.marvelproject.data.retrofit

import com.example.marvelproject.data.dto.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IMarvelApi {
    //https://gateway.marvel.com:443/v1/public/characters
    @GET("characters")
    suspend fun getCharacters(): Response<CharactersResponse>
}