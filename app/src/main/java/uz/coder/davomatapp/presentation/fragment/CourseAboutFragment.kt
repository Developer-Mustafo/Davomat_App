package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.databinding.FragmentCourseAboutBinding
import uz.coder.davomatapp.domain.student.Student
import uz.coder.davomatapp.presentation.adapter.StudentAdapter
import uz.coder.davomatapp.presentation.viewmodel.DavomatViewModel
import uz.coder.davomatapp.presentation.viewmodel.StudentViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CourseAboutFragment : Fragment() {
    private var _binding: FragmentCourseAboutBinding? = null
    private val args by navArgs<CourseAboutFragmentArgs>()
    private val binding: FragmentCourseAboutBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private lateinit var adapter:StudentAdapter
    private lateinit var viewModel: StudentViewModel
    private lateinit var davomatViewModel: DavomatViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        davomatViewModel = ViewModelProvider(this)[DavomatViewModel::class.java]
        adapter = StudentAdapter({ id->
            findNavController().navigate(CourseAboutFragmentDirections.actionCourseAboutFragmentToStudentAboutFragment(id))
        },{id->
            viewModel.delete(id)
        },{id->
            findNavController().navigate(CourseAboutFragmentDirections.actionCourseAboutFragmentToStudentFragment(StudentFragment.EDIT,id))
        })
        viewModel.getCourseByIdStudents(args.id).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //right 8
                //left 4
                val position = viewHolder.adapterPosition
                val student = adapter.currentList[position]
                if (!student.check){
                    when(direction){
                        8->{
                            davomat("Bor",student, position)
                        }
                        4->{
                            davomat("Yo'q",student,position)
                        }
                        else->{throw RuntimeException("xato yerga burildi")}
                    }
                }else{
                        Toast.makeText(requireContext(), "Davomat qilingan", Toast.LENGTH_SHORT).show()
                    adapter.notifyItemChanged(position)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rec)
        with(binding){
            rec.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            rec.adapter = adapter
        }
    }
    private fun davomat(string: String,student:Student,position:Int){
        davomatViewModel.addDavomat(student.name,student.surname,string,SimpleDateFormat("dd/MM/yy", Locale.US).format(Date()),student.id.toString(),student.gender)
        adapter.notifyItemMoved(position,position)
        Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}