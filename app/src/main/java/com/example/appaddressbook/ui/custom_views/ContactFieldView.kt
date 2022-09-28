package com.example.appaddressbook.ui.custom_views

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.appaddressbook.R
import com.example.appaddressbook.databinding.LayoutContactFieldViewBinding

class ContactFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val binding = LayoutContactFieldViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        with(binding) {
            root.context.obtainStyledAttributes(attrs, R.styleable.ContactFieldView, 0, 0).apply {
                try {
                    tvFieldTitle.text = (root.context.getString(getResourceId(R.styleable.ContactFieldView_fieldContent, R.string.empty)))
                } finally {
                    recycle()
                }
            }
        }
    }

    fun setData(text: String, action: (() -> Unit)? = null) {
        action?.let {
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(p0: View) {
                    action()
                }
            }
            val spanText = SpannableString(text).apply {
                setSpan(clickableSpan, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.tvField.setText(spanText, TextView.BufferType.SPANNABLE)
            binding.tvField.movementMethod = LinkMovementMethod.getInstance()
        } ?: run {
            binding.tvField.text = text
        }
    }
}
