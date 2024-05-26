package com.example.app3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class EventsActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerViewEvents: RecyclerView
    private lateinit var eventsList: MutableList<Event>
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        recyclerViewEvents = findViewById(R.id.recyclerViewEvents)
        recyclerViewEvents.layoutManager = GridLayoutManager(this, 2)
        eventsList = mutableListOf()

        eventAdapter = EventAdapter(this, eventsList)
        recyclerViewEvents.adapter = eventAdapter

        database = FirebaseDatabase.getInstance().getReference("events")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventsList.clear()
                for (eventSnapshot in snapshot.children) {
                    val event = eventSnapshot.getValue(Event::class.java)
                    if (event != null) {
                        eventsList.add(event)
                    }
                }
                eventAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}
