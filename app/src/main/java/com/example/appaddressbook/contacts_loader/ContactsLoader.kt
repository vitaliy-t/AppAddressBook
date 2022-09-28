package com.example.appaddressbook.contacts_loader

import android.content.Context
import android.net.Uri
import com.example.appaddressbook.data.models.Contact
import javax.inject.Inject

class ContactsLoader @Inject constructor (
    private val xmlMapper: AddressBookXmlMapper,
    private val jsonMapper: AddressBookJsonMapper,
) {

    fun loadContactsFromXml(context: Context, uri: Uri): List<Contact>? {
        val addressBook = try {
            context.contentResolver.openInputStream(uri)?.let {
                xmlMapper.mapToAddressBook(it)
            }
        } catch (th: Throwable) {
            null
        }
        return addressBook?.contacts
    }

    fun loadContactsFromJson(context: Context, uri: Uri): List<Contact>? {
        val addressBook = try {
            context.contentResolver.openInputStream(uri)?.let {
                jsonMapper.mapToAddressBook(it)
            }
        } catch (th: Throwable) {
            null
        }
        return addressBook?.contacts
    }

}