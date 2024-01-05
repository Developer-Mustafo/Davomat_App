package uz.coder.davomatapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.coder.davomatapp.data.davomat.DavomatRepositoryImpl
import uz.coder.davomatapp.data.student.StudentRepositoryImpl
import uz.coder.davomatapp.domain.davomat.AddDavomatUseCase
import uz.coder.davomatapp.domain.davomat.Davomat
import uz.coder.davomatapp.domain.davomat.EditDavomatUseCase
import uz.coder.davomatapp.domain.davomat.GetDavomatByIdUseCase
import uz.coder.davomatapp.domain.davomat.GetDavomatListByStudentIdUseCase
import uz.coder.davomatapp.domain.davomat.GetDavomatOneTimeUseCase
import uz.coder.davomatapp.domain.student.EditStudentUseCase
import uz.coder.davomatapp.domain.student.GetStudentByIdUseCase
import kotlin.coroutines.CoroutineContext

class DavomatViewModel(application: Application):AndroidViewModel(application),CoroutineScope {
    private val repository = DavomatRepositoryImpl(application)
    private val repositoryStudent = StudentRepositoryImpl(application)
    private val addDavomatUseCase = AddDavomatUseCase(repository)
    private val getDavomatOneTimeUseCase = GetDavomatOneTimeUseCase(repository)
    private val editDavomatUseCase = EditDavomatUseCase(repository)
    private val getDavomatListByStudentIdUseCase = GetDavomatListByStudentIdUseCase(repository)
    private val getDavomatByIdUseCase = GetDavomatByIdUseCase(repository)
    private val getStudentByIdUseCase = GetStudentByIdUseCase(repositoryStudent)
    private val editStudentUseCase = EditStudentUseCase(repositoryStudent)
    private val job = Job()
    private val _davomat = MutableLiveData<Davomat>()
    val davomat:LiveData<Davomat>
        get() = _davomat
    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName:LiveData<Boolean>
        get() = _errorInputName
    private val _errorInputVaqt = MutableLiveData<Boolean>()
    val errorInputVaqt:LiveData<Boolean>
        get() = _errorInputVaqt
    private val _errorInputSurName = MutableLiveData<Boolean>()
    val errorInputSurName:LiveData<Boolean>
        get() = _errorInputSurName
    private val _errorInputDavomat = MutableLiveData<Boolean>()
    val errorInputDavomat:LiveData<Boolean>
        get() = _errorInputDavomat
    private val _finish = MutableLiveData<Unit>()
    val finish:LiveData<Unit>
        get() = _finish
    private val _message = MutableLiveData<String>()
    val message:LiveData<String>
        get() = _message
    fun list(id:Int)  = getDavomatListByStudentIdUseCase(id)
    fun getById(id:Int) {
        launch(Dispatchers.Main) {
            val davomat = withContext(Dispatchers.IO) { getDavomatByIdUseCase(id) }
            _davomat.value = davomat
        }
    }

    private fun valiDateInput(
        name: String,
        surname: String,
        vaqt: String,
        davomat: String,
        studentId: Int,
        gender:String
    ): Boolean {
        var repo = true
        if (name.isBlank()){
            repo = false
            _errorInputName.value = true
        }
        if (surname.isBlank()){
            repo = false
            _errorInputSurName.value = true
        }
        if (davomat.isBlank()){
            repo = false
            _errorInputDavomat.value = true
        }
        if (vaqt.isBlank()){
            repo = false
            _errorInputVaqt.value = true
        }
        if (studentId<=0){
            repo = false
        }
        if (gender.isBlank()){
            repo = false
        }
        return repo
    }

    fun addDavomat(inputName:String?, inputSurname:String?, inputDavomat: String?, inputVaqt:String? = "aa", inputStudentId:String? = "aa",inputGender:String?) {
        val studentId = parseInt(inputStudentId)
        val name = parseString(inputName)
        val surname = parseString(inputSurname)
        val vaqt = parseString(inputVaqt)
        val davomat = parseString(inputDavomat)
        val gender = parseString(inputGender)
        launch {
            val davomatClass = withContext(Dispatchers.IO){
                getDavomatOneTimeUseCase(studentId,vaqt)
            }
            val valiDateInput = valiDateInput(name,surname,vaqt,davomat,studentId,gender)
            if (valiDateInput){
                val davomatWrong = Davomat(name = "aa", surname = "aa", gender = "aa", davomat = "aa", vaqt = "aa")
                Log.d("TAGA", "addDavomat: $davomatWrong")
                Log.d("TAGA", "addDavomat: $davomatClass")
                if (davomatClass == davomatWrong){
                    Log.d("TAGA", "addDavomat: teng")
                    addDavomatUseCase(
                        Davomat(
                            name = name,
                            surname = surname,
                            davomat = davomat,
                            vaqt = vaqt,
                            studentId = studentId,
                            gender = gender
                        )
                    )
                    launch(Dispatchers.Main) {
                        _message.value = davomat
                    }
                }else{
                    Log.d("TAGA", "addDavomat: teng emas")
                    launch(Dispatchers.Main) {
                        _message.value = "Davomat qilingan"
                    }
                }
                finishWork()
            }
        }
    }
fun editDavomat(inputName:String?, inputSurname:String?, inputDavomat: String?, inputVaqt:String?, inputStudentId:String?,inputGender: String?) {
        val studentId = parseInt(inputStudentId)
        val name = parseString(inputName)
        val surname = parseString(inputSurname)
        val vaqt = parseString(inputVaqt)
        val davomat = parseString(inputDavomat)
        val gender = parseString(inputGender)
        launch {
            val valiDateInput = valiDateInput(name,surname,vaqt,davomat,studentId,gender)
            if (valiDateInput){
                _davomat.value?.let {
                    editDavomatUseCase(it.copy(name=name, surname =  surname, davomat =  davomat, vaqt = vaqt, studentId = studentId, gender = gender))
                }
                finishWork()
            }

        }
    }

    private fun finishWork() {
        launch(Dispatchers.Main) {
            _finish.value = Unit
        }
    }

    private fun parseString(str: String?): String {
        return str?.trim()?:""
    }

    private fun parseInt(str: String?): Int {
        return try {
            str?.toInt()?:0
        }catch (e:Exception){
            0
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO+job+handler
    private val handler = CoroutineExceptionHandler{_,exception->
        Log.d(
            "davomat",
            "handler: Exception was caught: $exception"
        )}

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}