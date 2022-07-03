package com.adyen.android.assignment.utils

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.ui.APODDataAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

@BindingAdapter(value = ["toggle_visibility"])
fun View.toggleVisibility(show: Boolean) {
    updateViewVisibility(show)
}

@BindingAdapter(value = ["load_image"])
fun ImageView.loadImage(uri: String?) {
    uri?.let {
        Glide.with(context)
            .asBitmap()
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }

}

@BindingAdapter("adapter")
fun <T : RecyclerView.ViewHolder>
    RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<T>) {
    this.adapter = adapter
}

@BindingAdapter(value = ["apod_list"])
fun RecyclerView.setAPODList(data: List<AstronomyPicture>?) {
    val items = data.orEmpty()
    if (items.isEmpty()) hide() else {
        (adapter as? APODDataAdapter)?.submitList(items)
        show()
    }
}
