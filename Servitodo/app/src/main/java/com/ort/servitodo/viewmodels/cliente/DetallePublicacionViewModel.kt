package com.ort.servitodo.viewmodels.cliente

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.viewmodels.resources.CalendarViewModel
import com.ort.servitodo.viewmodels.resources.TimePickerViewModel
import java.util.*

class DetallePublicacionViewModel : ViewModel() {

    private lateinit var view : View
    private lateinit var fragmentManager: FragmentManager
    private lateinit var publicacion: Publicacion

    private var pedidosRepository = PedidosRepository()

    //--> View Models
    private val calendarViewModel = CalendarViewModel()
    private val timeViewModel = TimePickerViewModel()
    private val opiniones = OpinionesClienteViewModel()

    //--> Mutable Live Data
    val selectedDay = MutableLiveData<String>()
    val selectedHour = MutableLiveData<String>()

    val nombreCompleto = MutableLiveData<String>()
    val rubro = MutableLiveData<String>()
    val calificacion = MutableLiveData<String>()
    val descripcion = MutableLiveData<String>()
    val fotoPrestador = MutableLiveData<String>()

    //----------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
    }

    fun setFragmentManager(fm : FragmentManager){
        this.fragmentManager = fm
    }

    fun setPublicacion(publicacion : Publicacion){
        this.publicacion = publicacion
    }

    //----------------------------------------------------------------------
    fun initLiveData(){
        nombreCompleto.value = "${this.publicacion.nombrePrestador} ${this.publicacion.apellidoPrestador}"
        rubro.value = "Rubro: ${this.publicacion.rubro.nombre}"
        calificacion.value = ""
        descripcion.value = "${this.publicacion.descripcion}"
        fotoPrestador.value = this.publicacion.fotoPrestador
    }

    //-------------------- Seleccion del Horario --------------------------------------------------
    fun selectDate(){
        val calendar = calendarViewModel.calendar(this.fragmentManager)
        initializeCalendarMutableLiveData(calendar)
    }

    fun selectHour(){
        val fecha = this.selectedDay.value
        if(fecha != null){
            timeViewModel.showTimePicker(view, fecha, this.publicacion, this.selectedHour)
        }
    }

    //---------------- Calendario ------------------------------------------
    private fun initializeCalendarMutableLiveData(datePicker : MaterialDatePicker<Long>){

        datePicker.addOnPositiveButtonClickListener { selection: Long? ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = Date(selection!!)
            this.selectedDay.value = "${calendar.get(Calendar.DAY_OF_MONTH)}-" +
                    "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}"

            this.selectHour()
        }
    }

    //---------------- Contratacion de prestador ------------------------------------------
    fun contratar(){
        val calendarLive = this.selectedDay.value
        val timeLive = this.selectedHour.value
        val cond = !calendarLive.isNullOrEmpty() && !timeLive.isNullOrEmpty()
        if(cond) popUpContratar() else Snackbar.make(this.view, "Debes seleccionar el horario", Snackbar.LENGTH_SHORT).show()
    }

    private fun popUpContratar() : Int{
        var result = 0
        MaterialAlertDialogBuilder(view.context).setTitle("Confirmar").setMessage("Deseas confirmar el pedido?")
            .setNegativeButton("Cancelar") { dialog, which ->
                this.selectedDay.value = ""
                this.selectedHour.value = ""
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                pedidosRepository.addPedido(publicacion, selectedDay.value!!, selectedHour.value!!)
                Snackbar.make(this.view, "El pedido se agregó con exito", Snackbar.LENGTH_SHORT).show()
                view.findNavController().navigateUp()
            }
            .show()
        return result
    }

    //---------------- Calificaciones de prestador ------------------------------------------
    fun opinionesDelPrestador(){
        opiniones.opinionesDelPrestador(this.view)
    }

}