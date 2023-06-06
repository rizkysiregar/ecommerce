package com.rizkysiregar.ecommerce.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.rizkysiregar.ecommerce.data.network.response.DataDetail
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity
import com.rizkysiregar.ecommerce.data.repository.ContentRepository
import com.rizkysiregar.ecommerce.databinding.FragmentReviewBinding
import com.rizkysiregar.ecommerce.databinding.FragmentWishlistBinding
import kotlinx.coroutines.launch

class WishlistViewModel(private val contentRepository: ContentRepository): ViewModel() {

    val getAllWishList = contentRepository.getWishlist()

    fun deleteWishlist(data: DetailEntity) {
        contentRepository.deleteWishlist(data)
    }
}