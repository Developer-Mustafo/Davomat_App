package uz.coder.davomatapp.presentation.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationBarView
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController:NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
             navController = navHostFragment.navController
            NavigationUI.setupWithNavController(binding.bottomNavigation,navController)
            bottomNavigation.setOnItemSelectedListener(object :NavigationBarView.OnItemSelectedListener{
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                        if(item.itemId == R.id.home) {
                            navController.navigate(R.id.homeFragment)
                            return true
                        }
                        if (item.itemId == R.id.course){
                            navController.navigate(R.id.courseFragment)
                            return true
                        }
                        if (item.itemId == R.id.setting){
                            navController.navigate(R.id.settingFragment)
                            return true
                        }
                        return false
                }
            })
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (navController.currentDestination!!.id == R.id.homeFragment) {
            super.onBackPressed()
        } else {
            navController.navigateUp()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(this, R.id.fragmentContainerView).navigateUp()
    }
}