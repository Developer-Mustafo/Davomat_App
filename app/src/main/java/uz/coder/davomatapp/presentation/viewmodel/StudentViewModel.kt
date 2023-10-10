package uz.coder.davomatapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import uz.coder.davomatapp.data.student.StudentRepositoryImpl
import uz.coder.davomatapp.domain.student.DeleteStudentUseCase
import uz.coder.davomatapp.domain.student.GetStudentAllListUseCase
import uz.coder.davomatapp.domain.student.Student

class StudentViewModel(application: Application) :AndroidViewModel(application) {
    private val repository = StudentRepositoryImpl(application)
    private val deleteStudentUseCase = DeleteStudentUseCase(repository)
    private val getStudentAllListUseCase = GetStudentAllListUseCase(repository)
    val list: LiveData<List<Student>>
        get() = getStudentAllListUseCase()

    fun delete(id: Int){
        deleteStudentUseCase(id)
    }
}