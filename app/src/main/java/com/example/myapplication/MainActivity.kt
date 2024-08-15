package com.example.myapplication

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.home.AddGameFragment

class MainActivity : AppCompatActivity(), AddGameFragment.FinishedAddNewGameListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var isWorkingOnAddingNewGame = false
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var topView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (isWorkingOnAddingNewGame) {
                        navController.popBackStack(R.id.add_game_fragment, false)
                    } else {
                        navController.popBackStack(R.id.navigation_home, false)
                    }
                    true
                }
                R.id.navigation_dashboard -> {
                    if (navController.currentDestination?.id != R.id.navigation_dashboard) {
                        navController.navigate(R.id.navigation_dashboard)
                    }
                    true
                }
                R.id.navigation_notifications -> {
                    if (navController.currentDestination?.id != R.id.navigation_notifications) {
                        navController.navigate(R.id.navigation_notifications)
                    }
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.add_game_fragment -> {
                    isWorkingOnAddingNewGame = true
                    bottomNavigationView.selectedItemId = R.id.navigation_home
                }
                R.id.navigation_home -> bottomNavigationView.selectedItemId = R.id.navigation_home
                R.id.navigation_dashboard -> bottomNavigationView.selectedItemId = R.id.navigation_dashboard
                R.id.navigation_notifications -> bottomNavigationView.selectedItemId = R.id.navigation_notifications
            }
        }

        addKeyboardDetectListener()
    }

    override fun onBackPressed() {
        maybeResetWorkingOnAddingNewGameFlag()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        maybeResetWorkingOnAddingNewGameFlag()
        super.onSupportNavigateUp()
        return navController.navigateUp()
    }

    private fun maybeResetWorkingOnAddingNewGameFlag() {
        val isOnAddGameFragment = navController.currentDestination?.id == R.id.add_game_fragment
        if (isOnAddGameFragment) {
            isWorkingOnAddingNewGame = false
        }
    }

    override fun onFinishedAddNewGame() {
        isWorkingOnAddingNewGame = false
    }

    private val keyboardVisibilityListener = ViewTreeObserver.OnGlobalLayoutListener {
        val heightDifference = topView.rootView.height - topView.height
        Log.d("PBK - keyboardDetect", "${topView.rootView.height}" + ", " + "${topView.height}")
        if (heightDifference > 500) {
            // keyboard shown
            bottomNavigationView.visibility = View.GONE
        } else {
            // keyboard hidden
            bottomNavigationView.visibility = View.VISIBLE

        }
    }

    private fun addKeyboardDetectListener(){
        topView = window.decorView.findViewById<View>(android.R.id.content)
        topView.viewTreeObserver.addOnGlobalLayoutListener(keyboardVisibilityListener)
    }


    override fun onDestroy() {
        super.onDestroy()
        topView.viewTreeObserver.removeOnGlobalLayoutListener(keyboardVisibilityListener)
    }
}