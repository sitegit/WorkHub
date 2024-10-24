package com.example.feature_main.presenation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.core_ui.navigation.NavigationUi
import com.example.core_ui.utils.ViewModelFactory
import com.example.feature_main.databinding.FragmentMainBinding
import com.example.feature_main.di.DaggerMainFeatureComponent
import com.example.feature_main.di.MainFeatureComponentDependenciesProvider
import javax.inject.Inject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    private var navigationUi: NavigationUi? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

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
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            viewModel.getVacancies()
//        val textView =  view.findViewById<TextView>(R.id.label)
//        textView.setOnClickListener {
//            navigationUi?.navigateToRelevantVacanciesFragment()
//        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        navigationUi = null
        _binding = null
    }
}