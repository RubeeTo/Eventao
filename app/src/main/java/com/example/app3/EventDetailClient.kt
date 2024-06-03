package com.example.app3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EventDetailClient : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail_client)

        val textViewName = findViewById<TextView>(R.id.textViewName)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val buttonParticipants = findViewById<Button>(R.id.buttonParticipants)
        val buttonEditEvent = findViewById<Button>(R.id.buttonEditEvent)
        val buttonSubscribe = findViewById<Button>(R.id.buttonSubscribe)
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

        val eventId = intent.getStringExtra("EVENT_ID")
        val eventName = intent.getStringExtra("EVENT_NAME")
        val eventDescription = intent.getStringExtra("EVENT_DESCRIPTION")
        val eventDate = intent.getStringExtra("EVENT_DATE")

        Log.d("EventDetailClient", "Received eventId: $eventId")
        Log.d("EventDetailClient", "Received eventName: $eventName")
        Log.d("EventDetailClient", "Received eventDescription: $eventDescription")
        Log.d("EventDetailClient", "Received eventDate: $eventDate")

        textViewName.text = eventName
        textViewDescription.text = eventDescription
        textViewDate.text = eventDate

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        buttonParticipants.setOnClickListener {
            val intent = Intent(this, ParticipantActivity::class.java)
            intent.putExtra("EVENT_ID", eventId)
            startActivity(intent)
        }

        buttonEditEvent.setOnClickListener {
            val intent = Intent(this, EditEvent::class.java)
            intent.putExtra("EVENT_ID", eventId)
            startActivity(intent)
        }

        buttonSubscribe.setOnClickListener {
            Log.d("EventDetailClient", "Button Subscribe clicked")
            val user = auth.currentUser
            if (user != null) {
                Log.d("EventDetailClient", "User is not null: ${user.email}")
            } else {
                Log.d("EventDetailClient", "User is null")
            }
            if (eventId != null) {
                Log.d("EventDetailClient", "EventId is not null: $eventId")
            } else {
                Log.d("EventDetailClient", "EventId is null")
            }
            if (user != null && eventId != null) {
                Log.d("EventDetailClient", "User and eventId are not null")
                val userEmail = user.email
                if (userEmail != null) {
                    subscribeToEvent(eventId, userEmail)
                    val intent = Intent(this, EventDetailClientQRcode::class.java)
                    intent.putExtra("USER_EMAIL", userEmail)
                    intent.putExtra("EVENT_ID", eventId)
                    Log.d("EventDetailClient", "Starting QrCodeActivity with email: $userEmail")
                    startActivity(intent)
                } else {
                    Log.d("EventDetailClient", "User email is null")
                }
            } else {
                Log.d("EventDetailClient", "User or eventId is null")
            }
        }
    }

    private fun subscribeToEvent(eventId: String, userEmail: String) {
        val eventSubscription = hashMapOf(
            "eventId" to eventId,
            "userEmail" to userEmail
        )
        db.collection("subscriptions")
            .add(eventSubscription)
            .addOnSuccessListener { documentReference ->
                Log.d("EventDetailClient", "Subscription successfully written")
            }
            .addOnFailureListener { e ->
                Log.w("EventDetailClient", "Error adding document", e)
            }
    }
}
