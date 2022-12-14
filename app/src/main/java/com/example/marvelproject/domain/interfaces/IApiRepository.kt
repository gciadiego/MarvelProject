package com.example.marvelproject.domain.interfaces

import com.example.marvelproject.domain.model.CharacterDomain

interface IApiRepository {
    suspend fun getCharacters(): List<CharacterDomain>
}