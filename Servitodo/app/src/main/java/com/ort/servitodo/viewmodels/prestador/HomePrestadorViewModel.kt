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
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class HomePrestadorViewModel : ViewModel() {

    private lateinit var view : View
    private var repository = PedidosRepository()
    private lateinit var usuarioRep : UsuarioRepository

    val cargando = MutableLiveData<String>()
    var pedidos : MutableList<Pedido> = arrayListOf()

    //-------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
       usuarioRep = UsuarioRepository(this.view)
    }

    fun emptyList(){
        this.pedidos.clear()
    }

    //-------------------------------------------------------------------------------
    fun recyclerView(recyclerPedidosAceptados : RecyclerView){

        cargando.value = "Cargando..."

        viewModelScope.launch{
            repository.changeState()
            pedidos = repository.getPedidosAprobadosByPrestadorId(usuarioRep.getIdSession())

            if(pedidos.size < 1) {
                cargando.value = "No hay publicaciones disponibles"
            }
            else{
                recyclerPedidosAceptados.setHasFixedSize(true)

                cargando.value = ""

                recyclerPedidosAceptados.layoutManager  = LinearLayoutManager(view.context)

                recyclerPedidosAceptados.adapter = PedidosPrestadorAdapter(pedidos){}
            }
        }
    }

}