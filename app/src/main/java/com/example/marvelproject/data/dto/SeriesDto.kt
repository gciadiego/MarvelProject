package com.example.marvelproject.data.dto

data class SeriesDto(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemDto>,
    val returned: Int
)