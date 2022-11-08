package com.ort.servitodo.fragments.cliente

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentDetallePublicacionBinding
import com.ort.servitodo.databinding.FragmentHomeClienteBinding
import com.ort.servitodo.repositories.UsuarioRepository
import com.ort.servitodo.viewmodels.cliente.HomeClienteViewModel

class HomeClienteFragment : Fragment() {

    private val homeClienteViewModel: HomeClienteViewModel by viewModels()
    lateinit var v : View
    private lateinit var binding : FragmentHomeClienteBinding

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

    //lateinit var publicacionAdapter : PublicacionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeClienteBinding.inflate(inflater, container, false)

        v = binding.root

        homeClienteViewModel.setView(v)

        return v
    }

    override fun onResume() {
        super.onResume()
        val rubros = resources.getStringArray(R.array.rubros)
        val filtroDistancia = resources.getStringArray(R.array.filtroDistancia)
        val filtroPuntuacion = resources.getStringArray(R.array.filtroPuntuacion)

        val arrayAdapterRubros = ArrayAdapter(requireContext(), R.layout.dropdown_item, rubros)
        val arrayAdapterDistancia = ArrayAdapter(requireContext(), R.layout.dropdown_item, filtroDistancia)
        val arrayAdapterPuntuacion = ArrayAdapter(requireContext(), R.layout.dropdown_item, filtroPuntuacion)

        binding.autoCompleteTextView1.setAdapter(arrayAdapterPuntuacion)
        binding.autoCompleteTextView2.setAdapter(arrayAdapterRubros)
        binding.autoCompleteTextView3.setAdapter(arrayAdapterDistancia)

    }

    override fun onStart() {
        super.onStart()

        homeClienteViewModel.emptyList()

        homeClienteViewModel.cargando.observe(viewLifecycleOwner, Observer { result ->
            binding.cargandoTxt.text = result.toString()
        })

        homeClienteViewModel.recyclerView(binding.recPublicacion)




        val userrepo = UsuarioRepository(v)
        Log.d("clientecredentials", "mail: ${userrepo.getMailUserInSharedPreferences()} + " +
                    "Ps: ${userrepo.getPasswordUserInSharedPreferences()} - Id: ${userrepo.getIdSession()}" )



    }

}