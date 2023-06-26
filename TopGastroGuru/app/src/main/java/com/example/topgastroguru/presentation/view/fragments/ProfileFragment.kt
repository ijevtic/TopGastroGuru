package com.example.topgastroguru.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.FragmentProfileBinding
import com.example.topgastroguru.presentation.contract.MealDetaildContract
import com.example.topgastroguru.presentation.contract.MealEntityContract
import com.example.topgastroguru.presentation.view.viewmodels.MealDetailedViewModel
import com.example.topgastroguru.presentation.view.viewmodels.MealEntityViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val mealDetailedVM: MealDetaildContract.ViewModel by activityViewModel<MealDetailedViewModel>()
    private val mealEntityViewModel: MealEntityContract.ViewModel by activityViewModel<MealEntityViewModel>()

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private var testBT: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
        testBT = binding.button
        testBT!!.setOnClickListener(View.OnClickListener {
            Timber.d("test")
            mealEntityViewModel.getAllMeals()
        })
    }

    private fun initObservers() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}