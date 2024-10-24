package com.example.feature_main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.core_ui.navigation.NavigationUi

class MainFragment : Fragment() {

    private var navigationUi: NavigationUi? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navigationUi = activity as NavigationUi
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView =  view.findViewById<TextView>(R.id.label)
        textView.setOnClickListener {
            navigationUi?.navigateToRelevantVacanciesFragment()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        navigationUi = null
    }
}