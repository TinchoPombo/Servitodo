package com.ort.servitodo.viewmodels.cliente

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ort.servitodo.adapters.PedidosAdapter
import com.ort.servitodo.adapters.PedidosHistorialAdapter
import com.ort.servitodo.adapters.PedidosHistorialClienteAdapter
import com.ort.servitodo.adapters.PedidosPendientesPrestadorAdapter
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.TipoEstado
import com.ort.servitodo.fragments.cliente.HistorialClienteFragment
import com.ort.servitodo.fragments.cliente.HistorialClienteFragmentDirections
import com.ort.servitodo.fragments.prestador.HistorialPrestadorFragmentDirections
import com.ort.servitodo.repositories.CalificacionesRepository
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistorialClienteViewModel : ViewModel() {

    private lateinit var view : View
    private var repository = PedidosRepository()
    val cargando = MutableLiveData<String>()
    val pedidos = MutableLiveData<MutableList<Pedido>>()
    private var repoCalificacion = CalificacionesRepository()


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

    fun onClick(id: Int){

        viewModelScope.launch{

            val pedido = repository.getPedidoById(id)
            val estado = pedido.estado
            val tiene = repoCalificacion.hayCalificacionPorPedidoIdCliente(pedido)

            if(estado == "FINALIZADO") {
                if (!tiene) {
                    val action =
                        HistorialClienteFragmentDirections.actionHistorialClienteFragmentToCalificarPrestadorFragment(
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
            3L-> cargarPedidosCancelados()
            4L-> cargarPedidosPorPrecio()
            5L-> cargarPedidosPorFechaAscendente()
            6L-> cargarPedidosPorFechaDescendente()
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

    fun getFechaInTimeInMillis(dateToParse : String) : Long{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val mDate: Date = dateFormat.parse(dateToParse)
        return mDate.time
    }

    fun cargarPedidosPorFechaDescendente(){

        viewModelScope.launch {
            emptyList()

            val listaHistorial = repository.getPedidosByUserIndex(getActualId())


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
                emptyList()
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
    private fun cargarPedidosCancelados(){

        viewModelScope.launch {
            emptyList()

            val listaHistorial = repository.getPedidosByEstado(getActualId(), "CANCELADO")

            cargando.value = "Cargando...."

            if(listaHistorial.size < 1){
                cargando.value = "No hay pedidos"

            }else{
                pedidos.value = listaHistorial
                cargando.value = ""
            }

        }
    }
    private fun cargarPedidosPorPrecio(){

        viewModelScope.launch {
            emptyList()

            val listaHistorial = repository.getPedidosByUserIndex(getActualId())

            cargando.value = "Cargando...."

            if(listaHistorial.size < 1){
                cargando.value = "No hay pedidos"

            }else{
                pedidos.value = listaHistorial.sortedByDescending { it.precio }.toMutableList()
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