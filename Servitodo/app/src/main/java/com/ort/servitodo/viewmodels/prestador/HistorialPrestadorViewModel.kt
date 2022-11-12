package com.ort.servitodo.viewmodels.prestador

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.protobuf.Empty
import com.ort.servitodo.R
import com.ort.servitodo.adapters.PedidosAdapter
import com.ort.servitodo.adapters.PedidosHistorialAdapter
import com.ort.servitodo.adapters.PedidosPendientesPrestadorAdapter
import com.ort.servitodo.adapters.PublicacionAdapter
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.fragments.cliente.HomeClienteFragmentDirections
import com.ort.servitodo.fragments.prestador.HistorialPrestadorFragment
import com.ort.servitodo.fragments.prestador.HistorialPrestadorFragmentDirections
import com.ort.servitodo.fragments.prestador.PeticionesPendientesFragmentDirections
import com.ort.servitodo.repositories.CalificacionesRepository
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class HistorialPrestadorViewModel : ViewModel() {

    private lateinit var view : View
    private var repository = PedidosRepository()
    private var repoCalificacion = CalificacionesRepository()
    val cargando = MutableLiveData<String>()

    val pedidos = MutableLiveData<MutableList<Pedido>>()



    fun setView(v : View){
        this.view = v
    }

    fun emptyList(){
        this.pedidos.value?.clear()
    }


     private fun getActualId() : String{
         var id : String = ""
         id =  UsuarioRepository(this.view).getIdSession()
         return id
     }

    fun snackCalificar(){
        Snackbar.make(this.view, "No puedes calificar un pedido no finalizado", Snackbar.LENGTH_SHORT)
            .show()
    }
    fun snackYaTieneCalificacion(){
        Snackbar.make(this.view, "El pedido ya tiene calificacion", Snackbar.LENGTH_SHORT)
            .show()
    }
    fun getFechaInTimeInMillis(dateToParse : String) : Long{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val mDate: Date = dateFormat.parse(dateToParse)
        return mDate.time
    }

    fun onClick(id: Int){

        viewModelScope.launch{

            val pedido = repository.getPedidoById(id)
            val estado = pedido.estado
            val tiene = repoCalificacion.hayCalificacionPorPedidoIdPrestador(pedido)

            if(estado == "FINALIZADO") {
                if (!tiene) {
                    val action =
                        HistorialPrestadorFragmentDirections.actionHistorialPrestadorFragmentToCalificarClienteFragment(
                            pedido
                        )
                    view.findNavController().navigate(action)
                } else {
                    snackYaTieneCalificacion()
                }
            }else{
                snackCalificar()
            }
        }
    }
    fun onClickFiltro(l : Long){
        when(l){
            0L-> cargarPedidos()
            1L-> cargarPedidosFinalizados()
            2L-> cargarPedidosEnCurso()
            3L-> cargarPedidosRechazados()
            4L-> cargarPedidosAprobados()
            5L-> cargarPedidosPendientes()
            6L-> cargarPedidosPorFechaAscendente()
            7L-> cargarPedidosPorFechaDescendente()
        }
    }
    fun cargarPedidos(){

        viewModelScope.launch {
            emptyList()

            val listaHistorial = repository.getPedidosByUserIndex(getActualId())

            cargando.value = "Cargando...."

            if(listaHistorial.size < 1){
                cargando.value = "No hay pedidos"
            }else{
                pedidos.value = listaHistorial
                cargando.value = ""
            }

        }

    }

    fun cargarPedidosPorFechaDescendente(){

        viewModelScope.launch {
            emptyList()

            val listaHistorial = repository.getPedidosByUserIndex(getActualId())
            listaHistorial.sortedByDescending { it.fecha }

            cargando.value = "Cargando...."

            if(listaHistorial.size < 1){
                cargando.value = "No hay pedidos"
            }else{
                pedidos.value = listaHistorial.sortedByDescending { getFechaInTimeInMillis(it.fecha) }.toMutableList()
                cargando.value = ""
            }

        }
    }

    fun cargarPedidosPorFechaAscendente(){

        viewModelScope.launch {
            emptyList()

            val listaHistorial = repository.getPedidosByUserIndex(getActualId())
            listaHistorial.sortedBy { it.fecha }

            cargando.value = "Cargando...."

            if(listaHistorial.size < 1){
                cargando.value = "No hay pedidos"
            }else{
                pedidos.value = listaHistorial.sortedBy { getFechaInTimeInMillis(it.fecha) }.toMutableList()
                cargando.value = ""
            }

        }
    }


    private fun cargarPedidosFinalizados(){

        viewModelScope.launch {
            emptyList()

            val listaHistorial = repository.getPedidosByEstado(getActualId(), "FINALIZADO")

            cargando.value = "Cargando...."

            if(listaHistorial.size < 1){
                cargando.value = "No hay pedidos"
            }else{
                pedidos.value = listaHistorial
                cargando.value = ""
            }

        }
    }

    private fun cargarPedidosEnCurso(){

        viewModelScope.launch {
            emptyList()

            val listaHistorial = repository.getPedidosByEstado(getActualId(), "EN CURSO")

            cargando.value = "Cargando...."

            if(listaHistorial.size < 1){
                cargando.value = "No hay pedidos"

            }else{
                pedidos.value = listaHistorial
                cargando.value = ""
            }

        }
    }
    private fun cargarPedidosRechazados(){

        viewModelScope.launch {
            emptyList()

            val listaHistorial = repository.getPedidosByEstado(getActualId(), "RECHAZADO")

            cargando.value = "Cargando...."

            if(listaHistorial.size < 1){
                cargando.value = "No hay pedidos"
            }else{
                pedidos.value = listaHistorial
                cargando.value = ""
            }

        }
    }
    private fun cargarPedidosAprobados(){

        viewModelScope.launch {
            emptyList()

            val listaHistorial = repository.getPedidosByEstado(getActualId(), "APROBADO")

            cargando.value = "Cargando...."

            if(listaHistorial.size < 1){
                cargando.value = "No hay pedidos"
            }else{
                pedidos.value = listaHistorial
                cargando.value = ""
            }

        }
    }
    private fun cargarPedidosPendientes(){

        viewModelScope.launch {
            emptyList()

            val listaHistorial = repository.getPedidosByEstado(getActualId(), "PENDIENTE")

            cargando.value = "Cargando...."

            if(listaHistorial.size < 1){
                cargando.value = "No hay pedidos"
            }else{
                pedidos.value = listaHistorial
                cargando.value = ""
            }

        }
    }
}



