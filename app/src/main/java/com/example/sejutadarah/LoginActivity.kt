package com.example.sejutadarah
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            // Validasi inputan
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses login dengan Firebase Authentication
            auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login berhasil
                        Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()

                        // Lakukan tindakan sesuai kebutuhan, seperti mengarahkan pengguna ke halaman berikutnya
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        // Login gagal
                        when (task.exception) {
                            is FirebaseAuthInvalidUserException -> {
                                Toast.makeText(this, "Akun tidak valid", Toast.LENGTH_SHORT).show()
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(this, "Login gagal. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
