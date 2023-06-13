package com.rizkysiregar.ecommerce.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rizkysiregar.ecommerce.databinding.ListSearchBinding

class SearchAdapter(private val data: List<String>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(item: String)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListSearchBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.textProductName.text = item

        holder.itemView.setOnClickListener {
            listener?.onItemClick(item)
        }

//        holder.itemView.setOnClickListener {
//            val navController = holder.itemView.findNavController()
//            navController.popBackStack()
//        }
    }

    class ViewHolder(binding: ListSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textProductName = binding.tvProductName
    }


}