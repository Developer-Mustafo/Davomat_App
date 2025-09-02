package uz.coder.davomatapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.coder.davomatapp.databinding.FragmentLoginBinding
import uz.coder.davomatapp.viewModel.LoginViewModel
import uz.coder.davomatapp.viewModel.state.LoginState

class Login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding?: throw RuntimeException("FragmentLoginBinding == null")
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.state.collect {
                when(it){
                    is LoginState.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "observeViewModel: ${it.message}")
                        hideProgress()
                    }
                    is LoginState.ErrorEmail -> {
                        binding.email1.error = it.message
                        hideProgress()
                    }
                    is LoginState.ErrorPassword -> {
                        binding.password1.error = it.message
                        hideProgress()
                    }
                    LoginState.Init -> {
                        hideProgress()
                        binding.email.text?.clear()
                        binding.password.text?.clear()
                    }
                    LoginState.Loading -> {
                        showProgress()
                    }
                    is LoginState.Success -> {
                        hideProgress()
                        binding.email1.error=null
                        binding.password1.error=null
                        Toast.makeText(requireContext(), "O'xshadi", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showProgress() {
        binding.loading.visibility = View.VISIBLE
        binding.signIn.isEnabled = false
        binding.forgotPassword.isEnabled = false
        binding.email.isEnabled = false
        binding.password.isEnabled = false
    }

    fun hideProgress(){
        binding.loading.visibility = View.GONE
        binding.signIn.isEnabled = true
        binding.forgotPassword.isEnabled = true
        binding.email.isEnabled = true
        binding.password.isEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            signIn.setOnClickListener {
                val email = email.text.toString()
                val password = password.text.toString()
                viewModel.login(email, password)
            }
            forgotPassword.setOnClickListener {
                val url = "https://t.me/davomatAppBot"
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                startActivity(intent)
            }
            email1.addOnEditTextAttachedListener {
                viewModel.resetEmail()
            }
            password1.addOnEditTextAttachedListener {
                viewModel.resetPassword()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}