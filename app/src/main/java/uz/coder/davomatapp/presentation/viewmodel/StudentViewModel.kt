package uz.coder.davomatapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.coder.davomatapp.data.student.StudentRepositoryImpl
import uz.coder.davomatapp.domain.student.DeleteStudentUseCase
import uz.coder.davomatapp.domain.student.EditStudentUseCase
import uz.coder.davomatapp.domain.student.GetStudentAllListUseCase
import uz.coder.davomatapp.domain.student.GetStudentByIdUseCase
import uz.coder.davomatapp.domain.student.Student

class StudentViewModel(application: Application) :AndroidViewModel(application) {
    private val repository = StudentRepositoryImpl(application)
    private val editStudentUseCase = EditStudentUseCase(repository)
    private val deleteStudentUseCase = DeleteStudentUseCase(repository)
    private val getStudentByIdUseCase = GetStudentByIdUseCase(repository)
    private val getStudentAllListUseCase = GetStudentAllListUseCase(repository)
    val list = getStudentAllListUseCase.invoke()
    fun delete(student: Student){
        deleteStudentUseCase.invoke(student)
    }
    fun edit(student: Student){
        editStudentUseCase.invoke(student)
    }
    fun getStudentById(id:Int):Student{
        return getStudentByIdUseCase.invoke(id)
    }
}