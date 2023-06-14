package com.rizkysiregar.ecommerce.ui.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.model.NotificationEntity
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import kotlinx.coroutines.launch

class NotificationViewModel(private val contentRepository: ContentRepository) : ViewModel(){
    val getAllDataNotification = contentRepository.getAllNotification()

    fun setIsRead(notificationEntity: NotificationEntity, state: Boolean) {
        viewModelScope.launch {
            contentRepository.setReadNotification(notificationEntity, state)
        }
    }
}