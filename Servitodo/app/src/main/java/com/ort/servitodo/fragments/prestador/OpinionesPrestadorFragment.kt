package com.ort.servitodo.fragments.prestador

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentHistorialClienteBinding
import com.ort.servitodo.databinding.FragmentOpinionesPrestadorBinding
import com.ort.servitodo.viewmodels.cliente.HistorialClienteViewModel
import com.ort.servitodo.viewmodels.prestador.OpinionesPrestadorViewModel

class OpinionesPrestadorFragment : Fragment() {

    private val opinionesPrestadorViewModel : OpinionesPrestadorViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentOpinionesPrestadorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpinionesPrestadorBinding.inflate(inflater, container, false)

        v = binding.root

        opinionesPrestadorViewModel.setView(v)

        return v
    }


}