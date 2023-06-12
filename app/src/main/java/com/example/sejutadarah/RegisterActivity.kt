package com.example.sejutadarah

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class RegisterActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val fullName = etFullName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // Validasi inputan
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses pendaftaran pengguna baru dengan Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Pendaftaran berhasil
                        Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()

                        // Lakukan tindakan sesuai kebutuhan, seperti mengarahkan pengguna ke halaman berikutnya

                    } else {
                        // Pendaftaran gagal
                        val exception = task.exception
                        when (exception) {
                            is FirebaseAuthWeakPasswordException -> {
                                Toast.makeText(
                                    this,
                                    "Password terlalu lemah",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                Toast.makeText(
                                    this,
                                    "Email tidak valid",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is FirebaseAuthUserCollisionException -> {
                                Toast.makeText(
                                    this,
                                    "Akun dengan email tersebut sudah terdaftar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {
                                Toast.makeText(
                                    this,
                                    "Pendaftaran gagal. Silakan coba lagi.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
        }
    }
}
