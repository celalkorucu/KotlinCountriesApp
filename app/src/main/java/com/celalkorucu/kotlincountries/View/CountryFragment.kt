package com.celalkorucu.kotlincountries.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.celalkorucu.kotlincountries.R
import com.celalkorucu.kotlincountries.Util.downloadImage
import com.celalkorucu.kotlincountries.Util.placeHolderProgressBar
import com.celalkorucu.kotlincountries.ViewModel.CountryViewModel
import com.celalkorucu.kotlincountries.databinding.FragmentCountryBinding

class CountryFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var viewModel: CountryViewModel
    private lateinit var dataBinding: FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_country, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)


        observeLiveData()

    }

    private fun observeLiveData() {

        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->

            country?.let {

                dataBinding.selectedCountry = it

            }
        })
    }

}