package com.rizkysiregar.ecommerce.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.rizkysiregar.ecommerce.data.model.QueryProductModel
import com.rizkysiregar.ecommerce.databinding.ModalBottomSheetContentBinding

class ModelBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: ModalBottomSheetContentBinding
    private var onDataPassedListener: DataPassed? = null
    private val storeViewModel : StoreViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ModalBottomSheetContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // declaration variable
        var search: String = ""
        var brand: String = ""
        var lowest: Int = 0
        var highest: Int = 0
        var sort: String = ""

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
        }

        binding.chipGroupCategory1.setOnCheckedChangeListener { group, checkedId ->
            val checkIds = binding.chipGroupCategory1.checkedChipIds
            for (chipId in checkIds) {
                val chip = binding.chipGroupCategory1.findViewById<Chip>(chipId)
                val selectedValue = chip.text.toString()
                search = selectedValue
                brand = selectedValue
            }
        }

        binding.btnShowProduct.setOnClickListener {
            lowest = binding.edtTerendah.text.toString().toInt()
            highest = binding.edtTertinggi.text.toString().toInt()
            val data = QueryProductModel(search, brand, lowest, highest, sort)
            onDataPassedListener?.onDataPassed(data)
            dismiss()
        }
    }

    fun setOnDataPassedListener(listener: DataPassed) {
        onDataPassedListener = listener
    }
}