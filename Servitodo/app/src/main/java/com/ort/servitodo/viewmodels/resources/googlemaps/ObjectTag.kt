package com.ort.servitodo.viewmodels.resources.googlemaps

import com.ort.servitodo.entities.Publicacion


class ObjectTag (var publicacion : Publicacion?, var puntaje : Double, var distancia : Double){

    constructor() : this(null,0.00,0.0)

    fun getCompleteName() : String{
        return "${publicacion!!.nombrePrestador} ${publicacion!!.apellidoPrestador}"
    }

    fun getRubroTxt() : String{
        return publicacion!!.rubro.nombre.uppercase()
    }

    fun getDistanceTxt() : String{
        return "${String.format("%.2f", distancia)}km"
    }

    fun getPuntajeTxt() : String{
        return if(puntaje < 0.00){
            "Puntuacion: --"
        }
        else{
            "Puntuacion: ${puntaje}"
        }
    }
}