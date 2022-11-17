package com.ort.servitodo.fragments.cliente

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ort.servitodo.R
import com.ort.servitodo.adapters.PedidosHistorialAdapter
import com.ort.servitodo.adapters.PedidosHistorialClienteAdapter
import com.ort.servitodo.databinding.FragmentHistorialClienteBinding

import com.ort.servitodo.viewmodels.cliente.HistorialClienteViewModel


class HistorialClienteFragment : Fragment() {

    private val historialClienteViewModel : HistorialClienteViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentHistorialClienteBinding

    override fun onResume() {
        super.onResume()

        val filtro = resources.getStringArray(R.array.filtroHistorialCliente)
        val arrayAdapterFiltroHistorial = ArrayAdapter(requireContext(), R.layout.dropdown_item, filtro)
        binding.autoCompleteTextViewFiltroClientePrestador.setAdapter(arrayAdapterFiltroHistorial)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistorialClienteBinding.inflate(inflater, container, false)

        v = binding.root

        historialClienteViewModel.setView(v)

        historialClienteViewModel.cargarPedidos()

        binding.autoCompleteTextViewFiltroClientePrestador.setOnItemClickListener { adapterView, view, i, l ->
           // historialClienteViewModel.emptyList()
            historialClienteViewModel.onClickFiltro(l)
        }
        return v
    }

    override fun onStart() {
        super.onStart()

        historialClienteViewModel.pedidos.observe(viewLifecycleOwner, Observer { result ->
            val recyclerPedidos = binding.historialClienteRV

            recyclerPedidos.setHasFixedSize(true)
            recyclerPedidos.layoutManager = LinearLayoutManager(context)

            recyclerPedidos.adapter = PedidosHistorialClienteAdapter(result) { pos ->
                historialClienteViewModel.onClick(pos)

            }


        })
        historialClienteViewModel.cargando.observe(viewLifecycleOwner, Observer { result ->
            binding.cargandoTxtHistorial.text = result.toString()
        })



        //historialPrestadorViewModel.recyclerView(binding.historialPrestadorRV)
        //historialPrestadorViewModel.emptyList()
    }

}