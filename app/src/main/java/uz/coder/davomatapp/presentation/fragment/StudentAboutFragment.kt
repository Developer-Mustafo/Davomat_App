package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentStudentAboutBinding
import uz.coder.davomatapp.presentation.viewmodel.StudentParamViewModel

class StudentAboutFragment : Fragment() {
    private var _binding: FragmentStudentAboutBinding? = null
    private val binding: FragmentStudentAboutBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private val args by navArgs<StudentAboutFragmentArgs>()
    private lateinit var viewModel: StudentParamViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentAboutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[StudentParamViewModel::class.java]
        with(binding) {
            viewModel.getItemById(args.id)
            viewModel.student.observe(viewLifecycleOwner) {
                val img = when(it.gender){
                    "Erkak"-> R.drawable.erkak
                    "Ayol"->   R.drawable.ayol
                    else->R.drawable.erkak
                }
                imageView.setImageResource(img)
                val userName = "Ismi: "+it.name
                val userSurname = "Familiyasi: "+it.surname
                val userAge = "Yoshi: "+it.age.toString()
                val userPhone = "Telefon raqami: "+it.phone
                val userGender = "Jinsi: "+it.gender
                val userCourse = "To'garaki: "+it.course
                name.text = userName
                surname.text = userSurname
                age.text = userAge
                phone.text = userPhone
                gender.text = userGender
                course.text = userCourse
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}