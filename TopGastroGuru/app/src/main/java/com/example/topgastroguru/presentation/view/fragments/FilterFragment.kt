package com.example.topgastroguru.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.FragmentFilterMealsBinding
import com.example.topgastroguru.presentation.view.activities.recycler.adapter.CategoryAdapter
import com.example.topgastroguru.presentation.view.states.CategoriesState
import com.example.topgastroguru.presentation.view.states.CategoryState
import com.example.topgastroguru.presentation.view.viewmodels.CategoryViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import timber.log.Timber

class FilterFragment : Fragment(R.layout.fragment_filter_meals) {

    private val viewModel: CategoryViewModel by activityViewModel<CategoryViewModel>()
    private var _binding: FragmentFilterMealsBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        binding.categoryFilterBtn.setOnClickListener {
            binding.filterBtns.visibility = View.GONE
            binding.listRv.visibility = View.VISIBLE
            initRecycler()
            initObservers()
            Timber.e("fetching categories")
            viewModel.fetchCategories()
        }
    }

    private fun initObservers() {
        viewModel.categoriesState.observe(viewLifecycleOwner, Observer {
            Timber.e("category state: $it")
            renderState(it);
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = CategoryAdapter { category ->
            // Handle the item click event here
            Timber.e("Clicked on category: ${category.strCategory}")
            viewModel.selectedCategoryState.value = CategoryState.Selected(category)
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.listRv.adapter = adapter
    }

    private fun renderState(state: CategoriesState) {
        when (state) {
            is CategoriesState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.categories)
            }
            is CategoriesState.Error -> {
                showLoadingState(false)
                adapter.submitList(listOf())
            }
            is CategoriesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        //TODO hide others

        binding.loadingPb.isVisible = loading
    }
}