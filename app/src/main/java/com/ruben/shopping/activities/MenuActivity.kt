package com.ruben.shopping.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ruben.shopping.R
import com.ruben.shopping.app.BaseFragment
import kotlinx.android.synthetic.main.activity_menu.*
import java.lang.StringBuilder

class MenuActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setupNavigation()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.menu_nav_host_fragment)
        bottom_navigation_menu.setupWithNavController(navController)
    }

    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onDestroy() {
        val allSavedData = this.getSharedPreferences("myApp", Context.MODE_PRIVATE)
        allSavedData.edit().clear().commit()
        super.onDestroy()
    }
}