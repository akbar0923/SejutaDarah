package com.example.sejutadarah

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sejutadarah.Database.article
import com.example.sejutadarah.Fragment.EdukasiFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class InformasiDetail : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var adapter: InformasiAdapter

    private lateinit var database: FirebaseDatabase
    private lateinit var articleRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_informasi)

        adapter = InformasiAdapter()

        // Inisialisasi Firebase
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

                // Ambil artikel pertama dari daftar (misalnya, indeks 0)
                val firstArticle = articleList.getOrNull(0)
                firstArticle?.let { article ->
                    val infoImg = findViewById<ImageView>(R.id.info_image)
                    val infoTitle = findViewById<TextView>(R.id.info_title)
                    val infoDesc = findViewById<TextView>(R.id.info_description)

                    // Setel gambar, judul, dan deskripsi ke tampilan
                    // Menggunakan Picasso untuk memuat dan menampilkan gambar
                    Picasso.get()
                        .load(article.foto)
                        .placeholder(R.drawable.logo_apps) // Placeholder gambar default
                        .into(infoImg)

                    infoTitle.text = article.judul
                    infoDesc.text = article.deskripsi
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error jika terjadi
            }
        })

//         aksi back button
        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//
//        onBackPressed()
//        return true
//    }

}

private fun ImageView.setImageResource(foto: String) {

}
