package br.com.digitalhouse.marmeladamovie.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.digitalhouse.marmeladamovie.data.local.entity.MovieFavorite

@Dao
interface FavoriteDAO {

    @Insert
    suspend fun insert(movie: MovieFavorite): Long

    @Delete
    suspend fun delete(movie: MovieFavorite)

    @Query("SELECT * FROM favorites")
    suspend fun getFavorites(): List<MovieFavorite>

}