package com.adyen.android.assignment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.adyen.android.assignment.data.network.AstronomyPicture
import com.adyen.android.assignment.databinding.ItemApodBinding
import com.adyen.android.assignment.databinding.ItemTitleBinding

class APODDataAdapter constructor(
    private var itemClickListener: (AstronomyPicture) -> Unit
) :  ListAdapter<AstronomyPicture, RecyclerView.ViewHolder>(DiffCallBack()) {

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_TITLE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): APODViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_TITLE -> APODViewHolder.TitleViewHolder(
                ItemTitleBinding.inflate (
                    layoutInflater,
                    parent,
                    false
                )
            )
            else -> APODViewHolder.DataViewHolder(
                ItemApodBinding.inflate (
                    layoutInflater,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
       return if (currentList[position].isTitle) {
           TYPE_TITLE
        } else {
            TYPE_ITEM
       }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is APODViewHolder) {
            holder.bind(currentList[position])
        }

        if (currentList[position].isTitle.not()) {
            holder.itemView.setOnClickListener { itemClickListener(currentList[position])}
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