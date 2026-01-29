package com.ForSomeoneSpeical.app5.app_sketch.data.di

import com.ForSomeoneSpeical.app5.app_sketch.data.repo.UserLogRepositoryIml
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.UserLogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserLogModule {
    @Binds
    @Singleton
    abstract fun bindUserLogRepository(
        iml : UserLogRepositoryIml
    ) : UserLogRepository
}