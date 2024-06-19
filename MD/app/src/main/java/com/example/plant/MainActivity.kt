package com.example.plant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.plant.databinding.ActivityMainBinding
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navView = binding.navView

        //Close Application Function
        onBackPressedDispatcher.addCallback(this@MainActivity, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val startMain = Intent(Intent.ACTION_MAIN)
                startMain.addCategory(Intent.CATEGORY_HOME)
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(startMain)
            }
        })

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_Home, R.id.navigation_History, R.id.navigation_Camera, R.id.navigation_Form, R.id.navigation_Guidance
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_Home -> {
                    Log.d("CONSOLE", it.itemId.toString())
                    //navView.selectedItemId = R.id.navigation_home
                    navController.navigate(R.id.navigation_Home)
                    true
                }
                R.id.navigation_History -> {
                    navController.navigate(R.id.navigation_History)
                    true
                }
                R.id.navigation_Camera -> {
                    navController.navigate(R.id.navigation_Camera)
                    true
                }
                R.id.navigation_Form -> {
                    navController.navigate(R.id.navigation_Form)
                    true
                }
                R.id.navigation_Guidance -> {
                    navController.navigate(R.id.navigation_Guidance)
                    true
                }
                else -> false
            }
        }


        val pref = UserPreference.getInstance(application.dataStore)
        val datastoreViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            DataStoreViewModel::class.java)

        datastoreViewModel.getTokenKey().observe(this){
            Log.d(TAG, "token: $it")
        }
    }

    companion object{
        const val TAG = "MainActivity"
    }
}