package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import android.util.Log
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
import uz.coder.davomatapp.presentation.App
import uz.coder.davomatapp.presentation.adapter.StudentAdapter
import uz.coder.davomatapp.presentation.viewmodel.DavomatViewModel
import uz.coder.davomatapp.presentation.viewmodel.StudentViewModel
import uz.coder.davomatapp.presentation.viewmodel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CourseAboutFragment : Fragment() {
    private var _binding: FragmentCourseAboutBinding? = null
    private val args by navArgs<CourseAboutFragmentArgs>()
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        App().component
    }
    private val binding: FragmentCourseAboutBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private lateinit var adapter:StudentAdapter
    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory)[StudentViewModel::class.java]
    }
    private val davomatViewModel by lazy {
        ViewModelProvider(this,viewModelFactory)[DavomatViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
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
                davomatViewModel.message.observe(viewLifecycleOwner){
                    Log.d("TAGA", "onSwiped: $it")
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                //right 8
                //left 4
                val position = viewHolder.adapterPosition
                val student = adapter.currentList[position]
                adapter.notifyItemChanged(position)
                        when(direction){
                            8->{
                                davomat("Bor",student, position)
                            }
                            4->{
                                davomat("Yo'q",student,position)
                            }
                            else->{throw RuntimeException("xato yerga burildi")}
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
        davomatViewModel.addDavomat(student.name,student.surname,string,SimpleDateFormat("dd/MM/yyyy", Locale.US).format(Date()),student.id.toString(),student.gender)
        adapter.notifyItemChanged(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}