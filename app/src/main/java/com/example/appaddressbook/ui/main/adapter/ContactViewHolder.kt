package com.example.appaddressbook.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.databinding.ItemContactBinding
import com.example.appaddressbook.utils.onClick

class ContactViewHolder(
    private val itemPasswordBinding: ItemContactBinding,
    private val onItemClick: (String) -> Unit,
    private val onDeleteItemClick: (Contact) -> Unit,
    private val onEditItemClick: (String) -> Unit,
) : ViewHolder(itemPasswordBinding.root) {

    fun bindData(item: Contact?) {
        itemPasswordBinding.apply {
            item?.let { contact ->
                tvContactName.text = contact.contactName
                tvCompanyName.text = contact.companyName
                ivDelete.onClick { onDeleteItemClick(contact) }
                root.onClick { onItemClick(contact.customerId) }
                ivEdit.onClick { onEditItemClick(contact.customerId) }
            }
        }
    }
}