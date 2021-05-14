package br.com.digitalhouse.marmeladamovie.presenter.features.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.marmeladamovie.data.local.entity.MovieFavorite
import br.com.digitalhouse.marmeladamovie.data.remote.model.Movie
import br.com.digitalhouse.marmeladamovie.data.remote.model.toFavorite
import br.com.digitalhouse.marmeladamovie.domain.repositories.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val favoriteRepository: FavoriteRepository) :
    ViewModel() {

    private val _isFavorite = MutableLiveData<MovieFavorite>()
    val isFavorite : LiveData<MovieFavorite> = _isFavorite

    fun favorite(movie: Movie) {
        viewModelScope.launch {
            favoriteRepository.insert(movie.toFavorite())
        }
    }

    fun desfavorite(idFavorite: Long) {
        viewModelScope.launch {
            favoriteRepository.delete(idFavorite)
        }
    }

    fun checkIsFavorite(id : Int){
        viewModelScope.launch {
            for(fav in favoriteRepository.getFavorites()){
                if(fav.id == id){
                    _isFavorite.postValue(fav)
                }
            }
        }
    }

}