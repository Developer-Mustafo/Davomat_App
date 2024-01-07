package uz.coder.davomatapp.di

import dagger.Binds
import dagger.Module
import uz.coder.davomatapp.data.admin.AdminRepositoryImpl
import uz.coder.davomatapp.data.course.CourseRepositoryImpl
import uz.coder.davomatapp.data.davomat.DavomatRepositoryImpl
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
}