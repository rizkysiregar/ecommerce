package com.rizkysiregar.ecommerce.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.ImageDetailBinding

class DetailAdapter(private val data: List<String>) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_detail, parent, false)
        )


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = data[position]
        holder.bind(image)
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ImageDetailBinding.bind(item)

        fun bind(data: String){
            with(binding){
                Glide.with(itemView.context)
                    .load(data)
                    .into(imgDetailItem)
            }
        }
    }
}