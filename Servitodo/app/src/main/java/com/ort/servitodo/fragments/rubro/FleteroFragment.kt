package com.ort.servitodo.fragments.rubro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.databinding.FragmentFleteroBinding
import com.ort.servitodo.entities.Fletero
import com.ort.servitodo.entities.Prestador
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Rubro
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

        val descripcion : String = FleteroFragmentArgs.fromBundle(requireArguments()).descripcion
        val idRubro : Int = FleteroFragmentArgs.fromBundle(requireArguments()).idRubro
        val prestador : Prestador = Prestador(4,"CharlySanchez3","Raul","Richi","20-10-1989","https://static-cdn.jtvnw.net/jtv_user_pictures/574228be-01ef-4eab-bc0e-a4f6b68bedba-profile_image-300x300.png","matricula","Fletero","1122766971")


        binding.btnCrearPublicacion.setOnClickListener {
            val rubro : Rubro = Fletero(Integer.parseInt(binding.txtCostoHora.text.toString()), Integer.parseInt(binding.txtPesoMaximo.text.toString()), idRubro, "Fletero")
            val publicacion: Publicacion = Publicacion(/*(viewModel.getPublicaciones().size + 1)*/4, prestador.id, prestador.img, prestador.name,prestador.lastname, rubro, descripcion)
            db.collection("publicaciones").add(publicacion)
        }
    }

 /*   override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FleteroViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}