package com.example.app3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val textViewName = findViewById<TextView>(R.id.textViewName)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val buttonParticipants = findViewById<Button>(R.id.buttonParticipants)

        // Assume event details are passed via intent
        val eventId = intent.getStringExtra("EVENT_ID")
        val eventName = intent.getStringExtra("EVENT_NAME")
        val eventDescription = intent.getStringExtra("EVENT_DESCRIPTION")
        val eventDate = intent.getStringExtra("EVENT_DATE")

        textViewName.text = eventName
        textViewDescription.text = eventDescription
        textViewDate.text = eventDate

        buttonParticipants.setOnClickListener {
            val intent = Intent(this, ParticipantsActivity::class.java)
            intent.putExtra("EVENT_ID", eventId)
            startActivity(intent)
        }
    }
}
