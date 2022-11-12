package com.ort.servitodo.fragments.prestador

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
import com.ort.servitodo.adapters.CalificacionesAdapter
import com.ort.servitodo.databinding.FragmentHomePrestadorBinding
import com.ort.servitodo.databinding.FragmentOpinionesDelPrestadorBinding
import com.ort.servitodo.viewmodels.prestador.HomePrestadorViewModel
import com.ort.servitodo.viewmodels.prestador.OpinionesDelPrestadorViewModel

class OpinionesDelPrestadorFragment : Fragment() {

    private val opinionesDelPrestadorViewModel: OpinionesDelPrestadorViewModel by viewModels()
    lateinit var v : View
    private lateinit var binding : FragmentOpinionesDelPrestadorBinding


    override fun onResume() {
        super.onResume()

        val filtroOpiniones = resources.getStringArray((R.array.filtroOpiniones))
        val arrayAdapterFiltroHistorial = ArrayAdapter(requireContext(), R.layout.dropdown_item, filtroOpiniones)
        binding.filtradoOpinionesPrestador.setAdapter(arrayAdapterFiltroHistorial)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpinionesDelPrestadorBinding.inflate(inflater, container, false)

        v = binding.root

        opinionesDelPrestadorViewModel.setView(v)
        opinionesDelPrestadorViewModel.cargarCalificaciones()

        binding.filtradoOpinionesPrestador.setOnItemClickListener { adapterView, view, i, l ->
            opinionesDelPrestadorViewModel.onClickFiltro(l)
        }

       // binding.ratingBarPromedioPrestador.rating = opinionesDelPrestadorViewModel.promedioCalificaciones()

        return v

    }



    override fun onStart() {
        super.onStart()

        opinionesDelPrestadorViewModel.calificaciones.observe(viewLifecycleOwner, Observer { result ->
            val recyclerCalificaciones = binding.recyclerViewOpinionesDelPrestador

            recyclerCalificaciones.setHasFixedSize(true)
            recyclerCalificaciones.layoutManager = LinearLayoutManager(context)

            recyclerCalificaciones.adapter = CalificacionesAdapter(result, false){}
        })






    }
}