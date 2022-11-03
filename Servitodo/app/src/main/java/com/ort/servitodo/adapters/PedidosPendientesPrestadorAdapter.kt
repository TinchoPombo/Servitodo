package com.ort.servitodo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.servitodo.R
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.repositories.PublicacionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PedidosPendientesPrestadorAdapter (
    var listaPedidos : MutableList <Pedido>,
    var onClick : (Int) -> Unit
) : RecyclerView.Adapter<PedidosPendientesPrestadorAdapter.PedidosHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.peticion_pendiente_prestador, parent, false)
        return (PedidosHolder(view))
    }

    override fun onBindViewHolder(holder: PedidosHolder, position: Int) {

        holder.setDatos(listaPedidos[position].idPublicacion)

        holder.setHorario(listaPedidos[position].fecha, listaPedidos[position].hora)
        holder.setEstado(listaPedidos[position].estado)


        holder.getCardView().setOnClickListener {
            onClick(position)
        }



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

        fun setNombreCliente(nombre : String) {
            val txtNombreCliente: TextView = view.findViewById(R.id.txtNombrePrestador)
            txtNombreCliente.text = nombre
        }

        fun setImagenCliente(img : String) {
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
            var txtEstado: TextView = view.findViewById(R.id.txtEstado2)
            txtEstado.text = estado
        }


        fun setDatos(id : Int){
            var publicacion: Publicacion
            val parent = Job()
            val scope = CoroutineScope(Dispatchers.Main + parent)
            scope.launch() {
                publicacion = publicacionRepository.getPublicacionById(id)
                setNombreCliente(publicacion.nombrePrestador)
                setRubro(publicacion.rubro.nombre)
                setImagenCliente(publicacion.fotoPrestador)
            }
        }

        //--> WHATSAPP
/*        fun redirectionToWhatsApp(idPrestador : Int){
            val whatsAppViewModel = WhatsAppViewModel()
            whatsAppViewModel.confirmRedirectionToWhatsapp(idPrestador, view)
        }



        fun getWhatsappButton() : Button {
            return view.findViewById(R.id.whatsappPedidoButton)
        }*/

        //--> DETALLE DEL PEDIDO
      /*  fun detallesDelPedido(pedido : Pedido){
            val detalle = DetallePedidoViewModel()
            detalle.setView(view)
            detalle.detallesDelPedido(pedido)
        }*/

       /* fun getDetalleButton() : Button {
            return view.findViewById(R.id.detallePedidoButton)
        }*/

        //-------------------------------------------------------
        fun getCardView(): CardView {
            return view.findViewById(R.id.cardPedidoPendiente)
        }

    }
}