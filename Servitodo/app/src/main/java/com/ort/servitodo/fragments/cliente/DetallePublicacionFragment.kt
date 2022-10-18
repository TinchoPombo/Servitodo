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
import com.ort.servitodo.databinding.FragmentDetallePublicacionBinding
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

    private lateinit var binding: FragmentDetallePublicacionBinding
    private lateinit var publicacion : Publicacion
    private var receiveIndex : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetallePublicacionBinding.inflate(inflater, container, false)
        v = inflater.inflate(R.layout.fragment_detalle_publicacion, container, false)

        receiveIndex = DetallePublicacionFragmentArgs.fromBundle(requireArguments()).publicacionIndex
        publicacion = viewModel.getPublicacionByIndex(receiveIndex)

        viewModel.setView(v)

        return binding.root
    }

    override fun onStart() {
        super.onStart()


        binding.txtNombrePublicacion.text = "Nombre: " + publicacion.nombrePrestador
        binding.txtApellidoPublicacion.text = "Apellido: " + publicacion.apellidoPrestador
        binding.txtRubroPublicacion.text =  "Rubro: " + publicacion.nombreRubro
        binding.txtCalificacionPublicacion.text = "Calificacion: "
        binding.txtDescripcionPublicacion.text = "Descripcion: "

        //Glide
        Glide
            .with(v)
            .load(publicacion.fotoPrestador)
            .into(binding.imgPrestadorPublicacion);

        binding.contratarButton.setOnClickListener{
            viewModel.whatsapp(receiveIndex)
        }

        /*nombre.text = "Nombre: " + publicacion.nombrePrestador
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
        }*/

        /*
        btnBack.setOnClickListener{
            v.findNavController().navigateUp()
        }
        */
    }



}