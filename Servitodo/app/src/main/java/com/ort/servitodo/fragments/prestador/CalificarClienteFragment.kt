package com.ort.servitodo.fragments.prestador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.databinding.FragmentCalificarClienteBinding
import com.ort.servitodo.viewmodels.cliente.CalificarPrestadorViewModel
import com.ort.servitodo.viewmodels.prestador.CalificarClienteViewModel
import android.widget.RatingBar
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.fragments.cliente.CalificarPrestadorFragmentArgs

class CalificarClienteFragment : Fragment() {

    companion object {
        fun newInstance() = CalificarClienteFragment()
    }

    private val viewModel: CalificarClienteViewModel by viewModels()
    lateinit var v: View
    private lateinit var binding: FragmentCalificarClienteBinding
    private lateinit var receiveData : Pedido

    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalificarClienteBinding.inflate(inflater, container, false)
        v = binding.root

        viewModel.setView(v)
        receiveData = CalificarClienteFragmentArgs.fromBundle(requireArguments()).pedido1

        return v
    }

    override fun onStart() {
        super.onStart()



        binding.calificarClienteBtm.setOnClickListener {

            val rating = binding.ratingBar2.rating
            val descripcion : String = binding.txtDescripcionCalificarCliente.toString()
            viewModel.calificar(descripcion, rating , receiveData.idCliente, receiveData.idPublicacion)

            v.findNavController().navigate(CalificarClienteFragmentDirections.actionCalificarClienteFragmentToHomePrestadorFragment())
        }
    }

    /*   override fun onActivityCreated(savedInstanceState: Bundle?) {
           super.onActivityCreated(savedInstanceState)
           viewModel = ViewModelProvider(this).get(FleteroViewModel::class.java)
           // TODO: Use the ViewModel
       }*/

}


