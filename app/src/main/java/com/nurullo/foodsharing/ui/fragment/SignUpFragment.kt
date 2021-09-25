package com.nurullo.foodsharing.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.api.RetrofitClient
import com.nurullo.foodsharing.model.requestParams.RegisterParams
import com.nurullo.foodsharing.utils.SessionManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {

    lateinit var usernameEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var nickNameEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var phoneEditText: EditText

    lateinit var signUpBtn: Button
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_sign_up, container, false)
        navController = NavHostFragment.findNavController(this)
        usernameEditText = v.findViewById(R.id.username_edittext)
        emailEditText = v.findViewById(R.id.email_edittext)
        phoneEditText = v.findViewById(R.id.phone_edittext)
        nickNameEditText = v.findViewById(R.id.nickname_edittext)
        passwordEditText = v.findViewById(R.id.password_edittext)
        signUpBtn = v.findViewById(R.id.signup_button)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpBtn.setOnClickListener { v: View? ->
            signUpBtn.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.bubble)
            )

            val username: String = usernameEditText.text.toString()
            val email: String = emailEditText.text.toString()
            val phone: String = phoneEditText.text.toString()
            val nickname: String = nickNameEditText.text.toString()
            val password: String = passwordEditText.text.toString()

            RetrofitClient.getInstance(requireContext())
                .register(RegisterParams(username, email, phone, nickname, password), object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.code() == 200 && response.isSuccessful) {
                            val sm = SessionManager(requireContext())
                            sm.hideSoftKeyBoard(requireContext(), v)
                            Snackbar.make(v!!, "Регистрация успешна!\n Войдите в приложение." +
                                    "", Snackbar.LENGTH_SHORT).show()
                            navController.navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Snackbar.make(v!!, "Ошибка!", Snackbar.LENGTH_SHORT)
                            .show()
                    }

                })
        }
    }
}
