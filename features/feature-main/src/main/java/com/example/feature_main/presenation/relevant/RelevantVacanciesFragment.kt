package com.example.feature_main.presenation.relevant

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
import androidx.recyclerview.widget.ConcatAdapter
import com.example.core_ui.navigation.NavigationUi
import com.example.core_ui.utils.SpacingItemDecorator
import com.example.core_ui.utils.ViewModelFactory
import com.example.core_ui.utils.applySystemBarInsets
import com.example.feature_main.databinding.FragmentRelevantVacanciesBinding
import com.example.feature_main.di.DaggerMainFeatureComponent
import com.example.feature_main.di.MainFeatureComponentDependenciesProvider
import com.example.feature_main.presenation.adapter.HeaderVacanciesAdapter
import com.example.feature_main.presenation.adapter.VacanciesAdapter
import com.example.feature_main.presenation.main.MainUiState
import com.example.feature_main.presenation.main.MainViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class RelevantVacanciesFragment : Fragment() {

    private var _binding: FragmentRelevantVacanciesBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentRelevantVacanciesBinding == null")

    private var navigationUi: NavigationUi? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private val vacanciesAdapter by lazy {
        VacanciesAdapter(
            onFavoriteClick = { viewModel.onFavoriteClick(it) },
            onRespondClick = { navigationUi?.navigateFromRelevantToDetailFragment() }
        )
    }

    private val headerAdapter by lazy { HeaderVacanciesAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val componentDependencies =
            (requireActivity().application as MainFeatureComponentDependenciesProvider)
                .getMainFeatureComponentDependencies()
        val component = DaggerMainFeatureComponent.builder()
            .mainFeatureComponentDependencies(componentDependencies)
            .build()

        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navigationUi = activity as NavigationUi
        _binding = FragmentRelevantVacanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.applySystemBarInsets()

        val concatAdapter = ConcatAdapter(headerAdapter, vacanciesAdapter)
        binding.recyclerViewVacancies.adapter = concatAdapter
        binding.recyclerViewVacancies.addItemDecoration(SpacingItemDecorator(context = requireContext(), spacingVertical = 8))
        binding.buttonBack.setOnClickListener { navigationUi?.popUp() }
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
            is MainUiState.Success -> {
                val vacancies = state.data.vacancies
                headerAdapter.setVacancyCount(vacancies.size)
                vacanciesAdapter.submitList(vacancies)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navigationUi = null
        _binding = null
    }
}