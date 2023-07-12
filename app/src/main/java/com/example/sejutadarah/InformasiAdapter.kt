package com.example.sejutadarah

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.Database.article
import com.google.firebase.database.*
import java.util.Base64


class InformasiAdapter : RecyclerView.Adapter<InformasiAdapter.InformasiViewHolder>() {

    private var onItemClickListener: ((article) -> Unit)? = null

    private val articleList = mutableListOf<article>()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformasiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.informasi_view, parent, false)
        return InformasiViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: InformasiViewHolder, position: Int) {
        val article = articleList[position]
        holder.bindItem(article)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(article)
        }

    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun setData(data: List<article>) {
        articleList.clear()
        articleList.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (article) -> Unit) {
        onItemClickListener = listener
    }


    inner class InformasiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageProfile: ImageView = itemView.findViewById(R.id.informasi_image)
        private val judul: TextView = itemView.findViewById(R.id.judul_informasi)
        private val deskripsi: TextView = itemView.findViewById(R.id.isi_informasi)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindItem(article: article) {
            val imageUrl = article.foto // URL gambar dari artikel di Firebase Realtime Database

//            Picasso.get()
//                .load(imageUrl)
//                .placeholder(R.drawable.ic_user) // Placeholder gambar default
//                .into(imageProfile)

            val encodeByte = Base64.getDecoder().decode(article.foto)
            val decodedByte: Bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            imageProfile.setImageBitmap(decodedByte)

            judul.text = article.judul
            judul.maxLines = 2
            judul.ellipsize = TextUtils.TruncateAt.END

            deskripsi.text = article.deskripsi
            deskripsi.maxLines = 6 // Mengatur maksimum 2 baris untuk deskripsi
            deskripsi.ellipsize = TextUtils.TruncateAt.END // Menambahkan ellipsis jika teks terpotong
        }


    }
}

