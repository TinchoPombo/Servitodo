package com.ort.servitodo.viewmodels.resources

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ort.servitodo.entities.PaseaPerros
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.TipoEstado
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.PublicacionRepository
import kotlinx.coroutines.launch

class TimePickerViewModel : ViewModel() {

    private var pedidosRepository = PedidosRepository()
    private var publicacionesRepository = PublicacionRepository()

    private lateinit var publicacion : Publicacion
    private var pedidos : MutableList<Pedido> = mutableListOf()

    //--> Rango de horarios (de 8 a 20 - SOLO PARES)
    val hours = arrayOf("8:00", "10:00", "12:00", "14:00", "16:00", "18:00", "20:00")

    //----------------------------------------------------------------------
    private suspend fun getPedidosPublicacion(){
        val pedidosDb = pedidosRepository.getPedidos()
        this.pedidos = pedidosDb.filter{p -> p.idPublicacion == this.publicacion.idServicio}.toMutableList()
    }

    //----------------------------------------------------------------------
    private suspend fun getUnavailableHoursInPaseaPerros(arrayMismaHora : Array<Int>) : Array<String>{

        val detalleRubroPaseaPerros = publicacionesRepository.getRubro(this.publicacion.idServicio)
        var unavailableHours = arrayOf<String>()

        if(detalleRubroPaseaPerros is PaseaPerros){
            val cantMax = detalleRubroPaseaPerros.cantPerros

            for(h in arrayMismaHora){
                if(h == cantMax){
                    val index = arrayMismaHora.indexOf(h)
                    unavailableHours += this.hours[index]
                }
            }
        }
        return unavailableHours
    }


    private suspend fun getUnavailableHours(fecha : String) : Array<String>{

        var arrayMismaHora = Array(hours.size) { 0 }
        var unavailableHours = arrayOf<String>()

        for(p in pedidos){
            val condFecha = p.fecha == fecha
            val condEstados = p.estado == TipoEstado.RECHAZADO.toString()
                    || p.estado == TipoEstado.FINALIZADO.toString()
                    || p.estado == TipoEstado.CANCELADO.toString()

            if(condFecha && !condEstados){
                val idPaseaPerros = publicacionesRepository.getIdRubroPaseaPerros()

                if(this.publicacion.rubro.id == idPaseaPerros){

                    val getIndexFromHours = hours.indexOf(p.hora)
                    arrayMismaHora[getIndexFromHours]++

                    unavailableHours = getUnavailableHoursInPaseaPerros(arrayMismaHora)
                }
                else{
                    unavailableHours += p.hora
                }
            }
        }
        return unavailableHours
    }

    private fun getAvailableHours(unavailableHours : Array<String>) : Array<String>{
        var availableHours = arrayOf<String>()
        for(h in this.hours){
            if(!unavailableHours.contains(h)){
                availableHours += h
            }
        }
        return availableHours
    }


    fun showTimePicker(view : View, date : String, publicacion: Publicacion,
                       selectedHour : MutableLiveData<String>) {

        this.publicacion = publicacion
        viewModelScope.launch {
            getPedidosPublicacion()

            val unavailableHours = getUnavailableHours(date)
            val availableHours = getAvailableHours(unavailableHours)

            val checkedHour = 1

            //--> En caso de no haber horarios, se cambia el mensaje
            var title = "Horarios disponibles para el dia ${date}"
            if (availableHours.size == 0) {
                title = "No hay horarios disponibles. Elija otra fecha"
            }

            MaterialAlertDialogBuilder(view.context)
                .setTitle(title)
                .setCancelable(false)
                .setNegativeButton("cancelar") { dialog, which ->
                    selectedHour.value = ""
                }
                .setPositiveButton("ok") { dialog, which ->

                }
                .setSingleChoiceItems(availableHours, checkedHour) { dialog, which ->
                    selectedHour.value = "${availableHours[which]}"
                }
                .show()
        }
    }

}