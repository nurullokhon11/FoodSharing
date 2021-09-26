package com.nurullo.foodsharing.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.api.RetrofitClient
import com.nurullo.foodsharing.model.requestParams.CreateFoodAdPojo
import com.nurullo.foodsharing.model.requestParams.UpdateFoodAdPojo
import com.nurullo.foodsharing.model.response.FoodAdPojo
import com.nurullo.foodsharing.model.response.FoodTypePojo
import com.nurullo.foodsharing.repository.FoodAdRepository
import com.nurullo.foodsharing.repository.FoodTypeRepository
import com.nurullo.foodsharing.ui.activity.MainActivity
import com.nurullo.foodsharing.ui.adapter.FoodAdAdapter
import com.nurullo.foodsharing.ui.adapter.TypeAdapter
import com.nurullo.foodsharing.utils.SessionManager
import com.nurullo.foodsharing.viewmodel.FoodTypeViewModel
import com.nurullo.foodsharing.viewmodel.HomeViewModel
import com.nurullo.foodsharing.viewmodel_factory.FoodTypeViewModelFactory
import com.nurullo.foodsharing.viewmodel_factory.HomeViewModelFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class HomeFragment : Fragment(), FoodListener {

    private lateinit var foodAdsRecyclerView: RecyclerView

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var foodAdRepository: FoodAdRepository
    private lateinit var foodAdAdapter: FoodAdAdapter
    private lateinit var networkService: RetrofitClient

    private lateinit var foodTypeViewModel: FoodTypeViewModel
    private lateinit var foodTypeViewModelFactory: FoodTypeViewModelFactory
    private lateinit var foodTypeRepository: FoodTypeRepository
    private lateinit var foodTypeAdapter: TypeAdapter
    private lateinit var types: List<FoodTypePojo>
    private lateinit var ads: List<FoodAdPojo>
    private lateinit var typeSpinner: Spinner
    private var foodType: Long = -1L
    private lateinit var sm: SessionManager

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        sm = SessionManager(requireContext())
        swipeRefreshLayout = v.findViewById(R.id.swipe)
        this.foodTypeAdapter = TypeAdapter(requireContext())
        foodAdsRecyclerView = v.findViewById(R.id.food_ads_recyclerview)
        foodAdsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        foodAdAdapter = FoodAdAdapter(requireContext(), this)
        foodAdsRecyclerView.adapter = foodAdAdapter

        typeSpinner = v.findViewById(R.id.spinner)!!
        typeSpinner.adapter = this.foodTypeAdapter

        networkService = RetrofitClient.getInstance(requireContext())

        foodAdRepository = FoodAdRepository(networkService)
        homeViewModelFactory = HomeViewModelFactory(foodAdRepository)
        homeViewModel = ViewModelProvider(viewModelStore, homeViewModelFactory).get(
            HomeViewModel::class.java
        )

        homeViewModel.getAllFoodAds(type = foodType)

        homeViewModel.foodAds.observe(viewLifecycleOwner) { list ->
            if (list.items.size < 1) {
                Snackbar.make(typeSpinner, "По запросу ничего не найдено", Snackbar.LENGTH_SHORT)
                    .show()
            }
            ads = list.items
            foodAdAdapter.setFoodAds(list.items)
            foodAdAdapter.notifyDataSetChanged()
        }

        foodTypeRepository = FoodTypeRepository(networkService)
        foodTypeViewModelFactory = FoodTypeViewModelFactory(foodTypeRepository)
        foodTypeViewModel = ViewModelProvider(viewModelStore, foodTypeViewModelFactory).get(
            FoodTypeViewModel::class.java
        )

        foodTypeViewModel.getFoodTypes()
        foodTypeViewModel.foodAds.observe(viewLifecycleOwner) { list ->
            types = list
            foodTypeAdapter.setFoodTypes(types)
        }

        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                foodType = types[position].id
                homeViewModel.getAllFoodAds(type = foodType)
                //typeName = types[position].name
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.getAllFoodAds(type = foodType)
            swipeRefreshLayout.isRefreshing = false
        }
        return v
    }

    override fun onResume() {
        super.onResume()
        MainActivity.updateNavigationBarState(R.id.navigation_home)
    }

    override fun onFoodClicked(id: String, pos: Int) {
        networkService.updateFoodAd(id, UpdateFoodAdPojo(id, true), object :
            retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == 200) {
                    foodAdAdapter.notifyDataSetChanged()

                    val f: FoodAdPojo = ads[pos]
                    networkService.addUserAd(
                        sm.userId, CreateFoodAdPojo(
                            f.ownerId,
                            f.ownerName,
                            f.name,
                            f.address,
                            f.type,
                            f.typeName,
                            f.weight,
                            f.description,
                            f.image,
                            f.expDate,
                            f.latitude,
                            f.longitude,
                            true,
                            1
                        ), object : retrofit2.Callback<ResponseBody> {
                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                if (response.code() == 200) {
                                    Snackbar.make(
                                        typeSpinner,
                                        "Добавлено вам",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                t.printStackTrace()
                            }

                        }
                    )

                } else {
                    Snackbar.make(
                        typeSpinner,
                        "Уже зарезервировано!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onFoodReceived(id: String, pos: Int) {
        return
    }
}
