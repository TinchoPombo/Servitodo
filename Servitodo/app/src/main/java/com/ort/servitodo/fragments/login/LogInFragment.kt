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

    /*companion object {
        fun newInstance() = LogInFragment()
    }*/
    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

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
            viewModel.validarCuenta(binding.inputMailLogIn.text.toString(), binding.inputPasswordLogin.text.toString())
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login()

        }

        binding.txtRegistroLogin.setOnClickListener {
            viewModel.registroLogin()
        }

        binding.btnLogin2.setOnClickListener {
            viewModel.login2()
        }

        binding.btnCliente1.setOnClickListener{
            viewModel.cliente1()
        }
        binding.btnCliente2.setOnClickListener{
            viewModel.cliente2()
        }
        binding.btnCliente3.setOnClickListener{
            viewModel.cliente3()
        }
        binding.btnPrest1.setOnClickListener{
            viewModel.prest1()
        }
        binding.btnPrest2.setOnClickListener{
            viewModel.prest2()
        }
        binding.btnPrest3.setOnClickListener{
            viewModel.prest3()
        }

        binding.btnLogOut.setOnClickListener{
            viewModel.logOut()
        }

    }
}