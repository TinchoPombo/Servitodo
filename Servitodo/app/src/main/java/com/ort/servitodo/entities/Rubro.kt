package com.ort.servitodo.entities

import android.os.Parcel
import android.os.Parcelable

open class Rubro (id: Int, nombre: String,) :
    Parcelable{
    var id: Int = 0

    var nombre: String

    constructor() : this(0,"")

    init {
        this.id = id!!
        this.nombre = nombre!!
    }

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()!!

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(nombre)
    }

    override fun toString(): String {
        return "Rubro(id='$id', nombre='$nombre')"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Rubro> = object : Parcelable.Creator<Rubro> {
            override fun createFromParcel(source: Parcel): Rubro = Rubro(source)
            override fun newArray(size: Int): Array<Rubro?> = arrayOfNulls(size)
        }
    }
}