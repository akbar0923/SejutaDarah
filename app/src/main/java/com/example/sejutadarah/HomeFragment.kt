package com.example.sejutadarah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sejutadarah.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    private lateinit var imageProfile: ImageView
    private lateinit var textName: TextView
    private lateinit var textIdentity: TextView
    private lateinit var textBloodGroup: TextView
    private lateinit var textRewardPoints: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inisialisasi komponen tampilan
        imageProfile = view.findViewById(R.id.imageProfile)
        textName = view.findViewById(R.id.textName)
        textIdentity = view.findViewById(R.id.textIdentity)
        textBloodGroup = view.findViewById(R.id.textBloodGroup)
        textRewardPoints = view.findViewById(R.id.textRewardPoints)

        // Ambil data dari Firebase
        val userRef = FirebaseDatabase.getInstance().reference.child("users").child("user_id")
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Ambil nilai dari database
                val profileImageUrl = dataSnapshot.child("profileImageUrl").value.toString()
                val name = dataSnapshot.child("name").value.toString()
                val identity = dataSnapshot.child("identity").value.toString()
                val bloodGroup = dataSnapshot.child("bloodGroup").value.toString()
                val rewardPoints = dataSnapshot.child("rewardPoints").value.toString()

                // Set nilai ke komponen tampilan
                context?.let {
                    Glide.with(it)
                        .load(profileImageUrl)
                        .into(imageProfile)
                }
                textName.text = name
                textIdentity.text = identity
                textBloodGroup.text = bloodGroup
                textRewardPoints.text = "Reward Poin: $rewardPoints"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error jika terjadi
            }
        })

        return view
    }
}
