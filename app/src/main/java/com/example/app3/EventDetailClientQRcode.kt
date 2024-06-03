package com.example.app3

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix

class EventDetailClientQRcode : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var surfaceView: SurfaceView
    private lateinit var holder: SurfaceHolder
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private var eventId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        surfaceView = SurfaceView(this)
        setContentView(surfaceView)

        holder = surfaceView.holder
        holder.addCallback(this)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        eventId = intent.getStringExtra("EVENT_ID")
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Não é necessário chamar drawQRCodeAndText() aqui, pois isso será feito
        // quando o código QR for gerado.
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    override fun surfaceCreated(holder: SurfaceHolder) {
        drawQRCode()
    }

    private fun drawQRCode() {
        val userEmail = auth.currentUser?.email
        val eventId = eventId // Armazena eventId em uma variável local imutável

        if (userEmail != null && eventId != null) {
            try {
                val text = "User: $userEmail, Event ID: $eventId"
                val width = surfaceView.width
                val height = surfaceView.height

                val bitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height)

                val canvas = holder.lockCanvas()
                canvas.drawColor(Color.WHITE)

                val paint = Paint()
                paint.color = Color.BLACK

                // Desenha o código QR
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        if (bitMatrix[x, y]) {
                            val rect = Rect(x, y, x + 1, y + 1)
                            canvas.drawRect(rect, paint)
                        }
                    }
                }

                // Adiciona o texto "Verificado" abaixo do código QR
                paint.textSize = 40f
                canvas.drawText("Verificado", (width / 2).toFloat(), (height + 80).toFloat(), paint)

                holder.unlockCanvasAndPost(canvas)

                // Após a geração bem-sucedida do código QR, envie a descrição do usuário para o evento
                updateUserEventParticipation(userEmail, eventId)
            } catch (e: WriterException) {
                Log.e("EventDetailClientQRcode", "Error generating QR code", e)
            }
        } else {
            Log.e("EventDetailClientQRcode", "User email or event ID is null")
        }
    }

    private fun updateUserEventParticipation(userEmail: String, eventId: String) {
        // Atualiza os dados no Realtime Database
        val userRef = database.getReference("event_participants").child(eventId).child(userEmail.replace(".", ","))
        val user = User(email = userEmail, id = eventId)
        userRef.setValue(user)
            .addOnSuccessListener {
                Log.d("EventDetailClientQRcode", "User participation updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e("EventDetailClientQRcode", "Error updating user participation", e)
            }
    }

    data class User(
        val email: String = "",
        val id: String = ""
    )
}
