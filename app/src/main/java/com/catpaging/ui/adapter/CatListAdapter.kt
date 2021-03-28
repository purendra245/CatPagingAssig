

package com.catpaging.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.catpaging.model.CatsResponse
import com.example.mypaging.utils.GlideRequests


class CatListAdapter(private val glide: GlideRequests)
    : PagingDataAdapter<CatsResponse, CatViewHolder>(CAT_COMPARATOR) {

    var mClickController: OnCellClickListener? = null

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position),mClickController)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder.create(parent, glide)
    }

    companion object {
        val CAT_COMPARATOR = object : DiffUtil.ItemCallback<CatsResponse>() {
            override fun areContentsTheSame(oldItem: CatsResponse, newItem: CatsResponse): Boolean =
                oldItem.id == newItem.id

            override fun areItemsTheSame(oldItem: CatsResponse, newItem: CatsResponse): Boolean =
                    oldItem.id == newItem.id

        }

    }


    interface OnCellClickListener {
        fun onCellClicked(image: CatsResponse, view: View)
    }
    fun setOnClickListener(listener: OnCellClickListener) {
        mClickController = listener
    }
}
