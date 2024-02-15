package com.example.t120b192.fragments
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Database
import com.example.t120b192.R
import com.example.t120b192.components.LocationMatrix
import com.example.t120b192.database.DatabaseViewModel
import com.example.t120b192.database.DatabaseViewModelFactory
import com.example.t120b192.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var viewModel: DatabaseViewModel
    private var  userinitialized = false

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUserList.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_userListFragment)
        }
        binding.buttonSignalList.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_measurementListFragment)
        }

        // Find the LocationMatrix view in the layout
        val locMatrix: LocationMatrix = view.findViewById(R.id.locationMatrix)
        locMatrix.setLoading(true)

        // Create ViewModel with the ViewModelFactory
        val factory = DatabaseViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory)[DatabaseViewModel::class.java]
        //viewModel.delete()

        // Observe the LiveData
        viewModel.measurements.observe(viewLifecycleOwner, Observer { measurements ->
            //Log.d("post", "LiveData updated: ${measurements.size} measurements received.")
            // Update UI with measurements data
            lifecycleScope.launch {
                locMatrix.setLoading(false)
                locMatrix.initializeWithMeasurements(measurements)
            }
        })
        // Fetch data when the fragment is created
        viewModel.fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
