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
import uz.coder.davomatapp.data.admin.AdminRepositoryImpl
import uz.coder.davomatapp.domain.admin.AddAdminUseCase
import uz.coder.davomatapp.domain.admin.Admin
import uz.coder.davomatapp.domain.admin.DeleteAdminUseCase
import uz.coder.davomatapp.domain.admin.EditAdminUseCase
import uz.coder.davomatapp.domain.admin.GetAdminByIdUseCase
import uz.coder.davomatapp.domain.admin.GetLoginSignUseCase
import kotlin.coroutines.CoroutineContext

class AdminViewModel(application: Application):AndroidViewModel(application),CoroutineScope {
    private val repository = AdminRepositoryImpl(application)
    private val addAdminUseCase = AddAdminUseCase(repository)
    private val editAdminUseCase = EditAdminUseCase(repository)
    private val deleteAdminUseCase = DeleteAdminUseCase(repository)
    private val getAdminByIdUseCase = GetAdminByIdUseCase(repository)
    private val getLoginSignUseCase = GetLoginSignUseCase(repository)
    private val job = Job()
    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName
    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail
    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword
    private val _errorInputPhone = MutableLiveData<Boolean>()
    val errorInputPhone:LiveData<Boolean>
        get() = _errorInputPhone
    private val _finish = MutableLiveData<Unit>()
    val finish:LiveData<Unit>
        get() = _finish
    private val _finishWithOutID = MutableLiveData<Unit>()
    val finishWithOutID:LiveData<Unit>
        get() = _finishWithOutID
    private val _admin = MutableLiveData<Admin>()
    val admin:LiveData<Admin>
        get() = _admin

    fun deleteAdmin(id:Int) {
        launch { deleteAdminUseCase(id) }
    }
    private fun finishWithOutId(){
        _finishWithOutID.value = Unit
    }
    fun toMainActivity(b:Boolean){
        if (b){
            finishWithOutId()
        }
    }
    fun getLoginSign(inputEmail:String?, inputPassword:String?){
        Log.d(TAG, "getLoginSign: $this")
        val email = parseString(inputEmail)
        val password = parseString(inputPassword)
        val validateInput = validateInput(email, password)
        launch(Dispatchers.Main) {
        if (validateInput){
            val admin = getLoginSignUseCase(email, password)
            _admin.value = admin
            _admin.value?.let {
                if (it == Admin(name = "aa", password = "aa", email = "aa", phone = "aa", gender = "aa")){
                    validateInput()
                }else{
                    finishWork()
                }
            }
        }
        }

    }

    private fun validateInput() {
        _errorInputEmail.value = true
        _errorInputPassword.value = true
    }

    fun addAdmin(inputName:String?,inputEmail:String?,inputPhone:String?,inputPassword:String?,inputGender:String?){
        val name = parseString(inputName)
        val email = parseString(inputEmail)
        val phone = parseString(inputPhone)
        val password = parseString(inputPassword)
        val gender = parseString(inputGender)
        val validateInput = validateInput(name, email, phone, password,gender)
        if (validateInput){
            launch {
                addAdminUseCase(Admin(name = name, email = email, password = password, phone = phone, gender = gender))
            }
            finishWork()
        }
    }

    private fun finishWork() {
        _finish.value = Unit
    }

    fun editAdmin(inputName:String?,inputEmail:String?,inputPhone:String?,inputPassword:String?,inputGender:String?){
        val name = parseString(inputName)
        val email = parseString(inputEmail)
        val phone = parseString(inputPhone)
        val password = parseString(inputPassword)
        val gender = parseString(inputGender)
        val validateInput = validateInput(name, email, phone, password,gender)
        if (validateInput){
            launch {
                _admin.value?.let {
                    editAdminUseCase(it.copy(name = name, email = email, password = password, phone = phone, gender = gender))
                }
            }
        }
        finishWork()
    }
    fun getAdminById(id:Int){
        launch(Dispatchers.Main) {
            val admin = getAdminByIdUseCase(id)
            _admin.value = admin
        }

    }
    private fun validateInput(email: String,password: String):Boolean{
        var repo = true
        if (email.isBlank()){
            _errorInputEmail.value = true
            repo = false
        }
        if (password.isBlank()){
            _errorInputPassword.value = true
            repo = false
        }
        return repo
    }

    private fun validateInput(name:String, email:String, phone:String, password:String,gender:String):Boolean{
        var repo = true
        if (name.isBlank()){
            repo = false
            _errorInputName.value = true
        }
        if (phone.isBlank()){
            repo = false
            _errorInputPhone.value = true
        }
        if (gender.isBlank()){
            repo = false
        }
        if (password.isBlank()){
            repo = false
            _errorInputPassword.value = true
        }
        if (email.isBlank()){
            repo = false
            _errorInputEmail.value = true
        }

        return repo
    }
    private fun parseString(str: String?): String {
        return str?.trim()?:""
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO+job+handler
    private val handler = CoroutineExceptionHandler{_,exception->
        Log.d(TAG, "handler: $exception")
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
    fun resetErrorName(){
        _errorInputName.value = false
    }
    fun resetErrorPhone(){
        _errorInputPhone.value = false
    }
    fun resetErrorPassword(){
        _errorInputPassword.value = false
    }
    fun resetErrorEmail(){
        _errorInputEmail.value = false
    }
}

private const val TAG = "AdminViewModel"