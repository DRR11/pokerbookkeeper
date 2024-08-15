package com.example.myapplication.ui.home

import SwipeToRemoveHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.repo.AppDB
import com.example.myapplication.repo.PokerGameRepository
import com.example.myapplication.viewmodel.PokerGameViewModel
import com.example.myapplication.viewmodel.PokerGameViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {
    private val viewModel: PokerGameViewModel by activityViewModels {
        PokerGameViewModelFactory(PokerGameRepository(AppDB.getDatabase(this.requireContext()).pokerGameDao()))
    }
    private lateinit var addNewGameButton: FloatingActionButton
    private lateinit var gameListRecyclerView: RecyclerView
    private lateinit var pokerGameAdapter: PokerGameAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokerGameAdapter = PokerGameAdapter(emptyList())
        gameListRecyclerView = view.findViewById(R.id.recyclerView)
        gameListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        gameListRecyclerView.adapter = pokerGameAdapter


        addNewGameButton = view.findViewById(R.id.add_game_button)
        addNewGameButton.setOnClickListener(addGameClickListener)

        gameListRecyclerView.addOnItemTouchListener(SwipeToRemoveHelper(gameListRecyclerView))
    }

    override fun onResume() {
        super.onResume()
        viewModel.gameSessions.observe(viewLifecycleOwner) { games ->
            Log.d("PBK - home observer", viewModel.gameSessions.value?.size.toString())
            games?.let { pokerGameAdapter.updateList(it) }
        }
    }

    private val addGameClickListener = object: View.OnClickListener {
        override fun onClick(view: View?) {
            findNavController().navigate(R.id.action_home_to_add_game)
        }
    }
}