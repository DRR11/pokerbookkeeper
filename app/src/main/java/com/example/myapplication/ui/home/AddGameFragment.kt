package com.example.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.model.PokerGameSession
import com.example.myapplication.repo.AppDB
import com.example.myapplication.repo.PokerGameRepository
import com.example.myapplication.viewmodel.PokerGameViewModel
import com.example.myapplication.viewmodel.PokerGameViewModelFactory

class AddGameFragment : Fragment() {

    private val viewModel: PokerGameViewModel by activityViewModels {
        PokerGameViewModelFactory(PokerGameRepository(AppDB.getDatabase(requireContext()).pokerGameDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveButton: Button = view.findViewById(R.id.btnSave)
        saveButton.setOnClickListener {
            val smallBlind =
                view.findViewById<EditText?>(R.id.etSmallBlind).text.toString().toDoubleOrNull()
                    ?: 0.0
            val bigBlind =
                view.findViewById<EditText?>(R.id.etBigBlind).text.toString().toDoubleOrNull()
                    ?: 0.0
            val ante =
                view.findViewById<EditText?>(R.id.etAnte).text.toString().toDoubleOrNull() ?: 0.0
            //val date = view.findViewById<EditText?>(R.id.etDate).text.toString()
            //val startTime = view.findViewById<EditText?>(R.id.etStartTime).text.toString()
            //val endTime = binding.etEndTime.text.toString()
            val buyInAmount =
                view.findViewById<EditText?>(R.id.etBuyInAmount).text.toString().toDoubleOrNull()
                    ?: 0.0
            val cashOutAmount =
                view.findViewById<EditText?>(R.id.etCashOutAmount).text.toString().toDoubleOrNull()
                    ?: 0.0
            // val location = binding.etLocation.text.toString()

            val gameSession = PokerGameSession(
                name = null,
                smallBlind = smallBlind,
                bigBlind = bigBlind,
                ante = ante,
                date = null,
                startTime = null,
                endTime = null,
                buyInAmount = buyInAmount,
                cashOutAmount = cashOutAmount,
                location = null
            )
            viewModel.addGameSession(gameSession)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
