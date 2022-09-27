package com.example.appaddressbook.ui.main

import android.content.Intent

fun getFilePickerIntent() = Intent().apply {
    action = Intent.ACTION_GET_CONTENT
    type = "text/xml"
}