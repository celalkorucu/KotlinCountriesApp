package com.celalkorucu.kotlincountries.Service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.celalkorucu.kotlincountries.model2.Country

@Dao
interface CountryDAO {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg  countries : Country) : List<Long>

    // suspend -> coroutines için
    //vararg -> birden fazla verilmesi için


    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryID")
    suspend fun getCountry(countryID  : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}