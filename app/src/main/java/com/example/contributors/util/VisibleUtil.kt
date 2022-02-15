package com.example.contributors.util

import android.view.View
import androidx.databinding.BindingAdapter

object VisibleUtil {
    @JvmStatic
    @BindingAdapter("visible")
    fun loadImage(view: View?, str: String?) {
        if (str == null) {
            view?.visibility = View.GONE
            return
        }
        if (str == "") {
            view?.visibility = View.GONE
            return
        }
        view?.visibility = View.VISIBLE
    }
}
