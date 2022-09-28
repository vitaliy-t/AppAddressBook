package com.example.appaddressbook.ui.main

import android.content.Intent
import android.net.Uri

fun getXmlFilePickerIntent() = Intent().apply {
    action = Intent.ACTION_GET_CONTENT
    type = "text/xml"
}

fun getJsonFilePickerIntent() = Intent().apply {
    action = Intent.ACTION_GET_CONTENT
    type = "application/json"
}

fun getEmailIntent(email: String) = Intent().apply {
    action = Intent.ACTION_SENDTO
    data = Uri.parse("mailto:$email")
}

fun getPhoneIntent(phoneNumber: String) = Intent().apply {
    action = Intent.ACTION_DIAL
    data = Uri.parse("tel:$phoneNumber")
}