package com.celalkorucu.kotlincountries.model2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
public data class Country(

    @ColumnInfo("name")
    @SerializedName("name")
    val countryName : String?,

    @ColumnInfo("capital")
    @SerializedName("capital")
    val countryCapital : String?,

    @ColumnInfo("region")
    @SerializedName("region")
    val countryRegion : String? ,

    @ColumnInfo("currency")
    @SerializedName("currency")
    val countryCurrency : String? ,

    @ColumnInfo("language")
    @SerializedName("language")
    val countryLanguage : String? ,

    @ColumnInfo("flag")
    @SerializedName("flag")
    val countryFlagURL : String?){

    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}