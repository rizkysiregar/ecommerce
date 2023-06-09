package com.rizkysiregar.ecommerce.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.FragmentDetailBinding
import com.rizkysiregar.ecommerce.databinding.FragmentPaymentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PaymentFragment : Fragment() {

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
        paymentViewModel.data.observe(viewLifecycleOwner){
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter = ParentAdapter(it.data)
            recyclerView.adapter = adapter
        }

    }


}