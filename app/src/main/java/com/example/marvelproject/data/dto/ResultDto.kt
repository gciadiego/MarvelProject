package com.example.marvelproject.data.dto

data class ResultDto(
    val comics: ComicsDto,
    val description: String,
    val events: EventsDto,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: SeriesDto,
    val stories: StoriesDto,
    val thumbnail: ThumbnailDto,
    val urls: List<UrlDto>
)