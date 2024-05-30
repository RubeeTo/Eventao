package com.example.app3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import com.example.app3.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainAdmin : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationAdmin)

        bottomNavigationView.setOnItemReselectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.btnHome -> {
                    replaceFragment(HomeAdmin())
                    true
                }
                R.id.btnAddEvent -> {
                    replaceFragment(AddEvent())
                    true
                }
                else -> false
            }
        }

        replaceFragment(HomeAdmin())
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frameLayoutNavigationAdmin, fragment).commit()
    }
}