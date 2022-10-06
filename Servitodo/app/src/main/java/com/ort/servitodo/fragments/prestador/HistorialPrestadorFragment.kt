package com.ort.servitodo.fragments.prestador

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ort.servitodo.R
import com.ort.servitodo.viewmodels.prestador.HistorialPrestadorViewModel

class HistorialPrestadorFragment : Fragment() {

    companion object {
        fun newInstance() = HistorialPrestadorFragment()
    }

    private lateinit var viewModel: HistorialPrestadorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_historial_prestador, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistorialPrestadorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}