package com.ort.servitodo.fragments.cliente

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ort.servitodo.R
import com.ort.servitodo.viewmodels.cliente.CalificarPrestadorViewModel

class CalificarPrestadorFragment : Fragment() {

    companion object {
        fun newInstance() = CalificarPrestadorFragment()
    }

    private lateinit var viewModel: CalificarPrestadorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calificar_prestador, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalificarPrestadorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}