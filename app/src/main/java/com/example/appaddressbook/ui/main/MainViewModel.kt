package com.example.appaddressbook.ui.main

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appaddressbook.R
import com.example.appaddressbook.contacts_loader.ContactsExporter
import com.example.appaddressbook.contacts_loader.ContactsLoader
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.repository.ContactsRepository
import com.example.appaddressbook.utils.SingleLiveEvent
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

    private val searchQuery = MutableLiveData<String?>()
    private val contacts = contactsRepository.getContacts()
    private val contactsData = MediatorLiveData<List<Contact>>().apply {
        fun update() {
            value = contacts.value?.let { allContacts ->
                val withoutDuplicates = allContacts.distinct()
                searchQuery.value?.let { searchQuery ->
                    withoutDuplicates.filter { filterCondition(it, searchQuery) }
                } ?: withoutDuplicates
            } ?: emptyList()
        }
        addSource(contactsRepository.getContacts()) { update() }
        addSource(searchQuery) { update() }
        update()
    }

    fun getContacts(): LiveData<List<Contact>> = contactsData

    fun onContactDelete(contact: Contact) {
        contactsRepository.deleteContact(contact)
    }

    fun loadContactsFromXml(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val contacts = contactsLoader.loadContactsFromXml(context, uri)
            withContext(Dispatchers.Main) {
                if (contacts != null) {
                    contactsRepository.setContacts(contacts)
                } else {
                    toastLiveEvent.postValue(R.string.parse_xml_error)
                }
            }
        }
    }

    fun loadContactsFromJson(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val contacts = contactsLoader.loadContactsFromJson(context, uri)
            withContext(Dispatchers.Main) {
                if (contacts != null) {
                    contactsRepository.setContacts(contacts)
                } else {
                    toastLiveEvent.postValue(R.string.parse_json_error)
                }
            }
        }
    }

    fun exportContactsToXml() {
        viewModelScope.launch(Dispatchers.IO) {
            contacts.value?.let { contacts ->
                contactsExporter.exportContactsToXml(contacts)
                toastLiveEvent.postValue(R.string.exported_to_xml_message)
            }
        }
    }

    fun exportContactsToJson() {
        viewModelScope.launch(Dispatchers.IO) {
            contacts.value?.let { contacts ->
                contactsExporter.exportContactsToJson(contacts)
                toastLiveEvent.postValue(R.string.exported_to_json_message)
            }
        }
    }

    fun setSearchQuery(searchQuery: String?) {
        this.searchQuery.value = searchQuery
    }

    private fun filterCondition(contact: Contact, searchQuery: String): Boolean {
        return contact.contactName?.contains(searchQuery, ignoreCase = true) == true ||
                contact.companyName?.contains(searchQuery, ignoreCase = true) == true
    }
}