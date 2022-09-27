package com.example.appaddressbook.ui.main

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appaddressbook.R
import com.example.appaddressbook.contacts_loader.ContactsExporter
import com.example.appaddressbook.contacts_loader.ContactsLoader
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val contactsLoader: ContactsLoader,
    private val contactsExporter: ContactsExporter,
) : ViewModel() {

    val toastLiveEvent = SingleLiveEvent<Int>()

    fun getContacts() = contactsRepository.getContacts()

    fun onContactDelete(contact: Contact) {
        contactsRepository.deleteContact(contact)
    }

    fun loadContacts(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val contacts = contactsLoader.loadContacts(context, uri)
            withContext(Dispatchers.Main) {
                contactsRepository.setContacts(contacts)
            }
        }
    }

    fun exportContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            contactsRepository.getContacts().value?.let { contacts ->
                contactsExporter.exportContacts(contacts)
                toastLiveEvent.postValue(R.string.exported_message)
            }
        }
    }
}