package com.example.sejutadarah.Fragment

import android.R.attr.duration

import android.Manifest
import android.R.attr.duration
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.DataDonorConnectActivity
import com.example.sejutadarah.Database.DonorConnect
import com.example.sejutadarah.Database.NotificationMessage
import com.example.sejutadarah.Database.Token
import com.example.sejutadarah.Database.userSejutaDarah
import com.example.sejutadarah.DonorConnectActivity
import com.example.sejutadarah.DummyMapsActivity
import com.example.sejutadarah.HomeActivity
import com.example.sejutadarah.R
import com.example.sejutadarah.TiketPaymentActivity
import com.example.sejutadarah.databinding.FragmentDonorConnectBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.color.utilities.Score.score
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.sejutadarah.Database.DonorConnect
//import com.example.sejutadarah.Database.NotificationMessage
//import com.example.sejutadarah.Database.Token
//import com.example.sejutadarah.DonorConnectActivity
//import com.example.sejutadarah.DummyMapsActivity
//import com.example.sejutadarah.R
//import com.example.sejutadarah.Service.FCMSender
//import com.example.sejutadarah.databinding.FragmentDonorConnectBinding
//import com.google.android.material.color.utilities.Score.score
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.Query
//import com.google.firebase.database.ValueEventListener
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//import okhttp3.Call
//import okhttp3.Callback
//import okhttp3.Response
//import java.io.IOException
//
//
class DonorConnectFragment : Fragment() {

    private var _binding: FragmentDonorConnectBinding? = null

    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDonorConnectBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        auth = FirebaseAuth.getInstance()
        
        binding.cvTiketPayment.setOnClickListener {
            val intent = Intent(context, TiketPaymentActivity::class.java) // ganti tujuan tombol
            startActivity(intent)
        }

        binding.cvListDataDC.setOnClickListener{
            val intent = Intent(context, DataDonorConnectActivity::class.java)
            startActivity(intent)
        }

        binding.btnCariDonorConnect.setOnClickListener {
            addData()
        }


//            val message = NotificationMessage(
//                "cfS1orKHQAeJcvR6QtMP6L:APA91bHc3Kh5ZSl0NFVvec9348gN21d_ZSmNTnUXHd0QKfkiqsXpjuCMMCVviOJvcY7ZUue4m_pTBHQoa6rbYsgH3OpYhvOvIfiq050AntCmYuWzKg27nbehUCd-3K-eWPfE4siLDytC",
//                "tes"
//            ).message
//
//            FCMSender().send(message, object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    activity?.runOnUiThread {
//                        Toast.makeText(activity, "Kirim gagal", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    activity?.runOnUiThread {
//                        Toast.makeText(activity, response.body.toString(), Toast.LENGTH_SHORT).show()
//                    }
//                }
//            })
//        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun addData() {

        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val list: MutableList<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val kontak = binding.KontakDonorConnect.text.toString().trim()
                        val lokasi = list?.get(0)?.getAddressLine(0) ?: ""
                        val goldar = binding.GolDarDonorConnect.selectedItem.toString().trim()

                        val ref = FirebaseDatabase.getInstance().getReference("donorConnect")

                        val id = ref.push().key

                        // Inisialisasi Firebase
                        val currentUser = auth.currentUser!!
                        val userRef = FirebaseDatabase.getInstance().getReference("userSejutaDarah")
                        var nama = ""

                        // Ambil data dari Firebase
                        userRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                // Ambil nilai dari database
                                for (userSnapshot in dataSnapshot.children) {
                                    val user: userSejutaDarah? = userSnapshot.getValue(
                                        userSejutaDarah::class.java)
                                    if (user != null) {
                                        if(user.userId == currentUser.uid) {
                                            // Set nilai ke komponen tampilan
                                            nama = user.fullName

                                            break
                                        }
                                    }
                                }

                                val data = DonorConnect(id, nama,currentUser?.uid ?: "", kontak, lokasi, goldar)

                                // Menyimpan data pengguna ke Firebase Realtime Database
                                if (id != null) {
                                    ref.child(id).setValue(data).addOnCompleteListener{
                                        Toast.makeText(requireContext(), "Data berhasil terkirim", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                // Lakukan tindakan sesuai kebutuhan, seperti mengarahkan pengguna ke halaman berikutnya
                                val intent = Intent(requireContext(), HomeActivity::class.java)
                                startActivity(intent)
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Handle error jika terjadi
                                // Lakukan tindakan sesuai kebutuhan, seperti mengarahkan pengguna ke halaman berikutnya
                                Toast.makeText(requireContext(), "Data gagal Ditambahkan", Toast.LENGTH_SHORT).show()
                                val intent = Intent(requireContext(), HomeActivity::class.java)
                                startActivity(intent)
                            }
                        })
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
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

    companion object {
        private const val TAG = "DonorConnectFragment"
    }
}
