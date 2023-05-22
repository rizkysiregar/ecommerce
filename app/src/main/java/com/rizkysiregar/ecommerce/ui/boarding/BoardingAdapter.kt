package com.rizkysiregar.ecommerce.ui.boarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.DataBoarding
import com.rizkysiregar.ecommerce.databinding.ImageBoardingLayoutBinding

class BoardingAdapter(private val data: List<DataBoarding>) : RecyclerView.Adapter<BoardingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardingAdapter.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_boarding_layout,parent, false))

    override fun onBindViewHolder(holder: BoardingAdapter.ViewHolder, position: Int) {
        val mData = data[position]
        holder.bind(mData)
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ImageBoardingLayoutBinding.bind(item)
        fun bind(data: DataBoarding) {
            with(binding){
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(imgBoarding)
            }
        }
    }

}