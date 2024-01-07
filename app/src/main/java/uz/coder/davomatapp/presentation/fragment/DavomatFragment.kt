package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import uz.coder.davomatapp.databinding.FragmentDavomatBinding
import uz.coder.davomatapp.presentation.App
import uz.coder.davomatapp.presentation.adapter.DavomatAdapter
import uz.coder.davomatapp.presentation.viewmodel.DavomatViewModel
import uz.coder.davomatapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class DavomatFragment : Fragment() {
    private var _binding:FragmentDavomatBinding? = null
    private val binding:FragmentDavomatBinding
        get() = _binding?:throw RuntimeException("binding not init")
    @Inject
    lateinit var adapter: DavomatAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        App().component
    }
    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory)[DavomatViewModel::class.java]
    }
    private val args by navArgs<DavomatFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDavomatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        viewModel.list(args.id).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        with(binding){
            rec.layoutManager = LinearLayoutManager(requireContext())
            rec.adapter = adapter
            rec.recycledViewPool.setMaxRecycledViews(DavomatAdapter.ENABLED,DavomatAdapter.POOL_SIZE)
            rec.recycledViewPool.setMaxRecycledViews(DavomatAdapter.DISABLED,DavomatAdapter.POOL_SIZE)
        }
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}