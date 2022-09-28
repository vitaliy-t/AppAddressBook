package com.example.appaddressbook.ui.main

import android.app.Activity
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appaddressbook.base.BaseBindingFragment
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.databinding.FragmentMainBinding
import com.example.appaddressbook.ui.main.adapter.ContactsAdapter
import com.example.appaddressbook.utils.onClick
import com.example.appaddressbook.utils.setVisibleOrGone
import com.example.appaddressbook.utils.showToast
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

    private var isPortraitOrientation = true

    override fun initUI() {
        defineOrientation()
        setupContactList()
        setupListeners()
        subscribe()
    }

    private fun defineOrientation() {
        isPortraitOrientation = requireActivity().resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE
    }

    private fun setupContactList() = withBinding {
        rvContacts.layoutManager = if (isPortraitOrientation) {
            LinearLayoutManager(requireContext())
        } else {
            GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setupListeners() = withBinding {
        rvContacts.adapter = adapter
        ivImportContacts.onClick(::pickContactsFile)
        ivExportContacts.onClick(viewModel::exportContacts)
        searchInput.onSearchQueryChanged(viewModel::setSearchQuery)
    }

    private fun subscribe() {
        viewModel.getContacts().observe(viewLifecycleOwner) { displayContacts(it) }
        viewModel.toastLiveEvent.observe(viewLifecycleOwner) { showToast(it) }
    }

    private fun displayContacts(contacts: List<Contact>) = withBinding {
        adapter.setData(contacts)
        ivExportContacts.setVisibleOrGone(contacts.isNotEmpty())
        searchInput.setVisibleOrGone(contacts.isNotEmpty() || searchInput.isNotEmpty)
        rvContacts.setVisibleOrGone(contacts.isNotEmpty())
        clNoContacts.setVisibleOrGone(contacts.isEmpty())
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
                binding?.searchInput?.clear()
                viewModel.loadContacts(requireContext(), uri)
            }
        }

    companion object {
        fun newInstance() = MainFragment()
    }
}