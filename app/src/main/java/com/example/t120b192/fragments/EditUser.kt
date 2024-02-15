package com.example.t120b192.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.t120b192.R
import com.example.t120b192.database.DatabaseViewModel
import com.example.t120b192.database.DatabaseViewModelFactory
import com.example.t120b192.databinding.FragmentEditUserBinding

class EditUser : Fragment() {
    private lateinit var viewModel: DatabaseViewModel
    private var _binding: FragmentEditUserBinding? = null
    private var initialized = false

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = DatabaseViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[DatabaseViewModel::class.java]

        val deleteButton = binding.buttonDelete
        val editButton = binding.buttonEdit

        val macOld = arguments?.getString("mac")
        val s1Old = arguments?.getInt("s1", 0)
        val s2Old = arguments?.getInt("s2", 0)
        val s3Old = arguments?.getInt("s3", 0)

        binding.MAC.setText(macOld)
        binding.s1.setText(s1Old.toString())
        binding.s2.setText(s2Old.toString())
        binding.s3.setText(s3Old.toString())

        deleteButton.setOnClickListener {
            val macEditText = binding.MAC
            val s1EditText = binding.s1
            val s2EditText = binding.s2
            val s3EditText = binding.s3
            viewModel.deleteUser(macEditText, s1EditText, s2EditText, s3EditText)
            findNavController().navigate(R.id.action_editUser_to_FirstFragment)
        }

        editButton.setOnClickListener {
            val macEditText = binding.MAC
            val s1EditText = binding.s1
            val s2EditText = binding.s2
            val s3EditText = binding.s3
            if (macOld != null && s1Old != null && s2Old != null && s3Old != null  ) {
                viewModel.editUser(macOld, macEditText, s1Old, s1EditText, s2Old, s2EditText, s3Old, s3EditText)
            }
            findNavController().navigate(R.id.action_editUser_to_FirstFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}