package com.example.workhub

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.core_db.FavouriteVacanciesDao
import com.example.core_ui.R.color
import com.example.core_ui.navigation.NavigationUi
import com.example.core_ui.utils.toPx
import com.example.workhub.databinding.ActivityMainBinding
import com.example.workhub.di.DaggerApplicationComponent
import com.google.android.material.badge.BadgeDrawable
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationUi {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var vacanciesDao: FavouriteVacanciesDao

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerApplicationComponent.factory().create(this).inject(this)
        setContentView(binding.root)

        enableEdgeToEdge()
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        setupNavigation()
        createBadge()
        observeBadge()
    }

    private fun observeBadge() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    vacanciesDao.getFavouriteVacanciesCount().collect { count ->
                        updateBadge(count)
                    }
                }
            }
        }
    }

    private fun updateBadge(count: Int) {
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

    private fun setupNavigation() {
        binding.navView.setupWithNavController(navController)
        binding.navView.setOnItemReselectedListener { }

        binding.navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search_nav_graph -> {
                    navController.navigate(
                        R.id.search_nav_graph,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(navController.graph.startDestinationId, true)
                            .build()
                    )
                }
                else -> {
                    navController.navigate(menuItem.itemId)
                }
            }
            true
        }
    }

    override fun navigateToRelevantVacanciesFragment() {
        navController.navigate(R.id.action_mainFragment_to_relevantVacanciesFragment)
    }

    override fun navigateFromRelevantToDetailFragment() {
        navController.navigate(R.id.action_relevantVacanciesFragment_to_detailFragment)
    }

    override fun navigateFromFavouriteToDetailFragment() {
        navController.navigate(R.id.action_favouriteFragment_to_detailFragment)
    }

    override fun navigateFromMainToDetailFragment() {
        navController.navigate(R.id.action_mainFragment_to_detailFragment)
    }

    override fun popUp() {
        navController.popBackStack()
    }
}