package com.rizkysiregar.ecommerce.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.FragmentReviewBinding
import com.rizkysiregar.ecommerce.databinding.FragmentTransactionBinding
import com.rizkysiregar.ecommerce.ui.review.ReviewAdapter
import com.rizkysiregar.ecommerce.ui.review.ReviewViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class TransactionFragment : Fragment() {

    private val transactionViewModel: TransactionViewModel by viewModel()
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeRefresh = binding.swipeRefreshTransaction
        // display list transaction
        setDataReviewToAdapter()

        // update display when swipe refresh
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            transactionViewModel.getTransactionList()
            swipeRefresh.isRefreshing = false
        }
    }

    private fun setDataReviewToAdapter() {
        lifecycleScope.launch {
            transactionViewModel.data.collect {
                when (it?.code) {
                    404 -> {
                        binding.containerLayoutErrorTransaction.visibility = View.VISIBLE
                        binding.errorLayout.tvTitleError.text = "Empty"
                        binding.errorLayout.descError.text = "Your request data is unavailable"
                    }

                    200 -> {
                        binding.containerLayoutErrorTransaction.visibility = View.GONE
                        recyclerView = binding.rvTransaction
                        transactionAdapter = TransactionAdapter(it.data)
                        recyclerView.adapter = transactionAdapter
                        recyclerView.layoutManager = LinearLayoutManager(requireContext())
                        recyclerView.hasFixedSize()
                    }
                }
            }
        }
    }
}