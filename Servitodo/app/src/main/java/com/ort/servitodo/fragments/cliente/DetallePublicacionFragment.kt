package com.ort.servitodo.fragments.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.ort.servitodo.databinding.FragmentDetallePublicacionBinding
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.viewmodels.cliente.DetallePublicacionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetallePublicacionFragment : Fragment() {

    private val detalleViewModel : DetallePublicacionViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentDetallePublicacionBinding

    /*
    companion object {
        fun newInstance() = DetallePublicacionFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detalleViewModel = ViewModelProvider(this).get(DetallePublicacionViewModel::class.java)
        // TODO: Use the ViewModel
    }
    */

    private lateinit var publicacion : Publicacion
    private var receiveIndex : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetallePublicacionBinding.inflate(inflater, container, false)

        v = binding.root

        //--> Index recibido por parametro
        receiveIndex = DetallePublicacionFragmentArgs.fromBundle(requireArguments()).publicacionIndex
        detalleViewModel.initDetalle(receiveIndex)

        detalleViewModel.setView(v)
        detalleViewModel.setFragmentManager(activity?.supportFragmentManager!!)

        return v
    }

    override fun onStart() {
        super.onStart()

        detalleViewModel.nombre.observe(viewLifecycleOwner, Observer { result ->
            binding.txtNombrePublicacion.text = result.toString()
        })
        detalleViewModel.apellido.observe(viewLifecycleOwner, Observer { result ->
            binding.txtApellidoPublicacion.text = result.toString()
        })
        detalleViewModel.rubro.observe(viewLifecycleOwner, Observer { result ->
            binding.txtRubroPublicacion.text = result.toString()
        })
        detalleViewModel.calificacion.observe(viewLifecycleOwner, Observer { result ->
            binding.txtCalificacionPublicacion.text = result.toString()
        })
        detalleViewModel.descripcion.observe(viewLifecycleOwner, Observer { result ->
            binding.txtDescripcionPublicacion.text = result.toString()
        })
        detalleViewModel.fotoPrestador.observe(viewLifecycleOwner, Observer { result ->
            Glide
                .with(v)
                .load(result.toString())
                .into(binding.imgPrestadorPublicacion);
        })
        
        //----------------------------------------------------------------------------------
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
            detalleViewModel.whatsapp(receiveIndex)
        }

    }

}