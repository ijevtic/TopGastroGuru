package com.example.topgastroguru.presentation.view.activities.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.topgastroguru.R
import com.example.topgastroguru.databinding.FragmentMealDetailedBinding
import com.example.topgastroguru.presentation.view.activities.MainActivity
import com.example.topgastroguru.presentation.view.viewmodels.MealDetailedViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import timber.log.Timber
import java.io.InputStream
import java.net.URL


class MealDetailedFragment: Fragment(R.layout.fragment_meal_detailed) {

    private val mealDetailedVM: MealDetailedViewModel by activityViewModel<MealDetailedViewModel>()

    private var _binding: FragmentMealDetailedBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameTV: TextView
    private lateinit var areaTV: TextView
    private lateinit var instructionsTV: TextView
    private lateinit var photoIV: ImageView
    private lateinit var categoryTV: TextView
    private lateinit var tagsTV: TextView
    private lateinit var saveBT: Button
    private lateinit var linkTV: TextView

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
        nameTV = binding.name
        areaTV = binding.area
        instructionsTV = binding.instructions
        photoIV = binding.photo
        categoryTV = binding.category
        tagsTV = binding.tags
        saveBT = binding.save
        linkTV = binding.link

        binding.save.setOnClickListener {
            (activity as MainActivity?)?.addFragment(SaveMealFragment())
        }

        var meal = mealDetailedVM.getMealDetailed()
        Timber.e("MealDetailedFragment got notified") // null
        if (meal != null) {
            nameTV.setText(meal.name)
            areaTV.setText(meal.area)
            instructionsTV.setText(meal.instructions)
            categoryTV.setText(meal.category)
            tagsTV.setText(meal.tags)
            linkTV.setText(meal.link)
            DownloadImageFromInternet(photoIV).execute(meal.mealThumb)
        }
    }


    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
            Toast.makeText(requireContext().applicationContext, "Please wait, it may take a few minute...",     Toast.LENGTH_SHORT).show()
        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }

    private fun initObservers() {
        mealDetailedVM.meal.observe(viewLifecycleOwner, Observer {
            Timber.e("Meal: $it")
            nameTV.setText(it.name)
            areaTV.setText(it.area)
            instructionsTV.setText(it.instructions)
            categoryTV.setText(it.category)
            tagsTV.setText(it.tags)
            linkTV.setText(it.link)
            photoIV.setImageURI(it.mealThumb.toUri())
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}