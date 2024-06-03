package com.example.app3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class EditEvent : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        val editTextEventName: EditText = findViewById(R.id.editTextEventName)
        val editTextEventDescription: EditText = findViewById(R.id.editTextEventDescription)
        val editTextEventDate: EditText = findViewById(R.id.editTextEventDate)
        val editTextEventImageUrl: EditText = findViewById(R.id.editTextEventImageUrl)
        val buttonAddEvent: Button = findViewById(R.id.buttonAddEvent)

        database = FirebaseDatabase.getInstance().getReference("events")

        buttonAddEvent.setOnClickListener {
            val eventName = editTextEventName.text.toString()
            val eventDescription = editTextEventDescription.text.toString()
            val eventDate = editTextEventDate.text.toString()
            val eventImageUrl = editTextEventImageUrl.text.toString()

            if (eventName.isNotEmpty() && eventDescription.isNotEmpty() && eventDate.isNotEmpty() && eventImageUrl.isNotEmpty()) {
                getNextEventName { eventId ->
                    if (eventId != null) {
                        val event = Event(
                            id = eventId.hashCode(),
                            name = eventName,
                            description = eventDescription,
                            date = eventDate,
                            imageUrl = eventImageUrl
                        )

                        database.child(eventId).setValue(event).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Event added successfully", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this, "Failed to add event", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Failed to generate event ID", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getNextEventName(callback: (String?) -> Unit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var maxNumber = 0
                for (child in snapshot.children) {
                    val key = child.key
                    if (key != null && key.startsWith("event")) {
                        val number = key.removePrefix("event").toIntOrNull()
                        if (number != null && number > maxNumber) {
                            maxNumber = number
                        }
                    }
                }
                val nextNumber = maxNumber + 1
                callback("event$nextNumber")
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }
}
