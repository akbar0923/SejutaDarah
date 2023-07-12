package com.example.sejutadarah.Database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.firebase.firestore.PropertyName


@Parcelize
data class DonorConnect(

    @get:PropertyName("id")
    @set:PropertyName("id")
    var id : String? = "",

    @get:PropertyName("nama")
    @set:PropertyName("nama")
    var nama: String = "",

    @get:PropertyName("userId")
    @set:PropertyName("userId")
    var userId : String = "",

    @get:PropertyName("kontak")
    @set:PropertyName("kontak")
    var kontak : String = "",

    @get:PropertyName("lokasi")
    @set:PropertyName("lokasi")
    var lokasi : String = "",

    @get:PropertyName("goldar")
    @set:PropertyName("goldar")
    var goldar : String = ""
) : Parcelable