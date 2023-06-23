package com.example.sejutadarah.Fragment

import android.R.attr.duration
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sejutadarah.Database.DonorConnect
import com.example.sejutadarah.Database.NotificationMessage
import com.example.sejutadarah.Database.Token
import com.example.sejutadarah.DonorConnectActivity
import com.example.sejutadarah.DummyMapsActivity
import com.example.sejutadarah.R
import com.example.sejutadarah.Service.FCMSender
import com.example.sejutadarah.databinding.FragmentDonorConnectBinding
import com.google.android.material.color.utilities.Score.score
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class DonorConnectFragment : Fragment() {

    private var _binding: FragmentDonorConnectBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter = DonorConnectAdapter()
    private lateinit var database: DatabaseReference
    private var key: String? = null
    private var isLastPage = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDonorConnectBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvUntukSaya.setOnClickListener {
            val intent = Intent(activity, DummyMapsActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.cvUntukOrang.setOnClickListener {
            val intent = Intent(activity, DonorConnectActivity::class.java)
            activity?.startActivity(intent)


//            val message = NotificationMessage(
//                "cfS1orKHQAeJcvR6QtMP6L:APA91bHc3Kh5ZSl0NFVvec9348gN21d_ZSmNTnUXHd0QKfkiqsXpjuCMMCVviOJvcY7ZUue4m_pTBHQoa6rbYsgH3OpYhvOvIfiq050AntCmYuWzKg27nbehUCd-3K-eWPfE4siLDytC",
//                "tes"
//            ).message
//
//            FCMSender().send(message, object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    activity?.runOnUiThread {
//                        Toast.makeText(activity, "Kirim gagal", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    activity?.runOnUiThread {
//                        Toast.makeText(activity, response.body.toString(), Toast.LENGTH_SHORT).show()
//                    }
//                }
//            })
        }

        binding.rvDonorConnect.layoutManager = LinearLayoutManager(context)
        binding.rvDonorConnect.adapter = adapter
        binding.rvDonorConnect.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = (binding.rvDonorConnect.layoutManager as LinearLayoutManager).childCount
                Log.w(TAG, "1: $visibleItemCount")
                val totalItemCount = (binding.rvDonorConnect.layoutManager as LinearLayoutManager).itemCount
                Log.w(TAG, "2: $totalItemCount")
                val firstVisibleItemPosition = (binding.rvDonorConnect.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                Log.w(TAG, "3: $firstVisibleItemPosition")

//                if(!isLastPage)
//                {
//                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
//                        Log.w(TAG, "TESSSSSSSSSS")
//                        loadData()
//                    }
//                }
            }
        })
        loadData()
    }

    private fun loadData() {
        get(key).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val donorConnectList: MutableList<DonorConnect> = mutableListOf()
                for (donorConnectSnapshot in dataSnapshot.children) {
                    val donorConnect: DonorConnect? = donorConnectSnapshot.getValue(DonorConnect::class.java)
                    if (donorConnect != null) {
                        donorConnectList.add(donorConnect)
                    } else {
                        isLastPage = true
                        break
                    }
                    key = donorConnectSnapshot.key
                }

                adapter.addItem(donorConnectList)
                binding.rvDonorConnect.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    private fun get(key: String?) : Query
    {
        database = Firebase.database.reference

//        if(key == null){
//            return database.child("donorConnect").orderByKey().limitToFirst(5)
//        }

        return database.child("donorConnect")
    }

    companion object {
        private const val TAG = "DonorConnectFragment"
    }
}
