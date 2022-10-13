package com.ort.servitodo.viewmodels.cliente

import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.ort.servitodo.entities.Prestador
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.repositories.PrestadorRepository
import com.ort.servitodo.repositories.PublicacionRepository

class DetallePublicacionViewModel : ViewModel() {

    private lateinit var view : View
    private var publicacionRepository = PublicacionRepository()
    private var prestadorRepository = PrestadorRepository()

    //----------------------------------------------------------------------
    fun setView(v : View){
        this.view = v
    }

    //----------------------------------------------------------------------
    fun getPublicacionByIndex(receiveIndex : Int) : Publicacion{
        return publicacionRepository.getPublicaciones()[receiveIndex]
    }

    fun getPrestadorById(index : Int) : Prestador {
        return prestadorRepository.getPrestadores()[index]
    }

    //-------------------- Redireccion a whatsapp --------------------------------------------------
    fun whatsapp(index : Int){
        val prestador = getPrestadorById(index)
        val msg = "Hola. Te quiero contratar"
        val packageWhatsApp = "com.whatsapp"

        if(isAppInstalled(packageWhatsApp)){

            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, msg)
                putExtra("jid", "${prestador.numtel}@s.whatsapp.net")
                type = "text/plain"
                setPackage(packageWhatsApp)
            }
            view.context.startActivity(sendIntent)

        }
        else{
            Snackbar.make(view, "Debes instalar Whatsapp", Snackbar.LENGTH_SHORT).show()
        }
    }

    //--> Chequea que la App este instalada
    private fun isAppInstalled(packageName: String): Boolean {
        val pm: PackageManager = view.context.packageManager
        return try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}