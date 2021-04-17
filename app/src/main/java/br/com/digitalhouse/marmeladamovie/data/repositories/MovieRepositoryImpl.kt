package br.com.digitalhouse.marmeladamovie.data.repositories

import br.com.digitalhouse.marmeladamovie.data.di.RetrofitModule
import br.com.digitalhouse.marmeladamovie.data.remote.model.MovieResults
import br.com.digitalhouse.marmeladamovie.domain.repositories.MovieRepository
import br.com.digitalhouse.marmeladamovie.domain.utils.Result
import br.com.digitalhouse.marmeladamovie.domain.utils.Result.*
import br.com.digitalhouse.marmeladamovie.domain.utils.TOKEN
import java.lang.Exception
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(): MovieRepository {

    private val movieApi = RetrofitModule.getMovieApi()

    override suspend fun getMovies(page: Int): Result<MovieResults> {
        val response = movieApi.getPopularMovies(TOKEN, page)
        return when(response.code()){
            200 -> Success(response.body()!!)
            else -> Failure(Exception())
        }
    }
}
