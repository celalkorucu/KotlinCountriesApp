package com.celalkorucu.kotlincountries.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.celalkorucu.kotlincountries.model2.Country
import com.celalkorucu.kotlincountries.Service.CountryAPIService
import com.celalkorucu.kotlincountries.Service.CountryDatabase
import com.celalkorucu.kotlincountries.Util.CustomSharedPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryAPIService = CountryAPIService()

    //disposable = kullan - at yani fragment kapandığında call'erı kapatmak için callar disposableye atılır
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 1* 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val updateTime = customPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime <refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()

        }

    }

    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {

                        storeInSQLite(t)
                        Toast.makeText(getApplication() , "Countries From API", Toast.LENGTH_SHORT).show()

                    }
 
                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                    }

                })
        )
    }

    private fun getDataFromSQLite(){
        countryLoading.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication() , "Countries From SQLite",Toast.LENGTH_SHORT).show()

        }
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(countryList: List<Country>) {

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()

            val listLongUUID = dao.insertAll(*countryList.toTypedArray())

            var i = 0
            while (i < countryList.size) {
                countryList[i].uuid = listLongUUID[i].toInt()
                i++
            }

            showCountries(countryList)
        }

        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }



}