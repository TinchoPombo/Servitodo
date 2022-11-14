package com.ort.servitodo.viewmodels.login

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.ort.servitodo.fragments.login.LogInFragmentDirections
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.launch
import java.util.*

class LogInViewModel : ViewModel() {

    lateinit var v: View
    private lateinit var usuarioRepository : UsuarioRepository

    //---------------------------------------------------------------------------------------------------------
    fun setView(v: View) {
        this.v = v
        usuarioRepository = UsuarioRepository(v)
    }

    //---------------------------------------------------------------------------------------------------------
    fun validarCuenta(email : String, pw: String) {

        //todo encripto aca y paso la pw encriptada a validarMailYPw

        val encoder: Base64.Encoder = Base64.getEncoder()
        var encryptedPw : String = encoder.encodeToString(pw.toByteArray())

        viewModelScope.launch {
            val user = usuarioRepository.validarMailYPw(email, encryptedPw)

            if(user.mail != ""){
                logOut()
                saveSession(user.id, user.mail, user.password)
                redirect(user.esPrestador)
            }else{
                Toast.makeText(v.context, "Datos ingresados incorrectos", Toast.LENGTH_SHORT).show()
            }
            
        }
    }

    fun logOut() {
        usuarioRepository.deleteSharedPreferences()
    }

    fun saveSession(id: String, mail: String, password: String) {
        usuarioRepository.setUserCredentialsInSharedPreferences(id, mail, password)
    }

    private fun redirect(esPrestador: Boolean) {
        val action : NavDirections
        if(esPrestador){
            action = LogInFragmentDirections.actionLogInFragmentToPrestadorActivity()
            v.findNavController().navigate(action)
        }else{
            action = LogInFragmentDirections.actionLogInFragmentToClienteActivity()
            v.findNavController().navigate(action)
        }
    }

    //---------------------------------------------------------------------------------------------------------
    fun registroLogin(){
        val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
        v.findNavController().navigate(action)
    }

    fun login(){
        val action = LogInFragmentDirections.actionLogInFragmentToPrestadorActivity()
        v.findNavController().navigate(action)
    }

    fun login2(){
        val action = LogInFragmentDirections.actionLogInFragmentToClienteActivity()
        v.findNavController().navigate(action)
    }

    fun prest1(){
        saveSession("0123b583-cf9d-442f-8eec-bb164cd90ffa", "ladanyneta@gmail.com", "12345@Aa")
        val action = LogInFragmentDirections.actionLogInFragmentToPrestadorActivity()
        v.findNavController().navigate(action)
    }

    fun prest2(){
        saveSession("83dd325a-580d-4a49-bcc4-44f7acdeabcc", "robertitofunes@gmail.com", "12345@Aa")
        val action = LogInFragmentDirections.actionLogInFragmentToPrestadorActivity()
        v.findNavController().navigate(action)
    }

    fun prest3(){
        saveSession("1f305a50-bb73-4aea-8bf9-ec436fd31384", "josejose@gmail.com", "12345@Aa")
        val action = LogInFragmentDirections.actionLogInFragmentToPrestadorActivity()
        v.findNavController().navigate(action)
    }

    fun cliente1(){
        saveSession("b2427b10-989c-4a4c-9bf4-40a16799d2bc", "martinpombo@gmail.com", "12344Xdd+")
        val action = LogInFragmentDirections.actionLogInFragmentToClienteActivity()
        v.findNavController().navigate(action)
    }

    fun cliente2(){
        saveSession("92aa99d2-9eab-4a6f-a9ae-04f7e3859753", "tomas.berias1@hotmail.com", "1234@Aaa")
        val action = LogInFragmentDirections.actionLogInFragmentToClienteActivity()
        v.findNavController().navigate(action)
    }

    fun cliente3(){
        saveSession("2bcc236a-27b9-4d16-9cee-0f073b76f7cc", "frandesalvo@gmail.com", "12345@Aa")
        val action = LogInFragmentDirections.actionLogInFragmentToClienteActivity()
        v.findNavController().navigate(action)
    }

    //---------------------------------------------------------------------------------------------------------
    //val db = Firebase.firestore

    // Create a new user with a first and last name
    /*val user = Usuario(
        id = 1,
        nombre = "Lovelace",
        apellido = "born"
    )*/

    //db.collection("cities").document("new-city-id").set(user)


    // Add a new document with a generated ID
    /*db.collection("users")
        .add(user)
        .addOnSuccessListener { documentReference ->
        Log.e("Buenas", documentReference.toString())
        //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }*/

}