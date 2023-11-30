package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentAddCourseBinding
import uz.coder.davomatapp.databinding.FragmentCourseAboutBinding
import uz.coder.davomatapp.presentation.adapter.AdapterStudent
import uz.coder.davomatapp.presentation.viewmodel.StudentViewModel

class CourseAboutFragment : Fragment() {
    private var _binding: FragmentCourseAboutBinding? = null
    private val args by navArgs<CourseAboutFragmentArgs>()
    private val binding: FragmentCourseAboutBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private lateinit var adapter:AdapterStudent
    private lateinit var viewModel: StudentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCourseAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        adapter = AdapterStudent({id->
            findNavController().navigate(CourseAboutFragmentDirections.actionCourseAboutFragmentToStudentAboutFragment(id))
        },{id->
            findNavController().navigate(CourseAboutFragmentDirections.actionCourseAboutFragmentToStudentFragment(StudentFragment.EDIT,id))
        })
        viewModel.getCourseByIdStudents(args.id).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        with(binding){
            rec.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}