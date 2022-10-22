package com.ort.servitodo.viewmodels.resources

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.TipoEstado
import com.ort.servitodo.repositories.PedidosRepository
import kotlinx.coroutines.launch

class TimePickerViewModel : ViewModel() {

    private var pedidos = PedidosRepository()
    private var calendar = CalendarViewModel()

    //----------------------------------------------------------------------
    private suspend fun getUnavailableHours(fecha : String, publicacion : Publicacion) : Array<String>{

        var unavailableHours = arrayOf<String>()
        val pedidos = pedidos.getPedidos()
        val pedidoEncontrado = pedidos.find{p -> p.idPublicacion == publicacion.idServicio}

        if(pedidoEncontrado != null){
            val condFecha = pedidoEncontrado.fecha == fecha
            val condEstados = pedidoEncontrado.estado == TipoEstado.APROBADO.toString() || pedidoEncontrado.estado == TipoEstado.RESERVADO.toString() || pedidoEncontrado.estado == TipoEstado.EN_CURSO.toString()
            if(condFecha && condEstados){
                unavailableHours += pedidoEncontrado.hora
            }
        }

        return unavailableHours
    }

    private fun getAvailableHours(unavailableHours : Array<String>) : Array<String>{

        //--> Rango de horarios (de 8 a 20)
        val hours = arrayOf("8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00")

        val availableHours = hours.filter { it !in unavailableHours}

        return availableHours.toTypedArray()
    }


    fun showTimePicker(view : View, date : String, publicacion: Publicacion, selectedHour : MutableLiveData<String>) {

        viewModelScope.launch {
            val unavailableHours = getUnavailableHours(date, publicacion)
            val availableHours = getAvailableHours(unavailableHours)

            val checkedHour = 1

            //--> En caso de no haber horarios, se cambia el mensaje
            var title = "Horarios disponibles para el dia ${date}"
            if (availableHours.size == 0) {
                title = "No hay horarios disponibles. Elija otra fecha"
            }

            MaterialAlertDialogBuilder(view.context)
                .setTitle(title)
                .setNegativeButton("cancelar") { dialog, which ->

                }
                /*
                .setNeutralButton("cambiar fecha") { dialog, which ->

                }
                */
                .setPositiveButton("ok") { dialog, which ->

                }
                .setSingleChoiceItems(availableHours, checkedHour) { dialog, which ->
                    selectedHour.value = "${availableHours[which]} hs"
                }
                .show()
        }
    }

}