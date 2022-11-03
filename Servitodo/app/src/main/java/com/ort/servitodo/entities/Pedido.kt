package com.ort.servitodo.entities

import android.os.Parcel
import android.os.Parcelable

class Pedido (id : Int, idPublicacion : Int, idPrestador : String, idCliente : Int, fecha : String, hora : String, estado : String, precio : Double)
    : Parcelable {
    var id : Int = 0
    var idPublicacion : Int = 0
    var idPrestador : String
    var idCliente : Int = 0
    var fecha : String
    var hora : String
    var estado : String
    var precio : Double = 0.0

    constructor() : this(0,0,"",0,"","","",0.0)

    init {
        this.id = id
        this.idPublicacion = idPublicacion
        this.idCliente = idCliente
        this.idPrestador = idPrestador
        this.fecha = fecha
        this.hora = hora
        this.estado = estado
        this.precio = precio
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
        source.readString()!!,
        source.readInt(),
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readDouble(),
        )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeInt(idPublicacion)
        writeInt(idCliente)
        writeString(idPrestador)
        writeString(fecha)
        writeString(hora)
        writeString(estado)
        writeDouble(precio)
    }

    override fun toString(): String {
        return "Publicacion(idPublicacion='$idPublicacion', idPublicacion='$idPublicacion', "+
                "idCliente='$idCliente', fecha='$fecha', " +
                "hora=$hora, estado='$estado', precio='$precio'"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Pedido> = object : Parcelable.Creator<Pedido> {
            override fun createFromParcel(source: Parcel): Pedido = Pedido(source)
            override fun newArray(size: Int): Array<Pedido?> = arrayOfNulls(size)
        }
    }
}