package com.rizkysiregar.ecommerce.ui.payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.ItemsPayment
import com.rizkysiregar.ecommerce.databinding.FragmentDetailBinding
import com.rizkysiregar.ecommerce.databinding.FragmentPaymentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PaymentFragment : Fragment(), ParentAdapter.OnItemClickListener{

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ParentAdapter
    private lateinit var recyclerView: RecyclerView
    private val paymentViewModel: PaymentViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.parentRecyclerview
        paymentViewModel.data.observe(viewLifecycleOwner){response ->
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter = ParentAdapter(response.data)
            adapter.setOnItemClickListener(this)
            recyclerView.adapter = adapter
        }

    }
    override fun onItemClick(item: ItemsPayment) {
        val navController = view?.findNavController()
        val bundle = Bundle().apply {
            putParcelable(BUNDLE_PAYMENT, item)
        }
        setFragmentResult(RESULT_KEY, bundle)
        navController?.navigateUp()
    }

    companion object {
        const val BUNDLE_PAYMENT = "selectedPayment"
        const val RESULT_KEY = "PAYMENT_METHOD"
    }
}