package br.com.digitalhouse.marmeladamovie.data

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.digitalhouse.marmeladamovie.data.local.dao.FavoriteDAO
import br.com.digitalhouse.marmeladamovie.data.local.entity.MovieFavorite

@Database(entities = [MovieFavorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteDAO : FavoriteDAO
}
