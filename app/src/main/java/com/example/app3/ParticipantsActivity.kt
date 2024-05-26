package com.example.app3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ParticipantsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var participantAdapter: ParticipantAdapter
    private lateinit var participantList: MutableList<User>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participants)

        recyclerView = findViewById(R.id.recyclerViewParticipants)
        recyclerView.layoutManager = LinearLayoutManager(this)

        participantList = mutableListOf()
        participantAdapter = ParticipantAdapter(participantList)
        recyclerView.adapter = participantAdapter

        database = FirebaseDatabase.getInstance().reference.child("event_participants")

        fetchParticipants()
    }

    private fun fetchParticipants() {
        val eventId = intent.getStringExtra("EVENT_ID")
        if (eventId != null) {
            database.child(eventId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    participantList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val user = dataSnapshot.getValue(User::class.java)
                        if (user != null) {
                            participantList.add(user)
                        }
                    }
                    participantAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle possible errors.
                }
            })
        }
    }
}
