package com.rizkysiregar.ecommerce.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.network.response.DetailEntity
import com.rizkysiregar.ecommerce.data.network.response.ListSelectedProducts
import com.rizkysiregar.ecommerce.databinding.FragmentDetailBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailProductViewModel: DetailProductViewModel by viewModel()
    private var liked = false
    private val firebaseAnalytics: FirebaseAnalytics by inject()

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
            detailProductViewModel.setProductId(receivedData.toString())
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

        binding.btnAddToCart.setOnClickListener {
            insertToCart()
        }

        // add to wishlist
        check()
        getProduct()


    }

    private fun directBuy(listProduct: List<CartEntity>) {
        val navController = view?.findNavController()
        val data = ListSelectedProducts(listProduct)
        val action = DetailFragmentDirections.actionNavigationDetailToNavigationCheckout(selectedProducts = data)
        navController?.navigate(action)
    }

    private fun bindData() {
        detailProductViewModel.data.observe(viewLifecycleOwner) { response ->
            // firebase measure ecommerce
            val itemProduct = Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, response.data.productId)
                putString(FirebaseAnalytics.Param.ITEM_NAME, response.data.productName)
                putString(
                    FirebaseAnalytics.Param.ITEM_VARIANT,
                    response.data.productVariant[0].variantName
                )
                putString(FirebaseAnalytics.Param.ITEM_BRAND, response.data.brand)
                putInt(FirebaseAnalytics.Param.PRICE, response.data.productPrice)
            }
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, itemProduct)

            val viewPager = binding.viewpagerDetail
            viewPager.adapter = DetailAdapter(response.data.image)
            val dotsIndicator = binding.indicatorDetail
            dotsIndicator.attachTo(viewPager)

            binding.tvPriceProductDetail.text =
                getString(R.string.price_format, response.data.productPrice.toString())
            binding.tvTitleProductDetail.text = response.data.productName
            binding.tvSoldDetail.text = "Terjual ${response.data.sale}"
            binding.tvRatingDetail.text = "${response.data.productRating} ${response.data.totalReview})"
            binding.tvProductDescription.text = response.data.description
            binding.tvReviewBuyer.text = response.data.productRating.toString()
            binding.tvReviewPerValue.text = "/5.0"
            binding.tvSatisfiedBuyer.text = "${response.data.totalSatisfaction} pembeli merasa puas"
            binding.tvRatingCount.text = "${response.data.totalRating} rating . ${response.data.totalReview} ulasan"

            for (variant in response.data.productVariant) {
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
                        val price = response.data.productPrice + response.data.productVariant[1].variantPrice
                        binding.tvPriceProductDetail.text =
                            getString(R.string.price_format, price.toString())
                    } else {
                        chip.setChipBackgroundColorResource(R.color.white)
                        binding.tvPriceProductDetail.text =
                            getString(R.string.price_format, response.data.productPrice.toString())
                    }
                }
            }

            // colored the first child of variant group
            val firstChip = chipGroup.getChildAt(0) as Chip
            firstChip.setChipBackgroundColorResource(R.color.chip_color)

            // direct buy
            binding.btnBuyDirectly.setOnClickListener {
                val cartEntity = CartEntity(
                    image = response.data.image[0],
                    productId = response.data.productId,
                    description = response.data.description,
                    totalRating = response.data.totalRating,
                    productName = response.data.productName,
                    totalSatisfaction = response.data.totalSatisfaction,
                    totalReview = response.data.totalReview,
                    variantName = response.data.productVariant[0].variantName,
                    brand = response.data.brand,
                    sale = response.data.sale,
                    stock = response.data.stock,
                    productPrice = response.data.productPrice,
                    productRating = response.data.productRating.toString(),
                    store = response.data.store
                )
                val listEntity: List<CartEntity> = listOf(cartEntity)

                directBuy(listEntity)
            }
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

    private fun setWishlistIcon(state: Boolean) {
        if (state) {
            binding.likeImageButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.baseline_favorite_24_fill
                )
            )
        } else {
            binding.likeImageButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.baseline_favorite_border_24
                )
            )
        }
    }

    private fun getProduct() {
        detailProductViewModel.data.observe(viewLifecycleOwner) {
            val detailEntity = DetailEntity(
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
            binding.likeImageButton.setOnClickListener { view ->
                if (liked) {
                    detailProductViewModel.deleteWishlist(detailEntity)
                    Snackbar.make(view, "Deleted from wishlist", Snackbar.LENGTH_SHORT).show()
                } else {
                    detailProductViewModel.insertNewWishlist(detailEntity)
                    Snackbar.make(view, "Added to wishlist", Snackbar.LENGTH_SHORT).show()

                    val bundleAddToWishlist = bundleOf().apply {
                        putString(FirebaseAnalytics.Param.ITEM_ID, it.data.productId)
                        putString(FirebaseAnalytics.Param.ITEM_NAME, it.data.productName)
                        putString(
                            FirebaseAnalytics.Param.ITEM_VARIANT,
                            it.data.productVariant[0].variantName
                        )
                        putString(FirebaseAnalytics.Param.ITEM_BRAND, it.data.brand)
                        putInt(FirebaseAnalytics.Param.PRICE, it.data.productPrice)
                    }
                    firebaseAnalytics.logEvent(
                        FirebaseAnalytics.Event.ADD_TO_WISHLIST,
                        bundleAddToWishlist
                    )
                }
            }
        }
    }

    private fun check() {
        detailProductViewModel.result.observe(viewLifecycleOwner) { isLiked ->
            setWishlistIcon(isLiked)
            liked = isLiked
        }
    }

    private fun insertToCart() {
        detailProductViewModel.data.observe(viewLifecycleOwner) {
            val cartEntity = CartEntity(
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
                it.data.totalReview,
                it.data.productVariant[1].variantName
            )
            try {
                detailProductViewModel.insertProductToCart(cartEntity)
                Snackbar.make(requireView(), "Added to cart", Snackbar.LENGTH_SHORT).show()
                val cartItem = Bundle().apply {
                    putString(FirebaseAnalytics.Param.CURRENCY, "IDR")
                    putInt(FirebaseAnalytics.Param.VALUE, 1 * it.data.productPrice)
                }
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, cartItem)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "error : $e", Toast.LENGTH_SHORT).show()
            }
        }
    }
}