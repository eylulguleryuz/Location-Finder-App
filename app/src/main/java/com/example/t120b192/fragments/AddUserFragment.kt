package com.example.t120b192.fragments

import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.t120b192.R
import com.example.t120b192.databinding.FragmentAddUserBinding

import com.example.t120b192.database.DatabaseViewModel
import com.example.t120b192.database.DatabaseViewModelFactory

class AddUserFragment : Fragment() {

    private lateinit var viewModel: DatabaseViewModel
    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = DatabaseViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[DatabaseViewModel::class.java]

        val macEditText = binding.MAC
        val s1EditText = binding.s1
        val s2EditText = binding.s2
        val s3EditText = binding.s3
        val addButton = binding.buttonAdd

        addButton.setOnClickListener {
            viewModel.insertUser(macEditText,s1EditText,s2EditText,s3EditText
            )
            findNavController().navigate(R.id.action_addUserFragment_to_FirstFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}