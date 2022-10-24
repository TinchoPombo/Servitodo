package com.ort.servitodo.viewmodels.cliente

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ort.servitodo.R
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Prestador
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.repositories.PrestadorRepository
import com.ort.servitodo.repositories.PublicacionRepository
import kotlinx.coroutines.launch

class DetallePedidoViewModel : ViewModel() {

    private lateinit var view : View

    //----------------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
    }

    //----------------------------------------------------------------------------------------
    suspend fun getPublicacion(id : Int) : Publicacion{
        return PublicacionRepository().getPublicacionById(id)
    }

    suspend fun getPrestador(id : Int) : Prestador{
        return PrestadorRepository().getPrestadorById(id)
    }

    fun detallesDelPedido(pedido : Pedido){
        val dialog = BottomSheetDialog(view.context)
        dialog.setContentView(R.layout.fragment_bottom_sheet_pedido_cliente)
        val img = dialog.findViewById<ImageView>(R.id.imgBottomSheet)!!
        val nombre = dialog.findViewById<TextView>(R.id.nombreBottomSheet)!!
        val rubro = dialog.findViewById<TextView>(R.id.rubroBottomSheet)!!
        val fecha = dialog.findViewById<TextView>(R.id.fechaBottomSheet)!!
        val precio = dialog.findViewById<TextView>(R.id.precioBottomSheet)!!
        val estado = dialog.findViewById<TextView>(R.id.estadoBottomSheet)!!
        val descripcion = dialog.findViewById<TextView>(R.id.descripcionBottomSheet)!!

        viewModelScope.launch {
            val publicacion = getPublicacion(pedido.idPublicacion)
            val prestador = getPrestador(pedido.idPrestador)

            setImgPrestador(prestador.img, img)
            nombre.text = "${prestador.name} ${prestador.lastname}"
            rubro.text = "Rubro: ${publicacion.nombreRubro}"
            fecha.text = "Fecha: ${pedido.fecha}"
            precio.text = "Precio: ${setPrecio(pedido.precio)}"
            estado.text = "Estado: ${pedido.estado}"
            descripcion.text = "Descripcion: ${publicacion.descripcion}"
            dialog.show()
        }
    }

    private fun setImgPrestador(img : String, imageView : ImageView){
        Glide
            .with(view)
            .load(img)
            .into(imageView);
    }

    private fun setPrecio(precio : Double) : String{
        var msj = "${precio}"
        if(precio == 0.0 ){
            msj = "Precio a discutir"
        }
        return msj
    }
}