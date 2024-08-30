package com.celalkorucu.kotlincountries.adapter2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.celalkorucu.kotlincountries.model2.Country
import com.celalkorucu.kotlincountries.R
import com.celalkorucu.kotlincountries.View.FeedFragmentDirections
import com.celalkorucu.kotlincountries.databinding.ItemCountryBinding
import java.util.ArrayList

class CountryAdapter(val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() , CountryClickListener {


    class CountryViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {


        val binding = DataBindingUtil.inflate<ItemCountryBinding>(LayoutInflater.from(parent.context) , R.layout.item_country , parent , false)
        return CountryViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.binding.country = countryList[position]

        //extend olduğu için
        holder.binding.listener = this

    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {

        val uuidTextView = v.findViewById<TextView>(R.id.uuidText)
        val uuid = uuidTextView.text.toString().toInt()

        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}