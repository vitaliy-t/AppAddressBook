package com.example.appaddressbook.contacts_loader

import android.os.Environment
import com.example.appaddressbook.data.models.AddressBook
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.utils.getFormattedCurrentDate
import java.io.File
import javax.inject.Inject

class ContactsExporter @Inject constructor (
    private val xmlMapper: AddressBookXmlMapper
) {

    fun exportContacts(contact: List<Contact>) {
        xmlMapper.mapToXml(AddressBook(contact), generateXmlFilePathForExport())
    }

    private fun generateXmlFilePathForExport(): File {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (dir != null && !dir.exists()) {
            dir.mkdirs()
        }
        return File(dir, "contacts_${getFormattedCurrentDate()}.xml")

    }

}