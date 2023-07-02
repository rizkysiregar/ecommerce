package com.rizkysiregar.ecommerce.ui.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.rizkysiregar.ecommerce.data.network.api.queryErrorState
import com.rizkysiregar.ecommerce.databinding.FragmentStoreBinding
import kotlinx.coroutines.launch
import okio.IOException
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException

class StoreFragment : Fragment(), ProductListAdapter.OnItemProductClickListener, DataPassed {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private val storeViewModel: StoreViewModel by viewModel()
    private lateinit var shimmer: ShimmerFrameLayout
    val adapter = ProductListAdapter()
    private val firebaseAnalytics: FirebaseAnalytics by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

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

        setFragmentResultListener(ModelBottomSheet.RESULT_FILTER) { _, bundle ->
            val receivedFilter =
                bundle.getParcelable<QueryProductModel>(ModelBottomSheet.BUNDLE_FILTER)
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


    private fun getData(queryProductModel: QueryProductModel) {
        storeViewModel.setQuery(queryProductModel)
        binding.rvItem.visibility = View.VISIBLE
        binding.rvItem.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        storeViewModel.product.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)

        }

        lifecycleScope.launch {
            adapter.addLoadStateListener { loadState ->
                val isEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                if (isEmpty) {
                    shimmer.visibility = View.GONE
                    binding.containerLayoutErorr.visibility = View.VISIBLE
                    binding.errorLayout.tvTitleError.text = "Empty"
                    binding.errorLayout.descError.text = "Your request data is unavailable"
                }


                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                shimmer.visibility =
                    if (loadState.refresh is LoadState.Loading) View.VISIBLE else View.GONE


                when (val throwable = errorState?.error) {
                    is IOException -> {
                        if (throwable.localizedMessage == "Failed to connect to $queryErrorState") {
                            binding.containerLayoutErorr.visibility = View.VISIBLE
                            binding.errorLayout.tvTitleError.text = "Connection"
                            binding.errorLayout.descError.text = "Your connection lost"
                            shimmer.visibility = View.GONE
                        }
                    }

                    is HttpException -> {
                        if (throwable.code() == 401) {
                            Toast.makeText(
                                requireContext(),
                                "Your Token is Expires",
                                Toast.LENGTH_SHORT
                            ).show()

                            binding.containerLayoutErorr.visibility = View.VISIBLE
                            binding.errorLayout.tvTitleError.text = "401 Unauthorized"
                            binding.errorLayout.descError.text =
                                "Your Token is Expires pleas login again"
                            shimmer.visibility = View.GONE
                        } else if (throwable.code() == 404) {
                            binding.containerLayoutErorr.visibility = View.VISIBLE
                            binding.errorLayout.tvTitleError.text = "404 Not Found"
                            binding.errorLayout.descError.text =
                                "Your request unavailable"
                            shimmer.visibility = View.GONE

                        } else {
                            binding.containerLayoutErorr.visibility = View.VISIBLE
                            binding.errorLayout.tvTitleError.text = "500 Internal Server Error"
                            binding.errorLayout.descError.text = "Something went wrong"
                            shimmer.visibility = View.GONE
                        }
                    }
                }
            }
        }

        // when button error refresh clicked
        binding.errorLayout.btnAction.setOnClickListener {
            adapter.refresh()
            binding.containerLayoutErorr.visibility = View.GONE
            binding.rvItem.visibility = View.VISIBLE
        }

        // on swipe refresh pulled
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            adapter.refresh()
            binding.swipeRefresh.isRefreshing = false
            binding.rvItem.visibility = View.VISIBLE
            binding.chipGroup.removeAllViews()
        }
    }

    private fun callBottomSheet() {
        val fragmentManager = childFragmentManager
        val modalBottomSheet = ModelBottomSheet()
        modalBottomSheet.setOnDataPassedListener(this)
        modalBottomSheet.show(fragmentManager, "ModalBottomSheet")
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

    override fun onDataPassed(data: QueryProductModel) {
        getData(data)
        Log.d("onDataPassed: ", data.toString())
        binding.chipGroup.removeAllViews()
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

        if (data.highest != null) {
            val chipGroup = binding.chipGroup
            val chip = Chip(requireActivity())
            chip.text = data.highest.toString()
            chipGroup.addView(chip)
        }

        if (data.lowest != null) {
            val chipGroup = binding.chipGroup
            val chip = Chip(requireActivity())
            chip.text = data.lowest.toString()
            chipGroup.addView(chip)
        }
    }
}