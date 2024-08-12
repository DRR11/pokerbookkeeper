package com.example.myapplication.ui.home

import PokerGameRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.repo.AppDB
import com.example.myapplication.viewmodel.PokerGameViewModel
import com.example.myapplication.viewmodel.PokerGameViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {
    private val viewModel: PokerGameViewModel by viewModels {
        PokerGameViewModelFactory(PokerGameRepository(AppDB.getDatabase(this.requireContext()).pokerGameDao()))
    }
    private lateinit var addNewGameButton: FloatingActionButton
    private lateinit var gameListRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PokerGameAdapter(emptyList())
        gameListRecyclerView = view.findViewById(R.id.recyclerView)
        gameListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        gameListRecyclerView.adapter = adapter
        viewModel.gameSessions.observe(viewLifecycleOwner) { games ->
            games?.let { adapter.updateList(it) }
        }

        addNewGameButton = view.findViewById(R.id.add_game_button)
        addNewGameButton.setOnClickListener(addGameClickListener)
    }

    private val addGameClickListener = object: View.OnClickListener {
        override fun onClick(view: View?) {
            findNavController().navigate(R.id.action_home_to_add_game)
        }
    }
}