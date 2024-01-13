package uz.coder.davomatapp.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import uz.coder.davomatapp.data.admin.AdminDao
import uz.coder.davomatapp.data.admin.AdminRepositoryImpl
import uz.coder.davomatapp.data.course.CourseDao
import uz.coder.davomatapp.data.course.CourseRepositoryImpl
import uz.coder.davomatapp.data.davomat.DavomatDao
import uz.coder.davomatapp.data.davomat.DavomatRepositoryImpl
import uz.coder.davomatapp.data.db.MyDatabase
import uz.coder.davomatapp.data.student.StudentDao
import uz.coder.davomatapp.data.student.StudentRepositoryImpl
import uz.coder.davomatapp.domain.admin.AdminRepository
import uz.coder.davomatapp.domain.coure.CourseRepository
import uz.coder.davomatapp.domain.davomat.DavomatRepository
import uz.coder.davomatapp.domain.student.StudentRepository

@Module
interface DataModule {
    @Binds
    @ApplicationScope
    fun bindAdminImpl(impl:AdminRepositoryImpl):AdminRepository
    @Binds
    @ApplicationScope
    fun bindCourseImpl(impl:CourseRepositoryImpl):CourseRepository
    @Binds
    @ApplicationScope
    fun bindStudentImpl(impl: StudentRepositoryImpl):StudentRepository
    @Binds
    @ApplicationScope
    fun bindDavomatImpl(impl:DavomatRepositoryImpl):DavomatRepository
    companion object{
        @Provides
        fun provideAdminDao(application: Application):AdminDao{
            return MyDatabase.myDatabase(application).adminDao()
        }
        @Provides
        fun provideCourseDao(application: Application):CourseDao{
            return MyDatabase.myDatabase(application).courseDao()
        }
        @Provides
        fun provideStudentDao(application: Application):StudentDao{
            return MyDatabase.myDatabase(application).studentDao()
        }
        @Provides
        fun provideDavomatDao(application: Application):DavomatDao{
            return MyDatabase.myDatabase(application).davomatDao()
        }
    }
}