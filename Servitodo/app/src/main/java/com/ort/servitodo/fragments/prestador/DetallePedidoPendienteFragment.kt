package com.ort.servitodo.fragments.prestador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.ort.servitodo.databinding.FragmentDetallePedidoPendienteBinding
import com.ort.servitodo.entities.Pedido

import com.ort.servitodo.viewmodels.prestador.DetallePedidoPendienteViewModel

class DetallePedidoPendienteFragment : Fragment() {

    private val detalleViewModel : DetallePedidoPendienteViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentDetallePedidoPendienteBinding

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

    private var receivePedido = Pedido()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetallePedidoPendienteBinding.inflate(inflater, container, false)

        v = binding.root

        //--> Index recibido por parametro
        receivePedido = DetallePedidoPendienteFragmentArgs.fromBundle(requireArguments()).receivePedido
        detalleViewModel.setPedido(receivePedido)

        detalleViewModel.initLiveData()

        detalleViewModel.setView(v)
        detalleViewModel.setFragmentManager(activity?.supportFragmentManager!!)

        return v
    }

    override fun onStart() {
        super.onStart()

        detalleViewModel.nombreCompleto.observe(viewLifecycleOwner, Observer { result ->
            binding.txtNombreCompletoPublicacion.text = result.toString()
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
        })
        detalleViewModel.selectedHour.observe(viewLifecycleOwner, Observer { result ->
            binding.horaseleccionadaTextView.text = result.toString()
        })

        binding.verCalificacionesButton.setOnClickListener{
            detalleViewModel.opinionesDelPrestador()
        }

        binding.verhorariosButton.setOnClickListener{
            detalleViewModel.selectDate()
        }

        /*binding.contratarButton.setOnClickListener{
            detalleViewModel.contratar()
        }*/

    }

}