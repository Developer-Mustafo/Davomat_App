package uz.coder.davomatapp.presentation.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentDavomatCourseBinding
import uz.coder.davomatapp.presentation.activity.MainActivity
import uz.coder.davomatapp.presentation.adapter.DavomatAdapterCourse
import uz.coder.davomatapp.presentation.viewmodel.CourseViewModel

class DavomatCourseFragment : Fragment() {
    private var _binding:FragmentDavomatCourseBinding? = null
    private val binding:FragmentDavomatCourseBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private lateinit var adapter: DavomatAdapterCourse
    private val viewModel: CourseViewModel by lazy {
        ViewModelProvider(this)[CourseViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDavomatCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.app_name),
            Context.MODE_PRIVATE)
        adapter = DavomatAdapterCourse { id ->
            findNavController().navigate(
                DavomatCourseFragmentDirections.actionDavomatFragmentToDavomatStudentFragment(
                    id
                )
            )
        }
        viewModel.list(sharedPreferences.getInt(MainActivity.ID,1)).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        with(binding){
            rec.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            rec.adapter = adapter
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}