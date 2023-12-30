package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.coder.davomatapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding? = null
    private val binding:FragmentHomeBinding
        get() = _binding?:throw RuntimeException("binding not init")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            studentAdd.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStudentFragment(StudentFragment.ADD,0))
            }
            courseAdd.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddCourseFragment2(0,CourseFragment.ADD))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}