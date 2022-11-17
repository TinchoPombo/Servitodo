package com.ort.servitodo.viewmodels.prestador

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.fragments.prestador.PeticionesPendientesFragmentDirections
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class PeticionesPendientesViewModel : ViewModel() {

    private lateinit var view: View
    private var pedidosRepository = PedidosRepository()
    private var publicacionResrepository = PublicacionRepository()
    private lateinit var usuarioRepository: UsuarioRepository

    val cargando = MutableLiveData<String>()
    val pedidos = MutableLiveData<MutableList<Pedido>>()

    //-------------------------------------------------------------------------------
    fun setView(v: View) {
        this.view = v
        usuarioRepository = UsuarioRepository(this.view)
    }

    private fun emptyList() {
        this.pedidos.value?.clear()
    }

    //-------------------------------------------------------------------------------
    fun setPedidosPendientes() {

        viewModelScope.launch {

            cargando.value = "Cargando..."

            pedidosRepository.changeState()

            emptyList()

            val list = pedidosRepository.getPedidosPendientesByPrestadorId(usuarioRepository.getIdSession())

            if (list.size < 1) {
                cargando.value = "Todavia no hay pedidos solicitados"
            } else {
                pedidos.value = list
                cargando.value = ""
            }

        }
    }

    fun onItemClick(position: Int) {
        viewModelScope.launch {
            val pedido = pedidosRepository.getPedidoByIndex(position)
            val cliente = usuarioRepository.getUsuarioById(pedido.idCliente).gatArrayDatos()
            val publicacion = publicacionResrepository.getPublicacionById(pedido.idPublicacion)
            val action = PeticionesPendientesFragmentDirections.actionPeticionesPendientesFragment2ToDetallePedidoPendienteFragment(pedido, cliente, publicacion)
            view.findNavController().navigate(action)
        }


    }


}