package com.example.appaddressbook.ui.main

import androidx.lifecycle.ViewModel
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    fun getContacts() = contactsRepository.getContacts()

    fun loadContacts() {
    }

    fun onContactDelete(contact: Contact) {
        contactsRepository.deleteContact(contact)
    }
}