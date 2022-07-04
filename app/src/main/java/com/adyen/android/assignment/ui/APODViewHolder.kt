package com.adyen.android.assignment.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.data.network.AstronomyPicture
import com.adyen.android.assignment.databinding.ItemApodBinding
import com.adyen.android.assignment.databinding.ItemTitleBinding

sealed class APODViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: AstronomyPicture)

    class TitleViewHolder(val binding: ItemTitleBinding) : APODViewHolder(binding.root) {

        override fun bind(item: AstronomyPicture) {
            binding.data = item
        }
    }

    class DataViewHolder(val binding: ItemApodBinding) : APODViewHolder(binding.root) {
        override fun bind(item: AstronomyPicture) {
            binding.run {
                data = item
            }
        }
    }
}
