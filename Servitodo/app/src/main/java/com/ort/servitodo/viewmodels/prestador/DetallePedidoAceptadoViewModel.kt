package com.ort.servitodo.viewmodels.prestador

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ort.servitodo.R
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.TipoEstado
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.repositories.UsuarioRepository
import com.ort.servitodo.viewmodels.resources.WhatsAppViewModel
import kotlinx.coroutines.launch

class DetallePedidoAceptadoViewModel : ViewModel() {

    private lateinit var view : View
    private lateinit var pedido : Pedido
    private lateinit var usuario : Usuario

    //----------------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
    }

    fun setPedido(pedido : Pedido){
        this.pedido = pedido
    }

    //----------------------------------------------------------------------------------------
    suspend fun getPedido(id : Int) : Pedido{
        return PedidosRepository().getPedidoByIndex(id)
    }
    suspend fun getPublicacion(id : Int) : Publicacion{
        return PublicacionRepository().getPublicacionById(id)
    }
    suspend fun getRubroDetails(id : Int) : String {
        return PublicacionRepository().getRubro(id).toString()
    }
    suspend fun getUsuario(id: String) : Usuario{
        return UsuarioRepository(view).getUsuarioById(id)
    }
//---------------------------------------------------------------
  //metodo de prueba

     /*fun detallesDelPedidoCliente(pedido : Pedido){


        val dialog = BottomSheetDialog(view.context)
        dialog.setContentView(R.layout.fragment_card_historial_prestador)

        setPedido(pedido)

        val img = dialog.findViewById<ImageView>(R.id.imgPedido)!!
        val nombre = dialog.findViewById<TextView>(R.id.txtNombrePrestador)!!
        val fecha = dialog.findViewById<TextView>(R.id.txtHorario)!!
        val precio = dialog.findViewById<TextView>(R.id.txtPrecio)!!
        val estado = dialog.findViewById<TextView>(R.id.txtEstado)!!

        viewModelScope.launch {
            val pedido = getPedido(pedido.id)
            val user = getUsuario(pedido.idCliente)

            setImg(user.foto, img )
            nombre.text = "${user.nombre} ${user.apellido}"
            fecha.text = "Fecha: ${pedido.fecha} - Hora: ${pedido.hora}"
            precio.text = "Precio: $${setPrecio(pedido.precio)}"
            estado.text = "Estado: ${pedido.estado}"



            }

            dialog.show()
        }*/

    fun detallesDelPedido(pedido : Pedido){
        val dialog = BottomSheetDialog(view.context)
        dialog.setContentView(R.layout.fragment_bottom_sheet_pedido_aceptado_prestador)

        setPedido(pedido)

        val img = dialog.findViewById<ImageView>(R.id.imgBottomSheet)!!
        val nombre = dialog.findViewById<TextView>(R.id.nombreBottomSheet)!!
        val rubro = dialog.findViewById<TextView>(R.id.rubroBottomSheet)!!
        val rubroDetalle = dialog.findViewById<TextView>(R.id.rubroDetalleBottomSheet)!!
        val fecha = dialog.findViewById<TextView>(R.id.fechaBottomSheet)!!
        val precio = dialog.findViewById<TextView>(R.id.precioBottomSheet)!!
        val estado = dialog.findViewById<TextView>(R.id.estadoBottomSheet)!!
        val descripcion = dialog.findViewById<TextView>(R.id.descripcionBottomSheet)!!
        val whatsapp = dialog.findViewById<Button>(R.id.whatsappPedidoButton)!!

        viewModelScope.launch {
            val publicacion = getPublicacion(pedido.idPublicacion)
            val rubrodetails = getRubroDetails(publicacion.idServicio)
            val user = getUsuario(pedido.idCliente)
            setImg(user.foto, img)
            nombre.text = "${user.nombre} ${user.apellido}"
            rubro.text = "Rubro: ${publicacion.rubro!!.nombre}"
            fecha.text = "Fecha: ${pedido.fecha} - Hora: ${pedido.hora}"
            precio.text = "Precio: $${setPrecio(pedido.precio)}"
            estado.text = "Estado: ${pedido.estado}"
            descripcion.text = "${publicacion.descripcion}"
            rubroDetalle.text = rubrodetails

            enableWhatsappButton(whatsapp, pedido.estado).setOnClickListener{
                redirectionToWhatsApp(pedido.idPrestador)
            }

            dialog.show()
        }
    }

    private fun setImg(img : String, imageView : ImageView){
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

    //--> WHATSAPP
    fun redirectionToWhatsApp(idPrestador : String){
        val whatsAppViewModel = WhatsAppViewModel()
        whatsAppViewModel.confirmRedirectionToWhatsapp(idPrestador, view)
    }

    fun enableWhatsappButton(button : Button, estado : String) : Button{
        val condition = estado == TipoEstado.PENDIENTE.toString()
        if(condition){
            button.isEnabled = !condition
            button.setTextColor(ContextCompat.getColor(view.context, R.color.greyish))
            button.setBackgroundColor(ContextCompat.getColor(view.context, R.color.light_grey))
        }
        return button
    }
}