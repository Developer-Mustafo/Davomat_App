package uz.coder.davomatapp.presentation.fragment


import android.annotation.SuppressLint
import android.content.Context
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
import uz.coder.davomatapp.domain.coure.Course
import uz.coder.davomatapp.presentation.App
import uz.coder.davomatapp.presentation.activity.MainActivity.Companion.ID
import uz.coder.davomatapp.presentation.adapter.SpinnerAdapter
import uz.coder.davomatapp.presentation.adapter.SpinnerStudentAdapter
import uz.coder.davomatapp.presentation.viewmodel.StudentParamViewModel
import uz.coder.davomatapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject


class StudentFragment : Fragment() {
    private val args by navArgs<StudentFragmentArgs>()
    private var position:Int = 0
    private var text:String? = null
    private var _binding:FragmentStudentBinding? = null
    private lateinit var viewModel: StudentParamViewModel
    private lateinit var listCourse:List<Course>
    private val listForGenderFaMale = listOf("Erkak","Ayol")
    private val listForGenderMale = listOf("Ayol","Erkak")
    private lateinit var listForGenderEdit:MutableList<String>
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        App().component
    }
    private val binding:FragmentStudentBinding
        get() = _binding?:throw RuntimeException("binding not init")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.app_name),
            Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this,viewModelFactory)[StudentParamViewModel::class.java]
        when(args.status){
            ADD->launchAdd()
            EDIT->launchEdit()
            else->throw RuntimeException("status not found ${args.status}")
        }
        viewModel.finish.observe(viewLifecycleOwner){
                Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.homeFragment)
        }
        with(binding){
            viewModel.list(sharedPreferences.getInt(ID,1)).observe(viewLifecycleOwner){
                val list = try { it }catch (e:RuntimeException){ listOf(Course(id=1, name = "Kurs qo'shing")) } as List<Course>
                listCourse = list
                spinnerCourse.adapter = SpinnerStudentAdapter(listCourse)
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
            viewModel.errorInputCourse.observe(viewLifecycleOwner){
                val message = if (it){
                    getString(R.string.error_couse)
                }else{
                    null
                }
                spinnerCourse1.error = message
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
                listForGenderEdit = (if (it.gender == "Erkak") listForGenderFaMale else listForGenderMale).toMutableList()
                spinner.adapter = SpinnerAdapter(listForGenderEdit)
            }
            save.setOnClickListener {
                        val inputName = name.text.toString().trim()
                        val inputSurName = surname.text.toString().trim()
                        val inputPhone = phone.text.toString().trim()
                        val inputAge = age.text.toString().trim()
                        val gender = listForGenderEdit[spinner.selectedItemPosition].trim()
                        val course = listCourse[spinnerCourse.selectedItemPosition]
                        viewModel.editStudent(inputName,inputSurName,inputPhone,inputAge,course.name,gender,course.id.toString())
                text="O'zgardi"
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun launchAdd() {
        //todo add
        binding.apply {
            spinner.adapter = SpinnerAdapter(listForGenderFaMale)
                save.setOnClickListener {
                    val inputName = name.text.toString().trim()
                    val inputSurName = surname.text.toString().trim()
                    val inputPhone = phone.text.toString().trim()
                    val inputAge = age.text.toString().trim()
                    val gender = listForGenderFaMale[spinner.selectedItemPosition].trim()
                    val course = try {
                        listCourse[spinnerCourse.selectedItemPosition]
                    }catch (e:Exception){
                        Course(id = 1, name = "Kurs qo'shing")
                    }
                    position = spinner.selectedItemPosition
                    viewModel.addStudent(inputName, inputSurName, inputPhone,inputAge,course.name,gender,course.id.toString())
                    text="Saqlandi"
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