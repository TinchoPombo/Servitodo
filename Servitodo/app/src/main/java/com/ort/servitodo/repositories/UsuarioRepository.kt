package com.ort.servitodo.repositories

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext

class UsuarioRepository(view : View) {

    /*--> Shared preferences: persistir datos pequeños (como el username y password para que el user,
    sea prestador o cliente, este ya logueado).

    - Se guarda en un archivito de manera local.
    - Es la mejor opcion (no vamos a levantar ROOM solo por dos datos)
    */

    private var view = view

    private val PREF_NAME = "myPreferences"
    private val sharedPref : SharedPreferences = view.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    //-----------> Setea el username y password en la shared preferences <------------------------------

    fun setClienteCredentialsInSharedPreferences(username : String, password: String){ //--> CLIENTE
        editor.putString("USER_CLIENTE", username)
        editor.putString("PASSWORD_CLIENTE", password)
        editor.apply()
    }

    fun setPrestadorCredentialsInSharedPreferences(username : String, password: String){ //--> PRESTADOR
        editor.putString("USER_PRESTADOR", username)
        editor.putString("PASSWORD_PRESTADOR", password)
        editor.apply()
    }

    //----------------------------------------> GETTERS <--------------------------------------------

    fun getUsernameClienteInSharedPreferences() : String{ //--> Getters de CLIENTE
        return sharedPref.getString("USER_CLIENTE", "default")!!
    }

    fun getPasswordClienteInSharedPreferences() : String{
        return sharedPref.getString("PASSWORD_CLIENTE", "default")!!
    }

    fun getUsernamePrestadorInSharedPreferences() : String{ //--> Getters de PRESTADOR
        return sharedPref.getString("USER_PRESTADOR", "default")!!
    }

    fun getPasswordPrestadorInSharedPreferences() : String{
        return sharedPref.getString("PASSWORD_PRESTADOR", "default")!!
    }

    /*TODO:
       Cuando el usuario se loguea, que haya algun checkbox que diga "queres mantener la sesion iniciada?"
       o algo asi, y si le da al checkbox, que se guarde las credenciales en la shared preferences
     */

    /*--> PEGAR ESTE CODIGO EN ALGUN VIEW QUE TENGA UN BUTTON Y VER EN EL "lOGCAT" (esta chequeado -> FUNCIONA)

    val userrepo = UsuarioRepository(this.view)
        userrepo.setClienteCredentialsInSharedPreferences("dannybak", "123465")
        Log.d("clientecredentials", "username: ${userrepo.getUsernameClienteInSharedPreferences()} + " +
                "${userrepo.getPasswordClienteInSharedPreferences()}")

     */
}