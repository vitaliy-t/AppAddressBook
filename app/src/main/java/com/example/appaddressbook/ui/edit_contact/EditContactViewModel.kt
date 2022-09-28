package com.example.appaddressbook.ui.edit_contact

import androidx.lifecycle.ViewModel
import com.example.appaddressbook.R
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

    val toastLiveEvent = SingleLiveEvent<Int>()

    val successfulAdding = SingleLiveEvent<Unit>()
    val successfulEdited = SingleLiveEvent<Unit>()

    private var isNewContact = true

    fun loadContact(customerId: String) {
        isNewContact = false
        if (contactToEditEvent.value == null) {
            repository.getContactById(customerId)?.let { contact ->
                contactToEditEvent.postValue(contact)
            }
        }
    }

    fun saveContact(contact: Contact) {
        if (contactFieldsValidation(contact)) {
            if (isNewContact) {
                addNewContact(contact)
            } else {
                editContact(contact)
            }
        } else {
            toastLiveEvent.postValue(R.string.error_message_customerId_required)
        }
    }

    private fun contactFieldsValidation(contact: Contact): Boolean {
        return contact.customerId.isNotBlank() &&
                !contact.contactName.isNullOrBlank()
    }

    private fun addNewContact(contact: Contact) {
        repository.addContact(contact)
        successfulAdding.call()
    }

    private fun editContact(contact: Contact) {
        repository.updateContact(contact)
        successfulEdited.call()
    }
}