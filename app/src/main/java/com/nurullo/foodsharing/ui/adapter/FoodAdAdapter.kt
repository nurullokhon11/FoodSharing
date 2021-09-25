package com.nurullo.foodsharing.ui.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.model.response.FoodAdPojo
import com.squareup.picasso.Picasso
import java.util.*

class FoodAdAdapter(private val mContext: Context) :

    RecyclerView.Adapter<FoodAdAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    //private val enrollWorkout: EnrollWorkout
    private var foodAds: List<FoodAdPojo> = ArrayList<FoodAdPojo>()
    fun setFoodAds(foodAds: List<FoodAdPojo>) {
        this.foodAds = foodAds
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.food_ad_item_layout, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(foodAds[position].name)
        holder.description.setText(foodAds[position].description)
        holder.address.setText(foodAds[position].address)
        holder.weight.setText(mContext.getString(R.string.product_weight_gram, foodAds[position].weight))

        if (foodAds[position].reserved)
            holder.reserved.setText(mContext.getString(R.string.reserved))
        else
            holder.reserved.setText("")

        if (foodAds[position].image != null && !foodAds[position].image.isEmpty()) {
            Picasso.get()
                .load(foodAds[position].image)
                .noPlaceholder()
                .fit()
                .centerCrop()
                .into(holder.foodImage)
        }

//        holder.location.setOnClickListener { v: View? ->
//            Toast.makeText(mContext, mContext.getString(R.string.double_click), Toast.LENGTH_SHORT)
//                .show()
//            val uri = String.format(
//                Locale.ENGLISH,
//                "geo:%f,%f?q=$finalAddress $finalKnownName",
//                latitude,
//                longitude
//            )
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//            mContext.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return foodAds.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var description: TextView
        var address: TextView
        var weight: TextView
        var reserved: TextView
        var owner: TextView
        var foodImage: ImageView

        init {
            name = view.findViewById(R.id.textview_food_name)
            description = view.findViewById(R.id.textview_description)
            address = view.findViewById(R.id.textview_address)
            foodImage = view.findViewById(R.id.imageview_food)
            weight = view.findViewById(R.id.textview_weight)
            reserved = view.findViewById(R.id.textview_reserved)
            owner = view.findViewById(R.id.textview_owner)
        }
    }

    init {
        inflater = LayoutInflater.from(mContext)
        //enrollWorkout = ew
    }
}
