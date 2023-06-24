package com.example.topgastroguru.presentation.view.activities.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.FragmentSaveMealBinding
import com.example.topgastroguru.presentation.view.viewmodels.MealDetailedViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import android.app.AlertDialog;
import timber.log.Timber
import java.util.Calendar;


class SaveMealFragment: Fragment(R.layout.fragment_save_meal) {
    private val mealDetailedVM: MealDetailedViewModel by activityViewModel<MealDetailedViewModel>()

    private var _binding: FragmentSaveMealBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameTV: TextView
    private lateinit var areaTV: TextView
    private lateinit var instructionsTV: TextView
    private lateinit var linkTV: TextView
    private lateinit var datepickerTV: TextView
    private lateinit var photoIV: ImageView
    private lateinit var categoryTV: TextView
    private lateinit var tagsTV: TextView
    private lateinit var saveBT: Button

    private lateinit var dateButton: Button
    private lateinit var datePickerDialog: DatePickerDialog

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
        dateButton = binding.datePicker


        initDatePicker()
        dateButton.setText(getTodaysDate())

        binding.datePicker.setOnClickListener {
            openDatePicker()
//            Timber.e("Value of dateButton: "+ dateButton.text)
        }

        binding.save.setOnClickListener {

        }
    }

    private fun initObservers() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTodaysDate(): String? {
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        var month: Int = cal.get(Calendar.MONTH)
        month = month + 1
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        return makeDateString(day, month, year)
    }

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                val date = makeDateString(day, month, year)
                dateButton.text = date
            }
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        val style: Int = AlertDialog.THEME_HOLO_LIGHT
        datePickerDialog = DatePickerDialog(requireContext(), style, dateSetListener, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String? {
        return getMonthFormat(month) + " " + day + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        if (month == 1) return "JAN"
        if (month == 2) return "FEB"
        if (month == 3) return "MAR"
        if (month == 4) return "APR"
        if (month == 5) return "MAY"
        if (month == 6) return "JUN"
        if (month == 7) return "JUL"
        if (month == 8) return "AUG"
        if (month == 9) return "SEP"
        if (month == 10) return "OCT"
        if (month == 11) return "NOV"
        return if (month == 12) "DEC" else "JAN"

        //default should never happen
    }

    fun openDatePicker() {
        datePickerDialog.show()
    }

}