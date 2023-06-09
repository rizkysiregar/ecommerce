package com.rizkysiregar.ecommerce.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.databinding.FragmentCartBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment(), CartAdapter.OnItemClickListener {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by viewModel()
    private lateinit var cartAdapter: CartAdapter

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

        binding.btnBuyCart.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_cart_to_checkout)
        }

        binding.checkbox.setOnClickListener {
            // check all child box
        }
        selectedProduct()
    }

    override fun onItemClick(cartEntity: CartEntity, isChecked: Boolean) {
        // update isChecked in room
            cartViewModel.setSelectedProduct(cartEntity, isChecked)
    }

    override fun onDeleteIconClick(cartEntity: CartEntity) {
        cartViewModel.delete(cartEntity)
    }

    private fun selectedProduct() {
        cartViewModel.getSelectedProduct.observe(viewLifecycleOwner) { listProduct ->
            var totalPrice = 0
            for (product in listProduct) {
                totalPrice += product.productPrice
            }
            binding.tvTotalPaymentCart.text = "Rp. $totalPrice"
        }
    }

    private fun onItemChecked(listItemChecked: MutableList<CartEntity>) {
        val counterPrice = listItemChecked.sumOf { it.productPrice }
        binding.tvTotalPaymentCart.text = counterPrice.toString()
    }

    private fun onItemUnChecked(totalPrice: Int, listItemChecked: MutableList<CartEntity>) {
        val reducePrice = listItemChecked.fold(totalPrice) { acc, item ->
            acc - item.productPrice
        }
        binding.tvTotalPaymentCart.text = reducePrice.toString()
    }
}