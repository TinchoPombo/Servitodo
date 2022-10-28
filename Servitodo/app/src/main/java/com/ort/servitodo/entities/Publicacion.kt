package com.ort.servitodo.entities

import android.os.Parcel
import android.os.Parcelable

class Publicacion (idServicio : Int, idPrestador : Int, fotoPrestador : String, nombrePrestador : String, apellidoPrestador : String, rubro: Rubro, descripcion : String
    /*var listaPuntuaciones : MutableList<Puntuacion> = mutableListOf(),
    var imagenes : MutableList<String> = mutableListOf(),*/
) : Parcelable{
    var idServicio: Int = 0

    var idPrestador: Int = 0

    var fotoPrestador: String

    var nombrePrestador: String

    var apellidoPrestador: String

    var rubro : Rubro = Mantenimiento(0,0,"")

    var descripcion: String

    constructor() : this(0,0,"","","", Mantenimiento(0, 0,""),"")

    init {
        this.idServicio = idServicio!!
        this.idPrestador = idPrestador!!
        this.fotoPrestador = fotoPrestador!!
        this.nombrePrestador = nombrePrestador!!
        this.apellidoPrestador = apellidoPrestador!!
        this.rubro = rubro!!
        this.descripcion = descripcion!!
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readParcelable(Publicacion::class.java.classLoader)!!,
        source.readString()!!

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(idServicio)
        writeInt(idPrestador)
        writeString(fotoPrestador)
        writeString(nombrePrestador)
        writeString(apellidoPrestador)
        writeParcelable(rubro, flags)
        writeString(descripcion)
    }

    override fun toString(): String {
        return "Publicacion(idServicio='$idServicio', idPrestador='$idPrestador', fotoPrestador='$fotoPrestador', nombrePrestador=$nombrePrestador, apellidoPrestador='$apellidoPrestador', rubro='$rubro', descripcion='$descripcion')"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Publicacion> = object : Parcelable.Creator<Publicacion> {
            override fun createFromParcel(source: Parcel): Publicacion = Publicacion(source)
            override fun newArray(size: Int): Array<Publicacion?> = arrayOfNulls(size)
        }
    }
}