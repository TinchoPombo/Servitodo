package com.ort.servitodo.fragments.cliente

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ort.servitodo.R
import com.ort.servitodo.adapters.PublicacionAdapter
import com.ort.servitodo.entities.Publicacion
import com.ort.servitodo.repositories.PublicacionRepository
import com.ort.servitodo.viewmodels.cliente.HomeClienteViewModel

class HomeClienteFragment : Fragment() {

    /*
    companion object {
        fun newInstance() = HomeClienteFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeClienteViewModel::class.java)
        // TODO: Use the ViewModel
    }
    */

    private val viewModel: HomeClienteViewModel by viewModels()
    lateinit var v : View

    lateinit var recyclerPublicacion : RecyclerView
    //lateinit var publicacionAdapter : PublicacionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home_cliente, container, false)

        recyclerPublicacion = v.findViewById(R.id.recPublicacion)

        viewModel.setView(v)

        return v
    }

    override fun onStart() {
        super.onStart()

        viewModel.recyclerView(recyclerPublicacion)

    }





}