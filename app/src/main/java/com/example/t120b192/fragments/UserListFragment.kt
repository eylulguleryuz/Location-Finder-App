package com.example.t120b192.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.t120b192.R
import com.example.t120b192.database.DatabaseViewModel
import com.example.t120b192.database.DatabaseViewModelFactory

/**
 * A fragment representing a list of Items.
 */
class UserListFragment : Fragment() {
    private lateinit var viewModel: DatabaseViewModel
    private var columnCount = 1
    private var initialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_list_list, container, false)

        val factory = DatabaseViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory)[DatabaseViewModel::class.java]


        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                viewModel.usersWithSensors.observe(viewLifecycleOwner, Observer { usersWithSensors ->
                    adapter = MyItemRecyclerViewAdapter(usersWithSensors, viewModel, this@UserListFragment)

                })
                viewModel.nearestNeighborResult.observe(viewLifecycleOwner, Observer { result ->
                    Log.d("NearestNeighbor", "Distance: ${result.first}, X: ${result.second.first}, Y: ${result.second.second}")
                })
            }
        }
        return view
    }
    override fun onResume() {
        super.onResume()
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            UserListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}