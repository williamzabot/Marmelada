package br.com.digitalhouse.marmeladamovie.domain.repositories

import br.com.digitalhouse.marmeladamovie.data.remote.model.MovieResults
import br.com.digitalhouse.marmeladamovie.domain.utils.Result

interface MovieRepository {

    suspend fun getMovies(page : Int): Result<MovieResults>

}