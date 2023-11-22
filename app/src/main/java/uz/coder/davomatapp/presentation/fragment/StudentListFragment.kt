package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.databinding.FragmentStudentListBinding
import uz.coder.davomatapp.presentation.adapter.AdapterStudent
import uz.coder.davomatapp.presentation.viewmodel.StudentViewModel

class StudentListFragment : Fragment() {
    private lateinit var adapter:AdapterStudent
    private val viewModel by lazy {
        ViewModelProvider(this)[StudentViewModel::class.java]
    }
    private var _binding: FragmentStudentListBinding? = null
    private val binding:FragmentStudentListBinding
        get() = _binding?:throw RuntimeException("binding init")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStudentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AdapterStudent({position->
            viewModel.list.observe(viewLifecycleOwner){
                val id = it[position].id
                findNavController().navigate(StudentListFragmentDirections.actionStudentListFragmentToStudentAboutFragment(id))
            }
        },{id->
            findNavController().navigate(StudentListFragmentDirections.actionStudentListFragmentToStudentFragment(StudentFragment.EDIT,
                id
            ))
        })
        viewModel.list.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        val itemHelper = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.delete(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemHelper)
        itemTouchHelper.attachToRecyclerView(binding.rec)
        with(binding){
            rec.adapter = adapter
            rec.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}