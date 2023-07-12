package com.example.sejutadarah.Database
import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class article(

    @get:PropertyName("judul")
    @set:PropertyName("judul")
    var judul: String = "",

    @get:PropertyName("foto")
    @set:PropertyName("foto")
    var foto: String = "",

    @get:PropertyName("deskripsi")
    @set:PropertyName("deskripsi")
    var deskripsi: String = ""

) : Parcelable


