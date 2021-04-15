package br.com.digitalhouse.marmeladamovie.domain.usecases

import br.com.digitalhouse.marmeladamovie.domain.utils.Result

abstract class BaseUseCase<T : Any, in Params> {
    abstract suspend fun execute(params: Params): Result<T>
}
