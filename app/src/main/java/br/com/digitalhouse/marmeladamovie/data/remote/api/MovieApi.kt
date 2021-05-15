package br.com.digitalhouse.marmeladamovie.data.remote.api

import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.MovieResults
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.streaming.Streaming
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular/")
    suspend fun getPopularMovies(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): Response<MovieResults>

    @GET("search/movie/")
    suspend fun searchMovie(
        @Header("Authorization") token: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): Response<MovieResults>

    @GET("movie/{id}/watch/providers")
    suspend fun getStreaming(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Streaming>
}

