package com.ort.servitodo.fragments.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentLogInBinding
import com.ort.servitodo.databinding.FragmentSignUpBinding
import com.ort.servitodo.viewmodels.login.SignUpViewModel
import java.util.regex.Pattern

class SignUpFragment : Fragment() {

    companion object {
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

        emailFocusListener()
        return v
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

    override fun onStart() {
        super.onStart()

        viewModel.setView(v)

        binding.buttonRegister.setOnClickListener{

        }

    }











 /*   override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        // TODO: Use the ViewModel
    }*/

}