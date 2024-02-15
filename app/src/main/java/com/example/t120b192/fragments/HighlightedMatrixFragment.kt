package com.example.t120b192.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.t120b192.R
import com.example.t120b192.components.LocationMatrix
import com.example.t120b192.database.DatabaseViewModel
import com.example.t120b192.database.DatabaseViewModelFactory
import com.example.t120b192.databinding.HighlightedMatrixBinding
import kotlinx.coroutines.launch

class HighlightedMatrixFragment : Fragment() {

    private var _binding: HighlightedMatrixBinding? = null
    private lateinit var viewModel: DatabaseViewModel
    private var initialized = false

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HighlightedMatrixBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locMatrix: LocationMatrix = view.findViewById(R.id.locationMatrix)
        val coordinates: TextView = binding.coordinates
        val factory = DatabaseViewModelFactory(requireActivity().application)

        viewModel = ViewModelProvider(requireActivity(), factory)[DatabaseViewModel::class.java]


        viewModel.measurements.observe(viewLifecycleOwner, Observer { measurements ->
            Log.d("post", "LiveData updated: ${measurements.size} measurements received.")
            // Update UI with measurements data
            lifecycleScope.launch {
                viewModel.fetchData()
                locMatrix.initializeWithMeasurements(measurements)
                locMatrix.setLoading(false)

                Log.d("highlight", "matrix initialized.")
                viewModel.nearestNeighborResult.value?.let { result ->
                    Log.d("highlight", "observer triggered")
                    locMatrix.setLoading(false)

                    if (result != null) {
                        val text = "Coordinates \n" +
                                "X: ${result.second.first}, Y: ${result.second.second}"
                        Log.d("highlight", "$text")
                        coordinates.text = text
                        Log.d("highlight", "highlighting coordinates")
                        locMatrix.setHighlightCoordinates(result.second.first, result.second.second)
                    }
                    else{
                        Log.d("highlight", "result null")
                    }

                }
            }

            viewModel.fetchData()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
