package br.com.digitalhouse.marmeladamovie.presenter.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.Movie
import br.com.digitalhouse.marmeladamovie.domain.usecases.SearchUseCase
import br.com.digitalhouse.marmeladamovie.domain.usecases.StreamingUseCase
import br.com.digitalhouse.marmeladamovie.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun search(query: String) {
        viewModelScope.launch {
            when (val result = searchUseCase.execute(SearchUseCase.Params(query))) {
                is Result.Success -> _movies.postValue(result.data)
            }
        }
    }


}