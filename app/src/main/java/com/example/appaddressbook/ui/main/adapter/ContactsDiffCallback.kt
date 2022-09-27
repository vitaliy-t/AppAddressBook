package com.example.appaddressbook.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.appaddressbook.data.models.Contact

class ContactsDiffCallback(private val oldList: List<Contact>, private val newList: List<Contact>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].customerId === newList[newItemPosition].customerId
    }

    override fun areContentsTheSame(oldCourse: Int, newPosition: Int): Boolean {
        val (_, value, name) = oldList[oldCourse]
        val (_, value1, name1) = newList[newPosition]
        return name == name1 && value == value1
    }
}
