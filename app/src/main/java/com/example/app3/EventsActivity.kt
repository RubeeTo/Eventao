package com.example.app3

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class EventsActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerViewEvents: RecyclerView
    private lateinit var eventsList: MutableList<Event>
    private lateinit var eventAdapter: EventAdapter

    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)

        bottomNavigationView = findViewById(R.id.bottomNavigationAdmin)



        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
//                R.id.btnHome -> {
//                    replaceFragment(HomeAdmin())
//                    true
//                }
//                R.id.btnAddEvent -> {
//                    replaceFragment(CreateEventAdmin())
//                    true
//                }
                else -> false
            }
        }

        // Define o fragmento inicial
        if (savedInstanceState == null) {
            replaceFragment(HomeAdmin())
        }


//        recyclerViewEvents = findViewById(R.id.recyclerViewEvents)
//        recyclerViewEvents.layoutManager = GridLayoutManager(this, 2)
//        eventsList = mutableListOf()
//
//        eventAdapter = EventAdapter(this, eventsList)
//        recyclerViewEvents.adapter = eventAdapter
//
//        database = FirebaseDatabase.getInstance().getReference("events")
//
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                eventsList.clear()
//                for (eventSnapshot in snapshot.children) {
//                    val event = eventSnapshot.getValue(Event::class.java)
//                    if (event != null) {
//                        eventsList.add(event)
//                    }
//                }
//                eventAdapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle database error
//            }
//        })

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutNavigationAdmin, fragment)
            .commit()
    }


}
