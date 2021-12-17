package br.com.digitalhouse.marmeladamovie.data.repositories

import br.com.digitalhouse.marmeladamovie.data.local.dao.FavoriteDAO
import br.com.digitalhouse.marmeladamovie.data.local.entity.MovieFavorite
import br.com.digitalhouse.marmeladamovie.domain.repositories.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val favoriteDAO: FavoriteDAO) :
    FavoriteRepository {
    override suspend fun getFavorites(): List<MovieFavorite> {
        return favoriteDAO.getFavorites()
    }

    override suspend fun insert(movie: MovieFavorite): Long {
        return favoriteDAO.insert(movie)
    }

    override suspend fun delete(favorite: MovieFavorite) {
        favoriteDAO.delete(favorite)
    }
}