package com.rizkysiregar.ecommerce.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
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

        setFragmentResultListener("ITEM_ID") { _, bundle ->
            val receivedData = bundle.getString("itemId")
            detailProductViewModel.getDetailProduct(receivedData.toString())

        }

        // bind data from to view
        bindData()

        // loading indicator
        detailProductViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }

    private fun bindData(){
        detailProductViewModel.data.observe(viewLifecycleOwner){
            Glide.with(requireContext())
                .load(it.data.image[0])
                .into(binding.imgProductDetail)

            binding.tvPriceProductDetail.text = it.data.productPrice.toString()
            binding.tvTitleProductDetail.text = it.data.productName
            binding.tvSoldDetail.text = "Terjual ${it.data.sale}"
            binding.tvRatingDetail.text = "${it.data.productRating} (${it.data.totalReview})"
            binding.tvProductDescription.text = it.data.description
            binding.tvReviewBuyer.text = it.data.productRating.toString()
            binding.tvReviewPerValue.text = "5.0"
            binding.tvSatisfiedBuyer.text = "${it.data.totalSatisfaction} merasa puas"
            binding.tvRatingCount.text = "${it.data.totalRating}.${it.data.totalReview}"

            for (variant in it.data.productVariant){
                val chipGroup = binding.chipGroupVariant
                val chip = Chip(requireContext())
                chip.text = variant.variantName
                chipGroup.addView(chip)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressCircularDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}