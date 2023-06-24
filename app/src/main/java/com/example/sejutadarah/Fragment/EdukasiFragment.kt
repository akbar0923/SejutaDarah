package com.example.sejutadarah.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sejutadarah.Database.article
import com.example.sejutadarah.InformasiAdapter
import com.example.sejutadarah.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class EdukasiFragment : Fragment() {

    private lateinit var imageProfile: ImageView
    private lateinit var judul: TextView
    private lateinit var deskripsi: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var database: FirebaseDatabase
    private lateinit var articleRef: DatabaseReference

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InformasiAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edukasi, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewInformasi)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val data = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        adapter = InformasiAdapter(data)
        recyclerView.adapter = adapter

//        imageProfile = view.findViewById(R.id.informasi_image)
//        judul = view.findViewById(R.id.judul_informasi)
//        deskripsi = view.findViewById(R.id.isi_informasi)

        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!
        database = FirebaseDatabase.getInstance()
        articleRef = database.reference.child("Aritcle")

        // Ambil data dari Firebase
        articleRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (articleSnapshot in dataSnapshot.children) {
                    val article: article? = articleSnapshot.getValue(article::class.java)
                    if (article != null) {
                        // Menampilkan data ke komponen tampilan
                        article.let {
                            context?.let { context ->
                                Glide.with(context)
                                    .load(article.foto)
                                    .placeholder(R.drawable.ic_user) // Placeholder gambar default
                                    .into(imageProfile)
                            }
                            judul.text = article.judul
                            deskripsi.text = article.deskripsi
                        }
                        break
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error jika terjadi
            }
        })

        return view
    }
}
