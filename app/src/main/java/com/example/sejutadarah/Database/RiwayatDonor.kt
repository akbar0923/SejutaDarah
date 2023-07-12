package com.example.sejutadarah.Database

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RiwayatDonor (
    @get:PropertyName("beratBadan")
    @set:PropertyName("beratBadan")
    var beratBadan: String = "",

    @get:PropertyName("denyutNadi")
    @set:PropertyName("denyutNadi")
    var denyutNadi: String = "",

    @get:PropertyName("golonganDarah")
    @set:PropertyName("golonganDarah")
    var golonganDarah: String = "",

    @get:PropertyName("lokasiDonor")
    @set:PropertyName("lokasiDonor")
    var lokasiDonor: String = "",

    @get:PropertyName("suhuBadan")
    @set:PropertyName("suhuBadan")
    var suhuBadan: String = "",

    @get:PropertyName("tanggalDonor")
    @set:PropertyName("tanggalDonor")
    var tanggalDonor: String = "",

    @get:PropertyName("tekananDarah")
    @set:PropertyName("tekananDarah")
    var tekananDarah: String = "",

    @get:PropertyName("tinggiBadan")
    @set:PropertyName("tinggiBadan")
    var tinggiBadan: String = "",

    @get:PropertyName("uid")
    @set:PropertyName("uid")
    var uid: String = ""
) : Parcelable