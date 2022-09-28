package com.example.appaddressbook.ui.custom_views

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.doAfterTextChanged
import com.example.appaddressbook.databinding.ViewSearchBinding
import com.example.appaddressbook.utils.clear
import com.example.appaddressbook.utils.onClick
import com.example.appaddressbook.utils.setVisibleOrGone
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

const val DEBOUNCE_INTERVAL: Long = 300

class SearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewSearchBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private var onSearchQueryChanged: ((String?) -> Unit)? = null

    var currentSearchQuery: String? = null
        private set

    init {
        binding.input.doAfterTextChanged(::doAfterTextChanged)
        binding.clear.onClick { binding.input.clear() }
        setSearchListener()
    }

    private fun setSearchListener() {
        binding.input.afterTextChangeEvents()
            .skipInitialValue()
            .debounce(DEBOUNCE_INTERVAL, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onSearchQueryChanged() }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        binding.input.isEnabled = enabled
        binding.clear.isEnabled = enabled
    }

    fun clear() {
        binding.input.clear()
    }

    fun onSearchQueryChanged(listener: (String?) -> Unit) {
        onSearchQueryChanged = listener
    }

    val isNotEmpty
        get () = !binding.input.text.isNullOrEmpty()

    val isEmpty
        get () = binding.input.text.isNullOrEmpty()

    private fun onSearchQueryChanged() {
        val newSearchQuery = binding.input.text?.toString()?.takeIf { it.isNotEmpty() }
        if (newSearchQuery != currentSearchQuery) {
            currentSearchQuery = newSearchQuery
            onSearchQueryChanged?.invoke(newSearchQuery)
        }
    }

    private fun doAfterTextChanged(editable: Editable?) {
        binding.clear.setVisibleOrGone(!editable.isNullOrEmpty())
    }

}