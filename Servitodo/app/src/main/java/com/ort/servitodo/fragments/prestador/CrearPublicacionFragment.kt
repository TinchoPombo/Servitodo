package com.ort.servitodo.fragments.prestador

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentCrearPublicacionBinding
import com.ort.servitodo.databinding.FragmentDetallePublicacionBinding
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.viewmodels.prestador.CrearPublicacionViewModel

class CrearPublicacionFragment : Fragment() {

    companion object {
        fun newInstance() = CrearPublicacionFragment()
    }

    private lateinit var viewModel: CrearPublicacionViewModel
    lateinit var v: View
    private lateinit var binding: FragmentCrearPublicacionBinding

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_crear_publicacion, container, false)
        binding = FragmentCrearPublicacionBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CrearPublicacionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        var usuario: Usuario = Usuario(1, "Roque", "Fort", "roquefort@gmail.com", "quericoelroque", "1111111", "")

        binding.btnCrearPublicacionCp.setOnClickListener {
            // viewModel.crearPublicacion(usuario, binding. )
            db.collection("usuarios").document(usuario.nombre).set(usuario)
            //db.collection("usuarios").add(usuario)
        }

    }

}