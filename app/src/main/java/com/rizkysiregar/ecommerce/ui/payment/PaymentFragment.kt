package com.rizkysiregar.ecommerce.ui.payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.network.response.DataItem
import com.rizkysiregar.ecommerce.data.network.response.ItemsPayment
import com.rizkysiregar.ecommerce.databinding.FragmentDetailBinding
import com.rizkysiregar.ecommerce.databinding.FragmentPaymentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PaymentFragment : Fragment(), ParentAdapter.OnItemClickListener {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ParentAdapter
    private lateinit var recyclerView: RecyclerView
//    private val paymentViewModel: PaymentViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG, "Config param updated $updated")

                    val payment = remoteConfig.getString("payment")
                    val gson = Gson()
                    val paymentListType = object : TypeToken<List<DataItem>>() {}.type
                    val paymentList: List<DataItem> = gson.fromJson(payment, paymentListType)

                    setRecyclerview(paymentList)
                    Toast.makeText(
                        requireContext(),
                        "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Fetch failed",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun setRecyclerview(data : List<DataItem>) {
        recyclerView = binding.parentRecyclerview
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            adapter = ParentAdapter(data)
            adapter.setOnItemClickListener(this)
            recyclerView.adapter = adapter
    }

    override fun onItemClick(item: ItemsPayment) {
        val navController = view?.findNavController()
        val bundle = Bundle().apply {
            putParcelable(BUNDLE_PAYMENT, item)
        }
        setFragmentResult(RESULT_KEY, bundle)
        navController?.navigateUp()
    }

    companion object {
        const val BUNDLE_PAYMENT = "selectedPayment"
        const val RESULT_KEY = "PAYMENT_METHOD"
        const val TAG = "FragmentPayment"
    }
}