package com.example.sejutadarah

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
                else -> false
            }
        }

        // Set the default fragment
        switchFragment(HomeFragment())
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContent, fragment)
            .commit()
    }
}
