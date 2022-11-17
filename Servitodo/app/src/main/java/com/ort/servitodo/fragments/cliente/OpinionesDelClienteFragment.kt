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
import androidx.recyclerview.widget.LinearLayoutManager
import com.ort.servitodo.R
import com.ort.servitodo.adapters.CalificacionesAdapter
import com.ort.servitodo.databinding.FragmentOpinionesDelClienteBinding
import com.ort.servitodo.databinding.FragmentOpinionesDelPrestadorBinding
import com.ort.servitodo.viewmodels.cliente.OpinionesDelClienteViewModel
import com.ort.servitodo.viewmodels.prestador.OpinionesDelPrestadorViewModel

class OpinionesDelClienteFragment : Fragment() {

    private val opinionesDelClienteViewModel: OpinionesDelClienteViewModel by viewModels()
    lateinit var v : View
    private lateinit var binding : FragmentOpinionesDelClienteBinding


    override fun onResume() {
        super.onResume()

        val filtroOpiniones = resources.getStringArray((R.array.filtroOpiniones))
        val arrayAdapterFiltroHistorial = ArrayAdapter(requireContext(), R.layout.dropdown_item, filtroOpiniones)
        binding.filtradoOpinionesCliente.setAdapter(arrayAdapterFiltroHistorial)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpinionesDelClienteBinding.inflate(inflater, container, false)

        v = binding.root

        opinionesDelClienteViewModel.setView(v)

        opinionesDelClienteViewModel.cargarCalificaciones()

        binding.filtradoOpinionesCliente.setOnItemClickListener { adapterView, view, i, l ->
            opinionesDelClienteViewModel.onClickFiltro(l)
        }

        return v

    }

    override fun onStart() {
        super.onStart()

        opinionesDelClienteViewModel.calificaciones.observe(viewLifecycleOwner, Observer { result ->

            binding.ratingBarPromedioCliente.rating = opinionesDelClienteViewModel.promedioCalificaciones()

            val recyclerCalificaciones = binding.recyclerViewOpinionesDelCliente

            recyclerCalificaciones.setHasFixedSize(true)
            recyclerCalificaciones.layoutManager = LinearLayoutManager(context)

            recyclerCalificaciones.adapter = CalificacionesAdapter(result, true){}

        })

        opinionesDelClienteViewModel.cargando.observe(viewLifecycleOwner, Observer { result ->
            binding.cargandoOpinionesCliente.text = result.toString()
        })
    }
}