package com.rizkysiregar.ecommerce.ui.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.data.model.DummyStoreData
import com.rizkysiregar.ecommerce.databinding.ItemContentGridBinding

class StoreAdapterGrid(
    private val data: List<DummyStoreData>,
) :
    RecyclerView.Adapter<StoreAdapterGrid.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreAdapterGrid.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContentGridBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreAdapterGrid.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ItemContentGridBinding) :
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