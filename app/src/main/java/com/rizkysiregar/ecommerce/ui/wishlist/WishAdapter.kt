package com.rizkysiregar.ecommerce.ui.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity
import com.rizkysiregar.ecommerce.databinding.CardItemWishlishLinearBinding

class WishlistAdapter(private val dataWishlist: List<DetailEntity>) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: DetailEntity)
        fun onBtnAddToCartClick(item: DetailEntity)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_item_wishlish_linear, parent, false)
        )

    override fun getItemCount(): Int {
        return dataWishlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataWishlist[position]
        holder.bind(data)
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = CardItemWishlishLinearBinding.bind(item)

        fun bind(data: DetailEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(imgWishlistLinear)

                tvTitleProductWishlist.text = data.productName
                tvPriceWishlist.text = data.productPrice.toString()
                tvSellerWishlist.text = data.store
                tvRatingWishlist.text = "${data.totalRating} | Terjuaal ${data.sale} "

                imgButtonTrashWishlist.setOnClickListener {
                    listener?.onItemClick(data)
                }

                btnToCartViaWishlist.setOnClickListener {
                    listener?.onBtnAddToCartClick(data)
                }
            }
        }
    }
}