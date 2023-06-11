package com.rizkysiregar.ecommerce.ui.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.databinding.FragmentCheckoutBinding


class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerview: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("SELECTED_PRODUCTS"){_, bundle ->
            val receivedData = bundle.getParcelableArrayList<CartEntity>("selectedProducts")
            receivedData?.let { setRecyclerview(it.toList()) }
        }
    }


    fun setRecyclerview(data: List<CartEntity>){
        recyclerview = binding.rvCheckout
        recyclerview.adapter = CheckoutAdapter(data)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.hasFixedSize()
    }



}