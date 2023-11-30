package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentSettingBinding
import uz.coder.davomatapp.domain.admin.ItemSettingModel
import uz.coder.davomatapp.presentation.adapter.SettingAdapter

class SettingFragment : Fragment() {
    private var id = 0
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private val list = mutableListOf<ItemSettingModel>()
    private lateinit var adapter: SettingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        adapter = SettingAdapter {
//            when(it){
//
//            }
        }
        adapter.submitList(list)
        with(binding){
            rec.adapter = adapter
            rec.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadData() {
        list.add(ItemSettingModel(++id, R.drawable.settings_svgrepo_com,"Settings"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}