package br.com.digitalhouse.marmeladamovie.data.di

import br.com.digitalhouse.marmeladamovie.data.repositories.FavoriteRepositoryImpl
import br.com.digitalhouse.marmeladamovie.data.repositories.MovieRepositoryImpl
import br.com.digitalhouse.marmeladamovie.domain.repositories.FavoriteRepository
import br.com.digitalhouse.marmeladamovie.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun injectMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Singleton
    @Binds
    abstract fun injectFavoriteRepository(favoriteRepositoryImpl: FavoriteRepositoryImpl): FavoriteRepository

}