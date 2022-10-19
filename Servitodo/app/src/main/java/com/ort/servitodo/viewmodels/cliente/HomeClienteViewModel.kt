package com.ort.servitodo.viewmodels.cliente

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.servitodo.adapters.PublicacionAdapter
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.fragments.cliente.HomeClienteFragmentDirections
import com.ort.servitodo.repositories.PublicacionRepository
import kotlinx.coroutines.launch

class HomeClienteViewModel : ViewModel() {

    private lateinit var view : View
    private var repository = PublicacionRepository()

    val cargando = MutableLiveData<String>()

    //-------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
    }

    //-------------------------------------------------------------------------------
    fun recyclerView(recyclerPublicacion : RecyclerView){

        var publicaciones : MutableList<Publicacion> = arrayListOf()

        cargando.value = "Cargando..."

        viewModelScope.launch{
            publicaciones = repository.getPublicaciones()

            recyclerPublicacion.setHasFixedSize(true)

            cargando.value = ""

            recyclerPublicacion.layoutManager  = LinearLayoutManager(view.context)

            recyclerPublicacion.adapter = PublicacionAdapter(publicaciones){ pos ->
                onItemClick(pos)
                publicaciones.removeAll(publicaciones)
            }

        }
    }

    private fun onItemClick(position : Int){
        val action = HomeClienteFragmentDirections.actionHomeClienteFragmentToDetallePublicacionFragment(position)
        view.findNavController().navigate(action)
    }

}