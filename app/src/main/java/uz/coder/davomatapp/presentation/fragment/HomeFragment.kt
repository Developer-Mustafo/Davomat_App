package uz.coder.davomatapp.presentation.fragment

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
import uz.coder.davomatapp.databinding.FragmentHomeBinding
import uz.coder.davomatapp.domain.student.Student
import uz.coder.davomatapp.presentation.adapter.AdapterStudent
import uz.coder.davomatapp.presentation.viewmodel.StudentViewModel

class HomeFragment : Fragment() {
    private lateinit var adapter:AdapterStudent
    private lateinit var viewModel:StudentViewModel
    private var _binding:FragmentHomeBinding? = null
    private val binding:FragmentHomeBinding
        get() = _binding?:throw RuntimeException("binding init")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        adapter = AdapterStudent({position->
            viewModel.list.observe(viewLifecycleOwner){
                val id = it[position].id
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStudentAboutFragment(id))
            }
        },{id->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStudentFragment(StudentFragment.EDIT,
                id?:0
            ))
        })
        viewModel.list.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        val itemHelper = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.delete(adapter.currentList[viewHolder.adapterPosition].id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemHelper)
        itemTouchHelper.attachToRecyclerView(binding.rec)
        with(binding){
            rec.adapter = adapter
            rec.layoutManager = LinearLayoutManager(requireContext())
            fab.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStudentFragment(StudentFragment.ADD,0))
            }
        }
    }
}