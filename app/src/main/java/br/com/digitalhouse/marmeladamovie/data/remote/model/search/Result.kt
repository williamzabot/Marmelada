package br.com.digitalhouse.marmeladamovie.data.remote.model.search

data class Result(
    val adult: Boolean,
    val backdrop_path: Any,
    val gender: Int,
    val id: Int,
    val media_type: String,
    val name: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val profile_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Int,
    val vote_count: Int
)