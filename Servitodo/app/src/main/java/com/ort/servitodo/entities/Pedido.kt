package com.ort.servitodo.entities

import android.os.Parcel
import android.os.Parcelable

class Pedido (id : Int, idPublicacion : Int, idCliente : Int, fecha : String, hora : String, estado : String)
    : Parcelable {
    var id : Int = 0
    var idPublicacion : Int = 0
    var idCliente : Int = 0
    var fecha : String
    var hora : String
    var estado : String

    constructor() : this(0,0,0,"","","")

    init {
        this.id = id
        this.idPublicacion = idPublicacion
        this.idCliente = idCliente
        this.fecha = fecha
        this.hora = hora
        this.estado = estado
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readString()!!,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeInt(idPublicacion)
        writeInt(idCliente)
        writeString(fecha)
        writeString(hora)
        writeString(estado)
    }

    override fun toString(): String {
        return "Publicacion(idPublicacion='$idPublicacion', " +
                "idCliente='$idCliente', fecha='$fecha', " +
                "hora=$hora, estado='$estado'"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Pedido> = object : Parcelable.Creator<Pedido> {
            override fun createFromParcel(source: Parcel): Pedido = Pedido(source)
            override fun newArray(size: Int): Array<Pedido?> = arrayOfNulls(size)
        }
    }
}