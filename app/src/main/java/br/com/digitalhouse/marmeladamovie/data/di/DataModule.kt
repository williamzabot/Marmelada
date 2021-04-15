package br.com.digitalhouse.marmeladamovie.data.di

import br.com.digitalhouse.marmeladamovie.data.repositories.MovieRepositoryImpl
import br.com.digitalhouse.marmeladamovie.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataModule {
    @Singleton
    @Binds
    abstract fun injectMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}