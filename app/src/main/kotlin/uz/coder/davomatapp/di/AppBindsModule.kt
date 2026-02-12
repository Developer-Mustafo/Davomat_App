package uz.coder.davomatapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.coder.davomatapp.data.AttendanceRepositoryImpl
import uz.coder.davomatapp.data.CourseRepositoryImpl
import uz.coder.davomatapp.data.GroupRepositoryImpl
import uz.coder.davomatapp.data.StudentRepositoryImpl
import uz.coder.davomatapp.data.UserRepositoryImpl
import uz.coder.davomatapp.domain.repository.AttendanceRepository
import uz.coder.davomatapp.domain.repository.CourseRepository
import uz.coder.davomatapp.domain.repository.GroupRepository
import uz.coder.davomatapp.domain.repository.StudentRepository
import uz.coder.davomatapp.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindsModule {
    @Singleton
    @Binds
    abstract fun bindAttendanceRepository(impl: AttendanceRepositoryImpl): AttendanceRepository
    @Singleton
    @Binds
    abstract fun bindCourseRepository(impl: CourseRepositoryImpl): CourseRepository
    @Singleton
    @Binds
    abstract fun bindGroupRepository(impl: GroupRepositoryImpl): GroupRepository
    @Singleton
    @Binds
    abstract fun bindStudentRepository(impl: StudentRepositoryImpl): StudentRepository
    @Singleton
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}