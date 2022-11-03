package com.ort.servitodo.fragments.cliente

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentPerfilClienteBinding
import com.ort.servitodo.databinding.FragmentPerfilPrestadorBinding
import com.ort.servitodo.fragments.prestador.PerfilPrestadorFragmentDirections
import com.ort.servitodo.viewmodels.cliente.PerfilClienteViewModel
import com.ort.servitodo.viewmodels.prestador.PerfilPrestadorViewModel

class PerfilClienteFragment : Fragment() {

    private val perfilViewModel: PerfilClienteViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentPerfilClienteBinding

    private lateinit var viewModel: PerfilClienteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilClienteBinding.inflate(inflater, container, false)

        v = binding.root

        perfilViewModel.setView(v)

        return v
    }


    override fun onStart() {
        super.onStart()

        binding.btnHistorial.setOnClickListener {
            val action = PerfilClienteFragmentDirections.actionPerfilClienteFragmentToHistorialClienteFragment()
            v.findNavController().navigate(action)
        }




    }

}