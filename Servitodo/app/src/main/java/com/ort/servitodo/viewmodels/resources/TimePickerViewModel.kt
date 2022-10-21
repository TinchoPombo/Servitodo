package com.ort.servitodo.viewmodels.resources

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ort.servitodo.entities.TipoEstado
import com.ort.servitodo.repositories.PedidosRepository

class TimePickerViewModel : ViewModel() {

    private var pedidos = PedidosRepository()
    private var calendar = CalendarViewModel()

    //----------------------------------------------------------------------
    private fun getUnavailableHours(fecha : String, servicio : Int) : Array<String>{

        //--> Array que obtiene las horas que ya estan reservadas TODO: SACAR DE LA DB
        val pedidos = pedidos.getPedidos()

        var unavailableHours = arrayOf<String>()

        for(p in pedidos){
            if(p.fecha == fecha && p.idPublicacion == servicio+1 && p.estado == TipoEstado.APROBADO.toString()){
                unavailableHours += p.hora
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


    fun showTimePicker(view : View, date : String, service: Int, selectedHour : MutableLiveData<String>, fm : FragmentManager) {

        val unavailableHours = getUnavailableHours(date, service)
        val availableHours = getAvailableHours(unavailableHours)

        val checkedHour = 1

        //--> En caso de no haber horarios, se cambia el mensaje
        var title = "Horarios disponibles para el dia ${date}"
        if(availableHours.size == 0){
            title = "No hay horarios disponibles. Elija otra fecha"
        }

        MaterialAlertDialogBuilder(view.context)
            .setTitle(title)
            .setNegativeButton("cancelar") { dialog, which ->

            }
            /*
            .setNeutralButton("cambiar fecha") { dialog, which ->
                calendar.calendar(fm)
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