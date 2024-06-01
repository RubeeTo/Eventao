package com.example.app3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainClient : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_client)

        bottomNavigationView = findViewById(R.id.bottomNavigationClient)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.btnHome -> {
                    replaceFragment(HomeClient())
                    true
                }
                R.id.btnEventList -> {
                    replaceFragment(EventListClient())
                    true
                }
                R.id.btnProfile -> {
                    replaceFragment(ProfileClient())
                    true
                }
                else -> false
            }
        }

        // Define o fragmento inicial
        if (savedInstanceState == null) {
            replaceFragment(HomeClient())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutNavigationClient, fragment)
            .commit()
    }
}
