package com.rizkysiregar.ecommerce.ui.payment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.network.response.DataItem
import com.rizkysiregar.ecommerce.data.network.response.ItemsItem
import com.rizkysiregar.ecommerce.data.network.response.ItemsPayment
import com.rizkysiregar.ecommerce.databinding.ItemListPaymentBinding

class ParentAdapter(val data: List<DataItem>) :
    RecyclerView.Adapter<ParentAdapter.DataViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item : ItemsPayment)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_payment, parent, false)
        )

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataItem = data[position]
        holder.bind(dataItem)
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListPaymentBinding.bind(itemView)

        fun bind(data: DataItem) {
            binding.tvTitlePayment.text = data.title
            val childMemberAdapter = ChildMemberAdapter(data.item) { position ->
                onChildItemClick(adapterPosition, position)
            }
            binding.recyclerView2.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView2.adapter = childMemberAdapter
        }
    }

    private fun onChildItemClick(parentPosition: Int, childPosition: Int) {
        // Handle the click event of the child item
        val dataItem = data[parentPosition]
        val clickedChildItem = dataItem.item[childPosition]
        // Perform actions based on the clicked child item
        Log.d("ITEM CLICKED", clickedChildItem.label)
        listener?.onItemClick(clickedChildItem)
    }


}