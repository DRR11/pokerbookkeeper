package com.example.myapplication.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    interface FinishedAddNewGameListener {
        fun onFinishedAddNewGame()
    }

    private var finishedAddNewGameListener: FinishedAddNewGameListener? = null

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
            hideKeyboard()
            finishedAddNewGameListener?.onFinishedAddNewGame()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FinishedAddNewGameListener) {
            finishedAddNewGameListener = context
        } else {
            throw ClassCastException("$context must implement OnFlagResetListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        finishedAddNewGameListener = null
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // Find the currently focused view
        val currentFocus = activity?.currentFocus
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        } else {
            imm.hideSoftInputFromWindow(View(requireContext()).windowToken, 0)
        }
    }
}
