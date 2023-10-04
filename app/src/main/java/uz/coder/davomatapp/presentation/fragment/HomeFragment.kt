package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentHomeBinding
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
        adapter = AdapterStudent({

        },{

        })
        adapter.submitList(viewModel.list)
        with(binding){
            rec.adapter = adapter
            rec.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}