package com.ort.servitodo.viewmodels.cliente

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.servitodo.adapters.PublicacionAdapter
import com.ort.servitodo.fragments.cliente.HomeClienteFragmentDirections
import com.ort.servitodo.repositories.PublicacionRepository

class HomeClienteViewModel : ViewModel() {

    private lateinit var view : View
    private var repository = PublicacionRepository()

    //-------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
    }

    //-------------------------------------------------------------------------------
    fun recyclerView(recyclerPublicacion : RecyclerView){
        recyclerPublicacion.setHasFixedSize(true)
        recyclerPublicacion.layoutManager  = LinearLayoutManager(view.context)
        recyclerPublicacion.adapter = PublicacionAdapter(repository.getPublicaciones()){ pos ->
            onItemClick(pos)
        }
    }

    private fun onItemClick(position : Int){
        val action = HomeClienteFragmentDirections.actionHomeClienteFragmentToDetallePublicacionFragment(position)
        view.findNavController().navigate(action)
    }

}