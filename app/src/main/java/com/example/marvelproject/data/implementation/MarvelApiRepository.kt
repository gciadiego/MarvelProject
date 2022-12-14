package com.example.marvelproject.data.implementation

import com.example.marvelproject.data.mapper.toDomain
import com.example.marvelproject.data.retrofit.IMarvelApi
import com.example.marvelproject.domain.interfaces.IApiRepository
import com.example.marvelproject.domain.model.CharacterDomain
import javax.inject.Inject

class MarvelApiRepository @Inject constructor(private val marvelApi: IMarvelApi): IApiRepository {
    override suspend fun getCharacters(): List<CharacterDomain>{
        val response = marvelApi.getCharacters()

        return if(response.isSuccessful){
            val list = response.body()?.data?.results ?: emptyList()

            list.map { character ->
                character.toDomain()
            }
        }else{
            emptyList()
        }
    }
}