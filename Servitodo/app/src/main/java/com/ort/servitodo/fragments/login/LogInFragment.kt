package com.ort.servitodo.fragments.login

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentDetallePublicacionBinding
import com.ort.servitodo.databinding.FragmentLogInBinding

//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
import com.ort.servitodo.entities.Usuario
import com.ort.servitodo.viewmodels.login.LogInViewModel

class LogInFragment : Fragment() {

    companion object {
        fun newInstance() = LogInFragment()
    }



    lateinit var v : View;
    private val viewModel: LogInViewModel by viewModels()

    private lateinit var binding : FragmentLogInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        v = binding.root

        return v
    }

    override fun onStart() {
        super.onStart()

        viewModel.setView(v)

        binding.btnLoginTrue.setOnClickListener{

            var user : Usuario = viewModel.validarCuenta(
                binding.inputMailLogIn.text.toString(),
                binding.inputPasswordLogin.text.toString()
            )

            if(user.mail != ""){
                viewModel.logOut()
                viewModel.saveSession(user.id, user.mail, user.password)
                redirect(user.esPrestador)
            }else{
                Toast.makeText(context, "Datos ingresados incorrectos", Toast.LENGTH_SHORT).show()
                Log.d("TestCo", "testFail")
            }
        }

        binding.btnLogin.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToPrestadorActivity()
            v.findNavController().navigate(action)

        }

        binding.txtRegistroLogin.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
            v.findNavController().navigate(action)
        }

        binding.btnLogin2.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToClienteActivity()
            v.findNavController().navigate(action)
        }

        binding.btnCliente1.setOnClickListener{
            viewModel.saveSession("b2427b10-989c-4a4c-9bf4-40a16799d2bc", "martinpombo@gmail.com", "12344Xdd+")
            val action = LogInFragmentDirections.actionLogInFragmentToClienteActivity()
            v.findNavController().navigate(action)
        }
        binding.btnCliente2.setOnClickListener{
            viewModel.saveSession("92aa99d2-9eab-4a6f-a9ae-04f7e3859753", "tomas.berias1@hotmail.com", "1234@Aaa")
            val action = LogInFragmentDirections.actionLogInFragmentToClienteActivity()
            v.findNavController().navigate(action)
        }
        binding.btnCliente3.setOnClickListener{
            viewModel.saveSession("2bcc236a-27b9-4d16-9cee-0f073b76f7cc", "frandesalvo@gmail.com", "12345@Aa")
            val action = LogInFragmentDirections.actionLogInFragmentToClienteActivity()
            v.findNavController().navigate(action)
        }
        binding.btnPrest1.setOnClickListener{
            viewModel.saveSession("0123b583-cf9d-442f-8eec-bb164cd90ffa", "ladanyneta@gmail.com", "12345@Aa")
            val action = LogInFragmentDirections.actionLogInFragmentToPrestadorActivity()
            v.findNavController().navigate(action)
        }
        binding.btnPrest2.setOnClickListener{
            viewModel.saveSession("83dd325a-580d-4a49-bcc4-44f7acdeabcc", "robertitofunes@gmail.com", "12345@Aa")
            val action = LogInFragmentDirections.actionLogInFragmentToPrestadorActivity()
            v.findNavController().navigate(action)
        }
        binding.btnPrest3.setOnClickListener{
            viewModel.saveSession("1f305a50-bb73-4aea-8bf9-ec436fd31384", "josejose@gmail.com", "12345@Aa")
            val action = LogInFragmentDirections.actionLogInFragmentToPrestadorActivity()
            v.findNavController().navigate(action)
        }

        binding.btnLogOut.setOnClickListener{
            viewModel.logOut()
        }

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

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}