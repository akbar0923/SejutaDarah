// EdukasiFragment.kt
package com.example.sejutadarah.Fragment

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.Database.article
import com.example.sejutadarah.InformasiAdapter
import com.example.sejutadarah.InformasiDetail
import com.example.sejutadarah.R
import com.example.sejutadarah.TiketPaymentActivity
import com.example.sejutadarah.TukarPoinActivity
import com.example.sejutadarah.TukarPoinAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class EdukasiFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InformasiAdapter

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var database: FirebaseDatabase
    private lateinit var articleRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edukasi, container, false)

        // recycler view
        recyclerView = view.findViewById(R.id.recyclerViewInformasi)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = InformasiAdapter()
        recyclerView.adapter = adapter

        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!
        database = FirebaseDatabase.getInstance()
        articleRef = database.reference.child("article")

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

        adapter.setOnItemClickListener { article ->
            val intent = Intent(requireContext(), InformasiDetail::class.java)
            intent.putExtra(INTENT_PARCELABLE, article as Parcelable)
            startActivity(intent)
        }

        return view
    }

    companion object {
        val INTENT_PARCELABLE = "OBJECT_INTENT"
//        private const val TAG = "EdukasiFragment"
    }

}
