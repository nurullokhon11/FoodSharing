package com.nurullo.foodsharing.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.api.RetrofitClient
import com.nurullo.foodsharing.model.requestParams.FindFoodPojo
import com.nurullo.foodsharing.model.requestParams.UpdateFoodAdPojo
import com.nurullo.foodsharing.model.response.FoodAdPojo
import com.nurullo.foodsharing.repository.FoodAdRepository
import com.nurullo.foodsharing.ui.activity.MainActivity
import com.nurullo.foodsharing.ui.adapter.FoodAdAdapter
import com.nurullo.foodsharing.ui.adapter.MyAdsAdapter
import com.nurullo.foodsharing.utils.SessionManager
import com.nurullo.foodsharing.viewmodel.HomeViewModel
import com.nurullo.foodsharing.viewmodel.NotificationsViewModel
import com.nurullo.foodsharing.viewmodel_factory.FoodTypeViewModelFactory
import com.nurullo.foodsharing.viewmodel_factory.HomeViewModelFactory
import com.nurullo.foodsharing.viewmodel_factory.NotificationsViewModelFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsFragment : Fragment(), FoodListener {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var foodAdsRecyclerView: RecyclerView
    private lateinit var foodAdRepository: FoodAdRepository
    private lateinit var myAdsAdapter: MyAdsAdapter
    private lateinit var networkService: RetrofitClient
    private lateinit var notificationsViewModelFactory: NotificationsViewModelFactory
    private lateinit var sm: SessionManager
    private lateinit var ads: List<FoodAdPojo>
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_notifications, container, false)
        swipeRefreshLayout = v.findViewById(R.id.swipe)
        networkService = RetrofitClient.getInstance(requireContext())
        sm = SessionManager(requireContext())

        foodAdsRecyclerView = v.findViewById(R.id.food_ads_recyclerview)
        foodAdsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        myAdsAdapter = MyAdsAdapter(requireContext(), this)
        foodAdsRecyclerView.adapter = myAdsAdapter

        foodAdRepository = FoodAdRepository(networkService)
        notificationsViewModelFactory = NotificationsViewModelFactory(foodAdRepository)
        notificationsViewModel =
            ViewModelProvider(viewModelStore, notificationsViewModelFactory).get(
                NotificationsViewModel::class.java
            )

        notificationsViewModel.getAllFoodAdsByUserId(sm.userId)
        notificationsViewModel.foodAds.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                ads = list
                myAdsAdapter.setFoodAds(list)
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            notificationsViewModel.getAllFoodAdsByUserId(sm.userId)
            swipeRefreshLayout.isRefreshing = false
        }
        return v
    }

    override fun onResume() {
        super.onResume()
        MainActivity.updateNavigationBarState(R.id.navigation_notification)
    }

    override fun onFoodClicked(id: String, pos: Int) {
        networkService.updateFoodAdByQuery(
            FindFoodPojo(
                ads[pos].ownerId,
                ads[pos].address,
                ads[pos].description,
                ads[pos].name
            ), object :
                Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        networkService.deleteUserAd(
                            sm.userId,
                            ads[pos].id,
                            object : Callback<ResponseBody> {
                                override fun onResponse(
                                    call: Call<ResponseBody>,
                                    response: Response<ResponseBody>
                                ) {
                                    if (response.code() == 200) {
                                        Snackbar.make(
                                            foodAdsRecyclerView,
                                            "Вы отказались от продукта!",
                                            Snackbar.LENGTH_SHORT
                                        ).show()
                                        notificationsViewModel.getAllFoodAdsByUserId(sm.userId)
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    t.printStackTrace()
                                }

                            })
                    } else {
                        Snackbar.make(
                            foodAdsRecyclerView,
                            "Не получилось отказаться",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Snackbar.make(
                        foodAdsRecyclerView,
                        "Ошибка!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun onFoodReceived(id: String, pos: Int) {
        networkService.updateFoodVisibilityAdByQuery(
            FindFoodPojo(
                ads[pos].ownerId,
                ads[pos].address,
                ads[pos].description,
                ads[pos].name
            ), object :
                Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        networkService.deleteUserAd(
                            sm.userId,
                            ads[pos].id,
                            object : Callback<ResponseBody> {
                                override fun onResponse(
                                    call: Call<ResponseBody>,
                                    response: Response<ResponseBody>
                                ) {
                                    if (response.code() == 200) {
                                        Snackbar.make(
                                            foodAdsRecyclerView,
                                            "Ура! Вы получили продукт.",
                                            Snackbar.LENGTH_SHORT
                                        ).show()
                                        notificationsViewModel.getAllFoodAdsByUserId(sm.userId)

                                        networkService.deleteUserAdByQuery(ads[pos].ownerId,
                                            FindFoodPojo(
                                                ads[pos].ownerId,
                                                ads[pos].address,
                                                ads[pos].description,
                                                ads[pos].name
                                            ), object : Callback<ResponseBody> {
                                                override fun onResponse(
                                                    call: Call<ResponseBody>,
                                                    response: Response<ResponseBody>
                                                ) {
                                                    System.out.println(response.body())
                                                }

                                                override fun onFailure(
                                                    call: Call<ResponseBody>,
                                                    t: Throwable
                                                ) {
                                                    TODO("Not yet implemented")
                                                }

                                            })
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    t.printStackTrace()
                                }

                            })
                    } else {
                        Snackbar.make(
                            foodAdsRecyclerView,
                            "Не получилось отказаться",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Snackbar.make(
                        foodAdsRecyclerView,
                        "Ошибка!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
