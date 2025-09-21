package uz.coder.davomatapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentProfileBinding
import uz.coder.davomatapp.todo.ROLE_ADMIN
import uz.coder.davomatapp.todo.ROLE_STUDENT
import uz.coder.davomatapp.todo.ROLE_TEACHER
import uz.coder.davomatapp.todo.formatedDate
import uz.coder.davomatapp.viewModel.ProfileViewModel
import uz.coder.davomatapp.viewModel.state.ProfileState
import uz.coder.davomatapp.ui.ErrorDialog
import uz.coder.davomatapp.ui.InfoDialog

class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding?:throw RuntimeException("ActivityMainBinding = null")
    private val viewModel by viewModels<ProfileViewModel>()
    private val roles = mapOf(ROLE_STUDENT to R.string.role_student, ROLE_TEACHER to R.string.role_teacher, ROLE_ADMIN to R.string.role_admin)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it){
                    is ProfileState.Error -> {
                        hideProgress()
                        ErrorDialog.show(requireContext(), it.message).show()
                    }
                    ProfileState.Init -> {
                        hideProgress()
                    }
                    ProfileState.Loading -> {
                        showProgress()
                    }
                    is ProfileState.Success -> {
                        hideProgress()
                        with(binding){
                            fullName.text = String.format(requireContext().getString(R.string.full_name), it.user.firstName, it.user.lastName)
                            if (it.user.role == ROLE_STUDENT){
                                limit.visibility = View.VISIBLE
                                limit.text = String.format(requireContext().getString(R.string.limit), it.user.payedDate.formatedDate())
                            }else{
                                limit.visibility = View.GONE
                            }
                            roles.getValue(it.user.role)
                        }
                    }
                }
            }
        }
    }

    private fun hideProgress() {
        binding.loading.visibility = View.GONE
        binding.content.isEnabled = true
    }
    private fun showProgress() {
        binding.loading.visibility = View.VISIBLE
        binding.content.isEnabled = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.profile()
        with(binding){
            buyLimit.setOnClickListener {
                InfoDialog.show(requireContext(), requireContext().getString(R.string.buy_limit_from_telegram_bot)){
                    val url = "https://t.me/davomatAppBot"
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    startActivity(intent)
                }.show()
            }
            topBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            changeProfile.setOnClickListener {
//                val action = ProfileDirections.actionProfileToChangeProfile()
//                findNavController().navigate(action)
            }
            leaveSystem.setOnClickListener {
                viewModel.logOut{
                    findNavController().navigate(R.id.action_profile_to_login)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ErrorDialog.dismiss()
        InfoDialog.dismiss()
    }
}