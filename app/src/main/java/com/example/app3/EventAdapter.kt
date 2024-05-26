package com.example.app3

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class EventAdapter(private val context: Context, private val eventsList: List<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventsList[position]
        holder.textViewEventName.text = event.name
        holder.textViewEventDate.text = event.date

        Glide.with(context)
            .load(event.imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.imageViewEvent)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, EventDetailActivity::class.java).apply {
                putExtra("eventId", event.id)  // Passando o ID do evento
                putExtra("name", event.name)
                putExtra("description", event.description)
                putExtra("date", event.date)
                putExtra("imageUrl", event.imageUrl)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEventName: TextView = itemView.findViewById(R.id.textViewEventName)
        val textViewEventDate: TextView = itemView.findViewById(R.id.textViewEventDate)
        val imageViewEvent: ImageView = itemView.findViewById(R.id.imageViewEvent)
    }
}
