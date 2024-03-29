package com.rizkysiregar.ecommerce.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.rizkysiregar.ecommerce.databinding.FragmentSearchBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment(), SearchAdapter.OnItemClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchAdapter: SearchAdapter
    private val firebaseAnalytics: FirebaseAnalytics by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val edtSearch = binding.edtSearch
        edtSearch.requestFocus()
        showKeyboard()
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(string: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(string: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun afterTextChanged(string: Editable?) {
                val query = string.toString()
                searchViewModel.searchProduct(query)
            }
        })

        recyclerView = binding.rvSearchList
        searchViewModel.data.observe(viewLifecycleOwner) {
            searchAdapter = SearchAdapter(it.data)
            searchAdapter.setOnItemClickListener(this)
            recyclerView.adapter = searchAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.hasFixedSize()

            val bundleSearch = bundleOf().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, "SearchResult")
            }
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_SEARCH_RESULTS, bundleSearch)
        }
    }


    private fun showKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.edtSearch, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressCircularSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onItemClick(item: String) {
        val navController = view?.findNavController()
        val bundle = Bundle().apply {
            putString("bundle", item)
        }
        setFragmentResult("RESULT", bundle)
        navController?.navigateUp()
    }
}