package com.example.appaddressbook.ui.edit_contact

import androidx.lifecycle.ViewModel
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.repository.ContactsRepository
import com.example.appaddressbook.utils.SingleLiveEvent
import com.example.appaddressbook.utils.call
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditContactViewModel @Inject constructor(
    private val repository: ContactsRepository
): ViewModel() {

    val contactToEditEvent = SingleLiveEvent<Contact>()

    val successfulAdding = SingleLiveEvent<Unit>()
    val successfulEdited = SingleLiveEvent<Unit>()

    fun loadContact(customerId: String) {
        if (contactToEditEvent.value == null) {
            repository.getContact(customerId)?.let { contact ->
                contactToEditEvent.postValue(contact)
            }
        }
    }

    fun addNewContact(contact: Contact) {
        repository.addContact(contact)
        successfulAdding.call()
    }

    fun editContact(contact: Contact) {
        repository.updateContact(contact)
        successfulEdited.call()
    }
}