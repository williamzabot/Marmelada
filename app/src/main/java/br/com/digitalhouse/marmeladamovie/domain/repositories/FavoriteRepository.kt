package br.com.digitalhouse.marmeladamovie.domain.repositories

import br.com.digitalhouse.marmeladamovie.data.local.entity.MovieFavorite

interface FavoriteRepository {
    suspend fun getFavorites(): List<MovieFavorite>

    suspend fun insert(movie: MovieFavorite): Long

    suspend fun delete(favorite: MovieFavorite)
}