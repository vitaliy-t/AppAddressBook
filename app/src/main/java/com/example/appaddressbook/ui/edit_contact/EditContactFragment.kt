package com.example.appaddressbook.ui.edit_contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appaddressbook.R
import com.example.appaddressbook.base.BaseBindingFragment
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.databinding.FragmentEditContactBinding
import com.example.appaddressbook.utils.onClick
import com.example.appaddressbook.utils.setVisibleOrGone
import com.example.appaddressbook.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditContactFragment : BaseBindingFragment<FragmentEditContactBinding, EditContactViewModel>() {

    override val viewModel by viewModels<EditContactViewModel>()

    private val args by navArgs<EditContactFragmentArgs>()

    private val isNewContact: Boolean
        get() = args.customerId == null

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentEditContactBinding.inflate(inflater, container, false)

    override fun initUI() {
        args.customerId?.let(viewModel::loadContact)
        setupCustomerIdVisibility()
        setupTitle()
        subscribe()
        setupListeners()
    }

    private fun setupCustomerIdVisibility() = withBinding {
        customerIdTitle.setVisibleOrGone(isNewContact)
        customerIdInput.setVisibleOrGone(isNewContact)
    }

    private fun setupTitle() = withBinding {
        tvLabel.setText(if (isNewContact) {
            R.string.new_contact_title
        } else {
            R.string.edit_contact_title
        })
    }

    private fun setupListeners() = withBinding {
        ivSave.onClick(::onSaveClick)
    }

    private fun subscribe() {
        viewModel.contactToEditEvent.observe(viewLifecycleOwner, ::displayContactInfo)
        viewModel.toastLiveEvent.observe(viewLifecycleOwner) { showToast(it) }
        viewModel.successfulAdding.observe(viewLifecycleOwner) { onContactAdded() }
        viewModel.successfulEdited.observe(viewLifecycleOwner) { onContactEdited() }
    }

    private fun displayContactInfo(contact: Contact) {
        withBinding {
            companyNameInput.setText(contact.companyName)
            contactNameInput.setText(contact.contactName)
            contactTitleInput.setText(contact.contactTitle)
            contactAddressInput.setText(contact.address)
            contactCityInput.setText(contact.city)
            contactEmailInput.setText(contact.email)
            contactRegionInput.setText(contact.region)
            contactPostalCodeInput.setText(contact.postalCode)
            contactCountryInput.setText(contact.country)
            contactPhoneInput.setText(contact.phone)
            contactFaxInput.setText(contact.fax)
        }
    }

    private fun onSaveClick() {
        viewModel.saveContact(makeContact())
    }

    private fun makeContact() = Contact(
        customerId = if (isNewContact) binding?.customerIdInput?.text?.toString() ?: "" else args.customerId ?: "",
        companyName = binding?.companyNameInput?.text?.toString(),
        contactName = binding?.contactNameInput?.text?.toString(),
        contactTitle = binding?.contactTitleInput?.text?.toString(),
        address = binding?.contactAddressInput?.text?.toString(),
        city = binding?.contactCityInput?.text?.toString(),
        email = binding?.contactEmailInput?.text?.toString(),
        region = binding?.contactRegionInput?.text?.toString(),
        postalCode = binding?.contactPostalCodeInput?.text?.toString(),
        country = binding?.contactCountryInput?.text?.toString(),
        phone = binding?.contactPhoneInput?.text?.toString(),
        fax = binding?.contactFaxInput?.text?.toString(),
    )

    private fun onContactAdded() {
        showToast(R.string.contact_added_message)
        findNavController().popBackStack()
    }

    private fun onContactEdited() {
        showToast(R.string.contact_edited_message)
        findNavController().popBackStack()
    }

}