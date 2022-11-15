package com.ort.servitodo.fragments.cliente

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ort.servitodo.R
import com.ort.servitodo.databinding.FragmentDetallePublicacionBinding
import com.ort.servitodo.databinding.FragmentHomeClienteBinding
import com.ort.servitodo.repositories.UsuarioRepository
import com.ort.servitodo.viewmodels.cliente.HomeClienteViewModel

class HomeClienteFragment : Fragment(), AdapterView.OnItemClickListener {

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
        val rubros = resources.getStringArray(R.array.rubrosHomeCliente)
        val filtroPuntuacion = resources.getStringArray(R.array.filtroPuntuacion)

        val arrayAdapterRubros = ArrayAdapter(requireContext(), R.layout.dropdown_item, rubros)
        val arrayAdapterPuntuacion = ArrayAdapter(requireContext(), R.layout.dropdown_item, filtroPuntuacion)


        with(binding.autoCompleteTextView1){
            setAdapter(arrayAdapterPuntuacion)
            onItemClickListener = this@HomeClienteFragment
        }

        with(binding.autoCompleteTextView2){
            setAdapter(arrayAdapterRubros)
            onItemClickListener = this@HomeClienteFragment
        }

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

    override fun onItemClick(parent: AdapterView<*>?, p1: View?, position: Int, id: Long) {

        val a = binding.autoCompleteTextView1.text.toString()
        val b = binding.autoCompleteTextView2.text.toString()

        homeClienteViewModel.emptyList()

        homeClienteViewModel.cargando.observe(viewLifecycleOwner, Observer { result ->
            binding.cargandoTxt.text = result.toString()
        })

        homeClienteViewModel.recyclerViewFiltered(binding.recPublicacion,a,b)

    }



}