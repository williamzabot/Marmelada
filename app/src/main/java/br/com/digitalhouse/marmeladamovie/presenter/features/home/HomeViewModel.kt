package br.com.digitalhouse.marmeladamovie.presenter.features.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import br.com.digitalhouse.marmeladamovie.domain.usecases.MovieUseCase

class HomeViewModel @ViewModelInject constructor(private val movieUseCase: MovieUseCase):
    ViewModel() {

    val movies = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(movieUseCase) }
    ).liveData.cachedIn(viewModelScope)
}