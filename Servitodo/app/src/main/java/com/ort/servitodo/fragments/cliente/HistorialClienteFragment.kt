package com.ort.servitodo.fragments.cliente

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentHistorialClienteBinding

import com.ort.servitodo.viewmodels.cliente.HistorialClienteViewModel


class HistorialClienteFragment : Fragment() {

    private val historialClienteViewModel : HistorialClienteViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentHistorialClienteBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistorialClienteBinding.inflate(inflater, container, false)

        v = binding.root

        historialClienteViewModel.setView(v)


        return v
    }

    override fun onStart() {
        super.onStart()

        historialClienteViewModel.recyclerView(binding.historialClienteRV)

        historialClienteViewModel.emptyList()


    }

}