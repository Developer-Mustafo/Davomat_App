package uz.coder.davomatapp.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.coder.davomatapp.domain.coure.AddCourseUseCase
import uz.coder.davomatapp.domain.coure.Course
import uz.coder.davomatapp.domain.coure.EditCourseUseCase
import uz.coder.davomatapp.domain.coure.GetCourseByIdUseCase
import javax.inject.Inject

class CourseParamViewModel @Inject constructor(
    private val addCourseUseCase : AddCourseUseCase,
    private val editCourseUseCase : EditCourseUseCase,
    private val getCourseByIdUseCase : GetCourseByIdUseCase
): ViewModel() {
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
    fun addCourse(inputName:String?,inputAdminId:String?){
        val name = parseString(inputName)
        val id = parseInt(inputAdminId)
        val validateInput = validateInput(id,name)
        if (validateInput) {
            scope.launch {
                addCourseUseCase(Course(name = name, adminId = id))
            }
            finishWork()
        }
    }

    private fun parseInt(str: String?): Int {
        return try {
            str?.toInt()?:0
        }catch (e:Exception){
            0
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
    private fun validateInput(adminId:Int, name:String):Boolean{
        var repo = true
        if (name.isBlank()){
            repo = false
            _errorInputName.value = true
        }
        if (adminId <=0){
            repo = false
        }
        return repo
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