package com.nurullo.foodsharing.ui.adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.model.response.FoodAdPojo
import com.nurullo.foodsharing.ui.fragment.FoodListener
import com.nurullo.foodsharing.utils.SessionManager
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class FoodAdAdapter(private val mContext: Context, foodListener: FoodListener) :

    RecyclerView.Adapter<FoodAdAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private val listener: FoodListener
    private var foodAds: List<FoodAdPojo> = ArrayList<FoodAdPojo>()
    private lateinit var sm: SessionManager
    private lateinit var context: Context

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
        if (!sm.userId.equals(foodAds[position].ownerId)) {
            holder.name.setText(foodAds[position].name)


            val cred: List<String> = foodAds[position].ownerName.split(" ")
            holder.owner.setText(context.getString(R.string.owner, cred[0]))
            if (cred.size > 1)
                holder.owner.append("\nПочта: " + cred[1])
            if (cred.size > 2)
                holder.owner.append("\nНомер телефона: " + cred[2])


            holder.description.setText(foodAds[position].description)
            holder.address.setText(foodAds[position].address)
            holder.weight.setText(
                context.getString(
                    R.string.product_weight_gram,
                    foodAds[position].weight
                )
            )

            if (foodAds[position].reserved)
                holder.reserved.setText(context.getString(R.string.reserved))
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

            val inputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val outputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
            val date: LocalDate = LocalDate.parse(foodAds[position].expDate, inputFormatter)
            val formattedDate: String = outputFormatter.format(date)

            holder.expDate.setText(context.getString(R.string.product_exp_date_till, formattedDate))
            holder.reserveBtn.setOnClickListener { v: View? ->
                System.out.println(foodAds[position].ownerId + " id " + sm.userId)
                if (!foodAds[position].reserved && foodAds[position].ownerId == sm.userId) {
                    Snackbar.make(
                        holder.reserveBtn,
                        "Нельзя резервировать свое объявление!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (foodAds[position].reserved) {
                    Snackbar.make(
                        holder.reserveBtn,
                        "Уже зарезервировано!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (!foodAds[position].reserved)
                    listener.onFoodClicked(foodAds[position].id, position)
            }
        }
        else {
            holder.itemView.visibility = View.GONE
            holder.rootView.setLayoutParams(holder.params);
        }
    }

    override fun getItemCount(): Int {
        return foodAds.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var params: LinearLayout.LayoutParams
        var name: TextView
        var description: TextView
        var address: TextView
        var weight: TextView
        var reserved: TextView
        var owner: TextView
        var foodImage: ImageView
        var expDate: TextView
        var reserveBtn: Button
        var rootView: CardView

        init {
            rootView = view.findViewById(R.id.rootview)
            params = LinearLayout.LayoutParams(0, 0)
            name = view.findViewById(R.id.textview_food_name)
            description = view.findViewById(R.id.textview_description)
            address = view.findViewById(R.id.textview_address)
            foodImage = view.findViewById(R.id.imageview_food)
            weight = view.findViewById(R.id.textview_weight)
            reserved = view.findViewById(R.id.textview_reserved)
            owner = view.findViewById(R.id.textview_owner)
            expDate = view.findViewById(R.id.textview_exp_date)
            reserveBtn = view.findViewById(R.id.reserve_button)
        }
    }

    init {
        inflater = LayoutInflater.from(mContext)
        listener = foodListener
        context = mContext
        sm = SessionManager(context)
    }
}
