package com.example.sejutadarah

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.Database.article
import com.example.sejutadarah.Database.riwayatDonor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso


class RiwayatDonorAdapter: RecyclerView.Adapter<RiwayatDonorAdapter.RiwayatDonorViewHolder>() {

    private val dataRiwayat = mutableListOf<riwayatDonor>()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatDonorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.riwayat_donor_view, parent, false)
        return RiwayatDonorViewHolder(view)
    }


    override fun onBindViewHolder(holder: RiwayatDonorViewHolder, position: Int) {
        val riwayatDonor = dataRiwayat[position]
        holder.bindItem(riwayatDonor)
//        holder.riwayatTanggal.text = item
//        holder.riwayatTempat.text = item
//        holder.riwayatGambar.setImageResource(R.drawable.ic_calender)
    }

    override fun getItemCount(): Int {
        return dataRiwayat.size
    }

    fun setData(data: List<riwayatDonor>) {
        dataRiwayat.clear()
        dataRiwayat.addAll(data)
        notifyDataSetChanged()
    }

    inner class RiwayatDonorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val riwayatGambar: ImageView = itemView.findViewById(R.id.riwayat_image)
        private val riwayatTanggal: TextView = itemView.findViewById(R.id.riwayat_tanggal_donor)
        private val riwayatTempat : TextView = itemView.findViewById(R.id.riwayat_tempat)

        fun bindItem(riwayatDonor : riwayatDonor) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid

            val imageUrl = riwayatDonor.golonganDarah // URL gambar dari artikel di Firebase Realtime Database
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_calender) // Placeholder gambar default
                .into(riwayatGambar)


            riwayatTanggal.text = riwayatDonor.tanggalDonor

            riwayatTempat.text = riwayatDonor.lokasiDonor
        }

    }

}
