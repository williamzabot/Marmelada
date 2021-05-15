package br.com.digitalhouse.marmeladamovie.domain.usecases

import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.streaming.Streaming
import br.com.digitalhouse.marmeladamovie.domain.repositories.MovieRepository
import br.com.digitalhouse.marmeladamovie.domain.utils.Result
import javax.inject.Inject

class StreamingUseCase @Inject constructor(private val movieRepository: MovieRepository):
    BaseUseCase<Streaming, StreamingUseCase.Params>() {
    data class Params(val id: Int)

    override suspend fun execute(params: Params): Result<Streaming> {
        return when(val result = movieRepository.getStreaming(params.id)){
            is Result.Success -> Result.Success(result.data)
            is Result.Failure -> Result.Failure(result.exception)
        }
    }
}