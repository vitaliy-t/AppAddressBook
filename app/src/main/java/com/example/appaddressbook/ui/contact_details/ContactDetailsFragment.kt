package com.example.appaddressbook.ui.contact_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.appaddressbook.base.BaseBindingFragment
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.databinding.FragmentContactDetailsBinding
import com.example.appaddressbook.ui.main.getEmailIntent
import com.example.appaddressbook.ui.main.getPhoneIntent
import com.example.appaddressbook.utils.setVisibleOrGone
import com.example.appaddressbook.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsFragment : BaseBindingFragment<FragmentContactDetailsBinding, ContactDetailsViewModel>() {

    override val viewModel by viewModels<ContactDetailsViewModel>()
    private val args by navArgs<ContactDetailsFragmentArgs>()

    override fun initUI() {
        viewModel.init(args.customerId)
        subscribe()
    }

    private fun subscribe() {
        viewModel.contactSetupEvent.observe(viewLifecycleOwner) { displayContactData(it) }
        viewModel.errorEvent.observe(viewLifecycleOwner) { showToast(it) }
    }

    private fun displayContactData(contact: Contact) = withBinding {
        tvContactName.text = contact.contactName
        contact.companyName?.let { companyName -> cfvCompanyName.setData(companyName) }
        cfvCompanyName.setVisibleOrGone(!contact.companyName.isNullOrBlank())
        contact.contactTitle?.let { contactTitle -> cfvContactTitle.setData(contactTitle) }
        cfvContactTitle.setVisibleOrGone(!contact.contactTitle.isNullOrBlank())
        contact.address?.let { address -> cfvAddress.setData(address) }
        cfvAddress.setVisibleOrGone(!contact.address.isNullOrBlank())
        contact.city?.let { city -> cfvCity.setData(city) }
        cfvCity.setVisibleOrGone(!contact.city.isNullOrBlank())
        contact.email?.let { email -> cfvEmail.setData(email, action = { startActivity(getEmailIntent(email)) }) }
        cfvEmail.setVisibleOrGone(!contact.email.isNullOrBlank())
        contact.region?.let { region -> cfvRegion.setData(region) }
        cfvRegion.setVisibleOrGone(!contact.region.isNullOrBlank())
        contact.postalCode?.let { postalCode -> cfvPostalCode.setData(postalCode) }
        cfvPostalCode.setVisibleOrGone(!contact.postalCode.isNullOrBlank())
        contact.country?.let { country -> cfvCountry.setData(country) }
        cfvCountry.setVisibleOrGone(!contact.country.isNullOrBlank())
        contact.phone?.let { phone -> cfvPhone.setData(phone, action = { startActivity(getPhoneIntent(phone)) }) }
        cfvPhone.setVisibleOrGone(!contact.phone.isNullOrBlank())
        contact.fax?.let { fax -> cfvFax.setData(fax) }
        cfvFax.setVisibleOrGone(!contact.fax.isNullOrBlank())
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentContactDetailsBinding.inflate(inflater, container, false)
}