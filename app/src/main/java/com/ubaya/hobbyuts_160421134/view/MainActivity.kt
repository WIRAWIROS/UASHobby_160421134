package com.ubaya.hobbyuts_160421134.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ubaya.hobbyuts_160421134.R
import com.ubaya.hobbyuts_160421134.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController

        // Setup ActionBar with NavController
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)

        // Setup BottomNavigationView with NavController
        binding.bottomNav.setupWithNavController(navController)

        // Setup NavigationView (drawer) with NavController
        NavigationUI.setupWithNavController(binding.navView, navController)

        // Listener untuk mengubah visibilitas NavigationView dan BottomNavigationView
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment) {
                // Sembunyikan NavigationView dan BottomNavigationView di LoginFragment
                binding.navView.visibility = View.GONE
                binding.bottomNav.visibility = View.GONE
            } else {
                // Tampilkan kembali NavigationView dan BottomNavigationView
                binding.navView.visibility = View.VISIBLE
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout) || super.onSupportNavigateUp()
    }
}
