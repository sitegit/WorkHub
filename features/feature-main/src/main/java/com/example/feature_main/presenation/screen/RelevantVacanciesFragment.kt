package com.example.feature_main.presenation.screen

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
import com.example.core_ui.R
import com.example.core_ui.navigation.NavigationUi
import com.example.core_ui.utils.SpacingItemDecorator
import com.example.core_ui.utils.ViewModelFactory
import com.example.core_ui.utils.applySystemBarInsets
import com.example.feature_main.databinding.FragmentRelevantVacanciesBinding
import com.example.feature_main.di.DaggerMainComponent
import com.example.feature_main.di.MainComponentDependenciesProvider
import com.example.feature_main.domain.entity.Vacancy
import com.example.feature_main.presenation.MainUiState
import com.example.feature_main.presenation.MainViewModel
import com.example.feature_main.presenation.adapter.VacanciesAdapter
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
            onFavoriteClick = { viewModel.toggleFavorite(it) },
            onRespondClick = { navigationUi?.navigateFromRelevantToDetailFragment() }
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
        _binding = FragmentRelevantVacanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.applySystemBarInsets()

        viewModel.updateVacancies()
        initRecyclerView()
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
            is MainUiState.Error -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                binding.progressCircular.visibility = View.GONE
            }
            MainUiState.Initial -> {}
            MainUiState.Loading -> binding.progressCircular.visibility = View.VISIBLE
            is MainUiState.Success -> {
                setContent(state.data.vacancies)
                binding.progressCircular.visibility = View.GONE
            }
        }
    }

    private fun setContent(vacancies: List<Vacancy>) {
        val vacancyCount = vacancies.size
        binding.vacanciesCountTv.text = binding.root.context.resources.getQuantityString(
            R.plurals.total_vacancies, vacancyCount, vacancyCount
        )
        vacanciesAdapter.submitList(vacancies)
    }

    private fun initRecyclerView() {
        binding.recyclerViewVacancies.adapter = vacanciesAdapter
        binding.recyclerViewVacancies.addItemDecoration(SpacingItemDecorator(context = requireContext(), spacingVertical = 8))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navigationUi = null
        _binding = null
    }
}