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
import com.ort.servitodo.entities.Prestador
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Rubro
import com.ort.servitodo.viewmodels.prestador.CrearPublicacionViewModel

class PaseaPerrosFragment : Fragment() {

    companion object {
        fun newInstance() = PaseaPerrosFragment()
    }

    private val viewModel: CrearPublicacionViewModel by viewModels()
    lateinit var v: View
    private lateinit var binding: FragmentPaseaPerrosBinding

    val db = Firebase.firestore


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPaseaPerrosBinding.inflate(inflater, container, false)
        v = binding.root

        viewModel.setView(v)

        return v
    }

    override fun onStart() {
        super.onStart()

        val descripcion : String = PaseaPerrosFragmentArgs.fromBundle(requireArguments()).descripcion
        val idRubro : Int = PaseaPerrosFragmentArgs.fromBundle(requireArguments()).idRubro
        val prestador : Prestador = Prestador(2,"messiPaseaPerros","Andres","Gomez","20-10-1989","https://www.rockaxis.com/img/newsList/3070682.jpg","matricula","Paseador de perros","1122766971")

        binding.btnCrearPublicacion.setOnClickListener {
            val rubro : Rubro = PaseaPerros(Integer.parseInt(binding.txtCantidadPerros.text.toString()), Integer.parseInt(binding.txtPrecioPaseo.text.toString()), idRubro, "PaseaPerros")
            val publicacion: Publicacion = Publicacion(/*(viewModel.getPublicaciones().size + 1)*/2, prestador.id, prestador.img, prestador.name,prestador.lastname, rubro, descripcion)
            db.collection("publicaciones").add(publicacion)
            viewModel.emptyList()

        }
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaseaPerrosViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}