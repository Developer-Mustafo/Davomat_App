package uz.coder.davomatapp.presentation.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
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
    private lateinit var editor: Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences(getString(R.string.app_name),Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val id = intent.getIntExtra(ID,1)
        editor.putInt(ID,id)
        editor.commit()
        Log.d("TAG", "onCreate: $id")
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
        if (1 !=1){
            super.onBackPressed()
        }
        if (navController.currentDestination!!.id == R.id.homeFragment) {
            finish()
        } else {
            navController.navigateUp()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(this, R.id.fragmentContainerView).navigateUp()
    }
    companion object{
        const val ID = "id"
        fun newIntent(context:Context,id:Int):Intent{
            return Intent(context,MainActivity::class.java).apply {
                putExtra(ID,id)
            }
        }
    }
}