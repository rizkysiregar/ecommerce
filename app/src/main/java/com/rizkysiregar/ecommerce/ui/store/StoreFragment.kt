package com.rizkysiregar.ecommerce.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.chip.Chip
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.databinding.FragmentStoreBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoreFragment : Fragment(), DataPassed, ProductListAdapter.OnItemProductClickListener {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private val storeViewModel: StoreViewModel by viewModel()
    private lateinit var shimmer: ShimmerFrameLayout
    val adapter = ProductListAdapter()
    var baseQueryFilter: QueryProductModel = QueryProductModel(null, null, null, null, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // start shimmer
        shimmer = binding.shimmerStore
        adapter.setOnItemClickListener(this)

        // fetch data from api
        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())

        // hit product without query
        getData(baseQueryFilter)

        // set query search
        setFragmentResultListener("RESULT") { _, bundle ->
            val receivedData = bundle.getString("bundle")
            val query = QueryProductModel(receivedData.toString(), null, null, null, null)
            getData(query)
            binding.edtSearch.setText(receivedData)
        }

        // when search clicked
        binding.edtSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val navController = view.findNavController()
                navController.navigate(R.id.action_navigation_store_to_navigation_search)
            }
        }

        // call bottom sheet
        binding.actionChip.setOnClickListener {
            callBottomSheet()
        }

        // change layout
        binding.listRv.setOnClickListener {
            adapter.isLinearLayoutManager = !adapter.isLinearLayoutManager
            if (adapter.isLinearLayoutManager) {
                binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
            } else {
                val footerAdapter = LoadingStateAdapter {
                    adapter.retry()
                }

                val headerAdapter = LoadingStateAdapter {
                    adapter.retry()
                }

                val concatAdapter = adapter.withLoadStateHeaderAndFooter(
                    headerAdapter,
                    footerAdapter
                )
                val layoutManager = GridLayoutManager(requireContext(), 2)
                binding.rvItem.layoutManager = layoutManager
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == concatAdapter.itemCount - 1 && footerAdapter.itemCount > 0) {
                            // if it is the last position and we have a footer
                            2
                        } else {
                            1
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getData(queryProductModel: QueryProductModel) {
        storeViewModel.setQuery(queryProductModel)
        binding.rvItem.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        storeViewModel.product.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        lifecycleScope.launch {
            adapter.addLoadStateListener {
                shimmer.visibility =
                    if (it.refresh is LoadState.Loading) View.VISIBLE else View.GONE
                binding.containerLayoutErorr.visibility =
                    if (it.refresh is LoadState.Error) View.VISIBLE else View.GONE
            }
        }
    }

    private fun callBottomSheet() {
        val fragmentManager = childFragmentManager
        val modalBottomSheet = ModelBottomSheet()
        modalBottomSheet.setOnDataPassedListener(this)
        modalBottomSheet.show(fragmentManager, "ModalBottomSheet")
    }

    override fun onDataPassed(data: QueryProductModel) {
        // hit product with query filter
        if (data.brand!!.isNotEmpty()) {
            getData(data)
        }

        // add chip to chip group filter
        if (data.brand!!.isNotEmpty()) {
            val chipGroup = binding.chipGroup
            val chip = Chip(requireActivity())
            chip.text = data.brand
            chipGroup.addView(chip)
        }

        if (data.sort!!.isNotEmpty()) {
            val chipGroup = binding.chipGroup
            val chip = Chip(requireActivity())
            chip.text = data.sort
            chipGroup.addView(chip)
        }

        if (data.highest != 0) {
            val chipGroup = binding.chipGroup
            val chip = Chip(requireActivity())
            chip.text = data.highest.toString()
            chipGroup.addView(chip)
        }

        if (data.lowest != 0) {
            val chipGroup = binding.chipGroup
            val chip = Chip(requireActivity())
            chip.text = data.lowest.toString()
            chipGroup.addView(chip)
        }
    }

    override fun onDestroy() {
        shimmer.stopShimmer()
        shimmer.clearAnimation()
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(id: String) {
        val navController = view?.findNavController()
        val bundle = Bundle().apply {
            putString("itemId", id)
        }
        setFragmentResult("ITEM_ID", bundle)
        navController?.navigate(R.id.action_navigation_store_to_navigation_detail, bundle)
    }
}