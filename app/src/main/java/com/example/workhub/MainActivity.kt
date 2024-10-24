package com.example.workhub

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.core_ui.navigation.NavigationUi
import com.example.workhub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationUi {

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

        binding.navView.setupWithNavController(navController)

        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
    }

    override fun navigateToRelevantVacanciesFragment() {
        navController.navigate(R.id.action_navigation_main_to_relevantVacanciesFragment)
    }
}