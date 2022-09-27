package com.example.appaddressbook.ui.main

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.appaddressbook.base.BaseBindingFragment
import com.example.appaddressbook.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseBindingFragment<FragmentMainBinding, MainViewModel>() {

    override val viewModel by viewModels<MainViewModel>()

    override fun initUI() {
        setupListeners()
        subscribe()
    }

    private fun setupListeners() {
        binding?.openContacts?.setOnClickListener { pickContactsFile() }
    }

    private fun subscribe() {
        viewModel.getContacts().observe(viewLifecycleOwner) { }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentMainBinding.inflate(inflater, container, false)

    private fun pickContactsFile() {
        activityResultLauncher.launch(getFilePickerIntent())
    }

    private var activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data ?: return@registerForActivityResult
                viewModel.loadContacts(requireContext(), uri)
            }
        }

    companion object {
        fun newInstance() = MainFragment()
    }
}