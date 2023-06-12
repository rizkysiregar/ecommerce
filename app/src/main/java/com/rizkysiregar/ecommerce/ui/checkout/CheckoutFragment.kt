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
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.network.response.ItemsPayment
import com.rizkysiregar.ecommerce.data.network.response.ListSelectedProducts
import com.rizkysiregar.ecommerce.databinding.FragmentCheckoutBinding
import com.rizkysiregar.ecommerce.ui.payment.PaymentFragment


class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerview: RecyclerView
    private val args: CheckoutFragmentArgs by navArgs()
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

        setFragmentResultListener(PaymentFragment.RESULT_KEY) { _, bundle ->
            val receivedData = bundle.getParcelable<ItemsPayment>(PaymentFragment.BUNDLE_PAYMENT)
            Glide.with(requireContext())
                .load(receivedData?.image)
                .into(binding.imgPaymentCheckout)
            binding.tvLabelCardPayment.text = receivedData?.label
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
    }
}