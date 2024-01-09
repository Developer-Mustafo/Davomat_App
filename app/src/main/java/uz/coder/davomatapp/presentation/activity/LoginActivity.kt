package uz.coder.davomatapp.presentation.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.ActivityLoginBinding
import uz.coder.davomatapp.domain.admin.Admin
import uz.coder.davomatapp.presentation.App
import uz.coder.davomatapp.presentation.activity.MainActivity.Companion.BOOLEAN
import uz.coder.davomatapp.presentation.viewmodel.AdminViewModel
import uz.coder.davomatapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as App).component
    }
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var viewModel: AdminViewModel
    private lateinit var editor: Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        component.inject(this)
        sharedPreferences = getSharedPreferences(getString(R.string.app_name),Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        viewModel = ViewModelProvider(this,viewModelFactory)[AdminViewModel::class.java]
        val default = sharedPreferences.getBoolean("defaultAdmin",false)
        if(!default){
            val defaultAdmin = Admin(name="Mustafo", email = "mustaforahim45@gmail.com", password = "mustafo2009", phone = "+998905579765", gender = "Erkak")
            viewModel.addAdmin(defaultAdmin.name,defaultAdmin.email,defaultAdmin.phone,defaultAdmin.password,defaultAdmin.gender)
            editor.putBoolean(DEFAULT_ADMIN,true).apply()
        }
        val trueAdmin = sharedPreferences.getBoolean(BOOLEAN,false)
        Log.d("TAG", "onCreate: $trueAdmin")
        viewModel.toMainActivity(trueAdmin)
        binding.signup.setOnClickListener {someActivityResultLauncher.launch(Intent(this@LoginActivity,RegisterActivity::class.java))}
        binding.loginIn.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            viewModel.getLoginSign(email,password)
        }
        binding.apply {
            email.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.resetErrorEmail()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
            password.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.resetErrorPassword()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
            viewModel.errorInputEmail.observe(this@LoginActivity){
                val txt = if (it)
                    getString(R.string.error_email)
                else null
                email1.error = txt
            }
            viewModel.errorInputPassword.observe(this@LoginActivity){
                val txt = if (it)
                    getString(R.string.error_password)
                else null
                password1.error = txt
            }
            //todo finish login
            viewModel.finish.observe(this@LoginActivity){
                viewModel.admin.observe(this@LoginActivity){
                    binding.email.setText("")
                    binding.password.setText("")
                    startActivity(MainActivity.newIntent(this@LoginActivity,it.id,true))
                }
                finish()
            }
            //todo finish login using 1 time id
            viewModel.finishWithOutID.observe(this@LoginActivity){
                startActivity(MainActivity.newIntent(this@LoginActivity))
                finish()
            }
        }
    }



    private var someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val data: Intent? = it.data
            val email = data?.getStringExtra(EMAIL)
            val password = data?.getStringExtra(PASSWORD)
            binding.email.setText(email)
            binding.password.setText(password)
        }
    }

    companion object{
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val DEFAULT_ADMIN = "defaultAdmin"
        fun getIntent(context: Context,email:String,password:String):Intent{
            return Intent(context,LoginActivity::class.java).apply {
                putExtra(EMAIL,email)
                putExtra(PASSWORD,password)
            }
        }
    }
}