package com.example.appaddressbook.di

import com.example.appaddressbook.repository.ContactsRepository
import com.example.appaddressbook.repository.ContactsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContactsRepository(): ContactsRepository {
        return ContactsRepositoryImpl()
    }
}