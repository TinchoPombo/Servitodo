package com.ort.servitodo.fragments.cliente

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentOpinionesClienteBinding
import com.ort.servitodo.databinding.FragmentOpinionesPrestadorBinding
import com.ort.servitodo.viewmodels.cliente.OpinionesClienteViewModel


class OpinionesClienteFragment : Fragment() {

    private val opinionesClienteViewModel : OpinionesClienteViewModel by viewModels()

    lateinit var v : View
    private lateinit var binding : FragmentOpinionesClienteBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOpinionesClienteBinding.inflate(inflater, container, false)


        v = binding.root

        opinionesClienteViewModel.setView(v)


        return v
    }


}