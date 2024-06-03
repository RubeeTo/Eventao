package com.example.app3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class EventDetailClient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail_client)

        val textViewName = findViewById<TextView>(R.id.textViewName)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val buttonParticipants = findViewById<Button>(R.id.buttonParticipants)
        val buttonEditEvent = findViewById<Button>(R.id.buttonEditEvent)
        val toolbar = findViewById<Toolbar>(R.id.header)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.back_arrow)
        }

        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, LoginClient::class.java)
            startActivity(intent)
            finish()
        }

        fun onBackPressed() {
            super.onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
//
//        // Assume event details are passed via intent
//        val eventId = intent.getStringExtra("EVENT_ID")
//        val eventName = intent.getStringExtra("EVENT_NAME")
//        val eventDescription = intent.getStringExtra("EVENT_DESCRIPTION")
//        val eventDate = intent.getStringExtra("EVENT_DATE")
//
//        textViewName.text = eventName
//        textViewDescription.text = eventDescription
//        textViewDate.text = eventDate
//
//        buttonParticipants.setOnClickListener {
//            val intent = Intent(this, ParticipantActivity::class.java)
//            intent.putExtra("EVENT_ID", eventId)
//            startActivity(intent)
//        }

//        buttonEditEvent.setOnClickListener {
//            val intent = Intent(this, EditEventActivity::class.java)
//            intent.putExtra("EVENT_ID", eventId)
//            startActivity(intent)
//        }



    }
}
