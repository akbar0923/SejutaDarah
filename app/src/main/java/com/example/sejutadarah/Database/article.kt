package com.example.sejutadarah.Database
import com.google.firebase.firestore.PropertyName

data class article(
    @get:PropertyName("content")
    @set:PropertyName("content")
    var content: String = "",

    @get:PropertyName("judul")
    @set:PropertyName("judul")
    var judul: String = "",

    @get:PropertyName("foto")
    @set:PropertyName("foto")
    var foto: String = "",

    @get:PropertyName("deskripsi")
    @set:PropertyName("deskripsi")
    var deskripsi: String = ""
)


