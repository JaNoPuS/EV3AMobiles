package com.example.login4

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExperienceSummaryAdapter(
    private val context: Context,
    private val experiences: List<Experience>
) : RecyclerView.Adapter<ExperienceSummaryAdapter.ExperienceViewHolder>() {

    inner class ExperienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.experienceTitleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.experienceDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_experience, parent, false)
        return ExperienceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val experience = experiences[position]
        holder.titleTextView.text = experience.title
        holder.descriptionTextView.text = experience.shortDescription // Usar shortDescription en lugar de description

        // Navegar al detalle de la experiencia al hacer clic en el elemento
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ExperienceDetailActivity::class.java).apply {
                putExtra("EXPERIENCE_TITLE", experience.title)
                putExtra("EXPERIENCE_DESCRIPTION", experience.details) // Pasar details en lugar de shortDescription
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = experiences.size
}
