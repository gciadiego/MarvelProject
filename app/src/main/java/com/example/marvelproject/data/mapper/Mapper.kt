package com.example.marvelproject.data.mapper
import com.example.marvelproject.data.dto.ResultDto
import com.example.marvelproject.data.dto.UrlDto
import com.example.marvelproject.domain.model.CharacterDomain

fun ResultDto.toDomain(): CharacterDomain = CharacterDomain(
    name = this.name,
    description =
    if (this.description.length >= 100)
        this.description.take(100)+"..."
    else
        this.description,

    img = replaceHttp("${this.thumbnail.path}.${this.thumbnail.extension}") ,
    series = this.series.items.map { it.name },
    comics = this.comics.items.map { it.name },
    detail = findUrl(this.urls))

/*fun ItemDto.toDomain(): SeriesAndComicsDomain = SeriesAndComicsDomain(
    this.name
)*/

fun replaceHttp(s: String): String {
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