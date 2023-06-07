package com.rizkysiregar.ecommerce.ui.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.DataItemReview
import com.rizkysiregar.ecommerce.databinding.ReviewItemBinding

class ReviewAdapter(private val dataReview: List<DataItemReview>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.review_item, parent, false)
        )


    override fun getItemCount(): Int {
        return dataReview.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataReview[position]
        holder.bind(item)
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ReviewItemBinding.bind(itemView)

        fun bind(dataReview: DataItemReview) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(dataReview.userImage)
                    .into(binding.imgReview)

                tvUsernameReview.text = dataReview.userName
                ratingBar.rating= dataReview.userRating.toFloat()
                tvDescDetail.text = dataReview.userReview
            }


        }
    }

}