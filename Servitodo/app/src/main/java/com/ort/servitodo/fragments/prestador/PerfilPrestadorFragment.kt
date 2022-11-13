package com.ort.servitodo.fragments.prestador

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDeepLinkRequest.Builder.Companion.fromUri
import androidx.navigation.findNavController
import com.ort.servitodo.R
import com.ort.servitodo.activities.ClienteActivity
import com.ort.servitodo.activities.LoginActivity
import com.ort.servitodo.activities.MainActivity
import com.ort.servitodo.databinding.FragmentPerfilPrestadorBinding
import com.ort.servitodo.fragments.login.LogInFragment
import com.ort.servitodo.viewmodels.prestador.PerfilPrestadorViewModel

class PerfilPrestadorFragment : Fragment() {

    private val perfilViewModel: PerfilPrestadorViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentPerfilPrestadorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilPrestadorBinding.inflate(inflater, container, false)

        v = binding.root

        perfilViewModel.setView(v)

        return v
    }


    override fun onStart() {
        super.onStart()


        perfilViewModel.cargarPerfil(binding)

        binding.btnHistorial.setOnClickListener {
            val action = PerfilPrestadorFragmentDirections.actionPerfilPrestadorFragmentToHistorialPrestadorFragment()
            v.findNavController().navigate(action)
        }

        binding.prestadorCalificacionesBtm.setOnClickListener{
            val action = PerfilPrestadorFragmentDirections.actionPerfilPrestadorFragmentToOpinionesDelPrestadosFragment()
            v.findNavController().navigate(action)
        }

        binding.btnCerrarSesion.setOnClickListener{
            perfilViewModel.logOut()
        }
    }


}

