package com.rizkysiregar.ecommerce.ui.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.data.model.DummyStoreData
import com.rizkysiregar.ecommerce.databinding.ContentItemLinearBinding

class StoreAdapter(
    private val data: List<DummyStoreData>,
) :
    RecyclerView.Adapter<StoreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContentItemLinearBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ContentItemLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DummyStoreData) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(imgItem)

                tvTitleContent.text = data.title
                tvSeller.text = data.storeName
                textPrice.text = data.price.toString()
                tvRatingContent.text = "${data.rating} | ${data.sold}"
            }
        }
    }

}