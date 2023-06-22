package com.example.sejutadarah.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sejutadarah.DummyMapsActivity
import com.example.sejutadarah.HomeActivity
import com.example.sejutadarah.R

class DonorConnectFragment : Fragment() {

    private lateinit var dummyMap: CardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_donor_connect, container, false)

        dummyMap = view.findViewById(R.id.cvUntukSaya)

        dummyMap.setOnClickListener {
            val intent = Intent(activity, DummyMapsActivity::class.java)
            activity?.startActivity(intent)
        }

        return view
    }

}
