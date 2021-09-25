package com.nurullo.foodsharing.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.nurullo.foodsharing.R

class AuthFragment : Fragment() {

    private lateinit var signInBtn: Button
    private lateinit var signUpBtn: Button
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_auth, container, false)
        navController = NavHostFragment.findNavController(this)

        signInBtn = v.findViewById(R.id.button_login)
        signUpBtn = v.findViewById(R.id.button_register)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInBtn.setOnClickListener {
            navController.navigate(R.id.action_authFragment_to_signInFragment)
        }

        signUpBtn.setOnClickListener {
            navController.navigate(R.id.action_authFragment_to_signUpFragment)
        }
    }
}
