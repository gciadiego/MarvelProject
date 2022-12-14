package com.example.marvelproject.data.dto

data class StoriesDto(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXXDto>,
    val returned: Int
)