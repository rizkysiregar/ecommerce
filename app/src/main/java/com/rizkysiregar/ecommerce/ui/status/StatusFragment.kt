package com.rizkysiregar.ecommerce.ui.status

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.analytics.FirebaseAnalytics
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.RatingModel
import com.rizkysiregar.ecommerce.data.network.response.FulFillmentResponse
import com.rizkysiregar.ecommerce.databinding.FragmentCheckoutBinding
import com.rizkysiregar.ecommerce.databinding.FragmentStatusBinding
import com.rizkysiregar.ecommerce.ui.checkout.CheckoutViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class StatusFragment : Fragment() {

    private var _binding: FragmentStatusBinding? = null
    private val binding get() = _binding!!
    private val args: StatusFragmentArgs by navArgs()
    private val statusViewModel: StatusViewModel by viewModel()
    private val firebaseAnalytics: FirebaseAnalytics by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatusBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataStatus: FulFillmentResponse = args.statusTransaction
        binding.tvStatus.text = if (dataStatus.data.status) "Berhasil" else "Gagal"
        binding.tvTotalPayStatus.text = dataStatus.data.total.toString()
        binding.tvTime.text = dataStatus.data.time
        binding.tvDate.text = dataStatus.data.date
        binding.tvIdTransaction.text = dataStatus.data.invoiceId
        binding.tvPayMethod.text = dataStatus.data.payment

        val review = binding.review.text.toString()
        val rating = binding.ratingbarStatus.rating.toInt()
        binding.btnDoneStatus.setOnClickListener {
            val model = RatingModel(review, rating, dataStatus.data.invoiceId)
            statusViewModel.postStatusOrder(model)

            statusViewModel.data.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                val navController = view.findNavController()
                navController.navigate(R.id.action_navigation_status_to_navigation_home)

                val buttonClick = Bundle().apply {
                    putString(FirebaseAnalytics.Param.ITEMS, it.message)
                }
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, buttonClick)
            }
        }
    }
}