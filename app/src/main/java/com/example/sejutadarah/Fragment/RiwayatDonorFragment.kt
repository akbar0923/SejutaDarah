package com.example.sejutadarah.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.InformasiAdapter
import com.example.sejutadarah.R
import com.example.sejutadarah.RiwayatDonorAdapter

class RiwayatDonorFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RiwayatDonorAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_riwayat_donor, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewRiwayatDonor)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val data = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        adapter = RiwayatDonorAdapter(data)
        recyclerView.adapter = adapter


        return view
    }




}
