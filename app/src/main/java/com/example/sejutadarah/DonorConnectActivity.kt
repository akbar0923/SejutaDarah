package com.example.sejutadarah

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.sejutadarah.Database.DonorConnect
import com.example.sejutadarah.Database.Token
import com.example.sejutadarah.Database.userSejutaDarah
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class DonorConnectActivity : AppCompatActivity() {

    private lateinit var tvTiket: TextView
    private lateinit var etKontak: EditText
    private lateinit var rgGoldar: RadioGroup
    private lateinit var rbGoldar: RadioButton
    private lateinit var btnKirim: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_connect)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        auth = FirebaseAuth.getInstance()
        tvTiket = findViewById(R.id.tvTiket)
        etKontak = findViewById(R.id.etKontak)
        btnKirim = findViewById(R.id.btnKirim)
        rgGoldar = findViewById(R.id.rgGoldar)

        btnKirim.setOnClickListener {
            addData()
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun addData() {
        var currentLocation = "Banjarmasin"
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: MutableList<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val kontak = etKontak.text.toString().trim()
                        val lokasi = list?.get(0)?.getAddressLine(0) ?: ""
                        val idGoldar = rgGoldar.checkedRadioButtonId
                        rbGoldar = findViewById(idGoldar)
                        val goldar = rbGoldar.text.toString().trim()

                        val ref = FirebaseDatabase.getInstance().getReference("donorConnect")

                        val id = ref.push().key

                        // Inisialisasi Firebase
                        val currentUser = auth.currentUser!!
                        val userRef = ref.child("userSejutaDarah")
                        var nama = ""

                        // Ambil data dari Firebase
                        userRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                // Ambil nilai dari database
                                for (userSnapshot in dataSnapshot.children) {
                                    val user: userSejutaDarah? = userSnapshot.getValue(userSejutaDarah::class.java)
                                    if (user != null) {
                                        if(user.userId == currentUser.uid) {
                                            // Set nilai ke komponen tampilan
                                            user.let {
                                                nama = user.fullName
                                            }
                                            break
                                        }
                                    }
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Handle error jika terjadi
                            }
                        })

                        val data = DonorConnect(id, nama,currentUser?.uid ?: "", kontak, lokasi, goldar)

                        // Menyimpan data pengguna ke Firebase Realtime Database
                        if (id != null) {
                            ref.child(id).setValue(data).addOnCompleteListener{
                                Toast.makeText(applicationContext, "Data berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                            }
                        }

                        // Lakukan tindakan sesuai kebutuhan, seperti mengarahkan pengguna ke halaman berikutnya
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                addData()
            }
        }
    }
}