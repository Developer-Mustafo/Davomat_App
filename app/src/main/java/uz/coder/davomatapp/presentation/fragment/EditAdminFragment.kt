package uz.coder.davomatapp.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentEditAdminBinding
import uz.coder.davomatapp.presentation.App
import uz.coder.davomatapp.presentation.activity.MainActivity.Companion.ID
import uz.coder.davomatapp.presentation.adapter.SpinnerAdapter
import uz.coder.davomatapp.presentation.viewmodel.AdminViewModel
import uz.coder.davomatapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class EditAdminFragment : Fragment() {
    private lateinit var viewModel: AdminViewModel
    private val listForGenderMale = listOf("Erkak","Ayol")
    private val listForGenderFaMale = listOf("Ayol","Erkak")
    private lateinit var listForGender:MutableList<String>
    private var _binding:FragmentEditAdminBinding? = null
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        App().component
    }
    private val binding:FragmentEditAdminBinding
        get() = _binding?:throw RuntimeException("binding not init")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this,viewModelFactory)[AdminViewModel::class.java]
        val id = sharedPreferences.getInt(ID, 1)
        viewModel.getAdminById(id)
        with(binding){
        viewModel.admin.observe(viewLifecycleOwner){
                name.setText(it.name)
                email.setText(it.email)
                password.setText(it.password)
                phone.setText(it.phone)
                listForGender  =  (if (it.gender == "Erkak") listForGenderMale else listForGenderFaMale).toMutableList()
                spinner.adapter = SpinnerAdapter(listForGender)

            }
            edit.setOnClickListener {
                viewModel.editAdmin(name.text.toString(),email.text.toString(),phone.text.toString(),password.text.toString(),listForGender[spinner.selectedItemPosition])
            }
            viewModel.errorInputName.observe(viewLifecycleOwner){
                val message = if (it){
                    getString(R.string.admin_name)
                }else{
                    null
                }
                name1.error = message
            }
            viewModel.errorInputPhone.observe(viewLifecycleOwner){
                val message = if (it){
                    getString(R.string.admin_phone)
                }else{
                    null
                }
                phone1.error = message
            }
            viewModel.errorInputPassword.observe(viewLifecycleOwner){
                val message = if (it){
                    getString(R.string.admin_password)
                }else{
                    null
                }
                phone1.error = message
            }
            viewModel.errorInputEmail.observe(viewLifecycleOwner){
                val message = if (it){
                    getString(R.string.admin_email)
                }else{
                    null
                }
                phone1.error = message
            }
            name.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.resetErrorName()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
            email.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.resetErrorEmail()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
            phone.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.resetErrorPhone()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
            password.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.resetErrorPassword()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }
        viewModel.finish.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.action_editAdminFragment_to_settingFragment)
            Toast.makeText(requireContext(), "o'zgardi", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}