// InformasiAdapter.kt
package com.example.sejutadarah

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sejutadarah.Database.article
import com.squareup.picasso.Picasso

class InformasiAdapter : RecyclerView.Adapter<InformasiAdapter.InformasiViewHolder>() {

    private val articleList = mutableListOf<article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformasiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.informasi_view, parent, false)
        return InformasiViewHolder(view)
    }

    override fun onBindViewHolder(holder: InformasiViewHolder, position: Int) {
        val article = articleList[position]
        holder.bindItem(article)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun setData(data: List<article>) {
        articleList.clear()
        articleList.addAll(data)
        notifyDataSetChanged()
    }

    inner class InformasiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageProfile: ImageView = itemView.findViewById(R.id.informasi_image)
        private val judul: TextView = itemView.findViewById(R.id.judul_informasi)
        private val deskripsi: TextView = itemView.findViewById(R.id.isi_informasi)

        fun bindItem(article: article) {
//            Glide.with(itemView.context)
//                .load(article.foto)
//                .placeholder(R.drawable.ic_user) // Placeholder gambar default
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(imageProfile)

            Picasso.get()
                .load(article.foto)
                .placeholder(R.drawable.logo) // Gambar placeholder
                .into(imageProfile)

            judul.text = article.judul
            judul.maxLines = 2
            judul.ellipsize = TextUtils.TruncateAt.END

            deskripsi.text = article.deskripsi
            deskripsi.maxLines = 6 // Mengatur maksimum 2 baris untuk deskripsi
            deskripsi.ellipsize = TextUtils.TruncateAt.END // Menambahkan ellipsis jika teks terpotong
        }
    }
}
