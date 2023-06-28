package com.example.topgastroguru.presentation.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.FragmentOverviewBinding
import com.example.topgastroguru.presentation.contract.PlanOverViewContract
import com.example.topgastroguru.presentation.view.activities.recycler.adapter.PickMealAdapter
import com.example.topgastroguru.presentation.view.viewmodels.PlanOverviewViewModel
import com.example.topgastroguru.util.Weekday
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class TabPlanOverviewFragment : Fragment(R.layout.fragment_overview) {

    private val planOverviewViewModel: PlanOverViewContract.ViewModel by activityViewModel<PlanOverviewViewModel>()

    private lateinit var adapterMonday: PickMealAdapter
    private lateinit var adapterTuesday: PickMealAdapter
    private lateinit var adapterFriday: PickMealAdapter

    private var _binding: FragmentOverviewBinding? = null

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
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
        if(planOverviewViewModel.mondayState.value!!.size == 0) {
            binding.mondayTv.visibility = View.GONE
        } else {
            binding.mondayTv.visibility = View.VISIBLE
        }

        if(planOverviewViewModel.tuesdayState.value!!.size == 0) {
            binding.tuesdayTv.visibility = View.GONE
        } else {
            binding.tuesdayTv.visibility = View.VISIBLE
        }

        if(planOverviewViewModel.fridayState.value!!.size == 0) {
            binding.fridayTv.visibility = View.GONE
        } else {
            binding.fridayTv.visibility = View.VISIBLE
        }

        initRecycler()
    }

    private fun initRecycler() {
        binding.listRvMonday.layoutManager = LinearLayoutManager(context)
        binding.listRvTuesday.layoutManager = LinearLayoutManager(context)
        binding.listRvFriday.layoutManager = LinearLayoutManager(context)

        adapterMonday = PickMealAdapter ({ meal -> // on click
        },{ meal -> // on pick
            planOverviewViewModel.removeMealFromDay(meal.id, Weekday.MONDAY)
        })

        adapterTuesday = PickMealAdapter ({ meal -> // on click
        },{ meal -> // on pick
            planOverviewViewModel.removeMealFromDay(meal.id, Weekday.TUESDAY)
        })

        adapterFriday = PickMealAdapter ({ meal -> // on click
        },{ meal -> // on pick
            planOverviewViewModel.removeMealFromDay(meal.id, Weekday.FRIDAY)
        })

        binding.listRvMonday.adapter = adapterMonday
        binding.listRvTuesday.adapter = adapterTuesday
        binding.listRvFriday.adapter = adapterFriday
    }

    private fun initObservers() {
        planOverviewViewModel.mondayState.observe(viewLifecycleOwner, {
            adapterMonday.submitList(it)

            if(planOverviewViewModel.mondayState.value!!.size == 0) {
                binding.mondayTv.visibility = View.GONE
            } else {
                binding.mondayTv.visibility = View.VISIBLE
            }
        })

        planOverviewViewModel.tuesdayState.observe(viewLifecycleOwner, {
            adapterTuesday.submitList(it)
            if(planOverviewViewModel.tuesdayState.value!!.size == 0) {
                binding.tuesdayTv.visibility = View.GONE
            } else {
                binding.tuesdayTv.visibility = View.VISIBLE
            }
        })

        planOverviewViewModel.fridayState.observe(viewLifecycleOwner, {
            adapterFriday.submitList(it)
            if(planOverviewViewModel.fridayState.value!!.size == 0) {
                binding.fridayTv.visibility = View.GONE
            } else {
                binding.fridayTv.visibility = View.VISIBLE
            }
        })

        binding.savePlanBtn.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}