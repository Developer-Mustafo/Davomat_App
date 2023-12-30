package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.databinding.FragmentDavomatStudentBinding
import uz.coder.davomatapp.presentation.adapter.DavomatAdapterStudent
import uz.coder.davomatapp.presentation.viewmodel.StudentViewModel

class DavomatStudentFragment : Fragment() {
    private var _binding:FragmentDavomatStudentBinding? = null
    private val binding:FragmentDavomatStudentBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private val args by navArgs<DavomatStudentFragmentArgs>()
    private lateinit var adapter:DavomatAdapterStudent
    private lateinit var viewModel: StudentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDavomatStudentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        adapter = DavomatAdapterStudent { id ->
            findNavController().navigate(DavomatStudentFragmentDirections.actionDavomatStudentFragmentToDavomatFragment2(id))
        }
        viewModel.getCourseByIdStudents(args.id).observe(viewLifecycleOwner){
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