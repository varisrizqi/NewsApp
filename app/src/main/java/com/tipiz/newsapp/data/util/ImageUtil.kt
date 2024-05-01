package com.tipiz.newsapp.data.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tipiz.newsapp.R

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, urlString: String?) {
    urlString?.let {
        Glide.with(imageView)
            .load(urlString)
            .apply(
                RequestOptions.placeholderOf(R.drawable.placeholder).error(R.drawable.placeholder)
            )
            .into(imageView)
    }


}