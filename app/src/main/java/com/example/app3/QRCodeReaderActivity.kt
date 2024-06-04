package com.example.app3

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class QRCodeReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_reader)

        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR code")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val qrCodeData = result.contents
                verifyParticipant(qrCodeData)
            } else {
                Toast.makeText(this, "Leitura de QR code cancelada", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun verifyParticipant(qrCodeData: String) {
        val database = FirebaseDatabase.getInstance().reference
        val eventId = qrCodeData.split("Event ID: ")[1]
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        val sanitizedEmail = userEmail?.replace(".", ",")
        val participantRef = database.child("event_participants").child(eventId).child(sanitizedEmail ?: "")

        participantRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // O participante está cadastrado no evento, então pode ser autenticado
                    participantRef.child("status").setValue("autenticado")
                        .addOnSuccessListener {
                            Toast.makeText(this@QRCodeReaderActivity, "Usuário autenticado!", Toast.LENGTH_LONG).show()
                            val participantEmail = snapshot.child("email").getValue(String::class.java)
                            val status = snapshot.child("status").getValue(String::class.java)
                            findViewById<TextView>(R.id.textViewParticipantEmail).text = "Email: $participantEmail"
                            findViewById<TextView>(R.id.textViewStatus).text = "Status: $status"
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this@QRCodeReaderActivity, "Erro ao atualizar status: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                } else {
                    // O participante não está cadastrado no evento, exibe mensagem de erro
                    Toast.makeText(this@QRCodeReaderActivity, "Usuário não cadastrado neste evento.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@QRCodeReaderActivity, "Erro ao acessar o banco de dados: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
