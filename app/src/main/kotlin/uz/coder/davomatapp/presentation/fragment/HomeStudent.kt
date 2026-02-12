package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentHomeStudentBinding
import uz.coder.davomatapp.domain.model.StudentCourses
import uz.coder.davomatapp.presentation.ui.ErrorDialog
import uz.coder.davomatapp.presentation.ui.InternetErrorDialog
import uz.coder.davomatapp.presentation.ui.StudentCourseItem
import uz.coder.davomatapp.presentation.viewModel.HomeStudentViewModel
import uz.coder.davomatapp.presentation.viewModel.NetworkViewModel
import uz.coder.davomatapp.presentation.viewModel.state.HomeStudentState

@AndroidEntryPoint
class HomeStudent : Fragment() {
    private var _binding: FragmentHomeStudentBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentHomeStudentBinding == null")
    private val viewModel by viewModels<HomeStudentViewModel>()
    private var list by  mutableStateOf(listOf<StudentCourses>())
    private val networkViewModel by activityViewModels<NetworkViewModel>()
    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeNetwork()
        observeViewModel()
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(list){_,item->
                        StudentCourseItem(item = item)
                    }
                }
            }
        }
    }

    private fun setupUI() {
        with(binding){
            swipeRefresh.setOnRefreshListener {
                viewModel.seeCourses()
            }
            topBar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.profile -> {
                        findNavController().navigate(R.id.action_home_student_to_profile)
                        true
                    }
                    else -> false
                }
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
                        viewModel.seeCourses()
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect { state ->
                    when (state) {
                        is HomeStudentState.Error -> {
                            hideProgress()
                            ErrorDialog.show(requireContext(), state.message).show()
                        }
                        HomeStudentState.Init -> {
                            hideProgress()
                        }
                        HomeStudentState.Loading -> {
                            showProgress()
                        }
                        is HomeStudentState.Success -> {
                            Log.d(TAG, "observeViewModel: Data received: ${state.data.size} courses")
                            hideProgress()
                            binding.swipeRefresh.isRefreshing = false
                            list = state.data
                        }

                        is HomeStudentState.GroupsClicked -> {
                            val text = gson.toJson(state.data)
                            findNavController().navigate(HomeStudentDirections.actionHomeStudentToHomeStudentInfo(text))
                        }
                    }
                }
            }
        }
    }

    fun hideProgress() {
        if (_binding!=null){
            binding.loading.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    fun showProgress() {
        binding.loading.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ErrorDialog.dismiss()
        InternetErrorDialog.dismiss()
    }
}

private const val TAG = "HomeStudent"