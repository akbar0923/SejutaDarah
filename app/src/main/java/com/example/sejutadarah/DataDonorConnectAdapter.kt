package com.example.sejutadarah

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.Database.DonorConnect
import com.example.sejutadarah.Database.article
import com.example.sejutadarah.Database.riwayatDonor
import com.example.sejutadarah.Fragment.DonorConnectAdapter
import com.squareup.picasso.Picasso
import com.google.firebase.database.*

class DataDonorConnectAdapter : RecyclerView.Adapter<DataDonorConnectAdapter.ViewHolder>() {

//    private var onItemClickListener: ((article) -> Unit)? = null

//    private lateinit var onItemClickCallback: DonorConnectAdapter.OnItemClickCallback
    private val listConnect = mutableListOf<DonorConnect>()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_connect, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val donCon = listConnect[position]
        holder.bindItem(donCon)

//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(DonCon)
//        }

    }

    override fun getItemCount(): Int {
        return listConnect.size
    }

    fun setData(data: List<DonorConnect>) {
        listConnect.clear()
        listConnect.addAll(data)
        notifyDataSetChanged()
    }

//    fun setOnItemClickListener(listener: (DonorConnect) -> Unit) {
//        onItemClickListener = listener
//    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val foto: ImageView = itemView.findViewById(R.id.imageView_itemImage)
        private val nama: TextView = itemView.findViewById(R.id.textView_name)
        private val kontak: TextView = itemView.findViewById(R.id.textView_kontak)
        private val goldar: TextView = itemView.findViewById(R.id.textView_goldar)
        private val lokasi: TextView = itemView.findViewById(R.id.textView_lokasi)

        fun bindItem(DonorConnect: DonorConnect) {
            nama.text = DonorConnect.nama

            kontak.text = DonorConnect.kontak

            goldar.text = DonorConnect.goldar

            lokasi.text = DonorConnect.lokasi
        }

    }
}

