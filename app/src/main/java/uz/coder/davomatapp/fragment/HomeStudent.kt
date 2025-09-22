package uz.coder.davomatapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import uz.coder.davomatapp.R
import uz.coder.davomatapp.adapter.HomeStudentAdapter
import uz.coder.davomatapp.databinding.FragmentHomeStudentBinding
import uz.coder.davomatapp.model.Group
import uz.coder.davomatapp.model.StudentCourses
import uz.coder.davomatapp.todo.userId
import uz.coder.davomatapp.ui.InternetErrorDialog
import uz.coder.davomatapp.viewModel.HomeStudentViewModel
import uz.coder.davomatapp.viewModel.NetworkViewModel
import uz.coder.davomatapp.viewModel.state.HomeStudentState
import kotlin.getValue
import uz.coder.davomatapp.ui.ErrorDialog

class HomeStudent : Fragment(), HomeStudentAdapter.OnItemClicked {
    private var _binding: FragmentHomeStudentBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentHomeStudentBinding == null")
    private val viewModel by viewModels<HomeStudentViewModel>()
    private lateinit var adapter: HomeStudentAdapter
    private var list = listOf<StudentCourses>()
    private val networkViewModel by activityViewModels<NetworkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeStudentBinding.inflate(inflater, container, false)
        viewModel.seeCourses(userId)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            swipeRefresh.setOnRefreshListener {
                viewModel.seeCourses(userId)
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

    private fun observeViewModel() {
        lifecycleScope.launch {
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
                        Log.d(TAG, "observeViewModel: ${state.data}")
                        hideProgress()
                        binding.swipeRefresh.isRefreshing=false
                        list = state.data
                        setupAdapter()
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        if (list.isEmpty()) {
            binding.expandableListView.visibility = View.GONE
            return
        }

        adapter = HomeStudentAdapter(
            requireContext(),
            list,
            this
        )
        Log.d(TAG, "setupAdapter: ${list.map { it.course }} ${list.groupBy { it.course.id }.mapValues { entry -> entry.value.map { it.group } }}")
        binding.expandableListView.setAdapter(adapter)
    }

    fun hideProgress() {
        binding.loading.visibility = View.GONE
        binding.swipeRefresh.isRefreshing = false
    }

    fun showProgress() {
        binding.loading.visibility = View.VISIBLE
    }
    override fun groupClicked(group: Group) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ErrorDialog.dismiss()
        InternetErrorDialog.dismiss()
    }
}

private const val TAG = "HomeStudent"