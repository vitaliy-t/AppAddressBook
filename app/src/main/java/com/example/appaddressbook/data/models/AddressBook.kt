package com.example.appaddressbook.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("AddressBook")
data class AddressBook(
    @set:JsonProperty("Contact")
    var contacts: List<Contact> = listOf()
)