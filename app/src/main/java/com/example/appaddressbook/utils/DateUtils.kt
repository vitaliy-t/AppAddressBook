package com.example.appaddressbook.utils

import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT: SimpleDateFormat
    get() = SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.getDefault())

fun getFormattedCurrentDate(): String {
    return DATE_FORMAT.format(Date())
}