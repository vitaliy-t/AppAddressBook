package com.example.appaddressbook.repository

import com.example.appaddressbook.data.models.Contact

interface ContactsRepository {

    fun getContacts(): List<Contact>
    fun addContact(contact: Contact)
    fun updateContact(contact: Contact)
    fun deleteContact(contact: Contact)
}