package uz.coder.davomatapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationBarView
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController
            NavigationUI.setupWithNavController(binding.bottomNavigation,navController)
            bottomNavigation.setOnItemSelectedListener(object :NavigationBarView.OnItemSelectedListener{
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.home -> {
                            navController.navigate(R.id.homeFragment)
                            return true
                        }
                        R.id.student -> {
                            navController.navigate(R.id.studentFragment)
                            return true
                        }

                        R.id.setting -> {
                            navController.navigate(R.id.settingFragment)
                            return true
                        }

                        else -> {
                            return false
                        }
                    }
                }
            })
        }
    }
}