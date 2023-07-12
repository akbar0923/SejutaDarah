package com.example.sejutadarah


import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.Database.DonorConnect
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DataDonorConnectActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataDonorConnectAdapter

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var database: FirebaseDatabase
    private lateinit var dataDonorConnectRef: DatabaseReference

    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donor_connect_beta)

        // recycler view
//        val recyclerView = findViewById<RecyclerView>(R.id.rvListDataDonorConnect)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.setHasFixedSize(true)
        recyclerView = findViewById(R.id.rvListDataDonorConnect)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = DataDonorConnectAdapter()
        recyclerView.adapter = adapter

        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!
        database = FirebaseDatabase.getInstance()
        dataDonorConnectRef = database.reference.child("donorConnect")

        // Ambil data dari Firebase
        dataDonorConnectRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataDCList = mutableListOf<DonorConnect>()
                for (donorConnectSnapshot in dataSnapshot.children) {
                    val DonorConnect: DonorConnect? = donorConnectSnapshot.getValue(DonorConnect::class.java)
                    DonorConnect?.let {
                        dataDCList.add(DonorConnect)
                    }
                }
                adapter.setData(dataDCList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error jika terjadi
            }
        })

        backButton = findViewById(R.id.back_button)

        backButton.setOnClickListener {
            finish()
        }


    }

    companion object {
        val INTENT_PARCELABLE = "OBJECT_INTENT"

    }

}