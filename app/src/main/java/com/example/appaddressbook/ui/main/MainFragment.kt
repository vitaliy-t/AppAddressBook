package com.example.appaddressbook.ui.main

import android.content.res.Configuration
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appaddressbook.base.BaseBindingFragment
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.databinding.FragmentMainBinding
import com.example.appaddressbook.ui.main.adapter.ContactsAdapter
import com.example.appaddressbook.utils.*
import dagger.hilt.android.AndroidEntryPoint

private const val SPAN_COLUMN_FOR_LANDSCAPE = 2

@AndroidEntryPoint
class MainFragment : BaseBindingFragment<FragmentMainBinding, MainViewModel>() {

    override val viewModel by viewModels<MainViewModel>()
    private val adapter by lazy {
        ContactsAdapter(
            onItemClick = { openContactDetails(it) },
            onDeleteItemClick = { viewModel.onContactDelete(it) },
            onEditItemClick = ::navigateToEditContact
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
            GridLayoutManager(requireContext(), SPAN_COLUMN_FOR_LANDSCAPE)
        }
    }

    private fun setupListeners() = withBinding {
        rvContacts.adapter = adapter
        ivImportContactsXml.onClick(::pickContactsXmlFile)
        ivImportContactsJson.onClick(::pickContactsJsonFile)
        ivExportContactsXml.onClick(::onExportIntoXmlClick)
        ivExportContactsJson.onClick(::onExportIntoJsonClick)
        fabAddContact.onClick(::navigateToAddingNewContact)
        searchInput.onSearchQueryChanged(viewModel::setSearchQuery)
    }

    private fun subscribe() {
        viewModel.getContacts().observe(viewLifecycleOwner) { displayContacts(it) }
        viewModel.toastLiveEvent.observe(viewLifecycleOwner) { showToast(it) }
    }

    private fun displayContacts(contacts: List<Contact>) = withBinding {
        adapter.setData(contacts)
        ivExportContactsXml.setVisibleOrGone(contacts.isNotEmpty() || searchInput.isNotEmpty)
        ivExportContactsJson.setVisibleOrGone(contacts.isNotEmpty() || searchInput.isNotEmpty)
        searchInput.setVisibleOrGone(contacts.isNotEmpty() || searchInput.isNotEmpty)
        rvContacts.setVisibleOrGone(contacts.isNotEmpty())
        clNoContacts.setVisibleOrGone(contacts.isEmpty())
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentMainBinding.inflate(inflater, container, false)

    private fun onExportIntoXmlClick() {
        requestWriteExternalStoragePermission {
            if (it) { viewModel.exportContactsToXml() }
        }
    }

    private fun onExportIntoJsonClick() {
        requestWriteExternalStoragePermission {
            if (it) { viewModel.exportContactsToJson() }
        }
    }

    private fun pickContactsXmlFile() {
        xmlFileResultLauncher.launch(getXmlFilePickerIntent())
    }

    private fun pickContactsJsonFile() {
        jsonFileResultLauncher.launch(getJsonFilePickerIntent())
    }

    private fun onXmlFilePicked(uri: Uri) {
        binding?.searchInput?.clear()
        viewModel.loadContactsFromXml(requireContext(), uri)
    }

    private fun onJsonFilePicked(uri: Uri) {
        binding?.searchInput?.clear()
        viewModel.loadContactsFromJson(requireContext(), uri)
    }

    private val xmlFileResultLauncher = registererFilePicker(::onXmlFilePicked)
    private val jsonFileResultLauncher = registererFilePicker(::onJsonFilePicked)

    private fun openContactDetails(customerId: String) {
        findNavController().navigate(MainFragmentDirections.openContactDetails(customerId))
    }

    private fun navigateToAddingNewContact() {
        findNavController().navigate(MainFragmentDirections.toEditContact(null) )
    }

    private fun navigateToEditContact(customerId: String) {
        findNavController().navigate(MainFragmentDirections.toEditContact(customerId))
    }
}