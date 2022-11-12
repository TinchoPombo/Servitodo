package com.ort.servitodo.fragments.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ort.servitodo.databinding.FragmentPerfilClienteBinding
import com.ort.servitodo.viewmodels.cliente.PerfilClienteViewModel

class PerfilClienteFragment : Fragment() {

    private val perfilViewModel: PerfilClienteViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentPerfilClienteBinding


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


        perfilViewModel.cargarPerfil(binding)

        binding.btnHistorial.setOnClickListener {
            val action = PerfilClienteFragmentDirections.actionPerfilClienteFragmentToHistorialClienteFragment()
            v.findNavController().navigate(action)
        }

        binding.misCalificacionesClienteBtm.setOnClickListener{
            val action = PerfilClienteFragmentDirections.actionPerfilClienteFragmentToOpinionesDelClienteFragment()
            v.findNavController().navigate(action)
        }


    }



    }

