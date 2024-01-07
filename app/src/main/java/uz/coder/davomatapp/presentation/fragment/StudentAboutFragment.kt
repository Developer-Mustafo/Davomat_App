package uz.coder.davomatapp.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentStudentAboutBinding
import uz.coder.davomatapp.presentation.App
import uz.coder.davomatapp.presentation.viewmodel.StudentParamViewModel
import uz.coder.davomatapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class StudentAboutFragment : Fragment() {
    private var _binding: FragmentStudentAboutBinding? = null
    private lateinit var tel:String
    private val binding: FragmentStudentAboutBinding
        get() = _binding?:throw RuntimeException("binding not init")
    private val args by navArgs<StudentAboutFragmentArgs>()
    private lateinit var viewModel: StudentParamViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        App().component
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentAboutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        viewModel = ViewModelProvider(this,viewModelFactory)[StudentParamViewModel::class.java]
        with(binding) {
            callPhone.setOnClickListener {
                startActivity(Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${tel}")
                })
            }
            viewModel.getItemById(args.id)
            viewModel.student.observe(viewLifecycleOwner) {
                val img = when(it.gender){
                    "Erkak"-> R.drawable.erkak
                    "Ayol"->   R.drawable.ayol
                    else->R.drawable.erkak
                }
                tel = it.phone
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