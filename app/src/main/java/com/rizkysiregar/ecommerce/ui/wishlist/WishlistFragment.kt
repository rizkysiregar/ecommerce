package com.rizkysiregar.ecommerce.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity
import com.rizkysiregar.ecommerce.databinding.FragmentWishlistBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class WishlistFragment : Fragment(), WishlistAdapter.OnItemClickListener {
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private val wishlistViewModel: WishlistViewModel by viewModel()
    private lateinit var wishlistAdapter: WishlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.rvWishlist
        wishlistViewModel.getAllWishList.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.containerLayoutErorrWishlist.visibility = View.VISIBLE
                binding.errorLayout.tvTitleError.text = "Empty"
                binding.errorLayout.descError.text = "Your request data is unavailable"
            } else {
                binding.containerLayoutErorrWishlist.visibility = View.GONE
            }
            wishlistAdapter = WishlistAdapter(it)
            wishlistAdapter.setOnItemClickListener(this)
            recyclerView.adapter = wishlistAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.hasFixedSize()
        }
    }

    override fun onItemClick(item: DetailEntity) {
        wishlistViewModel.deleteWishlist(item)
    }
}