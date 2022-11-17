package com.ort.servitodo.viewmodels.cliente

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ort.servitodo.entities.Puntuacion
import com.ort.servitodo.repositories.CalificacionesRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class OpinionesDelClienteViewModel : ViewModel() {
    private lateinit var view : View
    private var repository = CalificacionesRepository()
    private lateinit var usuarioRep : UsuarioRepository

    val cargando = MutableLiveData<String>()
    val calificaciones = MutableLiveData<MutableList<Puntuacion>>()

    private var promedioCalificacion = 0F

    //-------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
        usuarioRep = UsuarioRepository(this.view)
    }

    fun emptyList(){
        this.calificaciones.value?.clear()
    }

    fun onClickFiltro(l : Long){
        when(l){
            0L-> cargarCalificaciones()
            1L-> cargarMejoresCalificaciones()
            2L-> cargarPeoresCalificaciones()
        }
    }
    fun promedioCalificaciones() : Float{

        var valorTotal  = 0F

        viewModelScope.launch {

            val listaCalificaciones = repository.getCalificacionesByClienteId(usuarioRep.getIdSession())
                if(listaCalificaciones.size > 0){
                    for(c in listaCalificaciones){
                        valorTotal += c.puntaje
                    }

                    promedioCalificacion = valorTotal.div(listaCalificaciones.size)
                }


            }
        return promedioCalificacion
    }

    fun cargarCalificaciones(){

        viewModelScope.launch {
            emptyList()

            val listaCalificaciones = repository.getCalificacionesByClienteId(usuarioRep.getIdSession())


            cargando.value = "Cargando...."

            if(listaCalificaciones.size < 1){
                cargando.value = "No hay calificaciones"

            }else{
                calificaciones.value = listaCalificaciones
                cargando.value = ""
            }

        }
    }

    private fun cargarMejoresCalificaciones(){

        viewModelScope.launch {
            emptyList()

            val listaCalificaciones = repository.getCalificacionesByClienteId(usuarioRep.getIdSession())

            cargando.value = "Cargando...."

            if(listaCalificaciones.size < 1){
                cargando.value = "No hay calificaciones"
            }else{
                calificaciones.value = listaCalificaciones.filter { it.puntaje >= 3F }.sortedByDescending { it.puntaje }.toMutableList()
                cargando.value = ""
            }

        }
    }

    private fun cargarPeoresCalificaciones(){

        viewModelScope.launch {
            emptyList()

            val listaCalificaciones = repository.getCalificacionesByClienteId(usuarioRep.getIdSession())

            cargando.value = "Cargando...."

            if(listaCalificaciones.size < 1){
                cargando.value = "No hay calificaciones"
            }else{
                calificaciones.value = listaCalificaciones.filter { it.puntaje < 3F }.sortedBy { it.puntaje }.toMutableList()
                cargando.value = ""
            }

        }
    }
}


