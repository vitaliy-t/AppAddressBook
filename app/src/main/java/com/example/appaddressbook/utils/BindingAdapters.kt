package com.example.appaddressbook.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

@BindingAdapter("supports_change_animations")
fun RecyclerView.setSupportChangeAnimation(isSupportChangeAnimation: Boolean) {
    (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = isSupportChangeAnimation
}