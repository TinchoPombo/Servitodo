package com.ort.servitodo.viewmodels.prestador

import android.content.ContentValues.TAG
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.TipoEstado
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.UsuarioRepository
import com.ort.servitodo.viewmodels.resources.CalendarViewModel
import com.ort.servitodo.viewmodels.resources.TimePickerViewModel
import com.ort.servitodo.viewmodels.resources.WhatsAppViewModel
import com.ort.servitodo.viewmodels.resources.googlemaps.GoogleMapsViewModel
import kotlinx.coroutines.launch
import java.util.*

class DetallePedidoPendienteViewModel : ViewModel() {
    private lateinit var view: View
    private lateinit var fragmentManager: FragmentManager
    private lateinit var pedido: Pedido
    private lateinit var usuario: Array<String>
    private lateinit var publicacion: Publicacion
    private var precio = 0
    val db = Firebase.firestore


    //--> View Models
    private val calendarViewModel = CalendarViewModel()
    private val timeViewModel = TimePickerViewModel()
    private val opiniones = OpinionesPrestadorViewModel()
    private val pedidoRepository = PedidosRepository()

    //--> Mutable Live Data
    val selectedDay = MutableLiveData<String>()
    val selectedHour = MutableLiveData<String>()

    val pedidoSelectedDay = MutableLiveData<String>()
    val pedidoSelectedHour = MutableLiveData<String>()

    val nombreCompleto = MutableLiveData<String>()
    val rubro = MutableLiveData<String>()
    val calificacion = MutableLiveData<String>()
    val direccion = MutableLiveData<String>()
    val fotoCliente = MutableLiveData<String>()

    //----------------------------------------------------------------------
    fun setView(v: View) {
        this.view = v
    }

    fun setFragmentManager(fm: FragmentManager) {
        this.fragmentManager = fm
    }

    fun setPedido(pedido: Pedido) {
        this.pedido = pedido
    }

    fun setUsuario(us: Array<String>) {
        this.usuario = us
    }

    fun setPublicacion(publicacion: Publicacion) {
        this.publicacion = publicacion
    }

    fun setPrecio(precio: String) {
        if (precio.isNullOrEmpty() || precio.equals("0")) {
            snackPrecio()
        }else{
            this.precio = Integer.parseInt(precio)
        }
    }

    //----------------------------------------------------------------------
    fun initLiveData() {
        viewModelScope.launch {
            nombreCompleto.value = "${usuario[0]} ${usuario[1]}"
            rubro.value = "${publicacion.rubro.nombre}"
            direccion.value = "${usuario[2]}"
            fotoCliente.value = usuario[3]
            selectedDay.value = "${pedido.fecha}"
            selectedHour.value = "${pedido.hora}"
            pedidoSelectedDay.value = "${pedido.fecha}"
            pedidoSelectedHour.value = "${pedido.hora}"
        }
    }

    //-------------------- Seleccion del Horario --------------------------------------------------
    fun selectDate() {
        val calendar = calendarViewModel.calendar(this.fragmentManager)
        initializeCalendarMutableLiveData(calendar)
    }

    fun selectHour() {
        val fecha = this.selectedDay.value
        if (fecha != null) {
            timeViewModel.showTimePicker(view, fecha, this.publicacion, this.selectedHour)
        }
    }

    fun redirectionToWhatsApp() {
        val whatsAppViewModel = WhatsAppViewModel()
        whatsAppViewModel.confirmRedirectionToWhatsapp(this.pedido.idCliente, view)
    }

    fun redirectionToMaps() {
        val googleMapsViewModel = GoogleMapsViewModel()
        googleMapsViewModel.confirmRedirectionToMaps(usuario[2], view )
    }

    //---------------- Calendario ------------------------------------------
    private fun initializeCalendarMutableLiveData(datePicker: MaterialDatePicker<Long>) {
        datePicker.addOnPositiveButtonClickListener { selection: Long? ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = Date(selection!!)
            this.selectedDay.value = "${calendar.get(Calendar.DAY_OF_MONTH)}-" +
                    "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}"
            selectHour()
        }
    }

    fun rechazarPedido() {
        pedidoRepository.rechazarPedido(this.pedido.id)
        Snackbar.make(view, "El pedido se rechazó", Snackbar.LENGTH_SHORT).show()
        view.findNavController().navigateUp()
    }

    fun confirmarPedido() {
        if(this.precio != 0) {
            if (pedidoSelectedDay.value.equals(selectedDay.value) && pedidoSelectedHour.value.equals(selectedHour.value)) {
                popUpAceptar()
            } else {
                popUpCambioFecha()
            }
        }
    }

    private fun aceptarPedido(){
        pedidoRepository.acceptPedido(this.pedido.id,this.selectedDay.value.toString(), this.selectedHour.value.toString(), this.precio )
        Snackbar.make(this.view, "El pedido se aceptó", Snackbar.LENGTH_SHORT).show()
        view.findNavController().navigateUp()
    }

    //---------------- popUpsConfirmacion ------------------------------------------

    private fun popUpCambioFecha(): Int {
        var result = 0
        MaterialAlertDialogBuilder(view.context).setTitle("Desea modificar la fecha del pedio?")
            .setMessage("De  ->  Fecha: ${this.pedidoSelectedDay.value}   Hora: ${this.pedidoSelectedHour.value} \n\nA  ->  Fecha: ${this.selectedDay.value}   Hora: ${this.selectedHour.value}")
            .setNegativeButton("Cancelar") { dialog, which ->
                this.selectedDay.value = this.pedidoSelectedDay.value
                this.selectedHour.value = this.pedidoSelectedHour.value
                Snackbar.make(this.view, "No se modificio la fecha", Snackbar.LENGTH_SHORT).show()
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                popUpAceptar()
            }
            .show()
        return result
    }

    private fun popUpAceptar(){
        MaterialAlertDialogBuilder(view.context).setTitle("Desea aceptar el pedido?")
            .setNegativeButton("Cancelar") { dialog, which ->
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                viewModelScope.launch {
                    aceptarPedido()
                }
            }
            .show()
    }

    fun popUpRecahzar(){
        MaterialAlertDialogBuilder(view.context).setTitle("Desea rechazar el pedido?")
            .setNegativeButton("Cancelar") { dialog, which ->
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                viewModelScope.launch {
                    rechazarPedido()
                }
            }
            .show()
    }

    fun snackPrecio() {
        Snackbar.make(this.view, "Debe ingresar el precio para acepetar el pedido", Snackbar.LENGTH_SHORT).show()
        this.precio = 0
    }

    //---------------- Calificaciones de prestador ------------------------------------------

    fun opinionesDelPrestador() {
        opiniones.emptyList()
        opiniones.recyclerView(this.view, this.pedido.idCliente)
    }


}