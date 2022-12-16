package com.example.marvelproject.domain.interfaces

import com.example.marvelproject.domain.model.CharacterDomain
import com.example.marvelproject.utils.Resource

interface IApiRepository {
    suspend fun getCharacters(): Resource<List<CharacterDomain>>
}