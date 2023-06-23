package com.example.topgastroguru.presentation.view.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.FragmentSaveMealBinding

class SaveMealFragment: Fragment(R.layout.fragment_save_meal) {
    private var _binding: FragmentSaveMealBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveMealBinding.inflate(inflater, container, false)
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
        binding.save.setOnClickListener {

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