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

class ProductListAdapter :
    PagingDataAdapter<ItemsItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    var isLinearLayoutManager = true

    override fun getItemViewType(position: Int): Int {
        return if (isLinearLayoutManager) {
            1
        } else {
            2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> {
                val binding =
                    ContentItemLinearBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return MyViewHolder(binding)
            }
            2 -> {
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
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            when (holder) {
                is MyViewHolder -> {
                    holder.bind(data)
                }

                is GridViewHolder -> {
                    holder.bindOK(data)
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
        fun bindOK(data: ItemsItem) {
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
