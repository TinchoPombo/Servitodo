package com.ort.servitodo.fragments.cliente

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ort.servitodo.R
import com.ort.servitodo.viewmodels.cliente.CalificarPrestadorViewModel

class CalificarPrestadorFragment : Fragment() {

    lateinit var v : View
    private val viewModel: CalificarPrestadorViewModel by viewModels()

    /*
    companion object {
        fun newInstance() = CalificarPrestadorFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalificarPrestadorViewModel::class.java)
        // TODO: Use the ViewModel
    }
    */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_calificar_prestador, container, false)



        return v
    }



}