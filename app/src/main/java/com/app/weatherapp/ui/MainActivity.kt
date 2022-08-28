package com.app.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.app.weatherapp.utils.MySharedPreference
import com.app.weatherapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        //to show splash screen
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //used to save login status
        MySharedPreference.init(applicationContext)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val graph = navHostFragment.navController.navInflater.inflate(R.navigation.nav_graph)

        graph.setStartDestination(
            if (MySharedPreference.getLoginStatus())
                R.id.usersListFragment else R.id.onBoardFragment
        )

        navHostFragment.navController.graph = graph
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (!navHostFragment.navController.popBackStack()) {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}