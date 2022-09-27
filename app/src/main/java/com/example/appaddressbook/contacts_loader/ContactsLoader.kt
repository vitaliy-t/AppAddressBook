package com.example.appaddressbook.contacts_loader

import android.content.Context
import android.net.Uri
import com.ctc.wstx.stax.WstxInputFactory
import com.ctc.wstx.stax.WstxOutputFactory
import com.example.appaddressbook.data.models.AddressBook
import com.example.appaddressbook.data.models.Contact
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlFactory
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

class ContactsLoader {

    private val xmlFactory = XmlFactory.builder()
        .xmlInputFactory(WstxInputFactory())
        .xmlOutputFactory(WstxOutputFactory())
        .build()

    private val kotlinXmlMapper = XmlMapper(xmlFactory, JacksonXmlModule().apply {
        setDefaultUseWrapper(false)
    }).registerKotlinModule()


    fun loadContacts(context: Context, uri: Uri): List<Contact> {
        val addressBook = kotlinXmlMapper.readValue(
            context.contentResolver.openInputStream(uri), AddressBook::class.java
        )
        return addressBook.contacts
    }

}