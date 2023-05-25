package com.rizkysiregar.ecommerce.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.DummyStoreData
import com.rizkysiregar.ecommerce.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = setDummyData()
        var isLinear = true

        recyclerView = binding.rvItem
        recyclerView.adapter = StoreAdapter(data)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val listFilter = requireArguments().getStringArray("filter")
        val list: MutableList<String> = listFilter?.toMutableList() ?: mutableListOf()

        binding.listRv.setOnClickListener {
            isLinear = !isLinear
            if (isLinear) {
                binding.listRv.setImageResource(R.drawable.baseline_format_list_bulleted_24)
                recyclerView = binding.rvItem
                recyclerView.adapter = StoreAdapter(data)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.hasFixedSize()
            } else {
                binding.listRv.setImageResource(R.drawable.baseline_grid_view_24)
                recyclerView = binding.rvItem
                recyclerView.adapter = StoreAdapterGrid(data)
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
        modalBottomSheet.show(fragmentManager, "ModalBottomSheet")
    }


    private fun setDummyData(): List<DummyStoreData> {
        return listOf(
            DummyStoreData(
                R.drawable.thumbnail,
                "Lenovo Legion 7 16 I7 11800 16GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
                2900000,
                "Enter Computer",
                "5.0",
                10
            ),
            DummyStoreData(
                R.drawable.thumbnail,
                "Lenovo Legion 7 16 I7 11800 16GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
                2900000,
                "Enter Computer",
                "5.0",
                10
            ),
            DummyStoreData(
                R.drawable.thumbnail,
                "Lenovo Legion 7 16 I7 11800 16GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
                2900000,
                "Enter Computer",
                "5.0",
                10
            ),
            DummyStoreData(
                R.drawable.thumbnail,
                "Lenovo Legion 7 16 I7 11800 16GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
                2900000,
                "Enter Computer",
                "5.0",
                10
            ),
            DummyStoreData(
                R.drawable.thumbnail,
                "Lenovo Legion 7 16 I7 11800 16GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
                2900000,
                "Enter Computer",
                "5.0",
                10
            ),
            DummyStoreData(
                R.drawable.thumbnail,
                "Lenovo Legion 7 16 I7 11800 16GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
                2900000,
                "Enter Computer",
                "5.0",
                10
            ),
            DummyStoreData(
                R.drawable.thumbnail,
                "Lenovo Legion 7 16 I7 11800 16GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
                2900000,
                "Enter Computer",
                "5.0",
                10
            ),
            DummyStoreData(
                R.drawable.thumbnail,
                "Lenovo Legion 7 16 I7 11800 16GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
                2900000,
                "Enter Computer",
                "5.0",
                10
            ),
        )
    }
}