package com.ort.servitodo.viewmodels.prestador

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.ort.servitodo.R
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.entities.TipoEstado
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.repositories.UsuarioRepository
import com.ort.servitodo.viewmodels.resources.WhatsAppViewModel
import com.ort.servitodo.viewmodels.resources.googlemaps.GoogleMapsViewModel
import kotlinx.coroutines.launch

class DetallePedidoAceptadoViewModel : ViewModel() {

    private lateinit var view : View
    private lateinit var pedido : Pedido

    //----------------------------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
    }

    fun setPedido(pedido : Pedido){
        this.pedido = pedido
    }

    //----------------------------------------------------------------------------------------

    suspend fun getPublicacion(id : Int) : Publicacion {
        return PublicacionRepository().getPublicacionById(id)
    }
    suspend fun getRubroDetails(id : Int) : String {
        return PublicacionRepository().getRubro(id).toString()
    }
    suspend fun getUsuario(id: String) : Usuario {
        return UsuarioRepository(view).getUsuarioById(id)
    }


    fun detallesDelPedido2(pedido : Pedido){
        val dialog = BottomSheetDialog(view.context)
        dialog.setContentView(R.layout.card_bottom_sheet_historial)

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
            descripcion.text = "Direccion: ${user.ubicacion}"
            rubroDetalle.text = rubrodetails

            enableButton(whatsapp, pedido.estado, TipoEstado.APROBADO).setOnClickListener{
                redirectionToWhatsApp(pedido.idCliente)
            }

            dialog.show()
        }
    }

    fun detallesDelPedido(pedido : Pedido){
        val dialog = BottomSheetDialog(view.context)
        dialog.setContentView(R.layout.fragment_bottom_sheet_pedido_aceptado_prestador)

        setPedido(pedido)

        val img = dialog.findViewById<ImageView>(R.id.imgBottomSheet)!!
        val nombre = dialog.findViewById<TextView>(R.id.nombreBottomSheet)!!
        val rubro = dialog.findViewById<TextView>(R.id.rubroBottomSheet)!!
        val fecha = dialog.findViewById<TextView>(R.id.rubroDetalleBottomSheet)!!
        val hora = dialog.findViewById<TextView>(R.id.fechaBottomSheet)!!
        val precio = dialog.findViewById<TextView>(R.id.precioBottomSheet)!!
        val estado = dialog.findViewById<TextView>(R.id.estadoBottomSheet)!!
        val descripcion = dialog.findViewById<TextView>(R.id.descripcionBottomSheet)!!
        val cancelar = dialog.findViewById<Button>(R.id.cancelarPedidoButton)!!
        val whatsapp = dialog.findViewById<Button>(R.id.whatsappPedidoButton)!!
        val maps = dialog.findViewById<Button>(R.id.btnMaps)!!


        viewModelScope.launch {
            val publicacion = getPublicacion(pedido.idPublicacion)
            val user = getUsuario(pedido.idCliente)
            setImg(user.foto, img)
            nombre.text = "${user.nombre} ${user.apellido}"
            rubro.text = "Rubro: ${publicacion.rubro!!.nombre}"
            hora.text = "Hora: ${pedido.hora}"
            precio.text = "Precio: $${setPrecio(pedido.precio)}"
            estado.text = "Estado: ${pedido.estado}"
            descripcion.text = "${user.ubicacion}"
            fecha.text = "Fecha: ${pedido.fecha}"



            whatsapp.setOnClickListener(){
                redirectionToWhatsApp(pedido.idCliente)
            }

            maps.setOnClickListener(){
                redirectionToMaps(user.ubicacion)
            }

            enableButton(cancelar, pedido.estado, TipoEstado.EN_CURSO).setOnClickListener{
                popUpCancel(dialog)
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
    fun redirectionToWhatsApp(idCliente : String){
        val whatsAppViewModel = WhatsAppViewModel()
        whatsAppViewModel.confirmRedirectionToWhatsapp(idCliente, view)
    }

    //--> MAPS
    fun redirectionToMaps(goto : String) {
        val googleMapsViewModel = GoogleMapsViewModel()
        googleMapsViewModel.confirmRedirectionToMaps(goto, view)
    }

    //--> ENABLE/ DISABLE BUTTON
    private fun enableButton(button : Button, estadoPedido : String, tipoEstado : TipoEstado) : Button{
        val condition = estadoPedido == tipoEstado.toString()
        if(condition){
            button.isEnabled = !condition
            button.setTextColor(ContextCompat.getColor(view.context, R.color.greyish))
            button.setBackgroundColor(ContextCompat.getColor(view.context, R.color.light_grey))
        }
        return button
    }


    //--> CANCEL BUTTON
    private fun cancel() {
        val pedidoRepository = PedidosRepository()
        pedidoRepository.cancelPedido(this.pedido.id)
    }

    private fun popUpCancel(bottomSheetDialog: BottomSheetDialog){
        MaterialAlertDialogBuilder(view.context).setTitle("Cancelar Pedido").setMessage("Deseas cancelar el pedido?")
            .setNegativeButton("Cancelar") { dialog, which ->

            }
            .setPositiveButton("Aceptar") { dialog, which ->
                viewModelScope.launch {
                    cancel()
                    bottomSheetDialog.dismiss()
                    view.findNavController().navigateUp()
                    Snackbar.make(view, "El pedido se cancel?? con exito", Snackbar.LENGTH_SHORT).show()
                }
            }
            .show()
    }

}