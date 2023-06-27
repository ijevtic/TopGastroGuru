package com.example.topgastroguru.presentation.view.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.FragmentMealPlanBinding
import com.example.topgastroguru.presentation.contract.MealPlanContract
import com.example.topgastroguru.presentation.view.activities.recycler.adapter.PickMealAdapter
import com.example.topgastroguru.presentation.view.activities.recycler.adapter.SavedMealAdapter
import com.example.topgastroguru.presentation.view.states.MealsState
import com.example.topgastroguru.presentation.view.viewmodels.MealPlanViewModel
import com.example.topgastroguru.util.MealType
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import timber.log.Timber

class MealPlanFragment : Fragment(R.layout.fragment_meal_plan) {

    private val mealPlanViewModel: MealPlanContract.ViewModel by activityViewModel<MealPlanViewModel>()
    private var _binding: FragmentMealPlanBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapterOnline: PickMealAdapter
    private lateinit var adapterLocal: PickMealAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealPlanBinding.inflate(inflater, container, false)
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
        if(mealPlanViewModel.mealTypeSelected.value == MealType.ALLMEALS) {
            binding.switchButton.isChecked = true
            binding.listRvLocal.visibility = View.GONE
            binding.listRvOnline.visibility = View.VISIBLE
        } else {
            binding.switchButton.isChecked = false
            binding.listRvLocal.visibility = View.VISIBLE
            binding.listRvOnline.visibility = View.GONE
        }
        initRecycler()
    }

    private fun initRecycler() {

        binding.listRvOnline.layoutManager = LinearLayoutManager(context)
        binding.listRvLocal.layoutManager = LinearLayoutManager(context)

        adapterOnline = PickMealAdapter ({ meal -> // on click
        },{ meal -> // on pick
            Toast.makeText(context, "Meal picked online " + meal.name, Toast.LENGTH_SHORT).show()
        })

        adapterLocal = PickMealAdapter ({ meal -> // on click
        },{ meal -> // on pick
            Toast.makeText(context, "Meal picked local " + meal.name, Toast.LENGTH_SHORT).show()
        })

        binding.listRvOnline.adapter = adapterOnline
        binding.listRvLocal.adapter = adapterLocal

        if(mealPlanViewModel.localMealsFiltered.value is MealsState.Success) {
            adapterLocal.submitList((mealPlanViewModel.localMealsFiltered.value as MealsState.Success).meals)
        }

    }

    private fun initObservers() {

        binding.nameSearch.doAfterTextChanged {
            mealPlanViewModel.updateQuery(it.toString())
        }

        binding.switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.switchButton.text = "Remote"
                binding.listRvLocal.visibility = View.GONE
                binding.listRvOnline.visibility = View.VISIBLE
            } else {
                binding.switchButton.text = "Local"
                binding.listRvLocal.visibility = View.VISIBLE
                binding.listRvOnline.visibility = View.GONE
            }
            if(isChecked) {
                mealPlanViewModel.updateMealType(MealType.ALLMEALS)
            } else {
                mealPlanViewModel.updateMealType(MealType.LOCALMEALS)
            }
        }

        mealPlanViewModel.allMealsFiltered.observe(viewLifecycleOwner, Observer {
            when(it) {
                is MealsState.Success -> {
                    Timber.e("acac event online success" + it.meals.size)
                    showLoadingState(false)
                    adapterOnline.submitList(it.meals)
                }
                is MealsState.Error -> {
                    showLoadingState(false)
                    adapterOnline.submitList(emptyList())
                }
                is MealsState.Loading -> {
                    showLoadingState(true)
                }
            }
        })

        mealPlanViewModel.localMealsFiltered.observe(viewLifecycleOwner, Observer {
            when(it) {
                is MealsState.Success -> {
                    showLoadingState(false)
                    adapterLocal.submitList(it.meals)
                }
                is MealsState.Error -> {
                    showLoadingState(false)
                    adapterLocal.submitList(emptyList())
                }
                is MealsState.Loading -> {
                    showLoadingState(true)
                }
            }
        })
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