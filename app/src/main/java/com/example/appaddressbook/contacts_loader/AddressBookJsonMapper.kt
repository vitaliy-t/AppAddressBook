package com.example.appaddressbook.contacts_loader

import com.example.appaddressbook.data.models.AddressBook
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.io.InputStream

class AddressBookJsonMapper {

    private val mapper = ObjectMapper().registerKotlinModule()

    fun mapToJson(addressBook: AddressBook, file: File) {
        mapper.writeValue(file, addressBook)
    }

    fun mapToAddressBook(inputStream: InputStream): AddressBook {
        return mapper.readValue(inputStream, AddressBook::class.java)
    }


}