package uz.coder.davomatapp.presentation.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.ActivityRegisterBinding
import uz.coder.davomatapp.presentation.App
import uz.coder.davomatapp.presentation.adapter.SpinnerAdapter
import uz.coder.davomatapp.presentation.viewmodel.AdminViewModel
import uz.coder.davomatapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as App).component
    }
    private val listForGender = listOf("Erkak","Ayol")
    private lateinit var viewModel: AdminViewModel
    private lateinit var name:String
    private lateinit var email:String
    private lateinit var phone:String
    private lateinit var gender:String
    private lateinit var password:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        component.inject(this)
        viewModel = ViewModelProvider(this,viewModelFactory)[AdminViewModel::class.java]
        binding.spinner.adapter = SpinnerAdapter(listForGender)
        binding.signup.setOnClickListener {
           name = binding.name.text.toString().trim()
           email = binding.email.text.toString().trim()
           password = binding.password.text.toString().trim()
           phone = binding.phone.text.toString().trim()
           gender = listForGender[binding.spinner.selectedItemPosition].trim()
           viewModel.addAdmin(name,email,phone,password,gender)
        }
        binding.apply {
            email.addTextChangedListener(object : TextWatcher {
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
            password.addTextChangedListener(object : TextWatcher {
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
            viewModel.errorInputEmail.observe(this@RegisterActivity){
                val txt = if (it)
                    getString(R.string.error_email)
                else null
                email1.error = txt
            }
            viewModel.errorInputPassword.observe(this@RegisterActivity){
                val txt = if (it)
                    getString(R.string.error_password)
                else null
                password1.error = txt
            }
            viewModel.errorInputName.observe(this@RegisterActivity){
                val txt = if (it)
                    getString(R.string.error_name)
                else null
                name1.error = txt
            }
        }
        viewModel.finish.observe(this@RegisterActivity){
                setResult(-1, LoginActivity.getIntent(this@RegisterActivity,email,password))
            finish()
        }
    }
}