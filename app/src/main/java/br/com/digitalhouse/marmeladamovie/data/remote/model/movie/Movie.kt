package br.com.digitalhouse.marmeladamovie.data.remote.model.movie

import br.com.digitalhouse.marmeladamovie.data.local.entity.MovieFavorite
import java.io.Serializable

data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val id: Int,
    val media_type: String?,
    val name: String?,
    val original_language: String,
    val original_title: String?,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String?,
    val title: String?,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    var favorite: Boolean = false,
    var idFavorite: Long = 0
) : Serializable

fun Movie.toFavorite(userId : String): MovieFavorite {
    return MovieFavorite(
        adult = adult,
        backdrop_path = backdrop_path,
        id = id,
        media_type = media_type,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        name = name,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
        userId = userId
    )

}