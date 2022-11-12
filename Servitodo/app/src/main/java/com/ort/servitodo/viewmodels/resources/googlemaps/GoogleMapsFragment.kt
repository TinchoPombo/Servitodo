package com.ort.servitodo.viewmodels.resources.googlemaps

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.ort.servitodo.R
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? {
        binding = FragmentGoogleMapsBinding.inflate(inflater, container, false)
        v = binding.root

        googleMapsViewModel.initMap(this, v)

        return v
    }

    override fun onResume() {
        super.onResume()
        val filtroDistancia = resources.getStringArray(R.array.filtroDistancia)
        val arrayAdapterDistancia = ArrayAdapter(requireContext(), R.layout.dropdown_item, filtroDistancia)
        binding.autoCompleteTextViewDistancia.setAdapter(arrayAdapterDistancia)
    }

    override fun onStart() {
        super.onStart()

        googleMapsViewModel.cargando.observe(viewLifecycleOwner, Observer { result ->
            Toast.makeText(v.context, result.toString(), Toast.LENGTH_SHORT).show()
        })

        googleMapsViewModel.kmFilter.observe(viewLifecycleOwner, Observer { result ->

        })

        binding.searchGoogleMapsImgButton.setOnClickListener{
            val optionDropDown = binding.autoCompleteTextViewDistancia.text.toString()
            googleMapsViewModel.createGoogleMaps(this, optionDropDown)
        }
    }
}