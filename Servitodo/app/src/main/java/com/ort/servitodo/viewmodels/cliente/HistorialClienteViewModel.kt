package com.ort.servitodo.viewmodels.cliente

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.servitodo.adapters.PedidosAdapter
import com.ort.servitodo.adapters.PedidosHistorialAdapter
import com.ort.servitodo.adapters.PedidosHistorialClienteAdapter
import com.ort.servitodo.adapters.PedidosPendientesPrestadorAdapter
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.TipoEstado
import com.ort.servitodo.fragments.cliente.HistorialClienteFragment
import com.ort.servitodo.fragments.cliente.HistorialClienteFragmentDirections
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class HistorialClienteViewModel : ViewModel() {

    private lateinit var view : View
    private var repository = PedidosRepository()
    val cargando = MutableLiveData<String>()
    var pedidos : MutableList<Pedido> = arrayListOf()


    fun setView(v : View){
        this.view = v
    }

    fun emptyList(){
        this.pedidos.clear()
    }

    private fun getActualId() : String{
        var id : String = ""


        id =  UsuarioRepository(this.view).getIdSession()

        return id
    }


    fun recyclerView(recyclerPedido : RecyclerView){

        cargando.value = "Cargando..."

        viewModelScope.launch{

            pedidos = repository.getPedidosByUserIndex(getActualId())


            if(pedidos.size < 1) {
                cargando.value = "No hay publicaciones disponibles"
            }
            else{
                recyclerPedido.setHasFixedSize(true)

                cargando.value = ""

                recyclerPedido.layoutManager  = LinearLayoutManager(view.context)



                recyclerPedido.adapter = PedidosHistorialClienteAdapter(pedidos){
                        pos ->
                    onClick(pos)}
            }
        }
    }

    private fun onClick(position: Int){
        viewModelScope.launch{

            val pedido = repository.getPedidoByIndex(position)
            val estado = pedido.estado
            if(estado == "FINALIZADO" ){
                val action = HistorialClienteFragmentDirections.actionHistorialClienteFragmentToCalificarPrestadorFragment(pedido)
                view.findNavController().navigate(action)
            }

        }


    }



}