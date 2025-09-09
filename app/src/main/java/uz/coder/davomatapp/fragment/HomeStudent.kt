package uz.coder.davomatapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import uz.coder.davomatapp.adapter.HomeStudentAdapter
import uz.coder.davomatapp.databinding.FragmentHomeStudentBinding
import uz.coder.davomatapp.model.Group
import uz.coder.davomatapp.model.StudentCourses
import uz.coder.davomatapp.todo.isConnected
import uz.coder.davomatapp.todo.userId
import uz.coder.davomatapp.ui.errorDialog
import uz.coder.davomatapp.ui.internetErrorDialog
import uz.coder.davomatapp.viewModel.HomeStudentViewModel
import uz.coder.davomatapp.viewModel.state.HomeStudentState

class HomeStudent : Fragment() {
    private var _binding: FragmentHomeStudentBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentHomeStudentBinding == null")
    private val viewModel by viewModels<HomeStudentViewModel>()
    private lateinit var adapter: HomeStudentAdapter
    private var list = listOf<StudentCourses>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeStudentBinding.inflate(inflater, container, false)
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is HomeStudentState.Error -> {
                        hideProgress()
                        errorDialog(requireContext(), state.message).show()
                    }
                    HomeStudentState.Init -> {
                        hideProgress()
                    }
                    HomeStudentState.Loading -> {
                        showProgress()
                    }
                    is HomeStudentState.Success -> {
                        hideProgress()
                        list = state.data
                        setupAdapter()
                    }
                    HomeStudentState.InternetError -> {
                        hideProgress()
                        internetErrorDialog(requireContext()) {
                            viewModel.seeCourses(userId)
                        }.show()
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
            list.map { it.course },
            list.groupBy { it.course.id }.mapValues { entry -> entry.value.map { it.group } }
        )

        binding.expandableListView.setAdapter(adapter)
        binding.expandableListView.setOnGroupClickListener { _, _, _, _ -> false }
        binding.expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            updateUi(adapter.getChild(groupPosition, childPosition)?: Group(0L, 0, ""))
            true }
    }

    fun hideProgress() {
        binding.loading.visibility = View.GONE
    }

    fun showProgress() {
        binding.loading.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        if (!requireContext().isConnected()) {
            internetErrorDialog(requireContext()) {
                viewModel.seeCourses(userId)
            }.show()
        } else {
            if (viewModel.state.value !is HomeStudentState.Success) {
                viewModel.seeCourses(userId)
            }
        }
    }

    private fun updateUi(group: Group) {
        TODO("Attendance list")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}