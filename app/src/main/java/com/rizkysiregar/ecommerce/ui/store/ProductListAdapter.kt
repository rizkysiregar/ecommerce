package com.rizkysiregar.ecommerce.ui.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.ItemsItem
import com.rizkysiregar.ecommerce.databinding.ContentItemLinearBinding
import com.rizkysiregar.ecommerce.databinding.ItemContentGridBinding
import java.lang.IllegalArgumentException

class ProductListAdapter(private val mode: Boolean) :
    PagingDataAdapter<ItemsItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private val VIEW_LINEAR = 1
    private val VIEW_GRID = 2

    override fun getItemViewType(position: Int): Int {
        return if (mode) {
            VIEW_LINEAR
        } else {
            VIEW_GRID
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_LINEAR -> {
                val binding =
                    ContentItemLinearBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return MyViewHolder(binding)
            }
            VIEW_GRID -> {
                val binding =
                    ItemContentGridBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return GridViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }

//        val binding =
//            ContentItemLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            when (holder) {
                is MyViewHolder -> {
                    holder.bind(data)
                }
                is GridViewHolder -> {
                    holder.bind(data)
                }
            }
        }
    }

    class MyViewHolder(private val binding: ContentItemLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemsItem) {
            Glide.with(itemView.context)
                .load(R.drawable.thumbnail)
                .into(binding.imgItem)

            binding.tvTitleContent.text = data.productName
            binding.tvSeller.text = data.store
            binding.textPrice.text = data.productPrice.toString()
            binding.tvRatingContent.text = "${data.productRating} | ${data.sale}"
        }
    }

    class GridViewHolder(private val binding: ItemContentGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemsItem) {
            Glide.with(itemView.context)
                .load(R.drawable.thumbnail)
                .into(binding.imgItem)

            binding.tvTitleContent.text = data.productName
            binding.tvSeller.text = data.store
            binding.textPrice.text = data.productPrice.toString()
            binding.tvRatingContent.text = "${data.productRating} | ${data.sale}"
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.productId == newItem.productId
            }
        }
    }
}
