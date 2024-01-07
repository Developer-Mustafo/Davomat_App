package uz.coder.davomatapp.presentation.fragment

import  android.content.Context
import android.content.SharedPreferences
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
import uz.coder.davomatapp.databinding.FragmentCourseBinding
import uz.coder.davomatapp.presentation.App
import uz.coder.davomatapp.presentation.activity.MainActivity.Companion.ID
import uz.coder.davomatapp.presentation.viewmodel.CourseParamViewModel
import uz.coder.davomatapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class CourseFragment : Fragment() {
    private var _binding: FragmentCourseBinding? = null
    private var text:String? = null
    private val binding:FragmentCourseBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private lateinit var viewModel: CourseParamViewModel
    private val args by navArgs<CourseFragmentArgs>()
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        App().component
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        val sharedPreferences = requireContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this,viewModelFactory)[CourseParamViewModel::class.java]
        when(args.status){
            ADD->launchAdd(sharedPreferences)
            EDIT->launchEdit()
        }
        with(binding){
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
            viewModel.finish.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.homeFragment)
            }
            viewModel.errorInputName.observe(viewLifecycleOwner){
                val massage = if (it){
                    getString(R.string.error_course)
                }else{
                    null
                }
                name1.error  = massage
            }
        }
    }

    private fun launchEdit() {
        //todo editCourse
        with(binding) {
            viewModel.getById(args.id)
            Log.d("TAG1", "launchEdit: $id")
            viewModel.course.observe(viewLifecycleOwner) {
                name.setText(it.name)
            }
            save.setOnClickListener {
                val inputName = name.text.toString()
                viewModel.editCourse(inputName)
            text="O'zgardi"
            }
        }
    }

    private fun launchAdd(sharedPreferences: SharedPreferences) {
        //todo addCourse
        with(binding){
            save.setOnClickListener {
                val inputName = name.text.toString()
                viewModel.addCourse(inputName,sharedPreferences.getInt(ID,1).toString())
                text="Saqlandi"
            }
        }
    }
    companion object{
        const val ADD = "add"
        const val EDIT = "edit"
    }
}