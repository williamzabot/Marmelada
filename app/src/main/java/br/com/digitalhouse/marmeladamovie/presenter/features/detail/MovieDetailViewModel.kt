package br.com.digitalhouse.marmeladamovie.presenter.features.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.marmeladamovie.data.local.entity.MovieFavorite
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.Movie
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.streaming.BR
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.streaming.Flatrate
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.streaming.Streaming
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.toFavorite
import br.com.digitalhouse.marmeladamovie.domain.repositories.FavoriteRepository
import br.com.digitalhouse.marmeladamovie.domain.repositories.MovieRepository
import br.com.digitalhouse.marmeladamovie.domain.usecases.StreamingUseCase
import br.com.digitalhouse.marmeladamovie.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val streamingUseCase: StreamingUseCase,
    private val favoriteRepository: FavoriteRepository
) :
    ViewModel() {

    private val _isFavorite = MutableLiveData<MovieFavorite>()
    val isFavorite: LiveData<MovieFavorite> = _isFavorite

    private val _streaming = MutableLiveData<BR>()
    val streaming: LiveData<BR> = _streaming

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun favorite(movie: Movie) {
        viewModelScope.launch {
            favoriteRepository.insert(movie.toFavorite())
        }.invokeOnCompletion {
            checkIsFavorite(movie.id)
        }
    }

    fun desfavorite(favorite: MovieFavorite) {
        viewModelScope.launch {
            favoriteRepository.delete(favorite)
        }
    }

    fun getStreaming(id: Int) {
        viewModelScope.launch {
            when (val result = streamingUseCase.execute(StreamingUseCase.Params(id))) {
                is Result.Success -> _streaming.postValue(result.data.results.BR)
                is Result.Failure -> _error.postValue(result.exception.message)
            }
        }
    }

    fun checkIsFavorite(id: Int) {
        viewModelScope.launch {
            for (fav in favoriteRepository.getFavorites()) {
                if (fav.id == id) {
                    _isFavorite.postValue(fav)
                }
            }
        }
    }

}