package com.ort.servitodo.viewmodels.prestador

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.servitodo.adapters.PedidosPendientesPrestadorAdapter
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.fragments.cliente.HomeClienteFragmentDirections
import com.ort.servitodo.fragments.prestador.HomePrestadorFragmentDirections
import com.ort.servitodo.fragments.prestador.PeticionesPendientesFragment
import com.ort.servitodo.fragments.prestador.PeticionesPendientesFragmentDirections
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch
import java.text.FieldPosition

class PeticionesPendientesViewModel : ViewModel() {

    private lateinit var view : View
    private var repositoryPedidos = PedidosRepository()
    private var repositoryPublicaciones = PublicacionRepository()
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
    fun recyclerView(recyclerPedido : RecyclerView){

        cargando.value = "Cargando..."

        viewModelScope.launch{
            repositoryPedidos.changeState()
            pedidos = repositoryPedidos.getPedidosPendientesByPrestadorId(usuarioRep.getIdSession())

            if(pedidos.size < 1) {
                cargando.value = "No hay publicaciones disponibles"
            }
            else{
                recyclerPedido.setHasFixedSize(true)

                cargando.value = ""

                recyclerPedido.layoutManager  = LinearLayoutManager(view.context)

                recyclerPedido.adapter = PedidosPendientesPrestadorAdapter(pedidos){
                    pos ->
                    onItemClick(pos)}
            }
        }
    }
// parametro position en el onClick arriba y abajo
    private fun onItemClick(position: Int){
        viewModelScope.launch{
            val pedido = repositoryPedidos.getPedidoByIndex(position)
            val cliente = usuarioRep.getUsuarioById(pedido.idCliente).gatArrayDatos()
            val publicacion = repositoryPublicaciones.getPublicacionById(pedido.idPublicacion)
            val action = PeticionesPendientesFragmentDirections.actionPeticionesPendientesFragment2ToDetallePedidoPendienteFragment(pedido, cliente, publicacion)
            view.findNavController().navigate(action)
        }


    }


}