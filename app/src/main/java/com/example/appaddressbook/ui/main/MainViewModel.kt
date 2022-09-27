package com.example.appaddressbook.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    private val contactsMutableLD: MutableLiveData<List<Contact>> = MutableLiveData()
    val contactsLiveData: LiveData<List<Contact>> = contactsMutableLD

    fun loadContacts() {
        contactsMutableLD.value = contactsRepository.getContacts()
    }
}