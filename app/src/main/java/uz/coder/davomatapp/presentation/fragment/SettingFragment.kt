package uz.coder.davomatapp.presentation.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentSettingBinding
import uz.coder.davomatapp.domain.admin.ItemSettingModel
import uz.coder.davomatapp.presentation.App
import uz.coder.davomatapp.presentation.activity.MainActivity.Companion.BOOLEAN
import uz.coder.davomatapp.presentation.activity.MainActivity.Companion.ID
import uz.coder.davomatapp.presentation.adapter.SettingAdapter
import uz.coder.davomatapp.presentation.viewmodel.AdminViewModel
import uz.coder.davomatapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class SettingFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        App().component
    }
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private val list = listOf(ItemSettingModel(2, R.drawable.haqida,"Haqida"),ItemSettingModel(1, R.drawable.ozgartirish,"O'zgartirish"))
    private lateinit var adapter: SettingAdapter
    private lateinit var viewModel: AdminViewModel
    private var adminId:Int = 0
    private lateinit var editListen:EditListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun editSetting(sharedPreferences: SharedPreferences) {
        findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToEditAdminFragment(sharedPreferences.getInt(ID,1)))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is EditListener){
            editListen = context
        }
    }
    interface EditListener{
        fun onEditListenerFinished()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.app_name),Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        adminId = sharedPreferences.getInt(ID,1)
        viewModel = ViewModelProvider(this,viewModelFactory)[AdminViewModel::class.java]
        viewModel.getAdminById(adminId)
        viewModel.admin.observe(viewLifecycleOwner){ it ->
            binding.apply {
                exit.setOnClickListener {
                    val dialog = AlertDialog.Builder(requireContext()).create()
                    dialog.setButton(AlertDialog.BUTTON_POSITIVE,"Ha") { _, _ ->
                    editor.putBoolean(BOOLEAN,false)
                    editor.apply()
                    dialog.dismiss()
                    editListen.onEditListenerFinished()
                    }
                    dialog.setIcon(R.drawable.exit_icon)
                    dialog.setTitle("Chiqish")
                    dialog.setMessage("Akkauntdan chiqishni xoxlaysizmi ?")
                    dialog.show()
                }
                "Ismi: ${it.name}".also { name.text = it }
                "Telefon raqami: ${it.phone}".also { phone.text = it }
                "Paroli: ${it.password}".also { password.text = it }
                "Emaili: ${it.email}".also { email.text = it }
                val img = if(it.gender == "Ayol"){
                    R.drawable.ayol
                }else{
                    R.drawable.erkak
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