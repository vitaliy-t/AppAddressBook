package com.example.appaddressbook.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.appaddressbook.data.models.Contact
import com.example.appaddressbook.databinding.ItemContactBinding

class ContactsAdapter(
    private val onItemClick: (String) -> Unit,
    private val onDeleteItemClick: (Contact) -> Unit,
    private val onEditItemClick: (String) -> Unit,
) : Adapter<ContactViewHolder>() {

    private val contacts = mutableListOf<Contact>()

    fun setData(newContacts: List<Contact>) {
        val diffCallback = ContactsDiffCallback(contacts, newContacts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        contacts.clear()
        contacts.addAll(newContacts)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClick,
            onDeleteItemClick,
            onEditItemClick
        )
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindData(contacts[position])
    }
}