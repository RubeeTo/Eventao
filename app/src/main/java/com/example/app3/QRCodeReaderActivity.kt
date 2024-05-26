// QRCodeReaderActivity.kt
package com.example.app3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class QRCodeReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code_reader)

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
        database.child("event_registrations").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var found = false
                for (eventSnapshot in snapshot.children) {
                    for (participantSnapshot in eventSnapshot.children) {
                        if (participantSnapshot.child("cpf").getValue(String::class.java) == qrCodeData) {
                            found = true
                            break
                        }
                    }
                    if (found) break
                }
                if (found) {
                    Toast.makeText(this@QRCodeReaderActivity, "Participante encontrado!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@QRCodeReaderActivity, "Participante não encontrado.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@QRCodeReaderActivity, "Erro ao acessar o banco de dados.", Toast.LENGTH_LONG).show()
            }
        })
    }
}
