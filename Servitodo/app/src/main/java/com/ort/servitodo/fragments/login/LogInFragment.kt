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
import androidx.navigation.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.R
import com.ort.servitodo.entities.Usuario
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


        val db = Firebase.firestore

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