package uz.coder.davomatapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.coder.davomatapp.data.student.StudentRepositoryImpl
import uz.coder.davomatapp.domain.student.DeleteStudentUseCase
import uz.coder.davomatapp.domain.student.GetStudentAllListUseCase

class StudentViewModel(application: Application) :AndroidViewModel(application) {
    private val repository = StudentRepositoryImpl(application)
    private val deleteStudentUseCase = DeleteStudentUseCase(repository)
    private val getStudentAllListUseCase = GetStudentAllListUseCase(repository)
    val list = getStudentAllListUseCase()
    fun delete(id: Int){
        deleteStudentUseCase(id)
    }
}