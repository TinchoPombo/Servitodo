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

     /* fun getId() : Int {
         return UsuarioRepository(this.view).getIdSession().toInt()
      }*/


    fun recyclerView(recyclerPedido : RecyclerView){

        cargando.value = "Cargando..."

        viewModelScope.launch{
           //    pedidos = repository.getPedidosByUserIndex(getId())
          pedidos = repository.getPedidosByUserIndex("5".toInt())

            if(pedidos.size < 1) {
                cargando.value = "No hay publicaciones disponibles"
            }
            else{
                recyclerPedido.setHasFixedSize(true)

                cargando.value = ""

                recyclerPedido.layoutManager  = LinearLayoutManager(view.context)



                recyclerPedido.adapter = PedidosHistorialClienteAdapter(pedidos){
                        pos ->
                    onItemClick(pos)}
            }
        }
    }

    private fun onItemClick(position: Int){
        viewModelScope.launch{

            val pedido = repository.getPedidoByIndex(position)
            if(pedido.estado == "FINALIZADO"){
                val action = HistorialClienteFragmentDirections.actionHistorialClienteFragmentToCalificarPrestadorFragment(pedido)
                view.findNavController().navigate(action)}

        }


    }
    // fun calificar() :CardView{
    //   return PedidosHistorialAdapter.PedidosHolder(view).getCardView()
    //}


}