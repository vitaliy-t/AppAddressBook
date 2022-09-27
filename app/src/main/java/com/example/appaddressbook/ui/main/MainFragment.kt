package com.example.appaddressbook.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
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
        binding?.openContacts?.setOnClickListener { viewModel.loadContacts() }
    }

    private fun subscribe() {
        viewModel.getContacts().observe(viewLifecycleOwner) { }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentMainBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = MainFragment()
    }
}