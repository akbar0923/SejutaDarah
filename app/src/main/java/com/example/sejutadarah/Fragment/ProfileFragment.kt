package com.example.sejutadarah.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sejutadarah.Database.userSejutaDarah
import com.example.sejutadarah.R
import com.example.sejutadarah.TukarPoinActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfileFragment : Fragment() {
    private lateinit var imageProfile: ImageView
    private lateinit var textName: TextView
    private lateinit var emailName: TextView
    private lateinit var golongandarah: TextView
    private lateinit var nik: TextView
    private lateinit var noHP: TextView
    private lateinit var gender: TextView

    private lateinit var btnTukarPoin: CardView

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Inisialisasi komponen tampilan
        imageProfile = view.findViewById(R.id.foto_profile)
        textName = view.findViewById(R.id.nama_user)
        emailName = view.findViewById(R.id.email_user)
        golongandarah = view.findViewById(R.id.tvSimbolGoldar)
        nik = view.findViewById(R.id.nik_user)
        noHP = view.findViewById(R.id.no_hp)
        gender = view.findViewById(R.id.tvSimbolGender)

        btnTukarPoin = view.findViewById(R.id.btn_tukarPoin)


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
                                emailName.text = user.email // Mengganti email dengan email pengguna
                                golongandarah.text = user.bloodGroup // Mengganti goldar dengan goldar pengguna
                                nik.text = user.nik // Mengganti nik dengan nik pengguna
                                noHP.text = user.noHP
                                gender.text = user.gender

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

        btnTukarPoin.setOnClickListener {
            val intent = Intent(context, TukarPoinActivity::class.java) // ganti tujuan tombol
            startActivity(intent)
        }

        return view
    }
}
