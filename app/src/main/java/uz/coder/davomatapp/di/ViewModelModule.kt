package uz.coder.davomatapp.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.coder.davomatapp.presentation.viewmodel.AdminViewModel
import uz.coder.davomatapp.presentation.viewmodel.CourseParamViewModel
import uz.coder.davomatapp.presentation.viewmodel.CourseViewModel
import uz.coder.davomatapp.presentation.viewmodel.DavomatViewModel
import uz.coder.davomatapp.presentation.viewmodel.StudentParamViewModel
import uz.coder.davomatapp.presentation.viewmodel.StudentViewModel

@Module
interface ViewModelModule {
    @IntoMap
    @Binds
    @ViewModelKey(AdminViewModel::class)
    fun bindAdmin(impl:AdminViewModel):ViewModel
    @IntoMap
    @Binds
    @ViewModelKey(CourseParamViewModel::class)
    fun bindCourseParam(impl:CourseParamViewModel):ViewModel
    @IntoMap
    @Binds
    @ViewModelKey(CourseViewModel::class)
    fun bindCourse(impl:CourseViewModel):ViewModel
    @IntoMap
    @Binds
    @ViewModelKey(DavomatViewModel::class)
    fun bindDavomat(impl:DavomatViewModel):ViewModel
    @IntoMap
    @Binds
    @ViewModelKey(StudentParamViewModel::class)
    fun bindStudentParam(impl:StudentParamViewModel):ViewModel
    @IntoMap
    @Binds
    @ViewModelKey(StudentViewModel::class)
    fun bindStudent(impl:StudentViewModel):ViewModel
}