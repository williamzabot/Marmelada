package br.com.digitalhouse.marmeladamovie.data.remote.model.search

import com.google.gson.annotations.SerializedName

data class SearchResult(
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)