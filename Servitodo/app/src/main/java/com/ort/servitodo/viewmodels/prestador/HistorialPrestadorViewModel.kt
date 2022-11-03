package com.ort.servitodo.viewmodels.prestador

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
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
import com.ort.servitodo.adapters.PedidosHistorialAdapter
import com.ort.servitodo.adapters.PedidosPendientesPrestadorAdapter
import com.ort.servitodo.adapters.PublicacionAdapter
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.fragments.cliente.HomeClienteFragmentDirections
import com.ort.servitodo.fragments.prestador.HistorialPrestadorFragment
import com.ort.servitodo.fragments.prestador.HistorialPrestadorFragmentDirections
import com.ort.servitodo.fragments.prestador.PeticionesPendientesFragmentDirections
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch

class HistorialPrestadorViewModel : ViewModel() {

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

  /*  fun getId() : Int {
       return UsuarioRepository(view).getIdSession().toInt()
    }*/


    fun recyclerView(recyclerPedido : RecyclerView){

        cargando.value = "Cargando..."

        viewModelScope.launch{
            //   pedidos = repository.getPedidosByUserIndex(getId())
            //pedidos = repository.getPedidosByUserIndex("5")

            if(pedidos.size < 1) {
                cargando.value = "No hay publicaciones disponibles"
            }
            else{
                recyclerPedido.setHasFixedSize(true)

                cargando.value = ""

                recyclerPedido.layoutManager  = LinearLayoutManager(view.context)



                recyclerPedido.adapter = PedidosHistorialAdapter(pedidos){
                   pos ->
                   onItemClick(pos)}
            }
        }
    }

   private fun onItemClick(position: Int){
      viewModelScope.launch{

          val pedido = repository.getPedidoByIndex(position)
            if(pedido.estado == "FINALIZADO"){
                val action = HistorialPrestadorFragmentDirections.actionHistorialPrestadorFragmentToCalificarClienteFragment(pedido)
                view.findNavController().navigate(action)            }

        }


    }
   // fun calificar() :CardView{
     //   return PedidosHistorialAdapter.PedidosHolder(view).getCardView()
    //}


}