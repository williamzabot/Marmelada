package br.com.digitalhouse.marmeladamovie.data.remote.model.movie.streaming

data class BR(
    val flatrate: List<Flatrate>? = emptyList(),
    val link: String
)