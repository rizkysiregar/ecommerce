package com.rizkysiregar.ecommerce.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.ModalBottomSheetContentBinding

class ModelBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: ModalBottomSheetContentBinding
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

        var bundle: Bundle? = null

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
            val selectedValues = mutableListOf<String>()

            for (chipId in checkChipIds) {
                val chip = binding.chipGroupOrdered.findViewById<Chip>(chipId)
                val selectedValue = chip.text.toString()
                selectedValues.add(selectedValue)

                bundle?.putStringArray("filter", selectedValues.toTypedArray())
            }
        }

        binding.btnShowProduct.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_bottonSheetDialogFragment_to_StoreFragment, bundle)
            dismiss()
        }
    }
}