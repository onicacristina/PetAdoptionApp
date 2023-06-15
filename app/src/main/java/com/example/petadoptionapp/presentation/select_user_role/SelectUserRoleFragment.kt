package com.example.petadoptionapp.presentation.select_user_role

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petadoptionapp.R

class SelectUserRoleFragment : Fragment() {

    companion object {
        fun newInstance() = SelectUserRoleFragment()
    }

    private lateinit var viewModel: SelectUserRoleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_user_role, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SelectUserRoleViewModel::class.java)
        // TODO: Use the ViewModel
    }

}