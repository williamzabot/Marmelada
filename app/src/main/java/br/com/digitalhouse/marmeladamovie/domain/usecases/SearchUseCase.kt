package br.com.digitalhouse.marmeladamovie.domain.usecases

import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.Movie
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.MovieResults
import br.com.digitalhouse.marmeladamovie.data.remote.model.search.SearchResult
import br.com.digitalhouse.marmeladamovie.domain.repositories.MovieRepository
import br.com.digitalhouse.marmeladamovie.domain.utils.Result
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    BaseUseCase<List<Movie>, SearchUseCase.Params>() {
    data class Params(val query: String)

    override suspend fun execute(params: Params): Result<List<Movie>> {
        return when (val result = movieRepository.searchMovies(params.query)) {
            is Result.Success -> Result.Success(result.data.movies.filter { it.media_type != "person" })
            is Result.Failure -> Result.Failure(result.exception)
        }
    }
}