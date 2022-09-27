package com.example.appaddressbook.data.models

data class Contact(
    val customerId: String,
    var companyName: String,
    var contactName: String,
    var contactTitle: String,
    var address: String,
    var city: String,
    var email: String,
    var region: String?,
    var postalCode: String,
    var country: String,
    var phone: String,
    var fax: String?,
)
