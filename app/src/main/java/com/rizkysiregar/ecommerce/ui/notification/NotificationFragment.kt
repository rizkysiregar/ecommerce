package com.rizkysiregar.ecommerce.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.rizkysiregar.ecommerce.R
import com.rizkysiregar.ecommerce.data.model.NotificationEntity
import com.rizkysiregar.ecommerce.databinding.FragmentNotificationBinding
import com.rizkysiregar.ecommerce.databinding.FragmentSearchBinding
import com.rizkysiregar.ecommerce.ui.search.SearchAdapter
import com.rizkysiregar.ecommerce.ui.search.SearchViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment(), NotificationAdapter.OnItemClickListener {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private val notificationViewModel: NotificationViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private val firebaseAnalytics: FirebaseAnalytics by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerview()
    }


    private fun setRecyclerview() {
        recyclerView = binding.rvNotification
        notificationViewModel.getAllDataNotification.observe(viewLifecycleOwner) {

            if (it.isNullOrEmpty()) {
                binding.containerLayoutErrorNotification.visibility = View.VISIBLE
                binding.errorLayout.tvTitleError.text = "Empty"
                binding.errorLayout.descError.text = "Your request data is unavailable"
            } else {
                binding.containerLayoutErrorNotification.visibility = View.GONE
            }

            notificationAdapter = NotificationAdapter(it)
            notificationAdapter.setOnItemClickListener(this)
            recyclerView.adapter = notificationAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.hasFixedSize()
        }
    }

    override fun onItemClick(item: NotificationEntity) {
        if (item.isRead) {
            Toast.makeText(requireContext(), "Has been read", Toast.LENGTH_SHORT).show()
        } else {
            notificationViewModel.setIsRead(item, true)
        }
    }

}