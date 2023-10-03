package uz.coder.davomatapp.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
    @Insert
    fun add(student: Student)

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("select * from student")
    fun getAllStudentList():List<Student>

    @Query("select * from student where id =:id")
    fun getByStudentId(id:Int):Student
}