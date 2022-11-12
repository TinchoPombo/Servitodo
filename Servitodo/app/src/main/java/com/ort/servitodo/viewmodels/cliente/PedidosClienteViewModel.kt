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

    val cargando = MutableLiveData<String>()
    val pedidos = MutableLiveData<MutableList<Pedido>>()

    //----------------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
        this.usuarioRepository = UsuarioRepository(v)
    }

    private fun emptyList(){
        this.pedidos.value?.clear()
    }

    //----------------------------------------------------------------------------------------
    fun setPedidos(){
        viewModelScope.launch{
            cargando.value = "Cargando..."

            repository.changeState()
            emptyList()
            val userId = usuarioRepository.getIdSession()
            val list = repository.getPedidosCliente(userId)

            if(list.size < 1) {
                cargando.value = "Todavia no hay pedidos solicitados"
            }
            else{
                pedidos.value = list
            }
        }
    }

    fun onItemClick(position : Int){

    }

}