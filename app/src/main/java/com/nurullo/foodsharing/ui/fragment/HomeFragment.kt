package com.nurullo.foodsharing.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.api.RetrofitClient
import com.nurullo.foodsharing.model.response.FoodTypePojo
import com.nurullo.foodsharing.repository.FoodAdRepository
import com.nurullo.foodsharing.repository.FoodTypeRepository
import com.nurullo.foodsharing.ui.adapter.FoodAdAdapter
import com.nurullo.foodsharing.ui.adapter.TypeAdapter
import com.nurullo.foodsharing.viewmodel.FoodTypeViewModel
import com.nurullo.foodsharing.viewmodel.HomeViewModel
import com.nurullo.foodsharing.viewmodel_factory.FoodTypeViewModelFactory
import com.nurullo.foodsharing.viewmodel_factory.HomeViewModelFactory

class HomeFragment : Fragment() {

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
    private lateinit var typeSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        this.foodTypeAdapter = TypeAdapter(requireContext())
        foodAdsRecyclerView = v.findViewById(R.id.food_ads_recyclerview)
        foodAdsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        foodAdAdapter = FoodAdAdapter(requireContext())
        foodAdsRecyclerView.adapter = foodAdAdapter

        typeSpinner = v.findViewById(R.id.spinner)!!
        typeSpinner.adapter = this.foodTypeAdapter

        networkService = RetrofitClient.getInstance(requireContext())

        foodAdRepository = FoodAdRepository(networkService)
        homeViewModelFactory = HomeViewModelFactory(foodAdRepository)
        homeViewModel = ViewModelProvider(viewModelStore, homeViewModelFactory).get(
            HomeViewModel::class.java
        )

        homeViewModel.getAllFoodAds()

        homeViewModel.foodAds.observe(viewLifecycleOwner) { list ->
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
        return v
    }
}
