package com.example.sejutadarah.Fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sejutadarah.Database.DonorConnect

import com.example.sejutadarah.R
import com.example.sejutadarah.databinding.ItemRowConnectBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.NonDisposableHandle.parent

class DonorConnectAdapter : RecyclerView.Adapter<DonorConnectAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listConnect: MutableList<DonorConnect> = mutableListOf()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun addItem(history: List<DonorConnect>) {
        listConnect.addAll(history)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowConnectBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            textViewName.text = listConnect[position].nama
            textViewKontak.text = listConnect[position].kontak
            textViewGoldar.text = listConnect[position].goldar
            textViewLokasi.text = listConnect[position].lokasi

        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listConnect[position])
        }
    }

    override fun getItemCount(): Int = listConnect.size

    class ViewHolder(val binding: ItemRowConnectBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(historyDetail: DonorConnect)
    }
}