package com.adyen.android.assignment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.databinding.ItemApodBinding

class APODDataAdapter constructor(
    private var itemClickListener: (AstronomyPicture) -> Unit
) :  ListAdapter<AstronomyPicture, RecyclerView.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        val binding = ItemApodBinding.inflate (
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GiphyViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GiphyViewHolder) {
            holder.onBind(currentList[position])
        }
    }


    class GiphyViewHolder constructor(
        private val binding: ItemApodBinding,
        var itemClickListener: (AstronomyPicture) -> Unit):
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item : AstronomyPicture) {
            binding.run {
                data = item
                root.setOnClickListener {
                    itemClickListener(item)
                }
            }
        }
    }


    /*
     Using diff util for efficient views update
     */
    class DiffCallBack : DiffUtil.ItemCallback<AstronomyPicture>() {

        override fun areItemsTheSame(oldItem: AstronomyPicture, newItem: AstronomyPicture): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: AstronomyPicture, newItem: AstronomyPicture): Boolean {
            return oldItem == newItem
        }
    }
}