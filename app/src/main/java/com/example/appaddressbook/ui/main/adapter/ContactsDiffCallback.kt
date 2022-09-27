package com.example.appaddressbook.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.appaddressbook.data.models.Contact

class ContactsDiffCallback(private val oldList: List<Contact>, private val newList: List<Contact>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].customerId === newList[newItemPosition].customerId
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val contact = oldList[oldPosition]
        val contact1 = newList[newPosition]
        return contact.customerId == contact1.customerId &&
                contact.companyName == contact1.companyName &&
                contact.contactName == contact1.contactName &&
                contact.contactTitle == contact1.contactTitle &&
                contact.address == contact1.address &&
                contact.city == contact1.city &&
                contact.email == contact1.email &&
                contact.region == contact1.region &&
                contact.postalCode == contact1.postalCode &&
                contact.country == contact1.country &&
                contact.phone == contact1.phone &&
                contact.fax == contact1.fax
    }
}
