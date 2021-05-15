package br.com.digitalhouse.marmeladamovie.presenter.features.home

import androidx.paging.PagingSource
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.Movie
import br.com.digitalhouse.marmeladamovie.domain.usecases.MovieUseCase
import br.com.digitalhouse.marmeladamovie.domain.utils.Result
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource (private val movieUseCase: MovieUseCase) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            when (val result = movieUseCase.execute(MovieUseCase.Params(page))) {
                is Result.Success -> LoadResult.Page(
                    data = result.data.movies,
                    prevKey = null,
                    nextKey = page + 1
                )
                is Result.Failure -> LoadResult.Error(result.exception)
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}