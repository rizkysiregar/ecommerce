package com.rizkysiregar.ecommerce.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.FilterModel
import com.rizkysiregar.ecommerce.databinding.FragmentStoreBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoreFragment : Fragment(), DataPassed {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val storeViewModel: StoreViewModel by viewModel()
//    private lateinit var dataFilter: FilterModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        storeViewModel.getProduct(null, null, null, null, null)
        var isLinear = true
        recyclerView = binding.rvItem
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        storeViewModel.data.observe(viewLifecycleOwner) {
            recyclerView.adapter = StoreAdapterGrid(it.data.items)
            recyclerView.adapter = StoreAdapter(it.data.items)
        }

        binding.edtSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val navController = view.findNavController()
                navController.navigate(R.id.action_navigation_store_to_navigation_search)
            }
        }

        binding.listRv.setOnClickListener {
            isLinear = !isLinear
            if (isLinear) {
                binding.listRv.setImageResource(R.drawable.baseline_format_list_bulleted_24)
                storeViewModel.data.observe(viewLifecycleOwner) {
                    recyclerView.adapter = StoreAdapter(it.data.items)
                }
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.hasFixedSize()
            } else {
                binding.listRv.setImageResource(R.drawable.baseline_grid_view_24)
                storeViewModel.data.observe(viewLifecycleOwner) {
                    recyclerView.adapter = StoreAdapterGrid(it.data.items)
                }
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerView.hasFixedSize()
            }
        }

        // bottom sheet
        binding.actionChip.setOnClickListener {
            callBottomSheet()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun callBottomSheet() {
        val fragmentManager = childFragmentManager
        val modalBottomSheet = ModelBottomSheet()
        modalBottomSheet.setOnDataPassedListener(this)
        modalBottomSheet.show(fragmentManager, "ModalBottomSheet")
    }

    override fun onDataPassed(data: FilterModel) {
        storeViewModel.getProduct(data.search, data.brand, data.lowest, data.highest, data.sort)
        // assign new value for query
//        dataFilter = data
        // assign for chip
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressCircularStore.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}