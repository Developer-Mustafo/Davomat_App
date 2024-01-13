package uz.coder.davomatapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import uz.coder.davomatapp.presentation.activity.LoginActivity
import uz.coder.davomatapp.presentation.activity.MainActivity
import uz.coder.davomatapp.presentation.activity.RegisterActivity
import uz.coder.davomatapp.presentation.fragment.CourseAboutFragment
import uz.coder.davomatapp.presentation.fragment.CourseFragment
import uz.coder.davomatapp.presentation.fragment.CourseListFragment
import uz.coder.davomatapp.presentation.fragment.DavomatCourseFragment
import uz.coder.davomatapp.presentation.fragment.DavomatFragment
import uz.coder.davomatapp.presentation.fragment.DavomatStudentFragment
import uz.coder.davomatapp.presentation.fragment.EditAdminFragment
import uz.coder.davomatapp.presentation.fragment.SettingFragment
import uz.coder.davomatapp.presentation.fragment.StudentAboutFragment
import uz.coder.davomatapp.presentation.fragment.StudentFragment

@Component(modules = [DataModule::class,ViewModelModule::class])
@ApplicationScope
interface AppComponent {
    fun inject(activity:MainActivity)
    fun inject(activity:LoginActivity)
    fun inject(activity:RegisterActivity)
    fun inject(fragment: CourseAboutFragment)
    fun inject(fragment: CourseFragment)
    fun inject(fragment: CourseListFragment)
    fun inject(fragment: DavomatCourseFragment)
    fun inject(fragment: DavomatFragment)
    fun inject(fragment: DavomatStudentFragment)
    fun inject(fragment: EditAdminFragment)
    fun inject(fragment: SettingFragment)
    fun inject(fragment: StudentAboutFragment)
    fun inject(fragment: StudentFragment)
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application):AppComponent
    }
}