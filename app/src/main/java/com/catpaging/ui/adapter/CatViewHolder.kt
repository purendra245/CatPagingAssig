
package com.catpaging.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.catpaging.R
import com.catpaging.model.CatsResponse
import com.example.mypaging.utils.GlideRequests


class CatViewHolder(private val view: View, private val glide: GlideRequests)
    : RecyclerView.ViewHolder(view) {

    private val ivCatMain : ImageView = view.findViewById(R.id.ivCatMain)
    private var cats : CatsResponse? = null
    init {
        view.setOnClickListener {
            cats?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(cat: CatsResponse?, mClickController: CatListAdapter.OnCellClickListener?) {
        this.cats = cat
          if (cats?.url?.startsWith("http") == true) {
              ivCatMain.visibility = View.VISIBLE
            glide.load(cat?.url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_cat_half_transparent)
                    .into(ivCatMain)
        } else {
              ivCatMain.visibility = View.GONE
            glide.clear(ivCatMain)
        }

        view.setOnClickListener {
            cats?.url?.let {
                mClickController?.onCellClicked(cats!!,view)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests): CatViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.cat_list_item, parent, false)
            return CatViewHolder(view, glide)
        }
    }


}