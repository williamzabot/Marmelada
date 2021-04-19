package br.com.digitalhouse.marmeladamovie.data.remote.api

import br.com.digitalhouse.marmeladamovie.data.remote.model.MovieResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular/")
    suspend fun getPopularMovies(
        @Header("Authorization") token: String,
        @Query("page") page : Int,
        @Query("language") language : String
    ): Response<MovieResults>

}