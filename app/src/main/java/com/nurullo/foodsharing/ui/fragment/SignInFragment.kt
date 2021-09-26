package com.nurullo.foodsharing.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.api.RetrofitClient
import com.nurullo.foodsharing.model.requestParams.AuthParams
import com.nurullo.foodsharing.model.response.UserPojo
import com.nurullo.foodsharing.repository.UserRepository
import com.nurullo.foodsharing.ui.activity.MainActivity
import com.nurullo.foodsharing.utils.SessionManager
import com.nurullo.foodsharing.viewmodel.UserViewModel
import com.nurullo.foodsharing.viewmodel_factory.UserViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInFragment : Fragment() {

    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginBtn: Button
    lateinit var navController: NavController

    lateinit var networkService: RetrofitClient
    lateinit var userRepository: UserRepository
    lateinit var userViewModel: UserViewModel
    lateinit var userViewModelFactory: UserViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_sign_in, container, false)
        navController = NavHostFragment.findNavController(this)
        usernameEditText = v.findViewById(R.id.email_edittext)
        passwordEditText = v.findViewById(R.id.password_edittext)
        loginBtn = v.findViewById(R.id.signin_button)

        networkService = RetrofitClient.getInstance(requireContext())
        userRepository = UserRepository(requireContext())
        userViewModelFactory = UserViewModelFactory(userRepository)
        userViewModel = ViewModelProvider(viewModelStore, userViewModelFactory).get(
            UserViewModel::class.java
        )

        userViewModel.successLiveData.observe(viewLifecycleOwner) {
            if (it == 1L) {
                Snackbar.make(v!!, "Успешно!", Snackbar.LENGTH_SHORT).show()
                startActivity(Intent(requireActivity(), MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                })
                requireActivity().finish()
            } else if (it == 0L) {
                Snackbar.make(v!!, "Неправильный логин или пароль", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBtn.setOnClickListener { v: View? ->
            loginBtn.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.bubble)
            )

            val username: String = usernameEditText.text.toString()
            val password: String = passwordEditText.text.toString()

            //userViewModel.login(AuthParams(username, password))


            RetrofitClient.getInstance(requireContext())
                .login(AuthParams(username, password), object : Callback<UserPojo> {
                    override fun onResponse(
                        call: Call<UserPojo>,
                        response: Response<UserPojo>
                    ) {
                        if (response.code() == 200 && response.isSuccessful) {
                            val sm = SessionManager(requireContext())
                            sm.hideSoftKeyBoard(requireContext(), v)
                            Snackbar.make(v!!, "Успешно!", Snackbar.LENGTH_SHORT).show()

                            sm.saveUserId(response.body()?.id)
                            sm.saveUsername(response.body()?.username)
                            sm.saveEmail(response.body()?.email)
                            sm.saveNickname(response.body()?.nickname)
                            sm.savePhone(response.body()?.phone)


                            startActivity(Intent(requireActivity(), MainActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            })
                            requireActivity().finish()
                        } else {
                            Snackbar.make(v!!, "Неправильный логин или пароль", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<UserPojo>, t: Throwable) {
                        Snackbar.make(v!!, "Неправильный логин или пароль", Snackbar.LENGTH_SHORT)
                            .show()
                    }

                })
        }
    }
}
