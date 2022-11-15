package com.ort.servitodo.viewmodels.resources.googlemaps

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.ort.servitodo.R
import com.ort.servitodo.entities.Publicacion
import javax.sql.DataSource

class MarkerInfoWindowAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {

    //*** Vista que se utilizará para toda la ventana de información ***
    //Si esta devuelve null, entonces se ejecuta el 2° metodo getInfoContents
    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    //*** Permite personalizar contenido de la ventana (info, marco, fondo, etc) ***
    //--> Si esta devuelve null, entonces se muestra la ventana de información predeterminada
    @SuppressLint("InflateParams", "SetTextI18n")
    override fun getInfoContents(marker: Marker): View? {

        return if(marker.title != "Mi casa"){
            val tag : ObjectTag = marker.tag as ObjectTag
            val view = LayoutInflater.from(context).inflate(R.layout.marker_info_contents, null)

            val imageView = view.findViewById<ImageView>(R.id.markerinfoimg)
            setImage(marker, tag.publicacion!!.fotoPrestador, imageView)

            view.findViewById<TextView>(R.id.text_view_title).text = tag.getCompleteName()
            view.findViewById<TextView>(R.id.text_view_rubro).text = tag.getRubroTxt()
            view.findViewById<TextView>(R.id.text_view_km).text = tag.getDistanceTxt()
            view.findViewById<TextView>(R.id.text_view_rating).text = tag.getPuntajeTxt()

            view
        } else null
    }


    //--------------------------------------- GLIDE ----------------------------------------------
    fun setImage(marker : Marker, foto : String, imageView: ImageView){
        val image = images[marker]
        if (image == null) {
            Glide.with(context)
                .asBitmap()
                .load(foto)
                .dontAnimate()
                .into(getTarget(marker))
        } else {
            imageView.setImageBitmap(image)
        }

    }

    private fun getTarget(marker: Marker): CustomTarget<Bitmap> {
        var target = targets[marker]
        if (target == null) {
            target = InfoTarget(marker)
            targets[marker] = target
        }
        return target
    }

    private val images: HashMap<Marker, Bitmap> = HashMap()
    private val targets: HashMap<Marker, CustomTarget<Bitmap>> = HashMap()

    //---> create this inner class
    inner class InfoTarget(var marker: Marker) : CustomTarget<Bitmap>() {
        override fun onLoadCleared(@Nullable placeholder: Drawable?) {
            //images.remove(marker)
        }

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            images.put(marker, resource)
            //marker.showInfoWindow()
        }
    }
}