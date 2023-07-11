package com.example.sejutadarah


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sejutadarah.Fragment.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class TiketPaymentActivity : AppCompatActivity() {

//    private lateinit var etUsername: EditText
//    private lateinit var etPassword: EditText
//    private lateinit var btnLogin: Button

    private lateinit var backButton: ImageView
//
//    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tiketpayment)

        backButton = findViewById(R.id.back_button)

        backButton.setOnClickListener {
            finish()
        }

    }

}