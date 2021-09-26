package com.nurullo.foodsharing.ui.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Looper
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.api.RetrofitClient
import com.nurullo.foodsharing.model.requestParams.CreateFoodAdPojo
import com.nurullo.foodsharing.model.requestParams.UpdateFoodAdPojo
import com.nurullo.foodsharing.model.response.FoodAdPojo
import com.nurullo.foodsharing.repository.FoodAdRepository
import com.nurullo.foodsharing.utils.SessionManager
import com.nurullo.foodsharing.viewmodel.HomeViewModel
import com.nurullo.foodsharing.viewmodel_factory.HomeViewModelFactory
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class FoodMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var foodAdRepository: FoodAdRepository
    private lateinit var networkService: RetrofitClient
    private lateinit var ads: List<FoodAdPojo>
    private lateinit var sm: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        sm = SessionManager(this)

        networkService = RetrofitClient.getInstance(this)
        foodAdRepository = FoodAdRepository(networkService)
        homeViewModelFactory = HomeViewModelFactory(foodAdRepository)
        homeViewModel = ViewModelProvider(viewModelStore, homeViewModelFactory).get(
            HomeViewModel::class.java
        )
        homeViewModel.getAllFoodAds(0)
    }

    override fun onResume() {
        super.onResume()
        MainActivity.updateNavigationBarState(R.id.navigation_map)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        homeViewModel.foodAds.observe(this) { list ->
            if (list.items != null && list.items.size > 0) {
                ads = list.items
                addMarkers(ads)
            }
        }
        googleMap.setOnInfoWindowClickListener { marker ->
            try {
                val bottomSheetDialog = BottomSheetDialog(this)
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_food_item)
                val name: TextView = bottomSheetDialog.findViewById(R.id.textview_food_name)!!
                val description: TextView =
                    bottomSheetDialog.findViewById(R.id.textview_description)!!
                val address: TextView = bottomSheetDialog.findViewById(R.id.textview_address)!!
                val weight: TextView = bottomSheetDialog.findViewById(R.id.textview_weight)!!
                val reserved: TextView = bottomSheetDialog.findViewById(R.id.textview_reserved)!!
                val owner: TextView = bottomSheetDialog.findViewById(R.id.textview_owner)!!
                val foodImage: ImageView = bottomSheetDialog.findViewById(R.id.imageview_food)!!
                val expDate: TextView = bottomSheetDialog.findViewById(R.id.textview_exp_date)!!
                val reserveBtn: Button = bottomSheetDialog.findViewById(R.id.reserve_button)!!

                val food: FoodAdPojo = ads.get(marker.snippet!!.toInt())

                name.setText(food.name)


                val cred: List<String> = food.ownerName.split(" ")
                val text: String = this@FoodMapActivity.getString(R.string.owner, cred[0])
                val styledText: CharSequence = Html.fromHtml(text)
                owner.setText(styledText)
                if (cred.size > 1)
                    owner.append("\nПочта " + cred[1])
                if (cred.size > 2)
                    owner.append("\nНомер телефона " + cred[2])


                description.setText(food.description)
                address.setText(food.address)
                weight.setText(
                    this@FoodMapActivity.getString(
                        R.string.product_weight_gram,
                        food.weight
                    )
                )

                if (food.reserved)
                    reserved.setText(this@FoodMapActivity.getString(R.string.reserved))
                else
                    reserved.setText("")

                if (food.image != null && !food.image.isEmpty()) {
                    Picasso.get()
                        .load(food.image)
                        .noPlaceholder()
                        .fit()
                        .centerCrop()
                        .into(foodImage)
                }

                val inputFormatter: DateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                val outputFormatter: DateTimeFormatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
                val date: LocalDate = LocalDate.parse(food.expDate, inputFormatter)
                val formattedDate: String = outputFormatter.format(date)

                expDate.setText(
                    this@FoodMapActivity.getString(
                        R.string.product_exp_date_till,
                        formattedDate
                    )
                )

                if (food.ownerId == sm.userId)
                    reserveBtn.visibility = View.INVISIBLE
                reserveBtn.setOnClickListener {
                    if (!food.reserved && food.ownerId == sm.userId) {
                        Snackbar.make(
                            reserveBtn,
                            "Нельзя резервировать свое объявление!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    } else if (food.reserved) {
                        Snackbar.make(
                            reserveBtn,
                            "Уже зарезервировано!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    } else if (!food.reserved) {
                        val networkService: RetrofitClient =
                            RetrofitClient.getInstance(this@FoodMapActivity)
                        networkService.updateFoodAd(
                            food.id,
                            UpdateFoodAdPojo(food.id, true),
                            object :
                                retrofit2.Callback<ResponseBody> {
                                override fun onResponse(
                                    call: Call<ResponseBody>,
                                    response: Response<ResponseBody>
                                ) {
                                    if (response.code() == 200) {
                                        networkService.addUserAd(
                                            sm.userId, CreateFoodAdPojo(
                                                food.ownerId,
                                                food.ownerName,
                                                food.name,
                                                food.address,
                                                food.type,
                                                food.typeName,
                                                food.weight,
                                                food.description,
                                                food.image,
                                                food.expDate,
                                                food.latitude,
                                                food.longitude,
                                                true,
                                                1
                                            ), object : retrofit2.Callback<ResponseBody> {
                                                override fun onResponse(
                                                    call: Call<ResponseBody>,
                                                    response: Response<ResponseBody>
                                                ) {
                                                    if (response.code() == 200) {
                                                        Toast.makeText(
                                                            this@FoodMapActivity,
                                                            "Добавлено вам",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                    }
                                                }

                                                override fun onFailure(
                                                    call: Call<ResponseBody>,
                                                    t: Throwable
                                                ) {
                                                    t.printStackTrace()
                                                }

                                            }
                                        )

                                    } else {
                                        Toast.makeText(
                                            this@FoodMapActivity,
                                            "Уже зарезервировано!",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    Toast.makeText(
                                        this@FoodMapActivity,
                                        "Ошибка",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            })
                    }
                }

                bottomSheetDialog.show()
            } catch (e: ArrayIndexOutOfBoundsException) {
                Log.e("ArrayIndexOutOfBoundsException", " Occured")
            }
        }

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
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14.0f))
                        mMap.addMarker(
                            MarkerOptions().position(loc).title("Вы тут").icon(
                                bitmapDescriptorFromVector(
                                    this@FoodMapActivity,
                                    R.drawable.ic_marker
                                )
                            )
                        )
                    }
                },
                Looper.myLooper()
            )
        }.addOnFailureListener { exception ->

        }
    }

    fun addMarkers(items: List<FoodAdPojo>) {
        var i = 0
        for (a: FoodAdPojo in items) {
            val loc = LatLng(
                a.latitude,
                a.longitude
            )
            val marker = MarkerOptions()
                .position(loc)
                .title(a.name)
                .snippet("" + i)
                .icon(
                    BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            mMap.addMarker(marker)
            i++
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
