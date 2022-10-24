package com.ort.servitodo.entities

import android.os.Parcel
import android.os.Parcelable

class Prestador (id : Int, username : String, name : String, lastname : String, birth: String, img : String,
                 matricula : String, rubro : String, numtel : String) : Parcelable {
    var id : Int
    var username : String
    var name : String
    var lastname : String
    var birth : String
    var img : String
    var rubro : String
    var matricula : String
    var numtel : String

    //var serviciosPublicados : MutableList<Publicacion> = mutableListOf()
    //var pedidosPendientes : MutableList<Pedido> = mutableListOf()
    //var pedidosAceptados : MutableList<Pedido> = mutableListOf()

    constructor() : this(0,"","","","","","","","")

    init {
        this.id = id!!
        this.username = username!!
        this.name = name!!
        this.lastname = lastname!!
        this.birth = birth!!
        this.img = img!!
        this.rubro = rubro!!
        this.matricula = matricula!!
        this.numtel = numtel!!
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,

        )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(username)
        writeString(name)
        writeString(lastname)
        writeString(birth)
        writeString(img)
        writeString(rubro)
        writeString(matricula)
        writeString(numtel)

    }

    override fun toString(): String {
        return "Prestador(id='$id', username='$username', name='$name', lastname='$lastname', birth='$birth', img='$img', rubro='$rubro', matricula='$matricula', numtel='$numtel')"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Prestador> = object : Parcelable.Creator<Prestador> {
            override fun createFromParcel(source: Parcel): Prestador = Prestador(source)
            override fun newArray(size: Int): Array<Prestador?> = arrayOfNulls(size)
        }
    }

}