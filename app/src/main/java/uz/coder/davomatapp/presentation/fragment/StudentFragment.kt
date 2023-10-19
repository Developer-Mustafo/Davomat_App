package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentStudentBinding
import uz.coder.davomatapp.presentation.viewmodel.StudentParamViewModel


@Suppress("UNUSED_EXPRESSION")
class StudentFragment : Fragment() {
    private var _binding:FragmentStudentBinding? = null
    private lateinit var viewModel: StudentParamViewModel
    private val args by navArgs<StudentFragmentArgs>()
    private val binding:FragmentStudentBinding
        get() = _binding?:throw RuntimeException("binding not init")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        when(args.status){
            ADD->launchAdd()
            EDIT->launchEdit()
            else->throw RuntimeException("status not found ${args.status}")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[StudentParamViewModel::class.java]

        viewModel.finish.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.homeFragment)
        }
        with(binding){
           name.addTextChangedListener(object :TextWatcher{
               override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

               }

               override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                   viewModel.resetInputName()
               }

               override fun afterTextChanged(p0: Editable?) {

               }
           })
            surname.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.resetInputSurName()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
            phone.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.resetInputPhone()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
            viewModel.errorInputName.observe(viewLifecycleOwner){
                val massage = if (it){
                    getString(R.string.Name)
                }else{
                    null
                }
                name1.error = massage
            }
            viewModel.errorInputSurName.observe(viewLifecycleOwner){
                val massage = if (it){
                    getString(R.string.SurName)
                }else{
                    null
                }
                surname1.error = massage
            }
            viewModel.errorInputPhone.observe(viewLifecycleOwner){
                val massage = if (it){
                    getString(R.string.Phone)
                }else{
                    null
                }
                phone1.error = massage
            }
        }

    }

    private fun launchEdit() {
        var id = 0
        binding.apply {
            id = args.id
            Log.d("launchEdit", "launchEdit: $id")
            viewModel.getItemById(id)

            save.setOnClickListener {
                        val inputName = name.text.toString()
                        val inputSurName = surname.text.toString()
                        val inputPhone = phone.text.toString()
                        viewModel.editStudent(inputName,inputSurName,inputPhone)
                }
        }
    }

    private fun launchAdd() {
        binding.apply {
                save.setOnClickListener {
                    val inputName = name.text.toString()
                    val inputSurName = surname.text.toString()
                    val inputPhone = phone.text.toString()
                    viewModel.addStudent(inputName, inputSurName, inputPhone)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        const val ADD = "add"
        const val EDIT = "edit"
    }
}