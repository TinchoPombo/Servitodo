package com.ort.servitodo.viewmodels.cliente

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ort.servitodo.R
import com.ort.servitodo.adapters.PedidosAdapter
import com.ort.servitodo.adapters.PublicacionAdapter
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.fragments.cliente.HomeClienteFragmentDirections
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class PedidosClienteViewModel : ViewModel() {

    private lateinit var view : View
    private lateinit var usuarioRepository: UsuarioRepository

    val repository = PedidosRepository()

    var pedidos : MutableList<Pedido> = arrayListOf()

    val cargando = MutableLiveData<String>()

    //----------------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
        this.usuarioRepository = UsuarioRepository(v)
    }

    fun emptyList(){
        this.pedidos.clear()
    }

    //----------------------------------------------------------------------------------------
    fun recyclerView(recyclerPedidos : RecyclerView){
        val userId = usuarioRepository.getIdSession()
        cargando.value = "Cargando..."

        viewModelScope.launch{
            repository.changeState()
            pedidos = repository.getPedidosCliente(userId)

            if(pedidos.size < 1) {
                cargando.value = "Todavia no hay pedidos solicitados"
            }
            else{
                cargando.value = ""

                recyclerPedidos.setHasFixedSize(true)
                recyclerPedidos.layoutManager  = LinearLayoutManager(view.context)

                recyclerPedidos.adapter = PedidosAdapter(pedidos){ pos ->
                    onItemClick(pos)
                }
            }
        }
    }

    private fun onItemClick(position : Int){

    }

}