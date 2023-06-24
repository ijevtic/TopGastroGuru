package com.example.topgastroguru.presentation.view.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.FragmentMealDetailedBinding
import com.example.topgastroguru.presentation.view.viewmodels.MealDetailedlViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealDetailedFragment: Fragment(R.layout.fragment_meal_detailed) {

    private val mealDetailedVM: MealDetailedlViewModel by viewModel<MealDetailedlViewModel>()

    private var _binding: FragmentMealDetailedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealDetailedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        initObservers()
    }

    private fun initView() {

        binding.save.setOnClickListener {
//            (activity as MainActivity?)?.addFragment(SaveMealFragment())
        }
    }

    private fun initObservers() {
//        mainViewModel.addDone.observe(viewLifecycleOwner, Observer {
//            renderState(it)
//        })
    }

//    private fun renderState(state: AddMovieState) {
//        when(state) {
//            is AddMovieState.Success -> Toast.makeText(context, "Movie added", Toast.LENGTH_SHORT)
//                .show()
//            is AddMovieState.Error -> Toast.makeText(context, "Error happened", Toast.LENGTH_SHORT)
//                .show()
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}