package com.rizkysiregar.ecommerce.ui.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.FulFillmentRequestModel
import com.rizkysiregar.ecommerce.data.model.ItemModel
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.network.response.ItemsPayment
import com.rizkysiregar.ecommerce.data.network.response.ListSelectedProducts
import com.rizkysiregar.ecommerce.databinding.FragmentCheckoutBinding
import com.rizkysiregar.ecommerce.ui.payment.PaymentFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerview: RecyclerView
    private val args: CheckoutFragmentArgs by navArgs()
    private var _selectedPayment: ItemsPayment = ItemsPayment("", "", false)
    private val checkoutViewModel: CheckoutViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get selectedProduct from checkout
        val selectedProduct: ListSelectedProducts = args.selectedProducts
        setRecyclerview(selectedProduct.selectedProducts)

        // selected payment method
        setFragmentResultListener(PaymentFragment.RESULT_KEY) { _, bundle ->
            val receivedData = bundle.getParcelable<ItemsPayment>(PaymentFragment.BUNDLE_PAYMENT)
            Glide.with(requireContext())
                .load(receivedData?.image)
                .into(binding.imgPaymentCheckout)
            binding.tvLabelCardPayment.text = receivedData?.label
            receivedData?.let {
                _selectedPayment = it
            }
        }

        binding.cardPayment.setOnClickListener {
            val navController = view.findNavController()
            navController.navigate(R.id.action_navigation_checkout_to_navigation_payment)
        }
    }

    private fun setRecyclerview(data: List<CartEntity>) {
        recyclerview = binding.rvCheckout
        recyclerview.adapter = CheckoutAdapter(data)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.hasFixedSize()
        var totalPrice = 0
        val itemList = mutableListOf<ItemModel>()
        for (product in data) {
            totalPrice += (product.productPrice * product.quantity)
            val item = ItemModel(product.productId, product.variantName, product.quantity)
            itemList.add(item)
        }
        binding.tvTotalPriceCheckout.text = "Rp. $totalPrice"
        binding.btnPurchasedCheckout.isEnabled = data.isNotEmpty()
        binding.btnPurchasedCheckout.setOnClickListener {
            val navController = view?.findNavController()
            val request = FulFillmentRequestModel(_selectedPayment.label, itemList)
            checkoutViewModel.buyProduct(request)
            checkoutViewModel.data.observe(viewLifecycleOwner) {
                val action =
                    CheckoutFragmentDirections.actionNavigationCheckoutToNavigationStatus(it)
                navController?.navigate(action)
            }
        }
    }
}