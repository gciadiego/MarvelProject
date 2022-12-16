package com.example.marvelproject.data.implementation

import com.example.marvelproject.data.mapper.toDomain
import com.example.marvelproject.data.retrofit.IMarvelApi
import com.example.marvelproject.domain.interfaces.IApiRepository
import com.example.marvelproject.domain.model.CharacterDomain
import com.example.marvelproject.utils.Resource
import javax.inject.Inject

class MarvelApiRepositoryImpl @Inject constructor(private val marvelApi: IMarvelApi): IApiRepository {
    override suspend fun getCharacters(): Resource<List<CharacterDomain>>{
        return try {
            val response = marvelApi.getCharacters()
            if(response.isSuccessful){
                val list = response.body()?.data?.results?.map { it.toDomain() } ?: emptyList()

                Resource.Success(list)
            }else{
                Resource.Failure("Error, something went wrong.")
            }
        }catch (ex :Exception){
            Resource.Failure("Error, something went wrong.")
        }

    }
}