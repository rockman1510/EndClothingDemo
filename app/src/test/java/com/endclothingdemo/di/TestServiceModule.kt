package com.endclothingdemo.di

import com.endclothingdemo.di.module.ServiceModule
import com.endclothingdemo.repository.ProductRepository
import com.endclothingdemo.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ServiceModule::class]
)
abstract class TestServiceModule {

    @Binds
    @Singleton
    abstract fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

}