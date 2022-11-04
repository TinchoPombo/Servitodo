package com.ort.servitodo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.servitodo.R
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.fragments.cliente.HistorialClienteFragmentDirections
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PedidosHistorialAdapter  (
    var listaPedidos : MutableList <Pedido>,
    var onClick : (Int) -> Unit
) : RecyclerView.Adapter<PedidosHistorialAdapter.PedidosHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_card_historial_prestador, parent, false)
        return (PedidosHolder(view))
    }

    override fun onBindViewHolder(holder: PedidosHolder, position: Int) {

        holder.setDatos(listaPedidos[position].id)

        holder.setHorario(listaPedidos[position].fecha, listaPedidos[position].hora)
        holder.setEstado(listaPedidos[position].estado)
        holder.setPrecio(listaPedidos[position].precio)

        holder.getCardView().setOnClickListener {
            onClick(position)
        }
      /* holder.getButom().setOnClickListener(){
         holder.redirect(listaPedidos[position])
       }*/

    }

    override fun getItemCount(): Int {
        return listaPedidos.size
    }

    class PedidosHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        private var pedidoRepository = PedidosRepository()


        init {
            this.view = v
        }

        private var usuarioRepository = UsuarioRepository(view)

        fun setNombreCliente(nombre : String) {
            val txtNombreCliente: TextView = view.findViewById(R.id.txtNombrePrestador)
            txtNombreCliente.text = nombre
        }

        fun setImagenCliente(img : String) {
            val imgPedido: ImageView = view.findViewById(R.id.imagePedido)
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
            var pedido: Pedido
            val parent = Job()
            val scope = CoroutineScope(Dispatchers.Main + parent)
            scope.launch() {
                pedido = pedidoRepository.getPedidoByIndex(id)
                val usuario = usuarioRepository.getUsuarioById(pedido.idCliente.toString())
                setNombreCliente(usuario.nombre + " " + usuario.apellido)
                setImagenCliente(usuario.foto)
                setPrecio(pedido.precio)
            }
        }

        //--> DETALLE DEL PEDIDO
      //  fun detallesDelPedido(pedido : Pedido){
        //    val detalle = DetallePedidoViewModel()
          //  detalle.setView(view)
            //detalle.detallesDelPedidoCliente(pedido)
        //}

       // fun getDetalleButton() : Button {
         //   return view.findViewById(R.id.detallePedidoClienteButton)
        //}

        //-------------------------------------------------------
        fun getCardView(): CardView {
            return view.findViewById(R.id.cardPedidoCliente)
        }

        fun getButom(): Button{
            return view.findViewById(R.id.btmCalificar)
        }

         fun redirect(pedido : Pedido){

            val action = HistorialClienteFragmentDirections.actionHistorialClienteFragmentToCalificarPrestadorFragment(pedido)
            view.findNavController().navigate(action)
        }

    }
}