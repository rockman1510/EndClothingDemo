package com.endclothingdemo.di.module

import com.endclothingdemo.repository.ProductRepository
import com.endclothingdemo.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

}