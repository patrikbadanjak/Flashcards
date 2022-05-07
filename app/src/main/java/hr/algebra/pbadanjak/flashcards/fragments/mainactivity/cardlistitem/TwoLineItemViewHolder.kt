package hr.algebra.pbadanjak.flashcards.fragments.mainactivity.cardlistitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import hr.algebra.pbadanjak.flashcards.R

class TwoLineItemViewHolder(itemView: View) : ViewHolder(itemView) {
    val tvFirstLine: TextView = itemView.findViewById(R.id.tvFirstLine)
    val tvSecondLine: TextView = itemView.findViewById(R.id.tvSecondLine)

    companion object {
        fun create(parent: ViewGroup) : TwoLineItemViewHolder {
            return TwoLineItemViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.two_line_item, parent, false)
            )
        }
    }
}