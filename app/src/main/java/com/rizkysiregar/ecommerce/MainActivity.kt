package com.rizkysiregar.ecommerce

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.rizkysiregar.ecommerce.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModel()
    private val badge by lazy { BadgeDrawable.create(this@MainActivity) }

    @ExperimentalBadgeUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        setupNavigation()
        mainActivityViewModel.getItemCountWishList.observe(this) {
            binding.navView.getOrCreateBadge(R.id.navigation_wishlist).apply {
                isVisible = it != 0
                number = it
            }
        }

        mainActivityViewModel.getItemCountCart.observe(this) { itemCount ->
            binding.materialToolbar.viewTreeObserver.addOnGlobalLayoutListener {
                badge.isVisible = itemCount != 0
                badge.number = itemCount
                BadgeUtils.attachBadgeDrawable(badge, binding.materialToolbar, R.id.navigation_cart)
            }
        }

        // [START subscribe_topics]
        Firebase.messaging.subscribeToTopic("promo")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                }
                Log.d(TAG, msg)
            }
        // [END subscribe_topics]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_container)
        navView.setupWithNavController(navController)


        // set appbar and bottom nav visibility in particular fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_search -> {
                    navView.visibility = View.GONE
                    binding.appBarLayout.visibility = View.GONE
                }

                R.id.navigation_detail -> {
                    binding.materialToolbar.setNavigationOnClickListener {
                        findNavController(R.id.nav_host_fragment_container).navigateUp()
                    }
                    navView.visibility = View.GONE
                    binding.materialToolbar.title = "Detail Product"
                    binding.materialToolbar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
                }

                R.id.navigation_review -> {
                    binding.materialToolbar.setNavigationOnClickListener {
                        findNavController(R.id.nav_host_fragment_container).navigateUp()
                    }
                    navView.visibility = View.GONE
                    binding.materialToolbar.title = "Ulasan Pembeli"
                    binding.materialToolbar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
//                    binding.materialToolbar.menu.clear()
                }

                R.id.navigation_cart -> {
                    navView.visibility = View.GONE
                    binding.materialToolbar.title = "Cart"
                    binding.materialToolbar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
                    binding.materialToolbar.setNavigationOnClickListener {
                        findNavController(R.id.nav_host_fragment_container).navigateUp()
                    }
                }

                R.id.navigation_payment -> {
                    navView.visibility = View.GONE
                    binding.materialToolbar.title = "Pilih Pembayaran"
                    binding.materialToolbar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
                    binding.materialToolbar.setNavigationOnClickListener {
                        findNavController(R.id.nav_host_fragment_container).navigateUp()
                    }
                }

                R.id.navigation_checkout -> {
                    navView.visibility = View.GONE
                    binding.materialToolbar.title = "Checkout"
                    binding.materialToolbar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
                    binding.materialToolbar.setNavigationOnClickListener {
                        findNavController(R.id.nav_host_fragment_container).navigateUp()
                    }
                }

                R.id.navigation_status -> {
                    navView.visibility = View.GONE
                    binding.materialToolbar.title = "Status"
                    binding.materialToolbar.textAlignment = View.TEXT_ALIGNMENT_CENTER
                }

                R.id.navigation_notification -> {
                    binding.materialToolbar.title = "Notification"
                    binding.materialToolbar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)
                    binding.materialToolbar.setNavigationOnClickListener {
                        findNavController(R.id.nav_host_fragment_container).navigateUp()
                    }
                    navView.visibility = View.GONE
                }

                else -> {
                    navView.visibility = View.VISIBLE
                    binding.appBarLayout.visibility = View.VISIBLE
                    binding.materialToolbar.title = "RizkySiregar"
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> {
                findNavController(R.id.nav_host_fragment_container).navigate(R.id.navigation_cart)
                return true
            }

            R.id.menu_notification -> {
                findNavController(R.id.nav_host_fragment_container).navigate(R.id.navigation_notification)
                return true
            }

            R.id.menu_hamburger -> {
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }

}