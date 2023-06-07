package com.rizkysiregar.ecommerce.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.databinding.FragmentReviewBinding
import com.rizkysiregar.ecommerce.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception


class ReviewFragment : Fragment() {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    private val reviewViewModel: ReviewViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("PRODUCT_REVIEW_ID") { _, bundle ->
            val receivedData = bundle.getString("productReviewId")
            try {
                reviewViewModel.getReviewById(receivedData.toString())
            }catch (e : Exception){
                Toast.makeText(requireContext(),e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        setDataReviewToAdapter()
    }

    private fun setDataReviewToAdapter(){
        reviewViewModel.data.observe(viewLifecycleOwner){
            recyclerView = binding.rvReview
            val reviewAdapter = ReviewAdapter(it.data)
            recyclerView.adapter = reviewAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.hasFixedSize()
        }
    }
}