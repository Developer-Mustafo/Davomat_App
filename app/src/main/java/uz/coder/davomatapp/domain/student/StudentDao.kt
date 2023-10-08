package uz.coder.davomatapp.domain.student

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
    @Insert
    fun add(student: Student)

    @Update
    fun update(student: Student)

    @Query("Delete from student where id = :id")
    fun delete(id: Int)

    @Query("select * from student")
    fun getAllStudentList():List<Student>
}