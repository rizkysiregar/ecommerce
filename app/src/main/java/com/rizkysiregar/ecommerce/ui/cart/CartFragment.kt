package com.rizkysiregar.ecommerce.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.CartEntity
import com.rizkysiregar.ecommerce.data.network.response.ListSelectedProducts
import com.rizkysiregar.ecommerce.databinding.FragmentCartBinding
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

            binding.checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (compoundButton.isPressed) {
                    for (product in it) {
                        cartViewModel.setSelectedProduct(product, isChecked)
                    }
                }
            }
        }

        selectedProduct()
        whenItemIsStillFalse()
    }

    override fun onItemClick(cartEntity: CartEntity, isChecked: Boolean) {
        // update isChecked in room
        cartViewModel.setSelectedProduct(cartEntity, isChecked)
    }

    override fun onDeleteIconClick(cartEntity: CartEntity) {
        cartViewModel.delete(cartEntity)
    }

    override fun onButtonCounterClick(cartEntity: CartEntity) {
        cartViewModel.setQuantityProduct(cartEntity)
    }

    override fun onButtonReduceClick(cartEntity: CartEntity) {
        cartViewModel.setQuantityProduct(cartEntity)
    }

    private fun selectedProduct() {
        cartViewModel.getSelectedProduct.observe(viewLifecycleOwner) { listProduct ->
            var totalPrice = 0
            for (product in listProduct) {
                totalPrice += (product.productPrice * product.quantity)
            }
            binding.tvTotalPaymentCart.text = "Rp. $totalPrice"

            // when btn buy clicked
            binding.btnBuyCart.setOnClickListener {
                selectedProducts(listProduct)
            }
        }
    }

    private fun whenItemIsStillFalse() {
        cartViewModel.getCountOfFalseValue.observe(viewLifecycleOwner) { value ->
            binding.checkbox.isChecked = (value == 0)
            if (value == 1) {
                binding.tvResetCart.visibility = View.VISIBLE
            } else {
                binding.tvResetCart.visibility = View.GONE
            }
        }
    }

    private fun selectedProducts(listProduct: List<CartEntity>) {
        val navController = view?.findNavController()
        val data = ListSelectedProducts(listProduct)
        val action = CartFragmentDirections.actionNavigationCartToCheckout(data)
        navController?.navigate(action)
    }
}