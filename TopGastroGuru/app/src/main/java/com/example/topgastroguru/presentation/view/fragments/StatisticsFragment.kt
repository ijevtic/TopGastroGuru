package com.example.topgastroguru.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.FragmentStatisticsBinding
import com.example.topgastroguru.presentation.contract.MealEntityContract
import com.example.topgastroguru.presentation.view.viewmodels.MealEntityViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {
    private val mealEntityViewModel: MealEntityContract.ViewModel by activityViewModel<MealEntityViewModel>()

    private var _binding: FragmentStatisticsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
//        Test DB
        binding.button.setOnClickListener {
            mealEntityViewModel.getAllMeals()
        }
    }

    private fun initObservers() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}