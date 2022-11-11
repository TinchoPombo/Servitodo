package com.ort.servitodo.fragments.rubro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.databinding.FragmentFleteroBinding
import com.ort.servitodo.entities.Fletero
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Rubro
import com.ort.servitodo.fragments.prestador.CrearPublicacionFragmentDirections
import com.ort.servitodo.viewmodels.prestador.CrearPublicacionViewModel
import com.ort.servitodo.viewmodels.rubro.FleteroViewModel

class FleteroFragment : Fragment() {

    companion object {
        fun newInstance() = FleteroFragment()
    }

    private val viewModel: FleteroViewModel by viewModels()
    lateinit var v: View
    private lateinit var binding: FragmentFleteroBinding

    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFleteroBinding.inflate(inflater, container, false)
        v = binding.root

        viewModel.setView(v)

        viewModel.setDescripcion(MantenimientoFragmentArgs.fromBundle(requireArguments()).descripcion)

        viewModel.setIdRubro(MantenimientoFragmentArgs.fromBundle(requireArguments()).idRubro)

        return v
    }

    override fun onStart() {
        super.onStart()

        binding.btnCrearPublicacion.setOnClickListener {
            val costoHora = binding.txtCostoHora.text.toString()
            val pesoMax = binding.txtPesoMaximo.text.toString()
            viewModel.validacion(costoHora, pesoMax)
        }
    }

 /*   override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FleteroViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}