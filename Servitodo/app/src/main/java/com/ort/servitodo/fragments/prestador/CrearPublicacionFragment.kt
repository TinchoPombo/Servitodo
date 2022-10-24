package com.ort.servitodo.fragments.prestador

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentCrearPublicacionBinding
import com.ort.servitodo.databinding.FragmentDetallePublicacionBinding
import com.ort.servitodo.entities.Prestador
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.fragments.login.LogInFragmentDirections
import com.ort.servitodo.viewmodels.prestador.CrearPublicacionViewModel

class CrearPublicacionFragment : Fragment() {

    companion object {
        fun newInstance() = CrearPublicacionFragment()
    }

    private val viewModel: CrearPublicacionViewModel by viewModels()
    lateinit var v: View
    private lateinit var binding: FragmentCrearPublicacionBinding

    val db = Firebase.firestore


    override fun onResume() {
        super.onResume()
        val rubros = resources.getStringArray(R.array.rubros)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, rubros)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrearPublicacionBinding.inflate(inflater, container, false)
        v = binding.root

        viewModel.setView(v)

        return v
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CrearPublicacionViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

    override fun onStart() {
        super.onStart()

       // val prestador : Prestador = Prestador(7,"messiChiquito","Lionel","Messi","20-10-1989","https://pbs.twimg.com/media/E8jxa-AWUAAPSX9.jpg:large","matricula","Paseador de Perros","1122766971")

            binding.btnSiguienteCrearPublicacion.setOnClickListener {
                /*val publicacion: Publicacion = Publicacion(viewModel.getPublicaciones().size + 1,prestador.id,prestador.img,prestador.name,prestador.lastname,2,prestador.rubro, binding.txtDescripcion.text.toString())
                db.collection("publicaciones").add(publicacion)*/

                when(binding.autoCompleteTextView.text.toString()){
                    "Mantenimiento" -> v.findNavController().navigate( CrearPublicacionFragmentDirections.actionCrearPublicacionFragmentToMantenimientoFragment(binding.txtDescripcion.text.toString(),1))
                    "Fletero" -> v.findNavController().navigate( CrearPublicacionFragmentDirections.actionCrearPublicacionFragmentToFleteroFragment(binding.txtDescripcion.text.toString(),2))
                    "Pasea perros" -> v.findNavController().navigate( CrearPublicacionFragmentDirections.actionCrearPublicacionFragmentToPaseaPerrosFragment(binding.txtDescripcion.text.toString(),3))
                }

            }

    }

}