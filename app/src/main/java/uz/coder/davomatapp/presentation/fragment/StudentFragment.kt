package uz.coder.davomatapp.presentation.fragment


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentStudentBinding
import uz.coder.davomatapp.presentation.adapter.SpinnerAdapter
import uz.coder.davomatapp.presentation.viewmodel.StudentParamViewModel


class StudentFragment : Fragment() {
    private val args by navArgs<StudentFragmentArgs>()
    private var position:Int = 0
    private var _binding:FragmentStudentBinding? = null
    private lateinit var viewModel: StudentParamViewModel
    private lateinit var listCourse:List<String>
    private val listForGender = listOf("Erkak","Ayol")
    private val binding:FragmentStudentBinding
        get() = _binding?:throw RuntimeException("binding not init")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[StudentParamViewModel::class.java]
        when(args.status){
            ADD->launchAdd()
            EDIT->launchEdit()
            else->throw RuntimeException("status not found ${args.status}")
        }
        viewModel.finish.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.homeFragment)
        }
        with(binding){
           spinner.adapter = SpinnerAdapter(listForGender)
            viewModel.list.observe(viewLifecycleOwner){
                spinnerCourse.adapter = SpinnerAdapter(it)
                listCourse = it
            }
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
            age.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.resetInputAge()
                }

                override fun afterTextChanged(s: Editable?) {

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
            viewModel.errorInputAge.observe(viewLifecycleOwner){
                val massage = if (it){
                    getString(R.string.Age)
                }else{
                    null
                }
                age1.error = massage
            }
        }

    }
    private fun launchEdit() {
        //todo edit
        binding.apply {
            Log.d("aaa", "launchEdit: ${args.id}")
            viewModel.getItemById(args.id)
            viewModel.student.observe(viewLifecycleOwner){
                val student = it
                Log.d("aaa", "121: ${it.id}")
                name.setText(student.name)
                surname.setText(student.surname)
                phone.setText(student.phone)
                age.setText(student.age.toString())
            }
            save.setOnClickListener {
                        val inputName = name.text.toString().trim()
                        val inputSurName = surname.text.toString().trim()
                        val inputPhone = phone.text.toString().trim()
                        val inputAge = age.text.toString().trim()
                        val gender = listForGender[spinner.selectedItemPosition].trim()
                        val course = listCourse[spinnerCourse.selectedItemPosition].trim()
                        viewModel.editStudent(inputName,inputSurName,inputPhone,inputAge,course,gender)
                        Toast.makeText(requireContext(), "o'zgardi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun launchAdd() {
        //todo add
        binding.apply {
                save.setOnClickListener {
                    val inputName = name.text.toString().trim()
                    val inputSurName = surname.text.toString().trim()
                    val inputPhone = phone.text.toString().trim()
                    val inputAge = age.text.toString().trim()
                    val gender = listForGender[spinner.selectedItemPosition].trim()
                    val course = listCourse[spinnerCourse.selectedItemPosition].trim()
                    position = spinner.selectedItemPosition
                    viewModel.addStudent(inputName, inputSurName, inputPhone,inputAge,course,gender)
                    Toast.makeText(requireContext(), "Saqlandi", Toast.LENGTH_SHORT).show()
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