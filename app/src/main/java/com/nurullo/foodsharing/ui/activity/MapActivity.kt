package com.nurullo.foodsharing.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.api.RetrofitClient
import com.nurullo.foodsharing.model.requestParams.CreateFoodAdPojo
import com.nurullo.foodsharing.model.response.FoodTypePojo
import com.nurullo.foodsharing.repository.FoodTypeRepository
import com.nurullo.foodsharing.ui.adapter.TypeAdapter
import com.nurullo.foodsharing.utils.SessionManager
import com.nurullo.foodsharing.viewmodel.FoodTypeViewModel
import com.nurullo.foodsharing.viewmodel_factory.FoodTypeViewModelFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var types: List<FoodTypePojo>
    private lateinit var foodTypeAdapter: TypeAdapter
    private lateinit var typeSpinner: Spinner
    private var foodType: Long = 0L
    private lateinit var typeName: String
    private lateinit var sm: SessionManager

    private lateinit var foodTypeViewModel: FoodTypeViewModel
    private lateinit var foodTypeViewModelFactory: FoodTypeViewModelFactory
    private lateinit var foodTypeRepository: FoodTypeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        sm = SessionManager(this)
        this.foodTypeAdapter = TypeAdapter(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val networkService = RetrofitClient.getInstance(this)
        foodTypeRepository = FoodTypeRepository(networkService)
        foodTypeViewModelFactory = FoodTypeViewModelFactory(foodTypeRepository)
        foodTypeViewModel = ViewModelProvider(viewModelStore, foodTypeViewModelFactory).get(
            FoodTypeViewModel::class.java
        )

        foodTypeViewModel.getFoodTypes()
        foodTypeViewModel.foodAds.observe(this) { list ->
            types = list
            this.foodTypeAdapter.setFoodTypes(types)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest()
        val locationSettingsRequest = LocationSettingsRequest.Builder().addLocationRequest(
            locationRequest
        ).build()
        val client = LocationServices.getSettingsClient(this).checkLocationSettings(
            locationSettingsRequest
        )
        client.addOnSuccessListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    2
                )
                return@addOnSuccessListener
            }
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val loc = LatLng(
                            locationResult.lastLocation.latitude,
                            locationResult.lastLocation.longitude
                        )
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
                        mMap.addMarker(MarkerOptions().position(loc).title("Вы тут"))
                    }
                },
                Looper.myLooper()
            )
        }.addOnFailureListener { exception ->

        }

        mMap.setOnMapClickListener { latlng ->
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latlng))

            val geocoder: Geocoder
            val addresses: List<Address>
            geocoder = Geocoder(this, Locale.getDefault())
            addresses = geocoder.getFromLocation(
                latlng.latitude,
                latlng.longitude,
                1
            )

            val address: String =
                addresses[0].getAddressLine(0)

            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_food)

            typeSpinner = bottomSheetDialog.findViewById(R.id.spinner)!!
            typeSpinner.setAdapter(this.foodTypeAdapter)

            typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    foodType = types[position].id
                    typeName = types[position].name
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            val name = bottomSheetDialog.findViewById<EditText>(R.id.textview_name)
            val description = bottomSheetDialog.findViewById<EditText>(R.id.textview_description)
            val weight = bottomSheetDialog.findViewById<EditText>(R.id.textview_weight)
            val expDate = bottomSheetDialog.findViewById<EditText>(R.id.textview_exp_date)
            val imageUrl = bottomSheetDialog.findViewById<EditText>(R.id.textview_imageurl)
            val addressText = bottomSheetDialog.findViewById<TextView>(R.id.textview_address)
            val add = bottomSheetDialog.findViewById<Button>(R.id.add)

            add!!.setOnClickListener { view ->
                if (name!!.text.length < 1) {
                    name.setError("Введите название продукта")
                } else if (description!!.text.length < 1) {
                    description.setError("Введите описание продукта")
                } else if (weight!!.text.length < 1) {
                    weight.setError("Введите вес продукта")
                } else if (expDate!!.text.length < 1) {
                    expDate.setError("Введите срок годности продукта")
                } else {
                    RetrofitClient.getInstance(this@MapActivity)
                        .createFoodAd(CreateFoodAdPojo(
                            sm.userId,
                            name.text.toString(),
                            addressText!!.text.toString(),
                            foodType,
                            typeName,
                            weight.text.toString().toLong(),
                            description.text.toString(),
                            imageUrl!!.text.toString(),
                            expDate.text.toString(),
                            latlng.latitude.toDouble(),
                            latlng.longitude.toDouble(),
                            false
                        ), object : Callback<ResponseBody> {
                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                Toast.makeText(
                                    this@MapActivity,
                                    "Продукт добавлен!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                bottomSheetDialog.dismiss()
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Toast.makeText(this@MapActivity, "Ошибка", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        })
                }
            }
            addressText!!.text = address
            bottomSheetDialog.show()
        }
    }
}
