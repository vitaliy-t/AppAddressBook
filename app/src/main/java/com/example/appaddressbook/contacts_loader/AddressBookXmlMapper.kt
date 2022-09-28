package com.example.appaddressbook.contacts_loader

import com.ctc.wstx.stax.WstxInputFactory
import com.ctc.wstx.stax.WstxOutputFactory
import com.example.appaddressbook.data.models.AddressBook
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlFactory
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.io.InputStream

class AddressBookXmlMapper {

    private val xmlFactory = XmlFactory.builder()
        .xmlInputFactory(WstxInputFactory())
        .xmlOutputFactory(WstxOutputFactory())
        .build()

    private val kotlinXmlMapper = XmlMapper(xmlFactory, JacksonXmlModule().apply {
        setDefaultUseWrapper(false)
    }).registerKotlinModule()

    fun mapToXml(addressBook: AddressBook, file: File) {
        kotlinXmlMapper.writeValue(file, addressBook)
    }

    fun mapToAddressBook(inputStream: InputStream): AddressBook {
        return kotlinXmlMapper.readValue(inputStream, AddressBook::class.java)
    }


}