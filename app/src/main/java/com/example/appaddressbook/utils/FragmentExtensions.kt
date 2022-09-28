package com.example.appaddressbook.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showToast(@StringRes strRes: Int) {
    Toast.makeText(requireContext(), strRes, Toast.LENGTH_LONG).show()
}

fun Fragment.registererFilePicker(callback: (Uri) -> Unit): ActivityResultLauncher<Intent> {
    return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data ?: return@registerForActivityResult
            callback(uri)
        }
    }
}