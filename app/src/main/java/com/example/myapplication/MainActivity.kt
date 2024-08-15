package com.example.myapplication

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setOnItemSelectedListener { item ->
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
            if (destination.id == R.id.add_game_fragment) {
                isWorkingOnAddingNewGame = true
                navView.selectedItemId = R.id.navigation_home
            }
        }
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
}