package com.example.sejutadarah

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RiwayatDonorAdapter(private val dataRiwayat: List<String>) : RecyclerView.Adapter<RiwayatDonorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.riwayat_donor_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataRiwayat[position]
        holder.riwayatTanggal.text = item
        holder.riwayatTempat.text = item
        holder.riwayatGambar.setImageResource(R.drawable.ic_calender)
    }

    override fun getItemCount(): Int {
        return dataRiwayat.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val riwayatTanggal: TextView = itemView.findViewById(R.id.riwayat_tanggal_donor)
        val riwayatTempat : TextView = itemView.findViewById(R.id.riwayat_tempat)
        val riwayatGambar : ImageView = itemView.findViewById(R.id.riwayat_image)

    }

}
