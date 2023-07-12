package com.example.sejutadarah.Fragment

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sejutadarah.Database.article
import com.example.sejutadarah.Database.InformasiClass
import com.example.sejutadarah.Database.userSejutaDarah
import com.example.sejutadarah.InformasiAdapter
import com.example.sejutadarah.InformasiDetail
import com.example.sejutadarah.LoginActivity
import com.example.sejutadarah.R
import com.example.sejutadarah.RegisterActivity
import com.example.sejutadarah.TiketPaymentActivity
import com.example.sejutadarah.TukarPoinActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class HomeFragment : Fragment() {
    private lateinit var imageProfile: ImageView
    private lateinit var textName: TextView
    private lateinit var textIdentity: TextView
    private lateinit var golongandarah: TextView
    private lateinit var textRewardPoints: TextView

    private lateinit var btnTukarPoin: CardView
    private lateinit var btnTiketPayment: CardView
    private lateinit var btnRiwayatDonor: CardView


    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InformasiAdapter
    private lateinit var infoList: List<InformasiClass>
    private lateinit var articleRef: DatabaseReference



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
        textRewardPoints = view.findViewById(R.id.textRewardPoints)
        golongandarah = view.findViewById(R.id.darahuser)
        btnTukarPoin = view.findViewById(R.id.btn_tukarPoin)
        btnTiketPayment = view.findViewById(R.id.btn_tiketPayment)
        btnRiwayatDonor = view.findViewById(R.id.btn_riwayatDonor)


        // Inisialisasi komponen recycler view
        dataInitialize()
        recyclerView = view.findViewById(R.id.rvInformasi)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapter = InformasiAdapter()
        recyclerView.adapter = adapter

        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!
        database = FirebaseDatabase.getInstance()
        userRef = database.reference.child("userSejutaDarah")
        articleRef = database.reference.child("article")


        // Ambil data dari Firebase
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Ambil nilai dari database
                for (userSnapshot in dataSnapshot.children) {
                    val user: userSejutaDarah? = userSnapshot.getValue(userSejutaDarah::class.java)
                    if (user != null) {
                        if (user.userId == currentUser.uid) {
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
                                golongandarah.text = user.bloodGroup
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

//      return view

        // Ambil data dari Firebase
        articleRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val articleList = mutableListOf<article>()
                for (articleSnapshot in dataSnapshot.children) {
                    val article: article? = articleSnapshot.getValue(article::class.java)
                    article?.let {
                        articleList.add(article)
                    }
                }
                adapter.setData(articleList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error jika terjadi
            }
        })

        btnTukarPoin.setOnClickListener {
            val intent = Intent(context, TukarPoinActivity::class.java) // ganti tujuan tombol
            startActivity(intent)
        }

        btnTiketPayment.setOnClickListener {
            val intent = Intent(context, TiketPaymentActivity::class.java) // ganti tujuan tombol
            startActivity(intent)
        }

//        btnRiwayatDonor.setOnClickListener {
//            val riwayatDonorFragment = RiwayatDonorFragment()
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_rd_container, riwayatDonorFragment)
//                .addToBackStack(null)
//                .commit()
//        }

//        adapter.setOnItemClickListener { article ->
//            val intent = Intent(requireContext(), InformasiDetail::class.java)
//            intent.putExtra(EdukasiFragment.INTENT_PARCELABLE, article as Parcelable)
//            startActivity(intent)
//        }
        adapter.setOnItemClickCallback(object: InformasiAdapter.OnItemClickCallback{
            override fun onItemClicked(articleDetail: article) {
                val intent = Intent(requireActivity(), InformasiDetail::class.java)
                intent.putExtra(EXTRA_DATA, articleDetail)
                startActivity(intent)
            }
        })


        return view
    }

    private fun dataInitialize(){
        // Mengatur data informasi untuk ditampilkan pada adapter

        infoList = listOf<InformasiClass>(
            InformasiClass(
                    R.drawable.ic_user,
                    infoJudul = "Judul 1",
                    infoSumber = "adasadsada",
                    infoIsi = "Deskripsi 1"
                ),
            InformasiClass(
                        R.drawable.ic_home,
                        infoJudul = "Judul 2",
                        infoSumber = "adsadadasd",
                        infoIsi = "Deskripsi 2"
                    )

        )

        // ...
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
}
