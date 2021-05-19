package com.rodrigotguerra.newsapp.util


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rodrigotguerra.newsapp.R


//fun getProgressDrawable(context: Context) : {
//    return CircularProgressIndicator(context)
//}

fun ImageView.loadImage(uri: String?) {
    val options =
        RequestOptions().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_error)
    Glide.with(context).setDefaultRequestOptions(options).load(uri).into(this)
}

@BindingAdapter("android:image_url")
fun loadImageAdapter(view: ImageView, url: String?) {
    view.loadImage(url)
}