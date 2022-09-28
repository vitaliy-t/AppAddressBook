package com.example.appaddressbook.di

import com.example.appaddressbook.contacts_loader.ContactsExporter
import com.example.appaddressbook.contacts_loader.ContactsLoader
import com.example.appaddressbook.contacts_loader.AddressBookXmlMapper
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

    @Provides
    @Singleton
    fun provideXmlMapper(): AddressBookXmlMapper {
        return AddressBookXmlMapper()
    }

    @Provides
    @Singleton
    fun provideContactLoader(xmlMapper: AddressBookXmlMapper): ContactsLoader {
        return ContactsLoader(xmlMapper)
    }

    @Provides
    @Singleton
    fun provideContactExporter(xmlMapper: AddressBookXmlMapper): ContactsExporter {
        return ContactsExporter(xmlMapper)
    }
}