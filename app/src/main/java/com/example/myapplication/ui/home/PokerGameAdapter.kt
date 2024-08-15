package com.example.myapplication.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.PokerGameSession

class PokerGameAdapter(private var games: MutableList<PokerGameSession>, private val onRemoveClick: (PokerGameSession) -> Unit): RecyclerView.Adapter<PokerGameAdapter.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val gameView: TextView = itemView.findViewById(R.id.game_session_item)
        private val removeText: TextView = itemView.findViewById(R.id.remove_text)
        fun bind(game: PokerGameSession) {
            val earning = game.cashOutAmount - game.buyInAmount
            var earningDisplayText = String.format("%.0f", earning)
            if (earning >= 0) {
                earningDisplayText = "+" + earningDisplayText
                gameView.setTextColor(ContextCompat.getColor(itemView.context, R.color.positive_outcome))
            } else {
                gameView.setTextColor(ContextCompat.getColor(itemView.context, R.color.negative_outcome))
            }
            gameView.setText(earningDisplayText)
            removeText.setOnClickListener {
                onRemoveClick(game)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_poker_session, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game)
    }

    fun updateList(updatedGames: List<PokerGameSession>) {
        games.clear()
        games.addAll(updatedGames)
        notifyDataSetChanged()
    }

    fun removeItem(game: PokerGameSession) {
        val position = games.indexOf(game)
        if (position != -1) {
            games.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}