package com.ort.servitodo.fragments.cliente

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.ort.servitodo.R
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.viewmodels.cliente.DetallePublicacionViewModel

class DetallePublicacionFragment : Fragment() {

    private val viewModel : DetallePublicacionViewModel by viewModels()
    lateinit var v : View

    /*
    companion object {
        fun newInstance() = DetallePublicacionFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetallePublicacionViewModel::class.java)
        // TODO: Use the ViewModel
    }
    */

    lateinit var imgPrestador : ImageView
    lateinit var nombre : TextView
    lateinit var apellido : TextView
    lateinit var rubro : TextView
    lateinit var calificacion : TextView
    lateinit var precioEstimado : TextView
    lateinit var contratarButton : Button

    lateinit var publicacion : Publicacion
    private var receiveIndex : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_publicacion, container, false)

        imgPrestador = v.findViewById(R.id.imgPrestadorPublicacion)
        nombre = v.findViewById(R.id.txtNombrePublicacion)
        apellido = v.findViewById(R.id.txtApellidoPublicacion)
        rubro = v.findViewById(R.id.txtRubroPublicacion)
        calificacion = v.findViewById(R.id.txtCalificacionPublicacion)
        precioEstimado = v.findViewById(R.id.txtPrecioEstimadoPublicacion)
        contratarButton = v.findViewById(R.id.contratarButton)

        receiveIndex = DetallePublicacionFragmentArgs.fromBundle(requireArguments()).publicacionIndex
        publicacion = viewModel.getPublicacionByIndex(receiveIndex)

        viewModel.setView(v)

        return v
    }

    override fun onStart() {
        super.onStart()

        nombre.text = "Nombre: " + publicacion.nombrePrestador
        apellido.text = "Apellido: " + publicacion.apellidoPrestador
        rubro.text =  "Rubro: " + publicacion.nombreRubro
        calificacion.text = "Calificacion: "
        precioEstimado.text = "Precio estimado: "

        //Glide
        Glide
            .with(v)
            .load(publicacion.fotoPrestador)
            .into(imgPrestador);

        contratarButton.setOnClickListener{
            viewModel.confirmRedirectionToWhatsapp(receiveIndex)
        }

        /*
        btnBack.setOnClickListener{
            v.findNavController().navigateUp()
        }
        */
    }



}