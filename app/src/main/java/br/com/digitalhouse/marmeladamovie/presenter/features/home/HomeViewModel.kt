package br.com.digitalhouse.marmeladamovie.presenter.features.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.marmeladamovie.data.remote.model.Movie
import br.com.digitalhouse.marmeladamovie.domain.usecases.MovieUseCase
import br.com.digitalhouse.marmeladamovie.domain.utils.Result
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val movieUseCase: MovieUseCase):
    ViewModel() {
        private val _movies = MutableLiveData<List<Movie>>()
        val movies: LiveData<List<Movie>> = _movies

    fun getMovies(page: Int){
        viewModelScope.launch {
            when(val result = movieUseCase.execute(MovieUseCase.Params(page))){
                is Result.Success -> _movies.postValue(result.data.movies)
            }
        }
    }
}