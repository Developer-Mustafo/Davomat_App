package uz.coder.davomatapp.presentation.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentEditAdminBinding
import uz.coder.davomatapp.presentation.activity.MainActivity.Companion.ID
import uz.coder.davomatapp.presentation.adapter.SpinnerAdapter
import uz.coder.davomatapp.presentation.viewmodel.AdminViewModel

class EditAdminFragment : Fragment() {
    private lateinit var viewModel: AdminViewModel
    private val listForGender = listOf("Erkak","Ayol")
    private var _binding:FragmentEditAdminBinding? = null
    private val binding:FragmentEditAdminBinding
        get() = _binding?:throw RuntimeException("binding not init")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        val id = sharedPreferences.getInt(ID, 1)
        viewModel.getAdminById(id)
        with(binding){
        viewModel.admin.observe(viewLifecycleOwner){
                name.setText(it.name)
                email.setText(it.email)
                password.setText(it.password)
                phone.setText(it.phone)
                spinner.adapter = SpinnerAdapter(listForGender)
            }
            edit.setOnClickListener {
                viewModel.editAdmin(name.text.toString(),email.text.toString(),phone.text.toString(),password.text.toString(),listForGender[spinner.selectedItemPosition])
                Toast.makeText(requireContext(), "o'zgardi", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.finish.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.action_editAdminFragment_to_settingFragment)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}