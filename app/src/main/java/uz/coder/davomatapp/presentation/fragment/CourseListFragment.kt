package uz.coder.davomatapp.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentCourseBinding
import uz.coder.davomatapp.databinding.FragmentCourseListBinding
import uz.coder.davomatapp.presentation.App
import uz.coder.davomatapp.presentation.activity.MainActivity.Companion.ID
import uz.coder.davomatapp.presentation.adapter.CourseAdapter
import uz.coder.davomatapp.presentation.viewmodel.CourseViewModel
import uz.coder.davomatapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class CourseListFragment:Fragment() {
    private var _binding:FragmentCourseListBinding? = null
    private val binding:FragmentCourseListBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private lateinit var adapter: CourseAdapter
    private val viewModel: CourseViewModel by lazy {
        ViewModelProvider(this,viewModelFactory)[CourseViewModel::class.java]
    }
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        App().component
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.app_name),Context.MODE_PRIVATE)
        adapter = CourseAdapter ({id->
            findNavController().navigate(CourseListFragmentDirections.actionCourseFragmentToAddCourseFragment2(id,CourseFragment.EDIT))
        },{id->
            findNavController().navigate(CourseListFragmentDirections.actionCourseFragmentToCourseAboutFragment(id))
        })
        viewModel.list(sharedPreferences.getInt(ID,1)).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        val callback = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val id = adapter.currentList[position].id
                viewModel.deleteCourse(id)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rec)
        with(binding){
            rec.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            rec.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}