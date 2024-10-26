package com.example.workhub

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.core_ui.R.color
import com.example.core_ui.navigation.NavigationUi
import com.example.core_ui.utils.BadgeUpdateListener
import com.example.core_ui.utils.toPx
import com.example.workhub.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable

class MainActivity : AppCompatActivity(), NavigationUi, BadgeUpdateListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        setupNavController()
        createBadge()

    }

    override fun updateBadge(count: Int) {
        binding.navView.getOrCreateBadge(R.id.favourite_nav_graph).apply {
            number = count
            isVisible = count > 0
        }
    }

    private fun createBadge() {
        binding.navView.getOrCreateBadge(R.id.favourite_nav_graph).apply {
            backgroundColor = ContextCompat.getColor(this@MainActivity, color.red)
            badgeTextColor = ContextCompat.getColor(this@MainActivity, color.white)
            badgeGravity = BadgeDrawable.TOP_END
            horizontalOffset = 4.toPx(this@MainActivity)
            verticalOffset = 5.toPx( this@MainActivity)
            maxCharacterCount = 3
            isVisible = false
        }
    }

    private fun setupNavController() {
        binding.navView.setupWithNavController(navController)
    }

    override fun navigateToRelevantVacanciesFragment() {
        navController.navigate(R.id.action_navigation_main_to_relevantVacanciesFragment)
    }

    override fun navigateFromRelevantToDetailFragment() {
        navController.navigate(R.id.action_relevantVacanciesFragment_to_detailFragment)
    }

    override fun navigateFromMainToDetailFragment() {
        navController.navigate(R.id.action_navigation_main_to_detailFragment)
    }

    override fun popUp() {
        navController.popBackStack()
    }
}