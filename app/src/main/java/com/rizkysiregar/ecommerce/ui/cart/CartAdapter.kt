package com.rizkysiregar.ecommerce.ui.cart


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.databinding.CartItemLayoutBinding


class CartAdapter(private val cartEntity: List<CartEntity>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var isChecked = false
        private set

    fun setIsChecked(checked: Boolean) {
        isChecked = checked
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(item: CartEntity, isChecked: Boolean)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_item_layout, parent, false)
        )

    override fun getItemCount(): Int {
        return cartEntity.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = cartEntity[position]
        holder.bind(data)
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = CartItemLayoutBinding.bind(item)

        fun bind(data: CartEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(imgCart)

                tvProductNameCart.text = data.productName
                tvVariantCart.text = data.variantName
                tvStockCart.text = "Sisa ${data.stock}"
                tvPriceCart.text = "Rp. ${data.productPrice}"
                checkbox.isChecked = isChecked

                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    listener?.onItemClick(data, isChecked)
                }

                binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
                    group.clearChecked()
                }

                if (data.stock < 10) {
                    tvStockCart.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.text_error
                        )
                    )
                } else {
                    tvStockCart.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.text_content
                        )
                    )
                }

                button1.setOnClickListener {
                    if (button2.text.toString().toInt() > 1) {
                        val base = button2.text.toString().toInt()
                        val reduce = base - 1
                        button2.text = reduce.toString()
                    } else if (button2.text.toString().toInt() == 1) {
                        button2.text = "1"
                        Toast.makeText(
                            itemView.context,
                            "minimum purchase is 1",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                button3.setOnClickListener {
                    if (button2.text.toString().toInt() >= data.stock) {
                        Toast.makeText(
                            itemView.context,
                            "Maximum purchase limited by stock",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val base = binding.button2.text.toString()
                        val add = base.toInt() + 1
                        button2.text = add.toString()
                    }
                }
            }
        }
    }

}