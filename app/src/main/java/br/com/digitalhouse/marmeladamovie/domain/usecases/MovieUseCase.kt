package br.com.digitalhouse.marmeladamovie.domain.usecases

import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.MovieResults
import br.com.digitalhouse.marmeladamovie.domain.repositories.MovieRepository
import br.com.digitalhouse.marmeladamovie.domain.utils.Result
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val movieRepository: MovieRepository):
    BaseUseCase<MovieResults, MovieUseCase.Params>() {
        data class Params(val page: Int)

    override suspend fun execute(params: Params): Result<MovieResults> {
        return when(val result = movieRepository.getMovies(params.page)){
            is Result.Success -> Result.Success(result.data)
            is Result.Failure -> Result.Failure(result.exception)
        }
    }
}