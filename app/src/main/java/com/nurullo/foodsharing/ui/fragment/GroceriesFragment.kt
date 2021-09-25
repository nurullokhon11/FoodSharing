package com.nurullo.foodsharing.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nurullo.foodsharing.R

class GroceriesFragment : Fragment() {

    companion object {
        fun newInstance() = GroceriesFragment()
    }

    private lateinit var viewModel: GroceriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grocceries, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroceriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}