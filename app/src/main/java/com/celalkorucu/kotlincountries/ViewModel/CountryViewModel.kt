package com.celalkorucu.kotlincountries.ViewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.celalkorucu.kotlincountries.model2.Country
import com.celalkorucu.kotlincountries.Service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid : Int){

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}