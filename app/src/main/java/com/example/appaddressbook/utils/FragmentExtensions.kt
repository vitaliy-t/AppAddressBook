package com.example.appaddressbook.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showToast(@StringRes strRes: Int) {
    Toast.makeText(requireContext(), strRes, Toast.LENGTH_LONG).show()
}