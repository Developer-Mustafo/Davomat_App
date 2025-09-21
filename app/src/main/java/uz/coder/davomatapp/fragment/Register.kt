package uz.coder.davomatapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentRegisterBinding
import uz.coder.davomatapp.todo.ROLE_STUDENT
import uz.coder.davomatapp.todo.ROLE_TEACHER
import uz.coder.davomatapp.ui.ErrorDialog
import uz.coder.davomatapp.ui.InternetErrorDialog
import uz.coder.davomatapp.ui.VerifiedDialog
import uz.coder.davomatapp.viewModel.NetworkViewModel
import uz.coder.davomatapp.viewModel.RegisterViewModel
import uz.coder.davomatapp.viewModel.state.RegisterState

@Suppress("DEPRECATION")
class Register : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding?: throw RuntimeException("ActivityMainBinding? = null")
    private val viewModel by viewModels<RegisterViewModel>()
    private val networkViewModel by activityViewModels<NetworkViewModel>()
    private val roles by lazy {
        listOf(
            Pair(requireContext().getString(R.string.choose), "0"),
            Pair(requireContext().getString(R.string.role_student), ROLE_STUDENT),
            Pair(requireContext().getString(R.string.role_teacher), ROLE_TEACHER)
        )
    }
    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        observeNetwork()
        observeViewModel()
        return binding.root
    }

    private fun observeNetwork() {
        networkViewModel.networkState.observe(viewLifecycleOwner){ state->
            state?.let {
                if (!it){
                    InternetErrorDialog.show(requireContext()).show()
                }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.state.collect { it ->
                when(it){
                    is RegisterState.Error -> {
                        hideProgress()
                        ErrorDialog.show(requireContext(), message = it.message?:"").show()
                    }
                    is RegisterState.ErrorEmail -> {
                        hideProgress()
                        binding.email1.error = it.message
                    }
                    is RegisterState.ErrorFirstName -> {
                        hideProgress()
                        binding.firstName1.error = it.message
                    }
                    is RegisterState.ErrorLastName -> {
                        hideProgress()
                        binding.lastName1.error = it.message
                    }
                    is RegisterState.ErrorPassword -> {
                        hideProgress()
                        binding.password1.error = it.message
                    }
                    is RegisterState.ErrorPhoneNumber -> {
                        hideProgress()
                        binding.phoneNumber1.error = it.message
                    }
                    is RegisterState.ErrorRole -> {
                        hideProgress()
                        binding.role1.error = it.message
                    }
                    RegisterState.Init -> {
                        hideProgress()
                        binding.firstName.text?.clear()
                        binding.lastName.text?.clear()
                        binding.email.text?.clear()
                        binding.password.text?.clear()
                        binding.phoneNumber.text?.clear()
                        binding.role.setSelection(0)
                    }
                    RegisterState.Loading -> {
                        showProgress()
                    }
                    is RegisterState.Success -> {
                        hideProgress()
                        VerifiedDialog.show(requireContext()) {
                            it.dismiss()
                            updateUi()
                        }.show()
                    }
                }
            }
        }
    }

    private fun updateUi() {
        setFragmentResult(EMAIL, bundleOf(EMAIL to binding.email.text.toString()))
        setFragmentResult(PASSWORD, bundleOf(PASSWORD to binding.password.text.toString()))
        findNavController().popBackStack()
    }

    private fun showProgress() {
        binding.loading.visibility = View.VISIBLE
        binding.signIn.isEnabled = false
        binding.email.isEnabled = false
        binding.password.isEnabled = false
        binding.phoneNumber.isEnabled = false
        binding.firstName.isEnabled = false
        binding.lastName.isEnabled = false
        binding.role.isEnabled = false
    }

    fun hideProgress(){
        binding.loading.visibility = View.GONE
        binding.signIn.isEnabled = true
        binding.email.isEnabled = true
        binding.password.isEnabled = true
        binding.phoneNumber.isEnabled = true
        binding.firstName.isEnabled = true
        binding.lastName.isEnabled = true
        binding.role.isEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            val arrayList = roles.map { it.first } as ArrayList<String>
            val ad = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayList)
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            role.adapter = ad
            role.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    this@Register.position = position
                    viewModel.resetRole()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
            signIn.setOnClickListener {
                viewModel.register(
                    inputFirstName = firstName.text.toString(),
                    inputLastName = lastName.text.toString(),
                    inputEmail = email.text.toString(),
                    inputPassword = password.text.toString(),
                    inputPhoneNumber = phoneNumber.text.toString().replace('+', ' '),
                    inputRole = roles[position].second
                )
            }
            back.setOnClickListener {
                requireActivity().onBackPressed()
            }
            firstName.addTextChangedListener {
                viewModel.resetFirstName()
            }
            lastName.addTextChangedListener {
                viewModel.resetLastName()
            }
            email.addTextChangedListener {
                viewModel.resetEmail()
            }
            password.addTextChangedListener {
                viewModel.resetPassword()
            }
            phoneNumber.addTextChangedListener {
                viewModel.resetPhoneNumber()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ErrorDialog.dismiss()
        InternetErrorDialog.dismiss()
        VerifiedDialog.dismiss()
        _binding = null
    }

    companion object{
        const val EMAIL = "LOGIN_EMAIL"
        const val PASSWORD = "LOGIN_PASSWORD"
    }
}