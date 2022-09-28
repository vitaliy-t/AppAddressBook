package com.example.appaddressbook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.appaddressbook.databinding.ActivityMainBinding
import com.example.appaddressbook.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        intent?.let(::handleIntent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val addNewContact = intent.extras?.getBoolean(ADD_CONTACT_SHORTCUT, false) ?: false
        if (addNewContact) {
            binding?.root?.post {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(MainFragmentDirections.toEditContact(null) )
            }
        }
    }
}