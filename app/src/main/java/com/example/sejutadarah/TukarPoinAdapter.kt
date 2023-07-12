package com.example.sejutadarah

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.Database.TukarPoinClass
import com.example.sejutadarah.Database.article
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class TukarPoinAdapter (private val context: Context, private val poinHadiah: List<TukarPoinClass>, val listener: (TukarPoinClass) -> Unit)
    : RecyclerView.Adapter<TukarPoinAdapter.TukarPoinViewHolder>(){
    class TukarPoinViewHolder(view: View): RecyclerView.ViewHolder(view) {

//        private val namaHadiah: TextView = itemView.findViewById(R.id.nama_item)
//        private val hargaHadiah: TextView = itemView.findViewById(R.id.harga_item)
//        private val hargaPoin: TextView = itemView.findViewById(R.id.harga_poin)

        val namaHadiah = view.findViewById<TextView>(R.id.nama_item)
        val hargaHadiah = view.findViewById<TextView>(R.id.harga_item)
        val hargaPoin = view.findViewById<TextView>(R.id.harga_poin)

        fun bindView(TukarPoin: TukarPoinClass, listener: (TukarPoinClass) -> Unit){

            namaHadiah.text = TukarPoin.namaHadiah
            hargaHadiah.text = TukarPoin.hargaHadiah
            hargaPoin.text = TukarPoin.hargaPoin





            itemView.setOnClickListener{
                (listener(TukarPoin))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TukarPoinViewHolder {
        return TukarPoinViewHolder(
            LayoutInflater.from(context).inflate(R.layout.tukar_poin_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TukarPoinViewHolder, position: Int) {
        holder.bindView(poinHadiah[position], listener)
    }

    override fun getItemCount(): Int = poinHadiah.size

}

// ==========================================================

//class TukarPoinAdapter: RecyclerView.Adapter<TukarPoinAdapter.TukarPoinViewHolder>() {
//
//    private val dataHadiah = mutableListOf<TukarPoinClass>()
//    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TukarPoinAdapter.TukarPoinViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.tukar_poin_view, parent, false)
//        return TukarPoinViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: TukarPoinAdapter.TukarPoinViewHolder, position: Int) {
//        val hadiahPoin = dataHadiah[position]
//        holder.bindItem(hadiahPoin)
//    }
//
//    override fun getItemCount(): Int {
//        return dataHadiah.size
//    }
//
//    fun setData(data: List<TukarPoinClass>) {
//        dataHadiah.clear()
//        dataHadiah.addAll(data)
//        notifyDataSetChanged()
//    }
//
//    inner class TukarPoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        private val namaHadiah: TextView = itemView.findViewById(R.id.nama_item)
//        private val hargaHadiah: TextView = itemView.findViewById(R.id.harga_item)
//        private val hargaPoin: TextView = itemView.findViewById(R.id.harga_poin)
//
//        fun bindItem(TukarPoinClass : TukarPoinClass) {
//
//            namaHadiah.text = TukarPoinClass.namaHadiah
//            hargaHadiah.text = TukarPoinClass.hargaHadiah
//            hargaPoin.text = TukarPoinClass.hargaPoin
//
//        }
//
//    }
//
//}


