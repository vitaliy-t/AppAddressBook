package com.example.appaddressbook.repository

import androidx.lifecycle.LiveData
import com.example.appaddressbook.data.models.Contact

interface ContactsRepository {

    fun setContacts(contacts: List<Contact>)
    fun getContacts(): LiveData<List<Contact>>
    fun addContact(contact: Contact)
    fun updateContact(contact: Contact)
    fun deleteContact(contact: Contact)
}