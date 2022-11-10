package com.ort.servitodo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.servitodo.R

import com.ort.servitodo.entities.Puntuacion
import com.ort.servitodo.entities.Usuario

import com.ort.servitodo.repositories.UsuarioRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CalificacionesAdapter (
    var listaCalificaciones : MutableList <Puntuacion>,
    var onClick : (Int) -> Unit
) : RecyclerView.Adapter<CalificacionesAdapter.CalificacionesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalificacionesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_card_opiniones, parent, false)
        return (CalificacionesHolder(view))
    }

    override fun onBindViewHolder(holder: CalificacionesHolder, position: Int) {

        holder.setDatos(listaCalificaciones[position].idCliente)

        holder.setRating(listaCalificaciones[position].puntaje)

        holder.setDescripcion(listaCalificaciones[position].comentario)

        holder.getCardView().setOnClickListener {
            onClick(position)
        }


    }

    override fun getItemCount(): Int {
        return listaCalificaciones.size
    }

    class CalificacionesHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View

        init {
            this.view = v
        }


        fun setNombreUsuario(nombre : String) {
            val nombreUsuario: TextView = view.findViewById(R.id.nombre_opinion)
            nombreUsuario.text = nombre
        }

        fun setRating(rating : Float){
            val ratingUsuario : RatingBar = view.findViewById((R.id.puntaje_opinion))
            ratingUsuario.rating = rating
        }

        fun setImagenUsuario(img : String) {
            val imgPedido: ImageView = view.findViewById(R.id.imagen_opinion)
            Glide
                .with(view)
                .load(img)
                .into(imgPedido);
        }
        fun setDescripcion(desc : String){
            val descripcion : TextView = view.findViewById(R.id.descripcion_opinion)
            descripcion.text = desc
        }

        fun setDatos(id : String){
            var usuario : Usuario
            val parent = Job()
            val scope = CoroutineScope(Dispatchers.Main + parent)
            scope.launch() {
                usuario = UsuarioRepository(view).getUsuarioById(id)
                setImagenUsuario(usuario.foto)
                setNombreUsuario(usuario.nombre + "" + usuario.apellido)
            }
        }

        //-------------------------------------------------------
        fun getCardView(): CardView {
            return view.findViewById(R.id.card_opinion)
        }



    }
}
