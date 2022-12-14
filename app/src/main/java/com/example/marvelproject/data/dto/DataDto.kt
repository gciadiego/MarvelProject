package com.example.marvelproject.data.dto

data class DataDto(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<ResultDto>,
    val total: Int
)