package com.example.sejutadarah
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sejutadarah.Database.userSejutaDarah
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var etNik: EditText
    private lateinit var etNoHP: EditText

    private lateinit var spinnerGender: Spinner
    private lateinit var spinnerGolonganDarah: Spinner

    private lateinit var tvLogin: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        etNik = findViewById(R.id.etNIK)
        etNoHP = findViewById(R.id.etNoHP)
        spinnerGender = findViewById(R.id.spinnerGender)
        spinnerGolonganDarah = findViewById(R.id.spinnerGolonganDarah)

        tvLogin = findViewById(R.id.tvLogin)

        btnRegister.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val nik = etNik.text.toString().trim()
            val noHP = etNoHP.text.toString().trim()
            val bloodGroup = spinnerGolonganDarah.selectedItem.toString()
            val gender = spinnerGender.selectedItem.toString()

            // Validasi inputan
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() ||bloodGroup.isEmpty()|| nik.isEmpty()|| noHP.isEmpty()|| gender.isEmpty()) {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses pendaftaran pengguna baru dengan Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val ref = FirebaseDatabase.getInstance().getReference("userSejutaDarah")

                        val id = ref.push().key

                        val user = auth.currentUser

                        val data = userSejutaDarah(
                            id,
                            fullName,
                            email,
                            password,
                            user?.uid ?: "",
                            "",
                            "",
                            bloodGroup,
                            "",
                            gender,
                            nik,
                            noHP
                        )

                        // Menyimpan data pengguna ke Firebase Realtime Database
                        if (id != null) {
                            ref.child(id).setValue(data).addOnCompleteListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Data berhasil Ditambahkan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        // Pendaftaran berhasil
                        Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()

                        // Lakukan tindakan sesuai kebutuhan, seperti mengarahkan pengguna ke halaman berikutnya
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()


                    } else {
                        // Pendaftaran gagal
                        when (task.exception) {
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
                                    "Format email tidak valid",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is FirebaseAuthUserCollisionException -> {
                                Toast.makeText(
                                    this,
                                    "Email sudah terdaftar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {
                                Toast.makeText(
                                    this,
                                    "Pendaftaran gagal",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
        }

        tvLogin.setOnClickListener {
            // Tindakan saat pengguna mengklik tombol login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
