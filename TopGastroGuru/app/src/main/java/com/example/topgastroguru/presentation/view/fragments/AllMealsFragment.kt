package com.example.topgastroguru.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.FragmentAllMealsBinding
import com.example.topgastroguru.presentation.contract.MealsContract
import com.example.topgastroguru.presentation.view.viewmodels.AllMealsViewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topgastroguru.presentation.view.activities.MainActivity
import com.example.topgastroguru.presentation.view.activities.recycler.adapter.MealAdapter
import com.example.topgastroguru.presentation.view.states.MealsState
import com.example.topgastroguru.presentation.view.states.ParameterState
import com.example.topgastroguru.presentation.view.viewmodels.MealDetailedViewModel
import com.example.topgastroguru.presentation.view.viewmodels.ParameterViewModel
import com.example.topgastroguru.util.SortType
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import timber.log.Timber

class AllMealsFragment : Fragment(R.layout.fragment_all_meals), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentAllMealsBinding? = null
    private val mealsViewModel: MealsContract.ViewModel by activityViewModel<AllMealsViewModel>()
    private val parameterViewModel: ParameterViewModel by activityViewModel<ParameterViewModel>()
    private val mealDetailedViewModel: MealDetailedViewModel by activityViewModel<MealDetailedViewModel>()
    private val sortList = arrayOf(SortType.NONE, SortType.ABC, SortType.CALORIES)
    private val binding get() = _binding!!

    private lateinit var adapter: MealAdapter

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
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)

        adapter = MealAdapter { meal ->
            Toast.makeText(context, "clicked on meal: ${meal.id}", Toast.LENGTH_SHORT).show()
            mealDetailedViewModel.fetchMealById(meal.id)
//            viewModel.selectedParameterState.value = ParameterState.Selected(parameter)
//            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.listRv.adapter = adapter
    }

    private fun initListeners() {
        binding.inputEt.doAfterTextChanged {
            mealsViewModel.updateSearchQuery(it.toString())
        }

        binding.filterBtn.setOnClickListener {
            (activity as MainActivity?)?.addFragmentHide(FilterFragment())
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sort_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.sortSpinner.adapter = adapter
        }
        binding.sortSpinner.onItemSelectedListener = this



//        binding.sortSpinner.setOnClickListener {
//            (activity as MainActivity?)?.addFragmentHide(SortFragment())
//        }
    }

    private fun initObservers() {
        mealsViewModel.mealsState.observe(viewLifecycleOwner, Observer {
            Timber.e("desilo se")
            renderState(it);
        })

        parameterViewModel.selectedParameterState.observe(viewLifecycleOwner, Observer {
            if(it is ParameterState.Selected) {
                mealsViewModel.setFilter(it.parameter)
            }
            else
                mealsViewModel.setFilter(null)
        })

    }

    private fun renderState(state: MealsState) {
        when (state) {
            is MealsState.Success -> {
                showLoadingState(false)
                Toast.makeText(context, "Success " + state.meals.size, Toast.LENGTH_SHORT).show()
                adapter.submitList(state.meals)
            }
            is MealsState.Error -> {
                showLoadingState(false)
                adapter.submitList(listOf())
                Toast.makeText(context, "error torima" + state.message, Toast.LENGTH_SHORT).show()
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mealsViewModel.setSort(sortList[position])
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}