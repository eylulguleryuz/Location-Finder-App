package com.example.t120b192.fragments

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.t120b192.R
import com.example.t120b192.database.DatabaseViewModel
import com.example.t120b192.databinding.FragmentUserListBinding
import com.example.t120b192.entities.UserWithSensors

class MyItemRecyclerViewAdapter(
    private val values: List<UserWithSensors>, private val viewModel: DatabaseViewModel, private val fragment: UserListFragment
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentUserListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.findUserButton.setOnClickListener {
            onFindUserButtonClick(item)
        }
        holder.editUserButton.setOnClickListener {
            onEditUserButtonClick(item)
        }
            holder.macView.text = item.mac
            holder.s1View.text = item.stiprumasList[0].toString()
            holder.s2View.text = item.stiprumasList[1].toString()
            holder.s3View.text = item.stiprumasList[2].toString()

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val findUserButton: Button = binding.findUserButton
        val editUserButton: Button = binding.editUserButton
        val macView: TextView = binding.macTextView
        val s1View: TextView = binding.s1TextView
        val s2View: TextView = binding.s2TextView
        val s3View: TextView = binding.s3TextView
    }

    fun onFindUserButtonClick(user: UserWithSensors) {
        try{
        viewModel.findNearestNeighbor(user.stiprumasList)
        Log.d("NearestNeighbor", "Button clicked for user: ${user.mac}")
        fragment.findNavController().navigate(R.id.action_userListFragment_to_highlightedMatrixFragment)}
        catch(e: Exception) {
            Log.e("NearestNeighbor", "There is a problem with user info, might be null.")
        }
    }

    private fun onEditUserButtonClick(item: UserWithSensors) {
        val action = UserListFragmentDirections.actionUserListFragmentToEditUser(
            mac = item.mac,
            s1 = item.stiprumasList[0],
            s2 = item.stiprumasList[1],
            s3 = item.stiprumasList[2]
        )
        fragment.findNavController().navigate(action)
    }

}