package com.ort.servitodo.fragments.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.ort.servitodo.R
import com.ort.servitodo.viewmodels.login.LogInViewModel

class LogInFragment : Fragment() {

    companion object {
        fun newInstance() = LogInFragment()
    }

    lateinit var v : View;
    lateinit var btnLogin : Button
    lateinit var btnLogin2 : Button
    private lateinit var viewModel: LogInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_log_in, container, false)
        btnLogin = v.findViewById(R.id.btnLogin)
        btnLogin2 = v.findViewById(R.id.btnLogin2)
        return v
    }

    override fun onStart() {
        super.onStart()

        btnLogin.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToPrestadorActivity()
            v.findNavController().navigate(action)

        }

        btnLogin2.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToClienteActivity()
            v.findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)
        // TODO: Use the ViewModel
    }

}