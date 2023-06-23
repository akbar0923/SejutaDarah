package com.example.sejutadarah

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.Database.InformasiClass

class InformasiAdapter(private val data: List<String>) : RecyclerView.Adapter<InformasiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.informasi_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.infoJudul.text = item
        holder.infoSumber.text = item
        holder.infoIsi.text = item
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val infoJudul: TextView = itemView.findViewById(R.id.judul_informasi)
        val infoSumber : TextView = itemView.findViewById(R.id.sumber_informasi)
        val infoIsi : TextView = itemView.findViewById(R.id.isi_informasi)

    }

}
//    class InformasiViewHolder (view: View) : RecyclerView.ViewHolder(view) {
//        val infoImg = view.findViewById<ImageView>(R.id.informasi_image)
//        val infoJudul = view.findViewById<TextView>(R.id.judul_informasi)
//        val infoSumber = view.findViewById<TextView>(R.id.sumber_informasi)
//        val infoIsi = view.findViewById<TextView>(R.id.isi_informasi)
//
//        fun bindView(informasi: InformasiClass, listener: (InformasiClass) -> Unit){
//
//            infoImg.setImageResource(informasi.infoImg)
//            infoJudul.text = informasi.infoJudul
//            infoSumber.text = informasi.infoSumber
//            infoIsi.text = informasi.infoIsi
//            itemView.setOnClickListener{
//                (listener(informasi))
//            }
//        }



//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformasiViewHolder {
//        return InformasiViewHolder(
//            LayoutInflater.from(context).inflate(R.layout.informasi_view, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: InformasiViewHolder, position: Int) {
//        holder.bindView(informasi[position], listener)
//    }
//
//    override fun getItemCount(): Int = informasi.size




