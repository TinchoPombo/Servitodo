package com.ort.servitodo.fragments.prestador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ort.servitodo.R
import com.ort.servitodo.adapters.PedidosHistorialAdapter
import com.ort.servitodo.databinding.FragmentHistorialPrestadorBinding

import com.ort.servitodo.viewmodels.prestador.HistorialPrestadorViewModel

class HistorialPrestadorFragment : Fragment() {

    private val historialPrestadorViewModel : HistorialPrestadorViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentHistorialPrestadorBinding


    override fun onResume() {
        super.onResume()

        val filtro = resources.getStringArray((R.array.filtroHistorial))
        val arrayAdapterFiltroHistorial = ArrayAdapter(requireContext(), R.layout.dropdown_item, filtro)
        binding.autoCompleteTextViewFiltroHistorialPrestador.setAdapter(arrayAdapterFiltroHistorial)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHistorialPrestadorBinding.inflate(inflater, container, false)

        v = binding.root

        historialPrestadorViewModel.setView(v)
        historialPrestadorViewModel.cargarPedidos()


        binding.autoCompleteTextViewFiltroHistorialPrestador.setOnItemClickListener { adapterView, view , i, l ->
            // historialPrestadorViewModel.emptyList()
            historialPrestadorViewModel.onClickFiltro(l)
        }


        return v
    }

    override fun onStart() {
        super.onStart()

        historialPrestadorViewModel.pedidos.observe(viewLifecycleOwner, Observer { result ->
            val recyclerPedidos = binding.historialPrestadorRV

            recyclerPedidos.setHasFixedSize(true)
            recyclerPedidos.layoutManager = LinearLayoutManager(context)

            recyclerPedidos.adapter = PedidosHistorialAdapter(result) { pos ->
                historialPrestadorViewModel.onClick(pos)
            }
        })
        historialPrestadorViewModel.cargando.observe(viewLifecycleOwner, Observer { result ->
            binding.cargandoTxtCliente.text = result.toString()
        })

    }

}