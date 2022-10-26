package com.ort.servitodo.viewmodels.prestador

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.servitodo.adapters.PedidosPrestadorAdapter
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.repositories.PedidosRepository
import kotlinx.coroutines.launch

class PeticionesPendientesViewModel : ViewModel() {

    private lateinit var view : View
    private var repository = PedidosRepository()

    val cargando = MutableLiveData<String>()
    var pedidos : MutableList<Pedido> = arrayListOf()

    //-------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
    }

    fun emptyList(){
        this.pedidos.clear()
    }

    //-------------------------------------------------------------------------------
    fun recyclerView(recyclerPedido : RecyclerView){

        cargando.value = "Cargando..."

        viewModelScope.launch{
            pedidos = repository.getPedidos()

            if(pedidos.size < 1) {
                cargando.value = "No hay publicaciones disponibles"
            }
            else{
                recyclerPedido.setHasFixedSize(true)

                cargando.value = ""

                recyclerPedido.layoutManager  = LinearLayoutManager(view.context)

                recyclerPedido.adapter = PedidosPrestadorAdapter(pedidos){}
            }
        }
    }

//    private fun onItemClick(position : Int){
//        viewModelScope.launch{
//            val pedido = repository.getPedidoByIndex(position)
//
//            val action = HomeClienteFragmentDirections.actionHomeClienteFragmentToDetallePublicacionFragment(pedido)
//            view.findNavController().navigate(action)
//        }
//    }
}