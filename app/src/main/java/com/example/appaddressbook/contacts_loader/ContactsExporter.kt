package com.example.appaddressbook.contacts_loader

import android.os.Environment
import com.ctc.wstx.stax.WstxInputFactory
import com.ctc.wstx.stax.WstxOutputFactory
import com.example.appaddressbook.data.models.AddressBook
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.utils.getFormattedCurrentDate
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlFactory
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

class ContactsExporter {

    private val xmlFactory = XmlFactory.builder()
        .xmlInputFactory(WstxInputFactory())
        .xmlOutputFactory(WstxOutputFactory())
        .build()

    private val kotlinXmlMapper = XmlMapper(xmlFactory, JacksonXmlModule().apply {
        setDefaultUseWrapper(false)
    }).registerKotlinModule()

    fun exportContacts(contact: List<Contact>) {
        kotlinXmlMapper.writeValue(generateFilePathForExport(), AddressBook(contact))
    }

    private fun generateFilePathForExport(): File {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (dir != null && !dir.exists()) {
            dir.mkdirs()
        }
        return File(dir, "contacts_${getFormattedCurrentDate()}.xml")

    }

}