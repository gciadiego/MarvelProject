package com.example.marvelproject.domain.model

data class CharacterDomain(
    val name: String,
    val description: String,
    val img: String,
    val series: List<SeriesAndComicsDomain>,
    val comics: List<SeriesAndComicsDomain>,
    val detail: String
)
