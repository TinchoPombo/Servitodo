package com.ort.servitodo.viewmodels.cliente

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.ort.servitodo.entities.Prestador
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.repositories.PrestadorRepository
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.viewmodels.resources.CalendarViewModel
import com.ort.servitodo.viewmodels.resources.TimePickerViewModel
import com.ort.servitodo.viewmodels.resources.WhatsAppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class DetallePublicacionViewModel : ViewModel() {

    private lateinit var view : View
    private lateinit var fragmentManager: FragmentManager
    private lateinit var publicacion: Publicacion


    //--> View Models
    private val calendarViewModel = CalendarViewModel()
    private val timeViewModel = TimePickerViewModel()
    private val whatsAppViewModel = WhatsAppViewModel()

    //--> Mutable Live Data
    val selectedDay = MutableLiveData<String>()
    val selectedHour = MutableLiveData<String>()

    val nombre = MutableLiveData<String>()
    val apellido = MutableLiveData<String>()
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
        nombre.value = "Nombre: ${this.publicacion.nombrePrestador}"
        apellido.value = "Apellido: ${this.publicacion.apellidoPrestador}"
        rubro.value = "Rubro: ${this.publicacion.nombreRubro}"
        calificacion.value = "Calificacion: "
        descripcion.value = "Descripcion: ${this.publicacion.descripcion}"
        fotoPrestador.value = this.publicacion.fotoPrestador
    }

    //-------------------- Redireccion a whatsapp --------------------------------------------------
    fun whatsapp(){
        val calendarLive = this.selectedDay.value
        val timeLive = this.selectedHour.value

        if(!calendarLive.isNullOrEmpty() && !timeLive.isNullOrEmpty()){
            whatsAppViewModel.confirmRedirectionToWhatsapp(this.publicacion, view)
        }
        else{
            Snackbar.make(this.view, "Debes seleccionar el horario", Snackbar.LENGTH_SHORT).show()
        }
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
        }
    }

}