package com.example.sejutadarah.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sejutadarah.Database.InformasiClass
import com.example.sejutadarah.Database.userSejutaDarah
import com.example.sejutadarah.InformasiAdapter
import com.example.sejutadarah.InformasiDetail
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

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InformasiAdapter
    private lateinit var infoList: List<InformasiClass>


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

        // Inisialisasi komponen recycle view
        recyclerView = view.findViewById(R.id.rvInformasi)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val data = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        adapter = InformasiAdapter(data)
        recyclerView.adapter = adapter


        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!
        database = FirebaseDatabase.getInstance()
        userRef = database.reference.child("userSejutaDarah")

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
                            break
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error jika terjadi
            }
        })



        return view
    }

    companion object {
        val INTENT_PARCELABLE = "OBJECT_INTENT"

    }

//    private fun dataInitialize() {
//        infoList = ListOf<InformasiClass>(
//            InformasiClass(
//                infoImg = ,
//                infoJudul = "Judul",
//
//
//
//            )
//        )



}
