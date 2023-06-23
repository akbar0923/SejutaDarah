package com.example.sejutadarah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sejutadarah.Fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.RecyclerView


class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val button = findViewById<FloatingActionButton>(R.id.fabBottom)
        button.setOnClickListener {
            switchFragment(DonorConnectFragment())
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    switchFragment(HomeFragment())
                    true
                }
                R.id.menu_edukasi -> {
                    switchFragment(EdukasiFragment())
                    true
                }
                R.id.fabBottom -> {
                    switchFragment(DonorConnectFragment())
                    true
                }
                R.id.menu_riwayat_donor -> {
                    switchFragment(RiwayatDonorFragment())
                    true
                }
                R.id.menu_profile -> {
                    switchFragment(ProfileFragment())
                    true
                }
                R.id.menu_kosong -> {
                    // Tambahkan logika yang sesuai dengan menu yang kosong
                    true
                }
                else -> false
            }
        }

        // Set the default fragment
        switchFragment(HomeFragment())

        // Mengaktifkan atau menonaktifkan menu kosong
        val menuKosong = bottomNavigationView.menu.findItem(R.id.menu_kosong)
        menuKosong.isEnabled = false // Ubah ke true untuk mengaktifkan, false untuk menonaktifkan
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContent, fragment)
            .commit()
    }
}
