package com.rizkysiregar.ecommerce.ui.payment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.DataItem
import com.rizkysiregar.ecommerce.databinding.ItemListPaymentBinding

class ParentAdapter(val data: List<DataItem>) : RecyclerView.Adapter<ParentAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_payment, parent, false)
        )

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataItem = data[position]
        holder.bind(dataItem)
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListPaymentBinding.bind(itemView)

        fun bind(data: DataItem){
            binding.tvTitlePayment.text = data.title
            val childMemberAdapter = ChildMemberAdapter(data.item)
            binding.recyclerView2.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView2.adapter = childMemberAdapter
        }
    }

}