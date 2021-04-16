package com.francescsoftware.weathersample.presentation.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.francescsoftware.weathersample.presentation.feature.databinding.ActivityMainBinding
import com.francescsoftware.weathersample.presentation.feature.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        configureToolbar()
        lifecycle.addObserver(navigator)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = getNavController()
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun configureToolbar() {
        setSupportActionBar(binding.toolbar)
        val navController = getNavController()
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        setSupportActionBar(binding.toolbar)
    }

    private fun getNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        return navHostFragment.navController
    }
}
