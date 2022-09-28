package com.example.appaddressbook.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appaddressbook.data.models.Contact
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor() : ContactsRepository {

    private val contactsLiveData = MutableLiveData<List<Contact>>(mutableListOf())

    override fun setContacts(contacts: List<Contact>) {
        contactsLiveData.value = contacts
    }

    override fun getContacts(): LiveData<List<Contact>> {
        return contactsLiveData
    }

    override fun getContactById(customerId: String): Contact? {
        return contactsLiveData.value?.find { it.customerId == customerId }
    }

    override fun addContact(contact: Contact) {
        val contacts = contactsLiveData.value ?: mutableListOf()
        (contacts as MutableList).add(0, contact)
        contactsLiveData.value = contacts
    }

    override fun updateContact(contact: Contact) {
        val contacts = contactsLiveData.value ?: mutableListOf()
        contacts.find { it.customerId == contact.customerId }?.apply {
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
        contactsLiveData.value = contacts
    }

    override fun deleteContact(contact: Contact) {
        val contacts = contactsLiveData.value ?: mutableListOf()
        (contacts as MutableList).remove(contact)
        contactsLiveData.value = contacts
    }
}