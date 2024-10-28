package com.example.feature_main.presenation.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.core_ui.navigation.NavigationUi
import com.example.core_ui.utils.SpacingItemDecorator
import com.example.core_ui.utils.ViewModelFactory
import com.example.core_ui.utils.applySystemBarInsets
import com.example.feature_main.R.plurals
import com.example.feature_main.databinding.FragmentMainBinding
import com.example.feature_main.di.DaggerMainComponent
import com.example.feature_main.di.MainComponentDependenciesProvider
import com.example.feature_main.presenation.MainViewModel
import com.example.feature_main.presenation.adapter.OffersAdapter
import com.example.feature_main.presenation.adapter.VacanciesAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private var navigationUi: NavigationUi? = null

    private val offersAdapter by lazy { OffersAdapter() }

    private val vacanciesAdapter by lazy {
        VacanciesAdapter(
            onFavoriteClick = { viewModel.toggleFavorite(it) },
            onRespondClick = { navigationUi?.navigateFromMainToDetailFragment() }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val componentDependencies =
            (requireActivity().application as MainComponentDependenciesProvider)
            .getMainFeatureComponentDependencies()
        val component = DaggerMainComponent.builder()
            .mainComponentDependencies(componentDependencies)
            .build()

        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navigationUi = activity as NavigationUi
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.applySystemBarInsets()
        initRecyclerView()
        observeContent()
    }

    private fun observeContent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.state.collect { state -> setScreenState(state) }
                }
            }
        }
    }

    private fun setScreenState(state: MainUiState) {
        when (state) {
            is MainUiState.Error -> Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            MainUiState.Initial -> {}
            MainUiState.Loading -> {}
            is MainUiState.Success -> setupAdapters(state)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateVacancies()
    }

    private fun initRecyclerView() {
        binding.apply {
            recyclerViewOffers.adapter = offersAdapter
            recyclerViewVacancies.adapter = vacanciesAdapter
            recyclerViewOffers.addItemDecoration(SpacingItemDecorator(context = requireContext(), spacingHorizontal = 4))
            recyclerViewVacancies.addItemDecoration(SpacingItemDecorator(context = requireContext(), spacingVertical = 8))
        }
    }

    private fun setupAdapters(state: MainUiState.Success) {
        val vacancies = state.data.vacancies
        offersAdapter.submitList(state.data.offers)
        vacanciesAdapter.submitList(vacancies.take(3))
        setupButtonMoreVacancies(vacancies.size)
    }

    private fun setupButtonMoreVacancies(vacancies: Int) {
        val vacanciesCount = vacancies - 3
        binding.moreVacanciesButton.apply {
            visibility = View.VISIBLE
            text = requireContext().resources.getQuantityString(
                plurals.more_vacancies, vacanciesCount, vacanciesCount
            )
            setOnClickListener { navigationUi?.navigateToRelevantVacanciesFragment() }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        navigationUi = null
        _binding = null
    }
}