package com.example.sejutadarah.Database

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TukarPoinClass (
//    @get:PropertyName("content")
//    @set:PropertyName("content")
//    var content: String = "",

    @get:PropertyName("namaHadiah")
    @set:PropertyName("namaHadiah")
    var namaHadiah: String = "",

    @get:PropertyName("hargaHadiah")
    @set:PropertyName("hargaHadiah")
    var hargaHadiah: String = "",

    @get:PropertyName("hargaPoin")
    @set:PropertyName("hargaPoin")
    var hargaPoin: String = ""

) : Parcelable