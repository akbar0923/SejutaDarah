package com.example.sejutadarah.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.Database.riwayatDonor
import com.example.sejutadarah.R
import com.example.sejutadarah.RiwayatDonorAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class RiwayatDonorFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RiwayatDonorAdapter

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var database: FirebaseDatabase
    private lateinit var riwayatDonorRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_riwayat_donor, container, false)

        // recycler view
        recyclerView = view.findViewById(R.id.recyclerViewRiwayatDonor)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = RiwayatDonorAdapter()
        recyclerView.adapter = adapter

        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!
        database = FirebaseDatabase.getInstance()
        riwayatDonorRef = database.reference.child("riwayatDonor")

        // Ambil data dari Firebase
        riwayatDonorRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val riwayatDonorList = mutableListOf<riwayatDonor>()

                for (riwayatDonorSnapshot in dataSnapshot.children) {
                    val riwayatDonor = riwayatDonorSnapshot.getValue(riwayatDonor::class.java)
                    riwayatDonor?.let {
                        if (it.userId == currentUser.uid) {
                            riwayatDonorList.add(it)
                        }
                    }
                }

                adapter.setData(riwayatDonorList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error jika terjadi
            }
        })


        return view
    }
}
