package uz.coder.davomatapp.presentation.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentSettingBinding
import uz.coder.davomatapp.domain.admin.ItemSettingModel
import uz.coder.davomatapp.presentation.activity.MainActivity.Companion.ID
import uz.coder.davomatapp.presentation.adapter.SettingAdapter
import uz.coder.davomatapp.presentation.viewmodel.AdminViewModel

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private val list = listOf(ItemSettingModel(2, R.drawable.about_svgrepo_com,"Haqida"),ItemSettingModel(1, R.drawable.settings_svgrepo_com,"Sozlamalar"))
    private lateinit var adapter: SettingAdapter
    private lateinit var viewModel: AdminViewModel
    private var adminId:Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun editSetting(sharedPreferences: SharedPreferences) {
        findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToEditAdminFragment(sharedPreferences.getInt(ID,1)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.app_name),Context.MODE_PRIVATE)
        adminId = sharedPreferences.getInt(ID,1)
        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        viewModel.getAdminById(adminId)
        viewModel.admin.observe(viewLifecycleOwner){ it ->
            binding.apply {
                "Ismi: ${it.name}".also { name.text = it }
                "Telefon raqami: ${it.phone}".also { phone.text = it }
                "Paroli: ${it.password}".also { password.text = it }
                "Emaili: ${it.email}".also { email.text = it }
                val img = if(it.gender == "Ayol"){
                    R.drawable.avatar_svgrepo_com__1_
                }else{
                    R.drawable.avatar_svgrepo_com
                }
                image.setImageResource(img)
            }
        }
        adapter = SettingAdapter {
            when(it){
                1->
                    editSetting(sharedPreferences)
                2->
                    about()
            }
        }
        adapter.submitList(list)
        with(binding){
            rec.adapter = adapter
            rec.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun about() {
        findNavController().navigate(R.id.action_settingFragment_to_haqidaFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}