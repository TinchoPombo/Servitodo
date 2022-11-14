@file:Suppress("DEPRECATION")

package com.ort.servitodo.fragments.login

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
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
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
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

    private  var imageUrl = ""

    //firebase storage
    val storage = Firebase.storage


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
            viewModel.validarMail(binding.emailEditText.text.toString())
            //submitForm()
        }

        binding.btnCargarImagen.setOnClickListener {
            pickImageFromGallery()

        }

        return v
    }

    override fun onStart() {
        super.onStart()

        viewModel.setView(v)

        viewModel.initLiveData()

        //--LiveData--//
        //Msg's
        viewModel.msgValidarNombre.observe(viewLifecycleOwner, Observer { result ->
            binding.nombreContainer.helperText = result.toString()
        })
        viewModel.msgValidarApellido.observe(viewLifecycleOwner, Observer { result ->
            binding.apellidoContainer.helperText = result.toString()
        })
        viewModel.msgValidarEmail.observe(viewLifecycleOwner, Observer { result ->
            binding.emailContainer.helperText = result.toString()
        })
        viewModel.msgValidarContrasenia.observe(viewLifecycleOwner, Observer { result ->
            binding.passwordContainer.helperText = result.toString()
        })
        viewModel.msgValidarTelefono.observe(viewLifecycleOwner, Observer { result ->
            binding.phoneContainer.helperText = result.toString()
        })
        viewModel.msgValidarCalle.observe(viewLifecycleOwner, Observer { result ->
            binding.calleAlturaContainer.helperText = result.toString()
        })
        //Guarda Url Imagen
        viewModel.imgUrl.observe(viewLifecycleOwner, Observer { result ->
            viewModel.guardarUrlImagen()
        })

        //ResetForm
        viewModel.resetForm.observe(viewLifecycleOwner, Observer { result ->
            resetForm()
        })

        //mailValido
        viewModel.mailValido.observe(viewLifecycleOwner, Observer { result ->
            submitForm()
        })

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
            viewModel.uploadImageToFirebase(data?.data!!)
        }
    }

    //TODO esto se supone que no es necesario
    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val fileName = UUID.randomUUID().toString() +".jpg"

             /*val storage = Firebase.storage*/
            val refStorage = storage.reference.child("images/$fileName")

            var imageUrl1 = ""
            refStorage.putFile(fileUri)
                .addOnSuccessListener(
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            imageUrl1 = it.toString()
                             Log.d("FOTOOO", "link ${it.toString()}")
                        }
                    })

                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
            imageUrl = imageUrl1
            Log.d("FOTOOO", "link 2 !! ${imageUrl}")
        }

        /*val storageRef = storage.reference
        val ref = storageRef.child("images/asdf.jpg")
        var uploadTask = ref.putFile(fileUri)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
            } else {
                // Handle failures
                // ...
            }
        }

        imageUrl = ref.downloadUrl.toString()*/
    }

    //--FocusListeners--//

    private fun calleAlturaFocusListener() {
        binding.calleAlturaEditText.setOnFocusChangeListener{_,focused ->
            viewModel.validarCalle(binding.calleAlturaEditText.text.toString())
        }
    }

    private fun apellidoFocusListener() {
        binding.apellidoEditText.setOnFocusChangeListener{_,focused ->
            viewModel.validApellido(binding.apellidoEditText.text.toString())
        }
    }

    private fun nombreFocusListener() {
        binding.nombreEditText.setOnFocusChangeListener{_,focused ->
            viewModel.validNombre(binding.nombreEditText.text.toString())
        }
    }

    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener{_,focused ->
            viewModel.validEmail(binding.emailEditText.text.toString())
        }
    }

    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener{_,focused ->
            viewModel.validPassword(binding.passwordEditText.text.toString())
        }
    }

    private fun phoneFocusListener()
    {
        binding.phoneEditText.setOnFocusChangeListener { _, focused ->
            viewModel.validTelefono(binding.phoneEditText.text.toString())

        }
    }

    private fun radioGroupListener() {
        binding.radioGroup.setOnCheckedChangeListener { _,change ->
            viewModel.validarRadio(binding.radioGroup.checkedRadioButtonId)
        }
    }

    //---------------------------------------------------------------------------------------

    private fun submitForm() {
        viewModel.validarForm(
            binding.nombreEditText.text.toString(),
            binding.apellidoEditText.text.toString(),
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString(),
            binding.phoneEditText.text.toString(),
            binding.calleAlturaEditText.text.toString(),
            binding.imageView.toString(),
            binding.radioGroup.checkedRadioButtonId,
            binding.radioPrestador.id       //Este id se pasa para poder hacer la comparacion con la opcion seleccionada
        )

    }

    private fun resetForm() {

        binding.emailEditText.text = null
        binding.passwordEditText.text = null
        binding.phoneEditText.text = null
        binding.apellidoEditText.text = null
        binding.nombreEditText.text = null
        binding.calleAlturaEditText.text = null
        binding.radioGroup.clearCheck()
    }

}