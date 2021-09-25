package com.nurullo.foodsharing.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.model.response.FoodTypePojo
import java.util.*

class TypeAdapter(var mContext: Context) :
    ArrayAdapter<FoodTypePojo?>(mContext, R.layout.spinner_view) {

    var types: List<FoodTypePojo> = ArrayList<FoodTypePojo>()

    fun setFoodTypes(c: List<FoodTypePojo>) {
        this.types = c
        notifyDataSetChanged()
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return types.size
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var root = convertView
        if (convertView == null) {
            root = LayoutInflater.from(mContext).inflate(R.layout.spinner_view, parent, false)
        }
        val name = root!!.findViewById<TextView>(R.id.text)
        name.setText(types[position].id.toString() + ". " + types[position].name)
        root.tag = types[position].id
        return root
    }
}
