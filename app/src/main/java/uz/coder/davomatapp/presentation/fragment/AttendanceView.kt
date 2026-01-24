package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentAttendanceBinding
import uz.coder.davomatapp.domain.model.Attendance
import uz.coder.davomatapp.domain.model.Student
import uz.coder.davomatapp.presentation.ui.AttendanceCalendarPager
import uz.coder.davomatapp.presentation.ui.ErrorDialog
import uz.coder.davomatapp.presentation.ui.InternetErrorDialog
import uz.coder.davomatapp.presentation.ui.StudentProfile
import uz.coder.davomatapp.presentation.viewModel.AttendanceViewModel
import uz.coder.davomatapp.presentation.viewModel.NetworkViewModel
import uz.coder.davomatapp.presentation.viewModel.state.AttendanceState

@AndroidEntryPoint
class AttendanceView : Fragment() {
    private var _binding: FragmentAttendanceBinding? = null
    private val binding get() = _binding?:throw RuntimeException("binding is null")
    private val args by navArgs<AttendanceViewArgs>()
    private val viewModel by viewModels<AttendanceViewModel>()
    private val networkViewModel by activityViewModels<NetworkViewModel>()
    private var student by mutableStateOf<Student?>(null)
    private var list by mutableStateOf<List<Attendance>>(emptyList())
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
        binding.apply {
            composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            composeView.setContent {
                Column(modifier = Modifier.fillMaxSize().background(colorResource(R.color.theme_background))) {
                    println(student)
                    StudentProfile(student)
                    AttendanceCalendarPager(student, list)
                }
            }
            swipeRefresh.setOnRefreshListener {
                viewModel.studentProfile(args.groupId)
            }
        }
    }

    private fun observeNetwork() {
        networkViewModel.networkState.observe(viewLifecycleOwner){state->
            if (isAdded){
                state?.let {
                    if (!it){
                        InternetErrorDialog.show(requireContext()).show()
                    }else{
                        viewModel.studentProfile(args.groupId)
                    }
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
                            Log.d(TAG, "observeViewModel: $list")
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
private const val TAG = "Attendance"