package com.example.topgastroguru.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.FragmentAllMealsBinding
import com.example.topgastroguru.presentation.contract.MealsContract
import com.example.topgastroguru.presentation.view.viewmodels.AllMealsViewModel
import androidx.lifecycle.Observer
import com.example.topgastroguru.presentation.view.states.MealsState
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import timber.log.Timber

class AllMealsFragment : Fragment(R.layout.fragment_all_meals) {

    private var _binding: FragmentAllMealsBinding? = null
    private val mealsViewModel: MealsContract.ViewModel by activityViewModel<AllMealsViewModel>()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllMealsBinding.inflate(inflater, container, false)
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

    }

    private fun initObservers() {
        mealsViewModel.mealsState.observe(viewLifecycleOwner, Observer {
            Timber.e("desilo se")
            renderState(it);
        })

        mealsViewModel.fetchMealsByFirstLetter('a')
    }

    private fun renderState(state: MealsState) {
        when (state) {
            is MealsState.Success -> {
                showLoadingState(false)
                Toast.makeText(context, "Success " + state.meals.size, Toast.LENGTH_SHORT).show()
//                adapter.submitList(state.meals)
            }
            is MealsState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        //TODO hide others
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}