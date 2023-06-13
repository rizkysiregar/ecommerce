package com.rizkysiregar.ecommerce.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
import com.google.firebase.analytics.FirebaseAnalytics
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.databinding.FragmentStoreBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class StoreFragment : Fragment(), ProductListAdapter.OnItemProductClickListener {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private val storeViewModel: StoreViewModel by viewModel()
    private lateinit var shimmer: ShimmerFrameLayout
    val adapter = ProductListAdapter()
    private val firebaseAnalytics: FirebaseAnalytics by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // empty query
        getData(QueryProductModel())

        // start shimmer
        shimmer = binding.shimmerStore
        adapter.setOnItemClickListener(this)

        // fetch data from api
        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())

        // set query search
        setFragmentResultListener("RESULT") { _, bundle ->
            val receivedData = bundle.getString("bundle")
            val query = QueryProductModel(search = receivedData.toString())
            getData(query)
            binding.edtSearch.setText(receivedData)
        }

        setFragmentResultListener(ModelBottomSheet.RESULT_FILTER) {_, bundle ->
            val receivedFilter = bundle.getParcelable<QueryProductModel>(ModelBottomSheet.BUNDLE_FILTER)
            receivedFilter?.let {
                getData(it)
            }
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

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            getData(QueryProductModel())
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun callBottomSheet() {
        val fragmentManager = childFragmentManager
        val modalBottomSheet = ModelBottomSheet()
        modalBottomSheet.show(fragmentManager, "ModalBottomSheet")
    }

    fun setChipBaseOnQuery(data: QueryProductModel) {
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
        val selectedItem = bundleOf().apply {
            putString(FirebaseAnalytics.Param.INDEX, id)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, selectedItem)
        val navController = view?.findNavController()
        val bundle = Bundle().apply {
            putString("itemId", id)
        }
        setFragmentResult("ITEM_ID", bundle)
        navController?.navigate(R.id.action_navigation_store_to_navigation_detail, bundle)
    }
}