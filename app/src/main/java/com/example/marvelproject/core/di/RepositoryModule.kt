package com.example.marvelproject.core.di

import com.example.marvelproject.data.implementation.MarvelApiRepository
import com.example.marvelproject.domain.interfaces.IApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Singleton
    @Binds
    abstract fun provideApiMarvelRepository(implementation: MarvelApiRepository): IApiRepository
}