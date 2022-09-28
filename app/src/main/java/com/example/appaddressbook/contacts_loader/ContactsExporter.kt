package com.example.appaddressbook.contacts_loader

import android.os.Environment
import com.example.appaddressbook.data.models.AddressBook
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.utils.getFormattedCurrentDate
import java.io.File
import javax.inject.Inject

private const val XML_EXTENSION = "xml"
private const val JSON_EXTENSION = "json"

class ContactsExporter @Inject constructor (
    private val xmlMapper: AddressBookXmlMapper,
    private val jsonMapper: AddressBookJsonMapper,
) {

    fun exportContactsToXml(contact: List<Contact>) {
        xmlMapper.mapToXml(AddressBook(contact), generateXmlFilePathForExport(XML_EXTENSION))
    }

    fun exportContactsToJson(contact: List<Contact>) {
        jsonMapper.mapToJson(AddressBook(contact), generateXmlFilePathForExport(JSON_EXTENSION))
    }

    private fun generateXmlFilePathForExport(extension: String): File {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (dir != null && !dir.exists()) {
            dir.mkdirs()
        }
        return File(dir, "contacts_${getFormattedCurrentDate()}.$extension")
    }

}