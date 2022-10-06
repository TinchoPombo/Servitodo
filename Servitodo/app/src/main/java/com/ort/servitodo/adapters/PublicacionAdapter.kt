package com.ort.servitodo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.servitodo.R
import com.ort.servitodo.entities.Publicacion

class PublicacionAdapter (
    var listaPublicaciones : MutableList <Publicacion>,
    var onClick : (Int) -> Unit
) : RecyclerView.Adapter<PublicacionAdapter.PublicacionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicacionHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_publicacion,parent,false)
        return (PublicacionHolder(view))
    }

    override fun onBindViewHolder(holder: PublicacionHolder, position: Int) {
        holder.setNombrePrestador(listaPublicaciones[position].nombrePrestador)
        holder.setRubro(listaPublicaciones[position].nombreRubro)
        holder.setImagenPrestador(listaPublicaciones[position].fotoPrestador)
        holder.getCardView().setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return listaPublicaciones.size
    }

    class PublicacionHolder (v: View) : RecyclerView.ViewHolder(v) {
        private var view: View
        init {
            this.view = v
        }

        fun setRubro (rubro : String){
            var txtRubro : TextView = view.findViewById(R.id.txtRubro)
            txtRubro.text = rubro
        }

        fun setNombrePrestador (nombrePrestador : String){
            var txtNombrePrestador : TextView = view.findViewById(R.id.txtNombrePrestador)
            txtNombrePrestador.text = nombrePrestador
        }

        fun setImagenPrestador (img : String){
            var imgPrestador : ImageView = view.findViewById(R.id.imgPrestador)
            Glide
                .with(view)
                .load(img)
                .into(imgPrestador);
        }


        fun getCardView () : CardView {
            return view.findViewById(R.id.cardPublicacion)
        }
    }



}