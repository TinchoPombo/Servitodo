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
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.ort.servitodo.R
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.viewmodels.cliente.DetallePublicacionViewModel
import org.w3c.dom.Text

class DetallePublicacionFragment : Fragment() {

    companion object {
        fun newInstance() = DetallePublicacionFragment()
    }

    private lateinit var viewModel: DetallePublicacionViewModel
    private var repository = PublicacionRepository()
    lateinit var publicacion : Publicacion
    lateinit var v : View
    lateinit var imgPrestador : ImageView
    lateinit var nombre : TextView
    lateinit var apellido : TextView
    lateinit var rubro : TextView
    lateinit var calificacion : TextView
    lateinit var precioEstimado : TextView
    lateinit var btnBack : Button

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
        btnBack = v.findViewById(R.id.btnVolverPublicacion)

        val receiveIndex = DetallePublicacionFragmentArgs.fromBundle(requireArguments()).publicacionIndex
        publicacion = repository.getPublicaciones()[receiveIndex]

        val txtNombre = "Nombre: " + publicacion.nombrePrestador
        val txtApellido = "Apellido: " + publicacion.apellidoPrestador
        val txtRubro = "Rubro: " + publicacion.nombreRubro
        val txtCalificacion = "Calificacion: "
        val txtPrecioEstimado = "Precio estimado: "

        nombre.text = txtNombre
        apellido.text = txtApellido
        rubro.text = txtRubro
        calificacion.text = txtCalificacion
        precioEstimado.text = txtPrecioEstimado

        Glide
            .with(v)
            .load(publicacion.fotoPrestador)
            .into(imgPrestador);

        btnBack.setOnClickListener{
            v.findNavController().navigateUp()
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetallePublicacionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}