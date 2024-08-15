package com.example.myapplication.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.GameListItem
import com.example.myapplication.model.PokerGameSession
import com.example.myapplication.ui.util.GameOrderingUtils

class PokerGameAdapter(
    private var items: List<GameListItem>,
    private val onRemoveClick: (GameListItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_GAME = 1
    }

    inner class MonthHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val monthTextView: TextView = itemView.findViewById(R.id.month_header)

        fun bind(month: String) {
            monthTextView.text = month
        }
    }

    inner class GameViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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
                onRemoveClick(items[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_month_header, parent, false)
            MonthHeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_poker_session, parent, false)
            GameViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        // Count the number of items including headers
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (item is GameListItem.MonthHeader) {
            (holder as MonthHeaderViewHolder).bind(item.month)
        } else if (item is GameListItem.Game) {
            (holder as GameViewHolder).bind(item.game)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is GameListItem.Game -> VIEW_TYPE_GAME
            is GameListItem.MonthHeader -> VIEW_TYPE_HEADER
        }
    }

    fun updateList(updatedGames: List<PokerGameSession>) {
        items = GameOrderingUtils.groupGamesByMonth(updatedGames).flatMap { listOf(
            GameListItem.MonthHeader(it.first),
            *it.second.map { game -> GameListItem.Game(game) }.toTypedArray()
        )}
        notifyDataSetChanged()
    }

    fun removeItem(itemToBeRemoved: GameListItem) {
        items.toMutableList().apply {
            val index = indexOf(itemToBeRemoved)
            remove(itemToBeRemoved)
            notifyItemRemoved(index)
        }
    }
}