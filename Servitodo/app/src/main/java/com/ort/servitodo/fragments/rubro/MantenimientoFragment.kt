package com.ort.servitodo.fragments.rubro

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentFleteroBinding
import com.ort.servitodo.databinding.FragmentMantenimientoBinding
import com.ort.servitodo.entities.Prestador
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.viewmodels.prestador.CrearPublicacionViewModel
import com.ort.servitodo.viewmodels.rubro.MantenimientoViewModel

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

        val descripcion : String = MantenimientoFragmentArgs.fromBundle(requireArguments()).descripcion
        val idRubro : Int = MantenimientoFragmentArgs.fromBundle(requireArguments()).idRubro
        val prestador : Prestador = Prestador(7,"messiMantenimiento","Lionel","Messi","20-10-1989","https://pbs.twimg.com/media/E8jxa-AWUAAPSX9.jpg:large","matricula","Mantenimiento","1122766971")

        binding.btnCrearPublicacion.setOnClickListener {
            val publicacion: Publicacion = Publicacion((viewModel.getPublicaciones().size + 1), prestador.id, prestador.img, prestador.name,prestador.lastname, idRubro, prestador.rubro, descripcion)
            db.collection("publicaciones").add(publicacion)
            viewModel.emptyList()

        }
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MantenimientoViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}