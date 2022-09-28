package com.example.appaddressbook.ui.main

import android.content.Intent

fun getXmlFilePickerIntent() = Intent().apply {
    action = Intent.ACTION_GET_CONTENT
    type = "text/xml"
}

fun getJsonFilePickerIntent() = Intent().apply {
    action = Intent.ACTION_GET_CONTENT
    type = "application/json"
}