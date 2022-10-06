package com.ort.servitodo.fragments.prestador

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ort.servitodo.R
import com.ort.servitodo.viewmodels.prestador.OpinionesPrestadorViewModel

class OpinionesPrestadorFragment : Fragment() {

    companion object {
        fun newInstance() = OpinionesPrestadorFragment()
    }

    private lateinit var viewModel: OpinionesPrestadorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_opiniones_prestador, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OpinionesPrestadorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}