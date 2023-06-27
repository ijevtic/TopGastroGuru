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
import com.example.topgastroguru.presentation.view.states.MealsApiState
import com.example.topgastroguru.presentation.view.states.MealsState
import com.example.topgastroguru.presentation.view.states.ParameterState
import com.example.topgastroguru.presentation.view.viewmodels.MealDetailedViewModel
import com.example.topgastroguru.presentation.view.viewmodels.ParameterViewModel
import com.example.topgastroguru.util.SortType
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import timber.log.Timber
import kotlin.math.min

class AllMealsFragment : Fragment(R.layout.fragment_all_meals) {

    private var _binding: FragmentAllMealsBinding? = null
    private val mealsViewModel: MealsContract.ViewModel by activityViewModel<AllMealsViewModel>()
    private val parameterViewModel: ParameterViewModel by activityViewModel<ParameterViewModel>()
    private val mealDetailedViewModel: MealDetailedViewModel by activityViewModel<MealDetailedViewModel>()
    private val sortList = arrayOf(SortType.NONE, SortType.ABC, SortType.CALORIES)
    private val paginationList = arrayOf(10, 10, 20, 50);
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

    private var pageSize = 10
    private var currentPage = 0


    private fun initListeners() {
        binding.nameSearch.doAfterTextChanged {
            mealsViewModel.updateSearchQuery(it.toString())
        }

        binding.tagSearch.doAfterTextChanged {
            mealsViewModel.updateSearchQuery(it.toString())
        }

        binding.filterBtn.setOnClickListener {
            (activity as MainActivity?)?.addFragmentHide(FilterFragment())
        }

        val sortArray = resources.getStringArray(R.array.sort_array)

        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            sortArray
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sortSpinner.adapter = this
            binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedItem = getItem(position)
                    mealsViewModel.setSort(sortList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle the case where no item is selected (optional)
                }
            }
        }

        val paginationArray = resources.getStringArray(R.array.pagination_array)

        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            paginationArray
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.pageSpinner.adapter = this
            binding.pageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    currentPage = 0
                    pageSize = paginationList[position]
                    renderAdapter()
//                    mealsViewModel.setSort(sortList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle the case where no item is selected (optional)
                }
            }
        }

        binding.backwardBtn.setOnClickListener {
            currentPage--
            renderAdapter()
        }

        binding.forwardBtn.setOnClickListener {
            currentPage++
            renderAdapter()
        }
    }

    private fun initObservers() {
        mealsViewModel.mealsState.observe(viewLifecycleOwner, Observer {
            Timber.e("desilo se")
            currentPage = 0
            renderState(it)
        })

        parameterViewModel.selectedParameterState.observe(viewLifecycleOwner, Observer {
            if(it is ParameterState.Selected) {
                mealsViewModel.setFilter(it.parameter)
            }
            else
                mealsViewModel.setFilter(null)
        })

    }

    private fun renderState(state: MealsApiState) {
        binding.pageNumberTV.text = (currentPage+1).toString()
        when (state) {
            is MealsApiState.Success -> {
                showLoadingState(false)
//                adapter.submitList(state.meals)
                renderAdapter()
            }
            is MealsApiState.Error -> {
                showLoadingState(false)
//                renderAdapter()
                adapter.submitList(listOf())
                binding.forwardBtn.isEnabled = false
                binding.backwardBtn.isEnabled = false
            }
            is MealsApiState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun renderAdapter() {
        binding.pageNumberTV.text = (currentPage+1).toString()
        var state: MealsApiState = mealsViewModel.mealsState.value!!

        if(state is MealsApiState.Success) {
            val totalNum = state.meals.size
            binding.forwardBtn.isEnabled = (currentPage+1)*pageSize < totalNum
            binding.backwardBtn.isEnabled = currentPage > 0
            if(state.meals.size == 0) {
                adapter.submitList(listOf())
                return
            }
            val minIndex = currentPage * pageSize
            val maxIndex = min((currentPage + 1) * pageSize, state.meals.size)
            adapter.submitList(
                state.meals.subList(
                    minIndex,
                    maxIndex
                )
            )
        }
        else
            adapter.submitList(listOf())
    }

    private fun showLoadingState(loading: Boolean) {
        //TODO hide others

        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        mealsViewModel.setSort(sortList[position])
//    }
//
//    override fun onNothingSelected(p0: AdapterView<*>?) {
//        TODO("Not yet implemented")
//    }
}