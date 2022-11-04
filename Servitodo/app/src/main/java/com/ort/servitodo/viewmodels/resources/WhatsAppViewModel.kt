package com.ort.servitodo.viewmodels.resources

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WhatsAppViewModel : ViewModel() {



    private fun whatsapp(numtel : String, view : View){

        val msg = "Hola. Te quiero contratar"
        val packageWhatsApp = "com.whatsapp"

        //if(isAppInstalled(packageWhatsApp, view)){
            //--> TODO: Opcion 3
            val gmnIntentUri = Uri.parse("https://wa.me/phone=${numtel}?text=${msg}")
            val sendIntent = Intent(Intent.ACTION_VIEW, gmnIntentUri)
            sendIntent.setPackage(packageWhatsApp)
            view.context.startActivity(sendIntent)
        /*}
        else{
            Snackbar.make(view, "Debes instalar Whatsapp", Snackbar.LENGTH_SHORT).show()
        }*/
    }

    private fun isAppInstalled(packageName: String, view : View): Boolean {
        val pm: PackageManager = view.context.packageManager
        return try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun confirmRedirectionToWhatsapp(idPrestador: String, view : View){

        MaterialAlertDialogBuilder(view.context)
            .setTitle("Confirmar")
            .setMessage("Deseas ser redireccionado a whatsapp?")
            .setNegativeButton("Cancelar") { dialog, which ->

            }
            .setPositiveButton("Aceptar") { dialog, which ->
                viewModelScope.launch {
                    val tel = getNumtel(idPrestador, view)

                    whatsapp(tel, view)
                }
            }
            .show()
    }

    private suspend fun getNumtel(idPrestador : String, v : View):String{
        var tel = ""

        val prestadores = UsuarioRepository(v).getUsuarios()
        val prestadorEncontrado = prestadores.find{p-> p.id.equals(idPrestador)}
        tel = prestadorEncontrado?.telefono!!

        return tel
    }
}