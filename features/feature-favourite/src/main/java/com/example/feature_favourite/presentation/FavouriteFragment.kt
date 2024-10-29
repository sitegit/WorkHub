package com.example.feature_favourite.presentation

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
import com.example.core_ui.R.plurals
import com.example.core_ui.navigation.NavigationUi
import com.example.core_ui.utils.SpacingItemDecorator
import com.example.core_ui.utils.ViewModelFactory
import com.example.core_ui.utils.applySystemBarInsets
import com.example.feature_favourite.databinding.FragmentFavouriteBinding
import com.example.feature_favourite.di.DaggerFavouriteComponent
import com.example.feature_favourite.di.FavouriteComponentDependenciesProvider
import com.example.feature_favourite.domain.Vacancy
import com.example.feature_favourite.presentation.adapter.VacanciesAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentFavouriteBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavouriteViewModel::class.java]
    }

    private var navigationUi: NavigationUi? = null

    private val vacanciesAdapter by lazy {
        VacanciesAdapter(
            onFavoriteClick = { viewModel.removeVacancy(it.id) },
            onRespondClick = { navigationUi?.navigateFromFavouriteToDetailFragment() }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val componentDependencies =
            (requireActivity().application as FavouriteComponentDependenciesProvider)
                .getFavouriteComponentDependencies()
        val component = DaggerFavouriteComponent.builder()
            .favouriteComponentDependencies(componentDependencies)
            .build()

        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navigationUi = activity as NavigationUi
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
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
                    viewModel.state.collect { state ->
                        setContent(state)
                    }
                }
            }
        }
    }

    private fun setContent(state: FavouriteUiState) {
        when (state) {
            is FavouriteUiState.Error -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                binding.progressCircular.visibility = View.GONE
            }
            FavouriteUiState.Initial -> {}
            FavouriteUiState.Loading -> binding.progressCircular.visibility = View.VISIBLE
            is FavouriteUiState.Success -> {
                setData(state.data)
                binding.progressCircular.visibility = View.GONE
            }
        }
    }

    private fun setData(data: List<Vacancy>) {
        val dataSize = data.size
        binding.vacanciesCount.text = requireContext().resources.getQuantityString(
            plurals.total_vacancies, dataSize, dataSize
        )
        vacanciesAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        binding.apply {
            recyclerViewVacancies.adapter = vacanciesAdapter
            recyclerViewVacancies.addItemDecoration(SpacingItemDecorator(context = requireContext(), spacingVertical = 8))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navigationUi = null
        _binding = null
    }
}