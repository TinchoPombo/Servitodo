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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.ort.servitodo.R
//import com.ort.servitodo.databinding.FragmentDetallePublicacionBinding
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.viewmodels.cliente.DetallePublicacionViewModel
import com.ort.servitodo.viewmodels.resources.CalendarViewModel
import com.ort.servitodo.viewmodels.resources.TimePickerViewModel

class DetallePublicacionFragment : Fragment() {

    private val detalleViewModel : DetallePublicacionViewModel by viewModels()
    private val calendarViewModel : CalendarViewModel by viewModels()
    private val timePickerViewModel : TimePickerViewModel by viewModels()

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
    lateinit var fechaseleccionadaTextView : TextView
    lateinit var horaseleccionadaTextView : TextView

    lateinit var contratarButton : Button
    lateinit var verhorariosButton : Button

    //private lateinit var binding : FragmentDetallePublicacionBinding
    private lateinit var publicacion : Publicacion
    private var receiveIndex : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding = FragmentDetallePublicacionBinding.inflate(inflater, container, false)
        v = inflater.inflate(R.layout.fragment_detalle_publicacion, container, false)


        imgPrestador = v.findViewById(R.id.imgPrestadorPublicacion)
        nombre = v.findViewById(R.id.txtNombrePublicacion)
        apellido = v.findViewById(R.id.txtApellidoPublicacion)
        rubro = v.findViewById(R.id.txtRubroPublicacion)
        calificacion = v.findViewById(R.id.txtCalificacionPublicacion)
        //precioEstimado = v.findViewById(R.id.txtPrecioEstimadoPublicacion)
        contratarButton = v.findViewById(R.id.contratarButton)
        verhorariosButton = v.findViewById(R.id.verhorariosButton)
        fechaseleccionadaTextView = v.findViewById(R.id.fechaseleccionadaTextView)
        horaseleccionadaTextView = v.findViewById(R.id.horaseleccionadaTextView)

        //--> Index recibido por parametro

        receiveIndex = DetallePublicacionFragmentArgs.fromBundle(requireArguments()).publicacionIndex
        publicacion = detalleViewModel.getPublicacionByIndex(receiveIndex)

        detalleViewModel.setView(v)
        detalleViewModel.setFragmentManager(activity?.supportFragmentManager!!)

        //return binding.root
        return v
    }

    override fun onStart() {
        super.onStart()

        /*
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
        */

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

        /*
        detalleViewModel.selectedDay.observe(viewLifecycleOwner, Observer { result ->
            binding.fechaseleccionadaTextView.text = result.toString()
            detalleViewModel.selectHour(receiveIndex)
        })
        detalleViewModel.selectedHour.observe(viewLifecycleOwner, Observer { result ->
            binding.horaseleccionadaTextView.text = result.toString()
        })

        binding.verhorariosButton.setOnClickListener{
            detalleViewModel.selectDate()
        }

        binding.contratarButton.setOnClickListener{
            detalleViewModel.confirmRedirectionToWhatsapp(receiveIndex)
        }
        */

        detalleViewModel.selectedDay.observe(viewLifecycleOwner, Observer { result ->
            fechaseleccionadaTextView.text = result.toString()
            detalleViewModel.selectHour(receiveIndex)
        })
        detalleViewModel.selectedHour.observe(viewLifecycleOwner, Observer { result ->
            horaseleccionadaTextView.text = result.toString()
        })

        verhorariosButton.setOnClickListener{
            detalleViewModel.selectDate()
        }

        contratarButton.setOnClickListener {
            detalleViewModel.confirmRedirectionToWhatsapp(receiveIndex)
        }
        /*
        btnBack.setOnClickListener{
            v.findNavController().navigateUp()
        }
        */
    }

}