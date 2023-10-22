package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.databinding.FragmentCourseBinding
import uz.coder.davomatapp.presentation.adapter.CourseAdapter
import uz.coder.davomatapp.presentation.viewmodel.CourseViewModel

class CourseFragment:Fragment() {
    private var _binding:FragmentCourseBinding? = null
    private val binding:FragmentCourseBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private lateinit var viewModel: CourseViewModel
    private lateinit var adapter: CourseAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CourseViewModel::class.java]
        adapter = CourseAdapter({id->

        },{id->

        })
        viewModel.list.observe(viewLifecycleOwner){
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
        itemTouchHelper.attachToRecyclerView(binding.rv)
        with(binding){
            rv.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}