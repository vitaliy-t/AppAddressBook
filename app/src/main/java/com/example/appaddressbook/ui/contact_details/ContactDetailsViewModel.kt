package com.example.appaddressbook.ui.contact_details

import androidx.lifecycle.ViewModel
import com.example.appaddressbook.R
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.repository.ContactsRepository
import com.example.appaddressbook.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    val contactSetupEvent = SingleLiveEvent<Contact>()
    val errorEvent = SingleLiveEvent<Int>()

    fun init(customerId: String) {
        contactsRepository.getContactById(customerId)?.let { contact ->
            contactSetupEvent.postValue(contact)
        } ?: errorEvent.postValue(R.string.error_contact_load)
    }
}