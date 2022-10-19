package com.ort.servitodo.viewmodels.resources

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.ort.servitodo.entities.Prestador

class WhatsAppViewModel : ViewModel() {

    private fun whatsapp(prestador : Prestador, view : View){

        val msg = "Hola. Te quiero contratar"
        val packageWhatsApp = "com.whatsapp"

        //if(isAppInstalled(packageWhatsApp, view)){

        /*--> TODO: Opcion 1
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, msg)
            putExtra("jid", "${prestador.numtel}@s.whatsapp.net")
            //putExtra("jid", "https://api.whatsapp.com/send/?phone=${prestador.numtel}")
            type = "text/plain"
            setPackage(packageWhatsApp)
        }
        */

        /*--> TODO: Opcion 2
        val gmnIntentUri = Uri.parse("https://api.whatsapp.com/send?phone=${prestador.numtel}&text=${msg}")
        val sendIntent = Intent(Intent.ACTION_VIEW, gmnIntentUri)
        sendIntent.setPackage(packageWhatsApp)
        view.context.startActivity(sendIntent)
        */

        //--> TODO: Opcion 3
        val gmnIntentUri = Uri.parse("https://wa.me/${prestador.numtel}?text=${msg}")
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

    fun confirmRedirectionToWhatsapp(prestador : Prestador, view : View){

        MaterialAlertDialogBuilder(view.context)
            .setTitle("Confirmar")
            .setMessage("Deseas ser redireccionado a whatsapp?")
            .setNegativeButton("Cancelar") { dialog, which ->

            }
            .setPositiveButton("Aceptar") { dialog, which ->
                this.whatsapp(prestador, view)
                view.findNavController().navigateUp()
            }
            .show()
    }
}