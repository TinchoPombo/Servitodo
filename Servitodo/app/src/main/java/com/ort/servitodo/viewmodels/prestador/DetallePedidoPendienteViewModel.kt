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
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.repositories.UsuarioRepository
import com.ort.servitodo.viewmodels.cliente.OpinionesClienteViewModel
import com.ort.servitodo.viewmodels.resources.CalendarViewModel
import com.ort.servitodo.viewmodels.resources.TimePickerViewModel
import com.ort.servitodo.viewmodels.resources.WhatsAppViewModel
import kotlinx.coroutines.launch
import java.util.*

class DetallePedidoPendienteViewModel : ViewModel() {
    private lateinit var view : View
    private lateinit var fragmentManager: FragmentManager
    private lateinit var pedido: Pedido
    private lateinit var usuario: Array<String>
    private lateinit var usuarioRepository : UsuarioRepository
    private lateinit var publicacion : Publicacion
    private var precio = 1
    val db = Firebase.firestore


    private var pedidosRepository = PedidosRepository()

    //--> View Models
    private val calendarViewModel = CalendarViewModel()
    private val timeViewModel = TimePickerViewModel()
    private val opiniones = OpinionesPrestadorViewModel()

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
        usuarioRepository = UsuarioRepository(v)
    }

    fun setFragmentManager(fm : FragmentManager){
        this.fragmentManager = fm
    }

    fun setPedido(pedido :Pedido ){
        this.pedido = pedido
    }

    fun setUsuario(us : Array<String>){
        this.usuario = us
    }

    fun setPublicacion (publicacion : Publicacion){
        this.publicacion = publicacion
    }

    fun setPrecio(precio : Int){
        this.precio = precio
    }

    //----------------------------------------------------------------------
    fun initLiveData(){
        viewModelScope.launch {
            nombreCompleto.value = "${usuario[0]} ${usuario[1]}"
            rubro.value = "${pedido.estado}"/*"${this.publicacion.rubro.nombre}"*/
            descripcion.value = "${usuario[2]}"
            fotoPrestador.value = usuario[3]
            selectedDay.value = "${pedido.fecha}"
            selectedHour.value= "${pedido.hora}"
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

    fun redirectionToWhatsApp(){
        val whatsAppViewModel = WhatsAppViewModel()
        whatsAppViewModel.confirmRedirectionToWhatsapp(this.pedido.idCliente, view)
    }

    //---------------- Calendario ------------------------------------------
    private fun initializeCalendarMutableLiveData(datePicker : MaterialDatePicker<Long>){

        datePicker.addOnPositiveButtonClickListener { selection: Long? ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = Date(selection!!)
            this.selectedDay.value = "${calendar.get(Calendar.DAY_OF_MONTH)}-" +
                    "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}"

            selectHour()
        }
    }

    fun rechazarPedido(){
        val pedidoActualizar = db.collection("pedidos").document(pedido.id.toString())

        pedidoActualizar
            .update("estado", TipoEstado.RECHAZADO.toString() )
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
        view.findNavController().navigateUp()
    }

    fun aceptarPedido(){
        val pedidoActualizar = db.collection("pedidos").document(pedido.id.toString())

        pedidoActualizar
            .update("estado", TipoEstado.APROBADO.toString(), "fecha", selectedDay.value.toString(), "hora", selectedHour.value.toString(), "precio", this.precio)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
        view.findNavController().navigateUp()
    }

    //---------------- Contratacion de prestador ------------------------------------------
/*    fun contratar(){
        val calendarLive = this.selectedDay.value
        val timeLive = this.selectedHour.value
        val cond = !calendarLive.isNullOrEmpty() && !timeLive.isNullOrEmpty()
        if(cond) popUpContratar() else Snackbar.make(this.view, "Debes seleccionar el horario", Snackbar.LENGTH_SHORT).show()
    }*/

   /* private fun popUpContratar() : Int{
        var result = 0
        MaterialAlertDialogBuilder(view.context).setTitle("Confirmar").setMessage("Deseas confirmar el pedido?")
            .setNegativeButton("Cancelar") { dialog, which ->
                this.selectedDay.value = ""
                this.selectedHour.value = ""
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                pedidosRepository.addPedido(pedido, selectedDay.value!!, selectedHour.value!!)
                Snackbar.make(this.view, "El pedido se agregó con exito", Snackbar.LENGTH_SHORT).show()
                view.findNavController().navigateUp()
            }
            .show()
        return result
    }*/

    //---------------- Calificaciones de prestador ------------------------------------------
    fun opinionesDelPrestador(){

      //  opiniones.setView(this.view)
        opiniones.emptyList()
        opiniones.recyclerView(this.view, this.pedido.idCliente)
    }


    fun snackPrecio(){
        Snackbar.make(this.view, "Debe ingresar el precio para acpetar el pedido", Snackbar.LENGTH_SHORT)
                    .show()
    }

}