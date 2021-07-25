package com.example.contributors.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.contributors.R
import com.squareup.picasso.Picasso

object ImageUtil {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView?, url: String?) {
        if (url == null) {
            return
        }
        if (url == "") {
            return
        }
        Picasso.get().load(url).error(R.drawable.ic_baseline_no_photography_24).into(view)
    }
}