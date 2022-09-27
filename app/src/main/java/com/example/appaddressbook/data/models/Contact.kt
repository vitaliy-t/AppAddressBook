package com.example.appaddressbook.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("Contact")
class Contact(
    @set:JsonProperty("CustomerID")
    var customerId: String,

    @set:JsonProperty("CompanyName")
    var companyName: String? = null,

    @set:JsonProperty("ContactName")
    var contactName: String? = null,

    @set:JsonProperty("ContactTitle")
    var contactTitle: String? = null,

    @set:JsonProperty("Address")
    var address: String? = null,

    @set:JsonProperty("City")
    var city: String? = null,

    @set:JsonProperty("Email")
    var email: String? = null,

    @set:JsonProperty("Region")
    var region: String? = null,

    @set:JsonProperty("PostalCode")
    var postalCode: String? = null,

    @set:JsonProperty("Country")
    var country: String? = null,

    @set:JsonProperty("Phone")
    var phone: String? = null,

    @set:JsonProperty("Fax")
    var fax: String? = null,
)
