package com.ForSomeoneSpeical.app5.app_sketch.data.di

import com.ForSomeoneSpeical.app5.app_sketch.data.repo.AuthRepositoryIml
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.AuthReporistory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepoModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepo(
        iml : AuthRepositoryIml
    ) : AuthReporistory
}