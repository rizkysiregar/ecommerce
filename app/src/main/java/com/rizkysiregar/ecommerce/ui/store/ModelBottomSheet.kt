package com.rizkysiregar.ecommerce.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.firebase.analytics.FirebaseAnalytics
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.databinding.ModalBottomSheetContentBinding
import org.koin.android.ext.android.inject

class ModelBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: ModalBottomSheetContentBinding

    var search: String = ""
    var brand: String = ""
    var lowest: Int = 0
    var highest: Int = 0
    var sort: String = ""
    private var onDataPassedListener: DataPassed? = null
    private val firebaseAnalytics: FirebaseAnalytics by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ModalBottomSheetContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // declaration variable

        binding.btnReset.setOnClickListener {
            binding.chipApple.isChecked = false
            binding.chipAsus.isChecked = false
            binding.chipDell.isChecked = false
            binding.chipLenovo.isChecked = false
            binding.chipPenjualan.isChecked = false
            binding.chipUlasan.isChecked = false
            binding.chipHargaTertinggi.isChecked = false
            binding.chipHargaTerendah.isChecked = false
        }

        binding.chipGroupOrdered.setOnCheckedChangeListener { group, checkedId ->
            val checkChipIds = binding.chipGroupOrdered.checkedChipIds

            for (chipId in checkChipIds) {
                val chip = binding.chipGroupOrdered.findViewById<Chip>(chipId)
                val selectedValue = chip.text.toString()
                sort = selectedValue
            }

            val paymentInfoBundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEMS, checkChipIds.toString())
            }
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, paymentInfoBundle)
        }

        binding.chipGroupCategory1.setOnCheckedChangeListener { group, checkedId ->

            val checkIds = binding.chipGroupCategory1.checkedChipIds
            for (chipId in checkIds) {
                val chip = binding.chipGroupCategory1.findViewById<Chip>(chipId)
                val selectedValue = chip.text.toString()
                search = selectedValue
                brand = selectedValue
            }

            val paymentInfoBundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEMS, checkIds.toString())
            }
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, paymentInfoBundle)
        }

        binding.btnShowProduct.setOnClickListener {
            try {
                lowest = binding.edtTerendah.text.toString().toInt()
                highest = binding.edtTertinggi.text.toString().toInt()
                val data = QueryProductModel(search, brand, lowest, highest, sort)
                // nav args
                selectedProducts(queryProductModel = data)
            } catch (e: Exception) {
                Toast.makeText(requireActivity(), e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectedProducts(queryProductModel: QueryProductModel) {
        val data = QueryProductModel(
            search = queryProductModel.search,
            brand = queryProductModel.brand,
            lowest = queryProductModel.lowest,
            highest = queryProductModel.highest,
            sort = queryProductModel.sort
        )
        onDataPassedListener?.onDataPassed(data)
//        val bundle = Bundle().apply {
//            putParcelable(BUNDLE_FILTER, data)
//        }
//        setFragmentResult(RESULT_FILTER, bundle)
        dismiss()
    }

    companion object {
        const val BUNDLE_FILTER = "bundleFilter"
        const val RESULT_FILTER = "resultFilter"
    }
}