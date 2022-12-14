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
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.repositories.UsuarioRepository
import com.ort.servitodo.viewmodels.prestador.DetallePedidoAceptadoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PedidosPrestadorAdapter(
    var listaPedidos: MutableList<Pedido>
) : RecyclerView.Adapter<PedidosPrestadorAdapter.PedidosHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.peticion_aceptada_prestador, parent, false)
        return (PedidosHolder(view))
    }

    override fun onBindViewHolder(holder: PedidosHolder, position: Int) {

        holder.setDatos(listaPedidos[position])

        holder.setHorario(listaPedidos[position].fecha, listaPedidos[position].hora)
        holder.setEstado(listaPedidos[position].estado)
        holder.setPrecio(listaPedidos[position].precio)

        holder.getCardView().setOnClickListener {
            holder.detallesDelPedido((listaPedidos[position]))
        }

    }

    override fun getItemCount(): Int {
        return listaPedidos.size
    }

    class PedidosHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        private var publicacionRepository = PublicacionRepository()
        private var usuarioRepository = UsuarioRepository(v)

        init {
            this.view = v
        }

        fun setRubro(rubro: String) {
            val txtRubro: TextView = view.findViewById(R.id.txtRubro)
            txtRubro.text = rubro
        }

        fun setNombreCliente(nombre: String) {
            val txtNombrePrestador: TextView = view.findViewById(R.id.txtNombrePrestador)
            txtNombrePrestador.text = nombre
        }

        fun setImagenCliente(img: String) {
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
        fun setPrecio(precio: Double) {
            var txtPrecio: TextView = view.findViewById(R.id.txtPrecio)
            if (precio == 0.0) {
                txtPrecio.text = "--.--"
            } else {
                txtPrecio.text = "$${precio}"
            }
        }

        fun setDatos(pedido: Pedido) {
            var publicacion: Publicacion
            var usuario: Usuario
            val parent = Job()
            val scope = CoroutineScope(Dispatchers.Main + parent)
            scope.launch() {
                publicacion = publicacionRepository.getPublicacionById(pedido.idPublicacion)
                usuario = usuarioRepository.getUsuarioById(pedido.idCliente)
                setNombreCliente(usuario.nombre + " " + usuario.apellido)
                setRubro(publicacion.rubro.nombre)
                setImagenCliente(usuario.foto)
            }
        }


        //--> DETALLE DEL PEDIDO
        fun detallesDelPedido(pedido: Pedido) {
            val detalle = DetallePedidoAceptadoViewModel()
            detalle.setView(view)
            detalle.detallesDelPedido(pedido)
        }


        //-------------------------------------------------------
        fun getCardView(): CardView {
            return view.findViewById(R.id.cardPedidoAceptado)
        }

    }
}