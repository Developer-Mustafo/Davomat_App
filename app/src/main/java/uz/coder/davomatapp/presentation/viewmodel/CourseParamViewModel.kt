package uz.coder.davomatapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.coder.davomatapp.data.course.CourseRepositoryImpl
import uz.coder.davomatapp.domain.coure.AddCourseUseCase
import uz.coder.davomatapp.domain.coure.Course
import uz.coder.davomatapp.domain.coure.EditCourseUseCase
import uz.coder.davomatapp.domain.coure.GetCourseByIdUseCase

class CourseParamViewModel(application: Application):AndroidViewModel(application) {
    private val repository = CourseRepositoryImpl(application)
    private val addCourseUseCase = AddCourseUseCase(repository)
    private val editCourseUseCase = EditCourseUseCase(repository)
    private val getCourseByIdUseCase = GetCourseByIdUseCase(repository)
    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName:LiveData<Boolean>
        get() = _errorInputName
    private val _finish = MutableLiveData<Unit>()
    val finish: LiveData<Unit>
        get() = _finish
    private val _course = MutableLiveData<Course>()
    val course:LiveData<Course>
        get() = _course
    private val scope = CoroutineScope(Dispatchers.IO)
    fun addCourse(inputName:String?){
        val name = parseString(inputName)
        val validateInput = validateInput(name)
        if (validateInput) {
            scope.launch {
                addCourseUseCase(Course(name = name))
            }
            finishWork()
        }
    }
    fun getById(id:Int){
        scope.launch(Dispatchers.Main) {
            val course = getCourseByIdUseCase(id)
            _course.value = course
        }
    }
    fun editCourse(inputName:String?){
        val name = parseString(inputName)
        val validateInput = validateInput(name)
        if (validateInput) {
            scope.launch {
                _course.value?.let {
                    val item = it.copy(name = name)
                    editCourseUseCase(item)
                }
            }
            finishWork()
        }
    }
    private fun validateInput(name:String):Boolean{
        var repo = true
        if (name.isBlank()){
            repo = false
            _errorInputName.value = true
        }
        return repo
    }
    private fun finishWork() {
        scope.launch(Dispatchers.Main) {
            _finish.value = Unit
        }
    }
    fun resetErrorName(){
        _errorInputName.value = false
    }

    private fun parseString(str: String?): String {
        return str?.trim()?:""
    }
}