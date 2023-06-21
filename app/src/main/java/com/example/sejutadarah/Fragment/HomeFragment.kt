package com.example.sejutadarah.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sejutadarah.Database.userSejutaDarah
import com.example.sejutadarah.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class HomeFragment : Fragment() {
    private lateinit var imageProfile: ImageView
    private lateinit var textName: TextView
    private lateinit var textIdentity: TextView
    private lateinit var textBloodGroup: TextView
    private lateinit var textRewardPoints: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference

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

        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!
        database = FirebaseDatabase.getInstance()
        userRef = database.reference.child("users").child(currentUser.uid)

        // Ambil data dari Firebase
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Ambil nilai dari database
                val user = dataSnapshot.getValue(userSejutaDarah::class.java)

                // Set nilai ke komponen tampilan
                user?.let {
                    context?.let { context ->
                        Glide.with(context)
                            .load(user.profileImageUrl)
                            .placeholder(R.drawable.ic_user) // Foto profil default
                            .into(imageProfile)
                    }
                    textName.text = user.fullName // Mengganti teks dengan nama pengguna
                    textIdentity.text = user.identity
                    textBloodGroup.text = user.bloodGroup
                    textRewardPoints.text = "Reward Poin: ${user.rewardPoints}"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error jika terjadi
            }
        })

        return view
    }
}
