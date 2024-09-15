package com.example.weatherus.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.example.weatherus.R
import com.example.weatherus.data.State

class ModuleArrayAdapter(context: Context,
                         @LayoutRes private val res: Int,
                         private val states: List<State>
): ArrayAdapter<State>(context, res, states) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getStateView(position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getStateView(position, parent)
    }

    private fun getStateView(position: Int, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(res, parent, false)
        view.findViewById<TextView>(R.id.text).text = states[position].abbreviation
        return view
    }
}