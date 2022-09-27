package com.example.appaddressbook.ui.main

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appaddressbook.repository.ContactsRepository
import com.example.appaddressbook.contacts_loader.ContactsLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
    private val contactsLoader: ContactsLoader,
) : ViewModel() {

    fun getContacts() = contactsRepository.getContacts()

    fun loadContacts(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val contacts = contactsLoader.loadContacts(context, uri)
            withContext(Dispatchers.Main) {
                contactsRepository.setContacts(contacts)
            }
        }
    }
}