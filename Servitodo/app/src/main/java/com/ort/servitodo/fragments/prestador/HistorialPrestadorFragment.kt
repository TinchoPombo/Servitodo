package com.ort.servitodo.fragments.prestador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.ort.servitodo.databinding.FragmentHistorialPrestadorBinding
import com.ort.servitodo.viewmodels.prestador.HistorialPrestadorViewModel

class HistorialPrestadorFragment : Fragment() {

    private val historialPrestadorViewModel : HistorialPrestadorViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentHistorialPrestadorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistorialPrestadorBinding.inflate(inflater, container, false)

        v = binding.root

        historialPrestadorViewModel.setView(v)


        return v
    }

    override fun onStart() {
        super.onStart()

        historialPrestadorViewModel.recyclerView(binding.historialPrestadorRV)
        historialPrestadorViewModel.emptyList()




    }

}