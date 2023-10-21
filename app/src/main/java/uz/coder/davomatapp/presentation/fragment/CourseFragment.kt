package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.coder.davomatapp.databinding.FragmentCourseBinding

class CourseFragment:Fragment() {
    private var _binding:FragmentCourseBinding? = null
    private val binding:FragmentCourseBinding
        get() = _binding?:throw RuntimeException("binding not init")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}