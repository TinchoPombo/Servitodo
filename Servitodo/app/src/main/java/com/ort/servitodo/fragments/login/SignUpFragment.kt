@file:Suppress("DEPRECATION")

package com.ort.servitodo.fragments.login

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentLogInBinding
import com.ort.servitodo.databinding.FragmentSignUpBinding
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.repositories.UsuarioRepository
import com.ort.servitodo.viewmodels.login.SignUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.regex.Pattern

class SignUpFragment : Fragment() {

    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
        fun newInstance() = SignUpFragment()
    }

    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding : FragmentSignUpBinding
    lateinit var v : View




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        v = binding.root

        binding.radioGroup.clearCheck()
        nombreFocusListener()
        apellidoFocusListener()
        calleAlturaFocusListener()
        emailFocusListener()
        passwordFocusListener()
        phoneFocusListener()
        radioGroupListener()

        binding.buttonRegister.setOnClickListener{
            submitForm()
        }



        binding.btnCargarImagen.setOnClickListener {
            pickImageFromGallery()
        }


        return v
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            binding.imageView.setImageURI(data?.data)
        }
    }



    private fun radioGroupListener() {
        binding.radioGroup.setOnCheckedChangeListener { _,change ->

        }
    }

    private fun validRadio(): String?{
        val radio = binding.radioGroup.getCheckedRadioButtonId()
        if(radio == -1){
            return "Seleccione una opcion de usuario"
        }
        return null
    }

    private fun calleAlturaFocusListener() {
        binding.calleAlturaEditText.setOnFocusChangeListener{_,focused ->
            binding.calleAlturaContainer.helperText = validCalleAltura()
        }
    }

    private fun validCalleAltura(): String? {
        val calleAlturaText = binding.calleAlturaEditText.text.toString()
        if(calleAlturaText.isEmpty()){
            return "Ingrese una direccion"
        }

        return null
    }

    private fun apellidoFocusListener() {
        binding.apellidoEditText.setOnFocusChangeListener{_,focused ->
            binding.apellidoContainer.helperText = validApellido()
        }
    }

    private fun validApellido(): String? {
        val apellidoText = binding.apellidoEditText.text.toString()
        if(apellidoText.length < 3){
            return "Ingrese un apellido valido"
        }

        return null
    }

    private fun nombreFocusListener() {
        binding.nombreEditText.setOnFocusChangeListener{_,focused ->
            binding.nombreContainer.helperText = validNombre()
        }
    }

    private fun validNombre(): String? {
        val nombreText = binding.nombreEditText.text.toString()
        if(nombreText.length < 3){
            return "Ingrese un nombre valido"
        }

        return null
    }

    private fun submitForm() {
        binding.emailContainer.helperText = validEmail()
        binding.passwordContainer.helperText = validPassword()
        binding.phoneContainer.helperText = validPhone()
        binding.apellidoContainer.helperText = validApellido()
        binding.nombreContainer.helperText = validNombre()
        binding.calleAlturaContainer.helperText = validCalleAltura()

        //TODO Agregar validacion de mail ya existente

        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validPhone = binding.phoneContainer.helperText == null
        val validNombre = binding.nombreContainer.helperText == null
        val validApellido = binding.apellidoContainer.helperText == null
        val validCalleAltura = binding.calleAlturaContainer.helperText == null
        val validRadio = validRadio() == null



        if(validRadio && validPhone && validPassword && validEmail && validNombre && validApellido && validCalleAltura){
            resetForm()
        }else{
            invalidForm()
        }

    }

    private fun invalidForm() {
        var msg = ""
        if(binding.nombreContainer.helperText != null)
            msg += "\n\nNombre: " + binding.nombreContainer.helperText
        if(binding.apellidoContainer.helperText != null)
            msg += "\n\nApellido: " + binding.apellidoContainer.helperText
        if(binding.emailContainer.helperText != null)
            msg += "\n\nEmail: " + binding.emailContainer.helperText
        if(binding.passwordContainer.helperText != null)
            msg += "\n\nContraseña: " + binding.passwordContainer.helperText
        if(binding.phoneContainer.helperText != null)
            msg += "\n\nTelefono: " + binding.phoneContainer.helperText
        if(binding.calleAlturaContainer.helperText != null)
            msg += "\n\nCalle: " + binding.calleAlturaContainer.helperText
        if(validRadio() != null)
            msg += "\n\nUsuario: " + validRadio()

        AlertDialog.Builder(context)
            .setTitle("Invalid Form")
            .setMessage(msg)
            .setPositiveButton("Okay"){ _,_ ->
                // do nothing
            }
            .show()
    }

    private fun resetForm() {

        val nombre = binding.nombreEditText.text.toString()
        val apellido = binding.apellidoEditText.text.toString()
        val mail = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val telefono = binding.phoneEditText.text.toString()
        val foto = binding.fotoEditText.text.toString()
        val ubicacion = binding.calleAlturaEditText.text.toString()
        val esPrestador = esPrestador()
        var id = UUID.randomUUID().toString()

//        auth.createUserWithEmailAndPassword(mail, password)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
//                    //updateUI(user)
//                    if (user != null) {
//                        scope.launch {
//                            setUid(user)
//
//                        }
//
//                    }
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        context, "Authentication failed.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    //updateUI(null)
//                }
//            }

        viewModel.saveSession(id, mail, password)

        viewModel.addUsuario(
           id, nombre, apellido, mail,password, telefono,foto, ubicacion, esPrestador
        )

        AlertDialog.Builder(context)
            .setTitle("Usuario creado")
            .setPositiveButton("Okay"){ _,_ ->
                binding.emailEditText.text = null
                binding.passwordEditText.text = null
                binding.phoneEditText.text = null
                binding.apellidoEditText.text = null
                binding.nombreEditText.text = null
                binding.calleAlturaEditText.text = null
                binding.fotoEditText.text = null

                binding.emailContainer.helperText = getString(R.string.required)
                binding.passwordContainer.helperText = getString(R.string.required)
                binding.phoneContainer.helperText = getString(R.string.required)
                binding.nombreContainer.helperText = getString(R.string.required)
                binding.apellidoContainer.helperText = getString(R.string.required)
                binding.calleAlturaContainer.helperText = getString(R.string.required)
                binding.radioGroup.clearCheck()
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

    private fun esPrestador(): Boolean {
        if(binding.radioGroup.checkedRadioButtonId == binding.radioPrestador.id)
            return true
        return false
    }

    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener{_,focused ->
            binding.emailContainer.helperText = validEmail()
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.emailEditText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Direccion de email invalida"
        }

        return null
    }

    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener{_,focused ->
            binding.passwordContainer.helperText = validPassword()
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding.passwordEditText.text.toString()
        if(passwordText.length < 8)
        {
            return "Debe contener 8 caracteres minimo"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Debe contener 1 mayuscula"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Debe contener 1 minuscula"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Debe contener 1 caracter especial (@#\$%^&+=)"
        }

        return null
    }

    private fun phoneFocusListener()
    {
        binding.phoneEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.phoneContainer.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String?
    {
        val phoneText = binding.phoneEditText.text.toString()
        if(phoneText.length != 10)
        {
            return "Debe tener 10 digitos"
        }
        return null
    }



    override fun onStart() {
        super.onStart()

        viewModel.setView(v)



    }











 /*   override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}