package br.com.digitalhouse.marmeladamovie.domain.repositories

import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.MovieResults
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.streaming.Streaming
import br.com.digitalhouse.marmeladamovie.data.remote.model.search.SearchResult
import br.com.digitalhouse.marmeladamovie.domain.utils.Result

interface MovieRepository {

    suspend fun getMovies(page: Int): Result<MovieResults>

    suspend fun searchMovies(query: String): Result<MovieResults>

    suspend fun getStreaming(id: Int) : Result<Streaming>

}