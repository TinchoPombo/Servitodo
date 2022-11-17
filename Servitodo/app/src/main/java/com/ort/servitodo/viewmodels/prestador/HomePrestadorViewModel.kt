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
    val pedidos = MutableLiveData<MutableList<Pedido>>()

    //-------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
       usuarioRep = UsuarioRepository(this.view)
    }

    private fun emptyList() {
        this.pedidos.value?.clear()
    }

    //-------------------------------------------------------------------------------

    fun setPedidosAprobados(){

        viewModelScope.launch{

            cargando.value = "Cargando..."

            repository.changeState()

            emptyList()

            val list = repository.getPedidosAprobadosByPrestadorId(usuarioRep.getIdSession())

            if(list.size < 1) {
                cargando.value = "No hay publicaciones disponibles"
            }
            else{
                pedidos.value = list
                cargando.value = ""
            }
        }

    }

    /*fun onItemClick(position : Int){

    }*/

}