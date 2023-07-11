package com.example.sejutadarah

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.Database.TukarPoinClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class TukarPoinActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RiwayatDonorAdapter

    private lateinit var backButton: ImageView
//
//    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tukar_poin)

        backButton = findViewById(R.id.back_button)

        // aksi back button
        backButton.setOnClickListener {
            finish()
        }


        val hadiahList = listOf<TukarPoinClass>(
            TukarPoinClass(
                namaHadiah = "Pulsa Telkomsel",
                hargaHadiah = "Rp25.000",
                hargaPoin = "2500"

            ),
            TukarPoinClass(
                namaHadiah = "Pulsa Telkomsel",
                hargaHadiah = "Rp20.000",
                hargaPoin = "2000"

            ),
            TukarPoinClass(
                namaHadiah = "Pulsa Telkomsel",
                hargaHadiah = "Rp25.000",
                hargaPoin = "2500"

            ),
            TukarPoinClass(
                namaHadiah = "Pulsa Telkomsel",
                hargaHadiah = "Rp50.000",
                hargaPoin = "5000"

            )
        )

        // recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.rvListHadiah)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = TukarPoinAdapter(this, hadiahList) {
            val intent = Intent(this, TiketPaymentActivity::class.java)
            intent.putExtra(INTENT_PARCELABLE, it)
            startActivity(intent)

        }


    }

    companion object {
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }




}