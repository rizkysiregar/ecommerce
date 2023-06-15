package com.rizkysiregar.ecommerce.ui.checkout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.databinding.CheckoutItemProductBinding

class CheckoutAdapter(
    private val selectedItems: List<CartEntity>
) : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(cartEntity: CartEntity, isChecked: Boolean)
        fun onDeleteIconClick(cartEntity: CartEntity)
        fun onButtonCounterClick(cartEntity: CartEntity)

        fun onButtonReduceClick(cartEntity: CartEntity)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.checkout_item_product, parent, false)
        )

    override fun getItemCount(): Int {
        return selectedItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = selectedItems[position]
        holder.bind(data)
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = CheckoutItemProductBinding.bind(item)


        fun bind(data: CartEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(imgProductCheckout)

                tvProductNameCheckout.text = data.productName
                tvVariantCheckout.text = data.variantName
                tvStockCheckout.text = "Stock: ${data.stock}"
                tvPriceCheckout.text = "Rp.${data.productPrice}"
                button2.text = data.quantity.toString()

                toggleButtonCount.addOnButtonCheckedListener { group, checkedId, isChecked ->
                    group.clearChecked()
                }

                button1.setOnClickListener {
                    if (data.quantity > 1) {
                        val cartEntity: CartEntity = data
                        cartEntity.quantity -= 1
                        listener?.onButtonReduceClick(cartEntity)
                    } else if (data.quantity == 1) {
                        button2.text = "1"
                        Toast.makeText(
                            itemView.context,
                            "Minimum purchase is 1",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                button3.setOnClickListener {
                    if (data.quantity >= data.stock) {
                        Toast.makeText(
                            itemView.context,
                            "Maximum purchase limited by stock",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val cartEntity: CartEntity = data
                        cartEntity.quantity += 1
                        listener?.onButtonCounterClick(cartEntity)
                    }
                }
            }
        }
    }

}