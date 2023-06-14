package com.rizkysiregar.ecommerce.ui.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.NotificationEntity
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity
import com.rizkysiregar.ecommerce.databinding.NotificationItemLayoutBinding

class NotificationAdapter(private val dataNotification: List<NotificationEntity>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: NotificationEntity)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.notification_item_layout, parent, false)
        )

    override fun getItemCount(): Int = dataNotification.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataNotification[position]
        holder.bind(item)
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = NotificationItemLayoutBinding.bind(item)

        fun bind(itemNotification: NotificationEntity) {
            Glide.with(itemView.context)
                .load(itemNotification.image)
                .into(binding.imgNotification)

            if (itemNotification.isRead){
                binding.containerItemNotification.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
            }else{
                binding.containerItemNotification.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.color_fab))
            }

            binding.tvTitleNotification.text = itemNotification.title
            binding.tvDateNotification.text = "${itemNotification.date}, ${itemNotification.time} "
            binding.tvTypeNotification.text = itemNotification.type
            binding.idTvDescriptionTransaction.text = itemNotification.description

            binding.containerItemNotification.setOnClickListener {
                listener?.onItemClick(itemNotification)
            }
        }
    }
}