package com.ort.servitodo.fragments.prestador

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentHistorialPrestadorBinding
import com.ort.servitodo.databinding.FragmentPerfilPrestadorBinding
import com.ort.servitodo.viewmodels.prestador.HistorialPrestadorViewModel
import com.ort.servitodo.viewmodels.prestador.PerfilPrestadorViewModel

class PerfilPrestadorFragment : Fragment() {

    private val perfilViewModel: PerfilPrestadorViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentPerfilPrestadorBinding

    private lateinit var viewModel: PerfilPrestadorViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilPrestadorBinding.inflate(inflater, container, false)

        v = binding.root

        perfilViewModel.setView(v)
        perfilViewModel.obtenerUsuario()

        return v
    }



    override fun onStart() {
        super.onStart()



        binding.btnHistorial.setOnClickListener {
            val action = PerfilPrestadorFragmentDirections.actionPerfilPrestadorFragmentToHistorialPrestadorFragment()
            v.findNavController().navigate(action)
        }


        binding.etName.text = perfilViewModel.getUsuarioData().nombre
        binding.etApellido.text = perfilViewModel.getUsuarioData().apellido
        binding.etEmail.text = perfilViewModel.getUsuarioData().mail
        binding.etDireccion.text = perfilViewModel.getUsuarioData().ubicacion
        binding.etTelefono.text = perfilViewModel.getUsuarioData().telefono
        Glide
            .with(v)
            .load(perfilViewModel.getUsuarioData().foto)
            .into(binding.foto);

    }


}

