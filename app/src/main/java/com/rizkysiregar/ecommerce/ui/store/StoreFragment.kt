package com.rizkysiregar.ecommerce.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.DummyStoreData
import com.rizkysiregar.ecommerce.databinding.FragmentStoreBinding
import com.rizkysiregar.ecommerce.databinding.ModalBottomSheetContentBinding

class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = setDummyData()
        recyclerView = binding.rvItem
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = StoreAdapter(data)


        binding.actionChip.setOnClickListener {
            val fragmentManager = childFragmentManager
            val modalBottomSheet = ModelBottomSheet()
            modalBottomSheet.show(fragmentManager, "ModalBottomSheet")
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
        )
    }
}