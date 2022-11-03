package com.ort.servitodo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.servitodo.R
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.viewmodels.cliente.DetallePedidoViewModel
import com.ort.servitodo.viewmodels.resources.WhatsAppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PedidosPrestadorAdapter (
    var listaPedidos : MutableList <Pedido>,
    var onClick : (Int) -> Unit
) : RecyclerView.Adapter<PedidosPrestadorAdapter.PedidosHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.peticion_aceptada_prestador, parent, false)
        return (PedidosHolder(view))
    }

    override fun onBindViewHolder(holder: PedidosHolder, position: Int) {

        holder.setDatos(listaPedidos[position].idPublicacion)

        holder.setHorario(listaPedidos[position].fecha, listaPedidos[position].hora)
        holder.setEstado(listaPedidos[position].estado)
        holder.setPrecio(listaPedidos[position].precio)

        holder.getCardView().setOnClickListener {
            holder.detallesDelPedido(listaPedidos[position])
        }

     /*   holder.getDetalleButton().setOnClickListener{
            holder.detallesDelPedido(listaPedidos[position])
        }

        holder.getWhatsappButton().setOnClickListener{
            holder.redirectionToWhatsApp(listaPedidos[position].idPrestador)
        }*/

    }

    override fun getItemCount(): Int {
        return listaPedidos.size
    }

    class PedidosHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        private var publicacionRepository = PublicacionRepository()

        init {
            this.view = v
        }

        fun setRubro(rubro : String) {
            val txtRubro: TextView = view.findViewById(R.id.txtRubro)
            txtRubro.text = rubro
        }

        fun setNombrePrestador(nombre : String) {
            val txtNombrePrestador: TextView = view.findViewById(R.id.txtNombrePrestador)
            txtNombrePrestador.text = nombre
        }

        fun setImagenPrestador(img : String) {
            val imgPedido: ImageView = view.findViewById(R.id.imgPedido)
            Glide
                .with(view)
                .load(img)
                .into(imgPedido);
        }

        @SuppressLint("SetTextI18n")
        fun setHorario(fecha: String, hora: String) {
            var txthorario: TextView = view.findViewById(R.id.txtHorario)
            txthorario.text = "Fecha: $fecha - Hora: $hora"
        }

        fun setEstado(estado: String) {
            var txtEstado: TextView = view.findViewById(R.id.txtEstado)
            txtEstado.text = estado
        }

        @SuppressLint("SetTextI18n")
        fun setPrecio(precio : Double) {
            var txtPrecio: TextView = view.findViewById(R.id.txtPrecio)
            if(precio == 0.0){
                txtPrecio.text = "--.--"
            }
            else{
                txtPrecio.text = "$${precio}"
            }
        }

        fun setDatos(id : Int){
            var publicacion: Publicacion
            val parent = Job()
            val scope = CoroutineScope(Dispatchers.Main + parent)
            scope.launch() {
                publicacion = publicacionRepository.getPublicacionById(id)
                setNombrePrestador(publicacion.nombrePrestador)
                setRubro(publicacion.rubro.nombre)
                setImagenPrestador(publicacion.fotoPrestador)
            }
        }

        //--> WHATSAPP
        fun redirectionToWhatsApp(idPrestador : String){
            val whatsAppViewModel = WhatsAppViewModel()
            whatsAppViewModel.confirmRedirectionToWhatsapp(idPrestador, view)
        }

/*        fun enableWhatsappButton() : Button{
            val button = getWhatsappButton()
                button.isEnabled = true
                button.setTextColor(ContextCompat.getColor(view.context, R.color.greyish))
                button.setBackgroundColor(ContextCompat.getColor(view.context, R.color.light_grey))
            return button
        }*/

       /* fun getWhatsappButton() : Button {
            return view.findViewById(R.id.whatsappPedidoButton)
        }*/

        //--> DETALLE DEL PEDIDO
        fun detallesDelPedido(pedido : Pedido){
            val detalle = DetallePedidoViewModel()
            detalle.setView(view)
            detalle.detallesDelPedido(pedido)
        }

        /*fun getDetalleButton() : Button {
            return view.findViewById(R.id.detallePedidoButton)
        }*/

        //-------------------------------------------------------
        fun getCardView(): CardView {
            return view.findViewById(R.id.cardPedidoAceptado)
        }

    }
}