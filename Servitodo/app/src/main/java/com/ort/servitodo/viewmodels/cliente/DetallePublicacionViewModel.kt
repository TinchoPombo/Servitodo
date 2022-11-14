package com.ort.servitodo.viewmodels.cliente

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.ort.servitodo.entities.*
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.repositories.UsuarioRepository
import com.ort.servitodo.viewmodels.resources.CalendarViewModel
import com.ort.servitodo.viewmodels.resources.TimePickerViewModel
import kotlinx.coroutines.launch
import java.util.*

class DetallePublicacionViewModel : ViewModel() {

    private lateinit var view : View
    private lateinit var fragmentManager: FragmentManager
    private lateinit var publicacion: Publicacion
    private var pedidos : MutableList<Pedido> = mutableListOf()
    private lateinit var usuarioRepository : UsuarioRepository

    private var pedidosRepository = PedidosRepository()
    private var publicacionRepository = PublicacionRepository()

    //--> View Models
    private val calendarViewModel = CalendarViewModel()
    private val timeViewModel = TimePickerViewModel()
    private val opiniones = OpinionesClienteViewModel()

    //--> Mutable Live Data
    val selectedDay = MutableLiveData<String>()
    val selectedHour = MutableLiveData<String>()

    val nombreCompleto = MutableLiveData<String>()
    val rubro = MutableLiveData<String>()
    val rubroDetalle = MutableLiveData<String>()
    val calificacion = MutableLiveData<String>()
    val descripcion = MutableLiveData<String>()
    val fotoPrestador = MutableLiveData<String>()
    val cantidadCuposDisponibles = MutableLiveData<String>()

    //----------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
        this.usuarioRepository = UsuarioRepository(v)
    }

    fun setFragmentManager(fm : FragmentManager){
        this.fragmentManager = fm
    }

    fun setPublicacion(publicacion : Publicacion){
        this.publicacion = publicacion
    }

    //----------------------------------------------------------------------
    fun initLiveData(){
        viewModelScope.launch {
            rubroDetalle.value = publicacionRepository.getRubro(publicacion.idServicio).toString()
            nombreCompleto.value = "${publicacion.nombrePrestador} ${publicacion.apellidoPrestador}"
            rubro.value = publicacion.rubro.nombre.uppercase()
            calificacion.value = ""
            descripcion.value = publicacion.descripcion
            fotoPrestador.value = publicacion.fotoPrestador
            pedidos = pedidosRepository.getPedidos()
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
                val idCliente = usuarioRepository.getIdSession()
                pedidosRepository.addPedido(publicacion, selectedDay.value!!, selectedHour.value!!, idCliente)
                Snackbar.make(view, "El pedido se agregó con exito", Snackbar.LENGTH_SHORT).show()
                view.findNavController().navigateUp()
            }
            .show()
        return result
    }

    //---------------- Calificaciones de prestador ------------------------------------------
    fun opinionesDelPrestador(){
        opiniones.emptyList()
        opiniones.recyclerView(this.view, this.publicacion.idPrestador)
    }

    //--------------- Obtener cupos para la misma fecha y hora --------------------
    fun getSpot(){
        val hora = selectedHour.value!!
        val dia = selectedDay.value!!
        var cupos: Int

        viewModelScope.launch {
            val filterPedidos = pedidos.filter { p -> p.fecha == dia
                    && p.hora == hora
                    && p.estado != TipoEstado.FINALIZADO.toString()
                    && p.estado != TipoEstado.RECHAZADO.toString()
                    && p.estado != TipoEstado.CANCELADO.toString()}.toMutableList()

            cupos = if(publicacion.rubro.nombre == "PaseaPerros"){
                paseaPerrosSpot(filterPedidos.size)
            } else{ 1 }
            cantidadCuposDisponibles.value = "Cupos disponibles: ${cupos}"
            filterPedidos.clear()
        }
    }
    /*fun getSpot(){
        val hora = selectedHour.value!!
        val dia = selectedDay.value!!
        var cupos: Int

        viewModelScope.launch {
            val filterPedidos = pedidos.filter { p -> p.fecha == dia && p.hora == hora && p.estado != TipoEstado.FINALIZADO.toString() && p.estado != TipoEstado.RECHAZADO.toString() }.toMutableList()

            if(publicacion.rubro.nombre == "PaseaPerros"){
                cupos = paseaPerrosSpot(filterPedidos.size)
            }
            else{
                cupos = 1
            }
            cantidadCuposDisponibles.value = "Cupos disponibles: ${cupos}"
            filterPedidos.clear()
        }
    }*/

    private suspend fun paseaPerrosSpot(cant : Int) : Int{
        var cuposDisponible = 0
        val detalleRubroPaseaPerros = publicacionRepository.getRubro(this.publicacion.idServicio)

        if(detalleRubroPaseaPerros is PaseaPerros){
            val cantMax = detalleRubroPaseaPerros.cantPerros
            cuposDisponible = cantMax - cant
        }
        return cuposDisponible
    }

}