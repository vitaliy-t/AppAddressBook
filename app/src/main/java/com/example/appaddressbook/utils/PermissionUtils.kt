package com.example.appaddressbook.utils

import android.Manifest
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.markodevcic.peko.Peko
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.launch

fun Fragment.requestWriteExternalStoragePermission(listener: (it: Boolean) -> Unit) {
    lifecycleScope.launch {
        if (Build.VERSION.SDK_INT >= 33) {
            listener(true)
        } else {
            val result = Peko.requestPermissionsAsync(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            listener(result is PermissionResult.Granted)
        }
    }
}