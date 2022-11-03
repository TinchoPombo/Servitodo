package com.ort.servitodo.fragments.rubro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.databinding.FragmentMantenimientoBinding
import com.ort.servitodo.entities.Mantenimiento
import com.ort.servitodo.entities.Prestador
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Rubro
import com.ort.servitodo.viewmodels.prestador.CrearPublicacionViewModel

class MantenimientoFragment : Fragment() {

    companion object {
        fun newInstance() = MantenimientoFragment()
    }

    private val viewModel: CrearPublicacionViewModel by viewModels()
    lateinit var v: View
    private lateinit var binding: FragmentMantenimientoBinding

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMantenimientoBinding.inflate(inflater, container, false)
        v = binding.root

        viewModel.setView(v)

        return v
    }

    override fun onStart() {
        super.onStart()

        binding.btnCrearPublicacion.setOnClickListener {
            val rubro : Rubro = Mantenimiento(Integer.parseInt(binding.txtPrecioConsulta.text.toString()), MantenimientoFragmentArgs.fromBundle(requireArguments()).idRubro, "Mantenimiento")
            val descripcion : String = MantenimientoFragmentArgs.fromBundle(requireArguments()).descripcion
            viewModel.crearPublicacion(descripcion, rubro)
        }
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MantenimientoViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}