package com.example.app3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class CreateEventAdmin : Fragment() {

    private lateinit var editTextName: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextLocation: EditText
    private lateinit var editTextDate: EditText
    private lateinit var imageViewEvent: ImageView
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_event_admin, container, false)
    }

//        editTextName = findViewById(R.id.editTextName)
//        editTextDescription = findViewById(R.id.editTextDescription)
//        editTextLocation = findViewById(R.id.editTextLocalName)
//        editTextDate = findViewById(R.id.editTextDate)
//        imageViewEvent = findViewById(R.id.imageViewEvent)
//        buttonSave = findViewById(R.id.buttonSave)
//
//        val calendar = Calendar.getInstance()
//        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//            editTextDate.setText("$dayOfMonth/${month + 1}/$year")
//        }
//
//        editTextDate.setOnClickListener {
//            DatePickerDialog(
//                this, datePicker, calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
//            ).show()
//        }
//
//        buttonSave.setOnClickListener {
//            val name = editTextName.text.toString()
//            val description = editTextDescription.text.toString()
//            val location = editTextLocation.text.toString()
//            val date = editTextDate.text.toString()
//            val imageUrl = "" // URL da imagem, se disponível
//
//            if (name.isNotEmpty() && description.isNotEmpty() && location.isNotEmpty() && date.isNotEmpty()) {
//                val database = FirebaseDatabase.getInstance().reference
//                val eventId = database.child("events").push().key ?: return@setOnClickListener
//                val event = Event(eventId.toInt(), name, description, date, imageUrl)
//                database.child("events").child(eventId).setValue(event)
//                    .addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            Toast.makeText(this, "Evento criado com sucesso.", Toast.LENGTH_SHORT).show()
//                            finish()
//                        } else {
//                            Toast.makeText(this, "Erro ao criar evento.", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//            } else {
//                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
//            }
//        }











}
