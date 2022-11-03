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

    private val viewModel: CrearPublicacionViewModel by viewModels()
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

        return v
    }

    override fun onStart() {
        super.onStart()

        binding.btnCrearPublicacion.setOnClickListener {
            val rubro : Rubro = Fletero(Integer.parseInt(binding.txtCostoHora.text.toString()), Integer.parseInt(binding.txtPesoMaximo.text.toString()), FleteroFragmentArgs.fromBundle(requireArguments()).idRubro, "Fletero")
            val descripcion : String = FleteroFragmentArgs.fromBundle(requireArguments()).descripcion
            viewModel.crearPublicacion(descripcion, rubro)

            v.findNavController().navigate( FleteroFragmentDirections.actionFleteroFragmentToHomePrestadorFragment())
        }
    }

 /*   override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FleteroViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}