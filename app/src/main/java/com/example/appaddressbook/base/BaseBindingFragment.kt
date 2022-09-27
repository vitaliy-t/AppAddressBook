package com.example.appaddressbook.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseBindingFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    protected var binding: VB? = null

    abstract val viewModel: VM

    abstract fun initUI()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = getInflatedView(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    abstract fun attachBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): VB

    fun withBinding(block: (VB.() -> Unit)) {
        binding?.apply {
            block.invoke(this)
        }
            ?: run {
                error("Accessing binding outside of lifecycle: ${this::class.java.simpleName}")
            }
    }

    protected open fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun getInflatedView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        binding = attachBinding(inflater, container, attachToRoot)
        return binding?.root
            ?: error("Provide binding")
    }
}