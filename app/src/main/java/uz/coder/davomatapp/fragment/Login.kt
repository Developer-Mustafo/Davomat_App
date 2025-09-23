package uz.coder.davomatapp.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentLoginBinding
import uz.coder.davomatapp.fragment.Register.Companion.EMAIL
import uz.coder.davomatapp.fragment.Register.Companion.PASSWORD
import uz.coder.davomatapp.todo.ROLE_ADMIN
import uz.coder.davomatapp.todo.ROLE_STUDENT
import uz.coder.davomatapp.todo.ROLE_TEACHER
import uz.coder.davomatapp.todo.role
import uz.coder.davomatapp.todo.userId
import uz.coder.davomatapp.ui.ErrorDialog
import uz.coder.davomatapp.ui.InfoDialog
import uz.coder.davomatapp.ui.InternetErrorDialog
import uz.coder.davomatapp.ui.VerifiedDialog
import uz.coder.davomatapp.viewModel.LoginViewModel
import uz.coder.davomatapp.viewModel.NetworkViewModel
import uz.coder.davomatapp.viewModel.state.LoginState

class Login : Fragment(){
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding?: throw RuntimeException("FragmentLoginBinding == null")
    private val viewModel by viewModels<LoginViewModel>()
    private val networkViewModel by activityViewModels<NetworkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        observeNetwork()
        observeViewModel()
        return binding.root
    }

    private fun isLogin() {
        Log.d(TAG, "isLogin: $userId")
        if (userId!=0L){
            when(role){
                ROLE_ADMIN, ROLE_TEACHER->{
                    findNavController().navigate(R.id.action_login_to_home)
                }
                ROLE_STUDENT->{
                    findNavController().navigate(R.id.action_login_to_homeStudent)
                }
            }
        }
    }

    private fun observeNetwork() {
        lifecycleScope.launch(Dispatchers.Main) {
            networkViewModel.networkState.observe(viewLifecycleOwner){state->
                if (isAdded){
                    state?.let { it ->
                        if (!it){
                            InternetErrorDialog.show(requireContext()).show()
                        }
                    }
                }
                Log.d(TAG, "observeNetwork: $state")
            }
        }
    }

    @SuppressLint("CommitTransaction")
    private fun observeViewModel() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.state.collect { state ->
                when(state){
                    is LoginState.Error -> {
                        hideProgress()
                        ErrorDialog.show(requireContext(), message = state.message?:"").show()
                    }
                    is LoginState.ErrorEmail -> {
                        binding.email1.error = state.message
                        hideProgress()
                    }
                    is LoginState.ErrorPassword -> {
                        binding.password1.error = state.message
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
                        VerifiedDialog.show(requireContext()){
                            when(state.data.role){
                                ROLE_ADMIN, ROLE_TEACHER->{
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.action_login_to_home, Home())
                                        .addToBackStack(null)
                                        .commit()
                                }
                                ROLE_STUDENT->{
                                    parentFragmentManager.beginTransaction()
                                        .replace(R.id.action_login_to_homeStudent, HomeStudent())
                                        .addToBackStack(null)
                                        .commit()
                                }
                            }
                            it.dismiss()
                        }.show()
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
        requireView().post {
            if (isAdded){
                isLogin()
            }
        }
        with(binding){
            setFragmentResultListener(EMAIL){_, bundle->
                val result = bundle.getString(EMAIL)
                email.setText(result)
            }
            setFragmentResultListener(PASSWORD){_,bundle->
                val result = bundle.getString(PASSWORD)
                password.setText(result)
            }
            signIn.setOnClickListener {
                val email = email.text.toString()
                val password = password.text.toString()
                viewModel.login(email, password)
            }
            forgotPassword.setOnClickListener {
                InfoDialog.show(requireContext(), requireContext().getString(R.string.go_to_telegram_bot)){
                    val url = "https://t.me/davomatAppBot"
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    startActivity(intent)
                }.show()
            }
            register.setOnClickListener {
                findNavController().navigate(R.id.action_login_to_register)
            }
            email.addTextChangedListener {
                viewModel.resetEmail()
            }
            password.addTextChangedListener {
                viewModel.resetPassword()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ErrorDialog.dismiss()
        InternetErrorDialog.dismiss()
        VerifiedDialog.dismiss()
        InfoDialog.dismiss()
    }
}

private const val TAG = "Login"