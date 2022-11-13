package com.ort.servitodo.viewmodels.login

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.R
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.fragments.login.SignUpFragmentDirections
import com.ort.servitodo.repositories.PedidosRepository
import com.ort.servitodo.repositories.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class SignUpViewModel : ViewModel() {

    private lateinit var v: View
    private lateinit var usuarioRepository : UsuarioRepository

    //---------------------------------------------------------------------------------------------------------
    fun setView(v: View) {
        this.v = v
        usuarioRepository = UsuarioRepository(v)
    }

    private var mailExistente = false
    private final var REQUERIDO = "Requerido"

    //----------LiveData----------------------------------------------------------------------------------------

    var msgValidarNombre = MutableLiveData<String>()
    var msgValidarApellido = MutableLiveData<String>()
    var msgValidarEmail = MutableLiveData<String>()
    var msgValidarContrasenia = MutableLiveData<String>()
    var msgValidarTelefono = MutableLiveData<String>()
    var msgValidarCalle = MutableLiveData<String>()
    var msgvalidarRadio = MutableLiveData<String>()
    var resetForm = MutableLiveData<Boolean>()
    var mailValido = MutableLiveData<Int>()  //se usara para definir si el email ya existe o no


    //----------------------------------------------------------------------------------------------------------

    fun initLiveData() {
        msgValidarNombre.value = REQUERIDO
        msgValidarApellido.value = REQUERIDO
        msgValidarEmail.value = REQUERIDO
        msgValidarContrasenia.value = REQUERIDO
        msgValidarTelefono.value = REQUERIDO
        msgValidarCalle.value = REQUERIDO
        msgvalidarRadio.value = "Seleccione una opcion"
        resetForm.value = false
        mailValido. value = 0   //0=No validado, 1=Mail valido, 2=Mail invalido
    }


    //----------Funciones de validacion de campos ingresados----------------------------------------------------
    fun validNombre(nombre: String) {
        if(nombre.length < 3){
            msgValidarNombre.value = "Ingrese un nombre valido"
        }else{
            msgValidarNombre.value= ""
        }
    }

    fun validApellido(apellido: String){
        if(apellido.length < 3){
            msgValidarApellido.value = "Ingrese un apellido valido"
        }else{
            msgValidarApellido.value= ""
        }
    }

    fun validEmail(email: String){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            msgValidarEmail.value = "Direccion de email invalida"
        }else{
            msgValidarEmail.value = ""
        }
    }

    fun validPassword(password: String){
        if(password.length < 8){
            msgValidarContrasenia.value = "Debe contener 8 caracteres minimo"
        }else {
            if (!password.matches(".*[A-Z].*".toRegex())) {
                msgValidarContrasenia.value = "Debe contener 1 mayuscula"
            } else {
                if (!password.matches(".*[a-z].*".toRegex())) {
                    msgValidarContrasenia.value = "Debe contener 1 minuscula"
                }else{
                    msgValidarContrasenia.value = ""
                }
            }
        }
    }

    fun validTelefono(telefono: String){
        if(telefono.length != 10){
            msgValidarTelefono.value = "Debe tener 10 digitos"
        }else{
            msgValidarTelefono.value = ""
        }
    }

    fun validarCalle(calle : String){
        if(calle.isEmpty()){
            msgValidarCalle.value = "Ingrese una direccion"
        }else{
            msgValidarCalle.value = ""
        }
    }

    fun validarRadio(selectedRadio : Int) {
        if(selectedRadio == -1){
            msgvalidarRadio.value = "Seleccione una opcion de usuario"
        }else{
            msgvalidarRadio.value = ""
        }
    }

    //---------------------------------------------------------------------------------------------------------

    fun saveSession(id: String, mail: String, password: String) {
        usuarioRepository.setUserCredentialsInSharedPreferences(id, mail, password)
    }

    fun validarForm(
        nombre : String,
        apellido : String,
        email : String,
        password : String,
        telefono : String,
        calle : String,
        foto : String,
        userSelecionado : Int,
        radioPrestador : Int    //se pasa por parametro el id de la opcion de prestador, poder compararla
    ) {

        val validNombre = msgValidarNombre.value == ""
        val validApellido = msgValidarApellido.value == ""
        val validEmail = msgValidarEmail.value == ""
        val validPassword = msgValidarContrasenia.value == ""
        val validPhone = msgValidarTelefono.value == ""
        val validCalleAltura = msgValidarCalle.value == ""
        val validRadio = msgvalidarRadio.value == ""

        if(mailValido.value != 0){
            if((mailValido.value == 1) && validRadio && validPhone && validPassword && validEmail && validNombre && validApellido && validCalleAltura){
                resetForm(
                    nombre,
                    apellido,
                    email,
                    password,
                    telefono,
                    calle,
                    foto,
                    userSelecionado,
                    radioPrestador
                )
            }else{
                invalidForm()
            }
        }
    }

    fun validarMail(email: String) {

    viewModelScope.launch {
        modificarMailValido(usuarioRepository.emailExistente(email))
    }

    /*
        viewModelScope.launch {
            val existe = usuarioRepository.emailExistente(email)
            modificarMailExistente(existe)
        }
    */
    }

    private fun modificarMailValido(emailExistente: Boolean) {
        if(!emailExistente){
            mailValido.value = 1    //mail correcto, no existe en la BD
        }else{
            mailValido.value = 2    //mail ya registrado en BD
        }
    }

    private fun modificarMailExistente(existe : Boolean) {
        this.mailExistente = existe
    }

    private fun invalidForm() {
        var msg = ""


        if(mailValido.value == 1) {
            if (msgValidarNombre.value != "")
                msg += "\n\nNombre: " + msgValidarNombre.value
            if (msgValidarApellido.value != "")
                msg += "\n\nApellido: " + msgValidarApellido.value
            if (msgValidarEmail.value != "")
                msg += "\n\nEmail: " + msgValidarEmail.value
            if (msgValidarContrasenia.value != "")
                msg += "\n\nContraseña: " + msgValidarContrasenia.value
            if (msgValidarTelefono.value != "")
                msg += "\n\nTelefono: " + msgValidarTelefono.value
            if (msgValidarCalle.value != "")
                msg += "\n\nCalle: " + msgValidarCalle.value
            if (msgvalidarRadio.value != "")
                msg += "\n\nUsuario: " + msgvalidarRadio.value
        }else{
            msg += "Email ya registrado"
        }


        AlertDialog.Builder(v.context)
            .setTitle("Datos incorrectos")
            .setMessage(msg)
            .setPositiveButton("Okay"){ _,_ ->

            }
            .show()

    }

    private fun resetForm(
        nombre : String,
        apellido : String,
        mail : String,
        password : String,
        telefono : String,
        calle : String,
        foto : String,
        userSelecionado : Int,
        radioPrestador : Int    //se pasa por parametro el id de la opcion de prestador, poder compararla
    ) {

        val esPrestador = esPrestador(userSelecionado,radioPrestador)
        val id = UUID.randomUUID().toString()

        saveSession(id, mail, password)

        addUsuario(
            id,
            nombre,
            apellido,
            mail,
            password,
            telefono,
            foto,
            calle,
            esPrestador
        )

        AlertDialog.Builder(v.context)
            .setTitle("Usuario creado")
            .setPositiveButton("Okay"){ _,_ ->

                msgValidarEmail.value = REQUERIDO
                msgValidarContrasenia.value = REQUERIDO
                msgValidarTelefono.value = REQUERIDO
                msgValidarNombre.value = REQUERIDO
                msgValidarApellido.value = REQUERIDO
                msgValidarCalle.value = REQUERIDO
                resetForm.value = true
            }
            .show()

        redirect(esPrestador)
    }

    private fun redirect(esPrestador: Boolean) {
        val action : NavDirections
        if(esPrestador){
            action = SignUpFragmentDirections.actionSignUpFragmentToPrestadorActivity()
            v.findNavController().navigate(action)
        }else{
            action = SignUpFragmentDirections.actionSignUpFragmentToClienteActivity()
            v.findNavController().navigate(action)
        }
    }

    private fun esPrestador(userSelecionado: Int, radioPrestador: Int) : Boolean {
        return userSelecionado == radioPrestador
    }

    fun addUsuario(
        id : String,
        nombre : String,
        apellido :String,
        mail : String,
        password: String,
        telefono : String,
        foto : String,
        ubicacion : String,
        esPrestador: Boolean
    ) {
        usuarioRepository.addUsuario(Usuario(
            id,
            nombre,
            apellido,
            mail,
            password,
            "549" + telefono,
            foto,
            ubicacion,
            esPrestador))
    }

    fun continueValidation() {
        TODO("Not yet implemented")
    }


}