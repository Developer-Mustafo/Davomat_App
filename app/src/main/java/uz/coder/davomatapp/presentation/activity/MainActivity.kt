package uz.coder.davomatapp.presentation.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationBarView
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.ActivityMainBinding
import uz.coder.davomatapp.presentation.App
import uz.coder.davomatapp.presentation.fragment.SettingFragment
import uz.coder.davomatapp.presentation.viewmodel.StudentParamViewModel
import uz.coder.davomatapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(),SettingFragment.EditListener {
    private lateinit var navController:NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var editor: Editor
    private lateinit var studentParamViewModel: StudentParamViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as App).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        component.inject(this)
        studentParamViewModel = ViewModelProvider(this,viewModelFactory)[StudentParamViewModel::class.java]
        val sharedPreferences = getSharedPreferences(getString(R.string.app_name),Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val id = intent.getIntExtra(ID,-1)
        val trueAdmin = intent.getBooleanExtra(BOOLEAN,false)
        if (id!=-1){
            editor.putInt(ID,id)
            editor.commit()
        }
        if (trueAdmin){
            editor.putBoolean(BOOLEAN,true)
            editor.commit()
        }

        Log.d("TAG", "onCreate: $id")
        binding.apply {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.navController
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
            bottomNavigation.setOnItemSelectedListener(object :
                NavigationBarView.OnItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    if (item.itemId == R.id.home) {
                        navController.navigate(R.id.homeFragment)
                        return true
                    }
                    if (item.itemId == R.id.course) {
                        navController.navigate(R.id.courseFragment)
                        return true
                    }
                    if (item.itemId == R.id.davomat) {
                        navController.navigate(R.id.davomatFragment)
                        return true
                    }
                    if (item.itemId == R.id.setting) {
                        navController.navigate(R.id.settingFragment)
                        return true
                    }
                    return false
                }
            })
        }
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (navController.currentDestination!!.id == R.id.homeFragment) {
            val dialog = AlertDialog.Builder(this@MainActivity).create()
            dialog.setButton(AlertDialog.BUTTON_POSITIVE,"Ha") { _, _ ->
                dialog.dismiss()
                finish()
            }
            dialog.setIcon(R.drawable.exit_icon)
            dialog.setTitle("Chiqish")
            dialog.setMessage("Chiqishni xoxlaysizmi ?")
            dialog.show()
        } else {
            navController.navigateUp()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(this, R.id.fragmentContainerView).navigateUp()
    }
    companion object{
        const val ID = "id"
        const val BOOLEAN = "boolean"
        fun newIntent(context:Context,id:Int,b: Boolean):Intent{
            return Intent(context,MainActivity::class.java).apply {
                putExtra(ID,id)
                putExtra(BOOLEAN,b)
            }
        }
        fun newIntent(context:Context):Intent{
            return Intent(context,MainActivity::class.java)
        }
    }

    override fun onEditListenerFinished() {
        Toast.makeText(this@MainActivity, "Akkaunddan chiqdingiz", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@MainActivity,LoginActivity::class.java))
        finish()
    }
}
