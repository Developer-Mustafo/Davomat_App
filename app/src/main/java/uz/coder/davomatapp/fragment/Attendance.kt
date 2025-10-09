package uz.coder.davomatapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import uz.coder.davomatapp.databinding.FragmentAttendanceBinding
import uz.coder.davomatapp.model.Student
import uz.coder.davomatapp.ui.AttendanceCalendarScreen
import uz.coder.davomatapp.ui.ErrorDialog
import uz.coder.davomatapp.ui.InternetErrorDialog
import uz.coder.davomatapp.ui.StudentProfile
import uz.coder.davomatapp.viewModel.AttendanceViewModel
import uz.coder.davomatapp.viewModel.NetworkViewModel
import uz.coder.davomatapp.viewModel.state.AttendanceState
import java.time.LocalDate

class Attendance : Fragment() {
    private var _binding: FragmentAttendanceBinding? = null
    private val binding get() = _binding?:throw RuntimeException("binding is null")
    private val args by navArgs<AttendanceArgs>()
    private val viewModel by viewModels<AttendanceViewModel>()
    private val networkViewModel by activityViewModels<NetworkViewModel>()
    private var student by mutableStateOf(Student("",0,0,"",0, LocalDate.now()))
    private var list by mutableStateOf<List<uz.coder.davomatapp.model.Attendance>>(emptyList())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttendanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNetwork()
        observeViewModel()
        viewModel.studentProfile(args.groupId)
        binding.apply {
            composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            composeView.setContent {
                Column(modifier = Modifier.fillMaxSize()) {
                    println(student)
                    StudentProfile(student)
                    AttendanceCalendarScreen(student, list)
                }
            }
        }
    }

    private fun observeNetwork() {
        networkViewModel.networkState.observe(viewLifecycleOwner){state->
            state?.let {
                if (!it){
                    InternetErrorDialog.show(requireContext()).show()
                }
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect {
                    when(it){
                        is AttendanceState.Error -> {
                            hideProgress()
                            ErrorDialog.show(requireContext(), it.message).show()
                        }
                        AttendanceState.Init -> {
                            hideProgress()
                        }
                        AttendanceState.Loading -> {
                            showProgress()
                        }
                        is AttendanceState.Success -> {
                            hideProgress()
                            student = it.student
                            list = it.data
                        }
                    }
                }
            }
        }
    }

    private fun showProgress() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.swipeRefresh.isRefreshing = false
        binding.loading.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        InternetErrorDialog.dismiss()
        ErrorDialog.dismiss()
        _binding = null
    }
}