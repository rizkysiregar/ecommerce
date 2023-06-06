package com.rizkysiregar.ecommerce.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity
import com.rizkysiregar.ecommerce.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailProductViewModel: DetailProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var productId = ""
        setFragmentResultListener("ITEM_ID") { _, bundle ->
            val receivedData = bundle.getString("itemId")
            detailProductViewModel.getDetailProduct(receivedData.toString())
            productId = receivedData.toString()
        }

        binding.tvShowAllReview.setOnClickListener {
            provideIdProductForReview(productId)
        }

        // bind data from to view
        bindData()

        // loading indicator
        detailProductViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.likeImageButton.setOnClickListener {
            insertWishlist()
        }
    }

    private fun bindData() {
        detailProductViewModel.data.observe(viewLifecycleOwner) {

            val viewPager = binding.viewpagerDetail
            viewPager.adapter = DetailAdapter(it.data.image)
            val dotsIndicator = binding.indicatorDetail
            dotsIndicator.attachTo(viewPager)

            binding.tvPriceProductDetail.text =
                getString(R.string.price_format, it.data.productPrice.toString())
            binding.tvTitleProductDetail.text = it.data.productName
            binding.tvSoldDetail.text = "Terjual ${it.data.sale}"
            binding.tvRatingDetail.text = "${it.data.productRating} (${it.data.totalReview})"
            binding.tvProductDescription.text = it.data.description
            binding.tvReviewBuyer.text = it.data.productRating.toString()
            binding.tvReviewPerValue.text = "5.0"
            binding.tvSatisfiedBuyer.text = "${it.data.totalSatisfaction} merasa puas"
            binding.tvRatingCount.text = "${it.data.totalRating}.${it.data.totalReview}"


            for (variant in it.data.productVariant) {
                val chipGroup = binding.chipGroupVariant
                val chip = Chip(requireContext())
                chip.text = variant.variantName
                chip.id = View.generateViewId()
                chipGroup.addView(chip)
            }

            val chipGroup = binding.chipGroupVariant
            chipGroup.setOnCheckedChangeListener { group, checkedId ->
                for (i in 0 until group.childCount) {
                    val chip = group.getChildAt(i) as Chip
                    if (chip.id == checkedId) {
                        chip.setChipBackgroundColorResource(R.color.chip_color)
                    } else {
                        chip?.setChipBackgroundColorResource(R.color.white)
                    }
                }
            }

//            val firstChip = binding.chipGroupVariant.getChildAt(0) as? Chip
//            firstChip?.isChecked = true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressCircularDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun provideIdProductForReview(productId: String) {
        val navController = view?.findNavController()
        val bundle = Bundle().apply {
            putString("productReviewId", productId)
        }
        setFragmentResult("PRODUCT_REVIEW_ID", bundle)
        navController?.navigate(R.id.action_navigation_detail_to_navigation_review, bundle)
    }

    private fun insertWishlist(){
        detailProductViewModel.data.observe(viewLifecycleOwner){
            val data = DetailEntity(
                it.data.image[0],
                it.data.productId,
                it.data.description,
                it.data.totalRating,
                it.data.store,
                it.data.productName,
                it.data.totalSatisfaction,
                it.data.sale,
                it.data.stock,
                it.data.productRating.toString(),
                it.data.brand,
                it.data.productPrice,
                it.data.totalReview
            )
            try {
                detailProductViewModel.insertNewWishlist(data)
                Toast.makeText(requireContext(), "Add to wishlist", Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                Log.d("ERORR INSERT: ", e.message.toString())
            }
        }
    }

    private fun updateFavorite() {
        detailProductViewModel.getAllWishList.observe(viewLifecycleOwner){

        }
    }

}