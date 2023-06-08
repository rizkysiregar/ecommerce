package com.rizkysiregar.ecommerce.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.databinding.FragmentCartBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment(), CartAdapter.OnItemClickListener {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by viewModel()
    private lateinit var cartAdapter: CartAdapter
    private var totalPrice = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = binding.recyclerView
        cartViewModel.getAllCart.observe(viewLifecycleOwner) {
            cartAdapter = CartAdapter(it)
            cartAdapter.setOnItemClickListener(this)
            recyclerview.adapter = cartAdapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext())
            recyclerview.hasFixedSize()
            recyclerview.itemAnimator = null
        }

        var isChecked = false
        binding.checkbox.setOnClickListener {
            isChecked = !isChecked
            binding.btnBuyCart.isEnabled = isChecked
        }
    }

    override fun onItemClick(item: CartEntity, isChecked: Boolean) {
        val listItemChecked = mutableListOf<CartEntity>()
        listItemChecked.add(item)
        if (isChecked) {
            onItemChecked(listItemChecked)
        } else {
            onItemUnChecked(totalPrice, listItemChecked)
        }
    }

    private fun onItemChecked(listItemChecked: MutableList<CartEntity>) {
        val counterPrice = listItemChecked.sumOf { it.productPrice }
        binding.tvTotalPaymentCart.text = counterPrice.toString()
        totalPrice = counterPrice
    }

    private fun onItemUnChecked(totalPrice: Int, listItemChecked: MutableList<CartEntity>) {
        val reducePrice = listItemChecked.fold(totalPrice) {
            acc, item -> acc - item.productPrice
        }
        binding.tvTotalPaymentCart.text = reducePrice.toString()
    }


}