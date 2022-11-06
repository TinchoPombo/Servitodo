package com.ort.servitodo.fragments.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ort.servitodo.databinding.FragmentCalificarPrestadorBinding
import com.ort.servitodo.entities.Pedido
import com.ort.servitodo.fragments.rubro.FleteroFragmentArgs
import com.ort.servitodo.viewmodels.cliente.CalificarPrestadorViewModel

class CalificarPrestadorFragment : Fragment() {

    companion object {
        fun newInstance() = CalificarPrestadorFragment()
    }

    private val viewModel: CalificarPrestadorViewModel by viewModels()
    lateinit var v: View
    private lateinit var binding: FragmentCalificarPrestadorBinding
    private lateinit var receiveData : Pedido

    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalificarPrestadorBinding.inflate(inflater, container, false)
        v = binding.root

        viewModel.setView(v)
        receiveData = CalificarPrestadorFragmentArgs.fromBundle(requireArguments()).pedido1

        return v
    }

    override fun onStart() {
        super.onStart()

        binding.calificarPrestadorBtm.setOnClickListener {

            val rating = binding.ratingBar.rating
            val descripcion : String  = binding.decripcionCalificacionPrestador.toString()
            viewModel.calificar(descripcion, rating, receiveData.idPrestador, receiveData.idPublicacion)

            v.findNavController().navigate(CalificarPrestadorFragmentDirections.actionCalificarPrestadorFragmentToHomeClienteFragment())
        }
    }

    /*   override fun onActivityCreated(savedInstanceState: Bundle?) {
           super.onActivityCreated(savedInstanceState)
           viewModel = ViewModelProvider(this).get(FleteroViewModel::class.java)
           // TODO: Use the ViewModel
       }*/

}