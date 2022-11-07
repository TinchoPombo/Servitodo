package com.ort.servitodo.viewmodels.resources.googlemaps

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ort.servitodo.databinding.FragmentGoogleMapsBinding

class GoogleMapsFragment : Fragment() {

    /*companion object {
        fun newInstance() = GoogleMapsFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GoogleMapsViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

    private val googleMapsViewModel : GoogleMapsViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentGoogleMapsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoogleMapsBinding.inflate(inflater, container, false)

        v = binding.root

        googleMapsViewModel.setView(v)
        googleMapsViewModel.initUsers()

        return v
    }

    override fun onStart() {
        super.onStart()

        googleMapsViewModel.cargando.observe(viewLifecycleOwner, Observer { result ->
            binding.cargandoTxtGoogleMaps.text = result.toString()
        })

        googleMapsViewModel.kmFilter.observe(viewLifecycleOwner, Observer { result ->

        })

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = v.findViewById<RadioButton>(checkedId)
            val km = googleMapsViewModel.getNumberFromRadioButton(radioButton.text.toString())
            googleMapsViewModel.createGoogleMaps(this, km)
        }

        /*binding.googleMapsAppButton.setOnClickListener{
            redirectToGoogleMaps
        }*/

    }
}