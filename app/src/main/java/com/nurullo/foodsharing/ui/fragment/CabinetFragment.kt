package com.nurullo.foodsharing.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.api.RetrofitClient
import com.nurullo.foodsharing.model.requestParams.UpdateUserPojo
import com.nurullo.foodsharing.ui.activity.MainActivity
import com.nurullo.foodsharing.utils.SessionManager
import com.nurullo.foodsharing.viewmodel.CabinetViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CabinetFragment : Fragment() {

    private lateinit var cabinetViewModel: CabinetViewModel
    private lateinit var sm: SessionManager
    private lateinit var usernameTextView: TextView
    private lateinit var emailEditText: EditText
    private lateinit var nicknameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var editButton: Button
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_cabinet, container, false)
        usernameTextView = v.findViewById(R.id.username)
        emailEditText = v.findViewById(R.id.email)
        nicknameEditText = v.findViewById(R.id.nickname)
        phoneEditText = v.findViewById(R.id.phone)
        editButton = v.findViewById(R.id.edit)
        swipeRefreshLayout = v.findViewById(R.id.swipe)

        cabinetViewModel = CabinetViewModel(requireContext())
        sm = SessionManager(requireContext())
        cabinetViewModel.getUserInfo(sm.userId)

        cabinetViewModel.data.observe(viewLifecycleOwner) { item ->
            usernameTextView.setText(item.username)
            emailEditText.setText(item.email)
            nicknameEditText.setText(item.nickname)
            phoneEditText.setText(item.phone)
        }

        swipeRefreshLayout.setOnRefreshListener {
            cabinetViewModel.getUserInfo(sm.userId)
            swipeRefreshLayout.isRefreshing = false
        }
        return v
    }

    override fun onResume() {
        super.onResume()
        MainActivity.updateNavigationBarState(R.id.navigation_user)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editButton.setOnClickListener {
            sm.hideSoftKeyBoard(requireContext(), usernameTextView)
            if (emailEditText.text.isEmpty()) {
                emailEditText.setError("Пустое поле!")
                return@setOnClickListener
            }
            if (nicknameEditText.text.isEmpty()) {
                nicknameEditText.setError("Пустое поле!")
                return@setOnClickListener
            }
            if (phoneEditText.text.isEmpty()) {
                phoneEditText.setError("Пустое поле!")
                return@setOnClickListener
            }
            editButton.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.bubble)
            )

            RetrofitClient.getInstance(requireContext())
                .updateUserInfo(sm.userId, UpdateUserPojo(
                    usernameTextView.text.toString(),
                    emailEditText.text.toString(),
                    nicknameEditText.text.toString(),
                    phoneEditText.text.toString()
                ), object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.code() == 200) {
                            Snackbar.make(
                                usernameTextView,
                                "Успешно! Обновите экран.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        } else {
                            Snackbar.make(
                                usernameTextView,
                                "Не удалось!",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        t.printStackTrace()
                        Snackbar.make(
                            usernameTextView,
                            "Не удалось!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                })
        }
    }
}