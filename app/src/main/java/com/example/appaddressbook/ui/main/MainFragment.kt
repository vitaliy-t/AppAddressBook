package com.example.appaddressbook.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.appaddressbook.base.BaseBindingFragment
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.databinding.FragmentMainBinding
import com.example.appaddressbook.ui.main.adapter.ContactsAdapter
import com.example.appaddressbook.utils.setVisibleOrGone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseBindingFragment<FragmentMainBinding, MainViewModel>() {

    override val viewModel by viewModels<MainViewModel>()
    private val adapter by lazy {
        ContactsAdapter(
            onItemClick = {},
            onDeleteItemClick = { viewModel.onContactDelete(it) },
            onEditItemClick = { }
        )
    }

    override fun initUI() {
        setupListeners()
        subscribe()
    }

    private fun setupListeners() = withBinding {
        rvContacts.adapter = adapter
        ivImportContacts.setOnClickListener { viewModel.loadContacts() }
    }

    private fun subscribe() {
        viewModel.getContacts().observe(viewLifecycleOwner) { displayContacts(it) }
    }

    private fun displayContacts(contacts: List<Contact>) = withBinding {
        adapter.setData(contacts)
        rvContacts.setVisibleOrGone(contacts.isNotEmpty())
        clNoContacts.setVisibleOrGone(contacts.isEmpty())
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentMainBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = MainFragment()
    }
}