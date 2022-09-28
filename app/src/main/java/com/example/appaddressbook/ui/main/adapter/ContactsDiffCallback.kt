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
        return oldList[oldPosition] == newList[newPosition]
    }
}
