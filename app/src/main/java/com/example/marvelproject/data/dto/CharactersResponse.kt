package com.example.marvelproject.data.dto

data class CharactersResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: DataDto,
    val etag: String,
    val status: String
)