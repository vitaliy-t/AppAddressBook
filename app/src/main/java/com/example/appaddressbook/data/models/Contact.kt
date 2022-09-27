package com.example.appaddressbook.data.models

data class Contact(
    val customerId: String,
    val companyName: String,
    val contactName: String,
    val contactTitle: String,
    val address: String,
    val city: String,
    val email: String,
    val region: String?,
    val postalCode: String,
    val country: String,
    val phone: String,
    val fax: String?,
)
