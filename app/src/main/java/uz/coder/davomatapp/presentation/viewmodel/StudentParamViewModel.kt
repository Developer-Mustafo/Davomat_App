package uz.coder.davomatapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import uz.coder.davomatapp.R
import uz.coder.davomatapp.data.student.StudentRepositoryImpl
import uz.coder.davomatapp.domain.student.AddStudentUseCase
import uz.coder.davomatapp.domain.student.EditStudentUseCase
import uz.coder.davomatapp.domain.student.GetStudentByIdUseCase
import uz.coder.davomatapp.domain.student.Student

class StudentParamViewModel(application: Application):AndroidViewModel(application) {
    private val repository = StudentRepositoryImpl(application)
    private val addStudentUseCase = AddStudentUseCase(repository)
    private val editStudentUseCase = EditStudentUseCase(repository)
    private val getStudentByIdUseCase = GetStudentByIdUseCase(repository)
    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName:LiveData<Boolean>
        get() = _errorInputName
    private val _errorInputSurName = MutableLiveData<Boolean>()
    val errorInputSurName:LiveData<Boolean>
        get() = _errorInputSurName
    private val _errorInputPhone = MutableLiveData<Boolean>()
    val errorInputPhone:LiveData<Boolean>
        get() = _errorInputPhone
    private val _finish = MutableLiveData<Unit>()
    val finish:LiveData<Unit>
        get() = _finish
    private val _student = MutableLiveData<Student>()
    val student:LiveData<Student>
        get() = _student
    private val _errorInputAge = MutableLiveData<Boolean>()
    val errorInputAge:LiveData<Boolean>
        get() = _errorInputAge
    private val scope = CoroutineScope(Dispatchers.Default)
    fun addStudent(inputName:String?,inputSurName: String?,inputPhone:String?,inputAge:String?,inputImage:String?){
            val name = parseString(inputName)
            val surName = parseString(inputSurName)
            val phone = parseString(inputPhone)
            val age = parseInt(inputAge)
            val img = parseString(inputImage)
            val validateInput = validateInput(name, surName, phone,age)
            if (validateInput) {
                scope.launch {
                addStudentUseCase(Student(name = name, surname = surName,age=age, phone = phone, img = img, course = ""))
            }
                finishWork()
            }
    }

    private fun parseInt(inputAge: String?): Int {
        return try {
            inputAge?.toInt()?:0
        }catch (
            e:Exception
        ){
            0
        }
    }

    fun editStudent(inputName:String?,inputSurName: String?,inputPhone:String?,inputAge:String?,inputImage: String?){
        val name = parseString(inputName)
        val surName = parseString(inputSurName)
        val phone = parseString(inputPhone)
        val age = parseInt(inputAge)
        val img = parseString(inputImage)
        val validateInput = validateInput(name, surName, phone,age)
        if (validateInput) {
            _student.value?.let {
                scope.launch {
                val item = it.copy(name = name, surname = surName,age = age, phone = phone, img = img)
                editStudentUseCase(item)
            }
                finishWork()
            }
        }
    }

    private fun finishWork() {
        _finish.value = Unit
    }

    private fun parseString(str: String?): String {
        return str?.trim()?:""
    }

    private fun validateInput(name: String, surName: String, phone: String,age:Int): Boolean {
        var repo = true
        if (name.isBlank()){
            repo = false
            _errorInputName.value = true
        }
        if (age <=0){
            repo = false
            _errorInputAge.value = true
        }
        if (surName.isBlank()){
            repo = false
            _errorInputSurName.value = true
        }
        if (phone.isBlank()){
            repo = false
            _errorInputPhone.value = true
        }
        return repo
    }
    fun resetInputName(){
        _errorInputName.value = false
    }
    fun resetInputAge(){
        _errorInputAge.value = false
    }
    fun resetInputSurName(){
        _errorInputSurName.value = false
    }
    fun resetInputPhone(){
        _errorInputPhone.value = false
    }
    fun getItemById(id:Int){
         viewModelScope.launch {
             val student = getStudentByIdUseCase(id)
             _student.value = student
         }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}