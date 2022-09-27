package com.example.appaddressbook.repository

import com.example.appaddressbook.data.ContactsDataHolder
import com.example.appaddressbook.data.models.Contact
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val contactsDataHolder: ContactsDataHolder
) : ContactsRepository {
    override fun getContacts(): List<Contact> {
        return contactsDataHolder.contacts
    }

    override fun addContact(contact: Contact) {
        contactsDataHolder.contacts.add(contact)
    }

    override fun updateContact(contact: Contact) {
        contactsDataHolder.contacts.find { it.customerId == contact.customerId }?.apply {
            companyName = contact.companyName
            contactName = contact.contactName
            contactTitle = contact.contactTitle
            address = contact.address
            city = contact.city
            email = contact.email
            region = contact.region
            postalCode = contact.postalCode
            country = contact.country
            phone = contact.phone
            fax = contact.fax
        }
    }

    override fun deleteContact(contact: Contact) {
        contactsDataHolder.contacts.remove(contact)
    }
}