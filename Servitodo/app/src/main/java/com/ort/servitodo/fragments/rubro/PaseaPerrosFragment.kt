package com.ort.servitodo.fragments.rubro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.databinding.FragmentPaseaPerrosBinding
import com.ort.servitodo.entities.PaseaPerros
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Rubro
import com.ort.servitodo.viewmodels.prestador.CrearPublicacionViewModel
import com.ort.servitodo.viewmodels.rubro.PaseaPerrosViewModel

class PaseaPerrosFragment : Fragment() {

    companion object {
        fun newInstance() = PaseaPerrosFragment()
    }

    private val viewModel: PaseaPerrosViewModel by viewModels()
    lateinit var v: View
    private lateinit var binding: FragmentPaseaPerrosBinding

    val db = Firebase.firestore


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPaseaPerrosBinding.inflate(inflater, container, false)
        v = binding.root

        viewModel.setView(v)

        viewModel.setDescripcion(PaseaPerrosFragmentArgs.fromBundle(requireArguments()).descripcion)

        viewModel.setIdRubro(PaseaPerrosFragmentArgs.fromBundle(requireArguments()).idRubro)

        return v
    }

    override fun onStart() {
        super.onStart()

        binding.btnCrearPublicacion.setOnClickListener {
            val precioPaseo = binding.txtPrecioPaseo.text.toString()
            val cantPerros = binding.txtCantidadPerros.text.toString()
            viewModel.validacion(precioPaseo, cantPerros)
        }
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaseaPerrosViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}