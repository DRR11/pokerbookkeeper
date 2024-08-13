package com.example.myapplication.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.PokerGameSession

class PokerGameAdapter(private var games: List<PokerGameSession>): RecyclerView.Adapter<PokerGameAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textView: TextView =itemView.findViewById(R.id.game_session_item)
        fun bind(game: PokerGameSession) {
            textView.text = game.id.toString()
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
        games = updatedGames
        notifyDataSetChanged()
    }
}