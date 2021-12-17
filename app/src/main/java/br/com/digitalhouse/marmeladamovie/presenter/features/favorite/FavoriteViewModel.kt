package br.com.digitalhouse.marmeladamovie.presenter.features.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.marmeladamovie.data.local.entity.MovieFavorite
import br.com.digitalhouse.marmeladamovie.domain.repositories.FavoriteRepository
import br.com.digitalhouse.marmeladamovie.presenter.extensions.toValidId
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteRepository: FavoriteRepository) :
    ViewModel() {
    
    private val auth = FirebaseAuth.getInstance()

    private val _favorites = MutableLiveData<List<MovieFavorite>>()
    val favorites: LiveData<List<MovieFavorite>> = _favorites

    fun getFavorites() {
        viewModelScope.launch {
            _favorites.postValue(favoriteRepository.getFavorites(auth.currentUser.email.toValidId()))
        }
    }
}