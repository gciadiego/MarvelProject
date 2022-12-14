package com.example.marvelproject.data.mapper

import com.example.marvelproject.data.dto.ItemDto
import com.example.marvelproject.data.dto.ResultDto
import com.example.marvelproject.data.dto.UrlDto
import com.example.marvelproject.domain.model.CharacterDomain
import com.example.marvelproject.domain.model.SeriesAndComicsDomain

fun ResultDto.toDomain(): CharacterDomain = CharacterDomain(
    this.name,
    if (this.description.length >= 100) this.description.take(100)+"..." else this.description,
    replaceHttp(this.thumbnail.path) + "." + this.thumbnail.extension,
    this.series.items.map { itemDto -> itemDto.toDomain() },
    this.comics.items.map { itemDto -> itemDto.toDomain() },
    findUrl(this.urls))

fun ItemDto.toDomain(): SeriesAndComicsDomain = SeriesAndComicsDomain(
    this.name
)

fun replaceHttp(s: String): String{
    return s.replace("http", "https")
}

fun findUrl(urls: List<UrlDto>): String {
    var result = ""

    for (url in urls){
        if(url.type == "detail"){
            result = replaceHttp(url.url)
        }
    }

    return result
}