package uz.coder.davomatapp.domain.student

import androidx.lifecycle.LiveData
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
    fun getAllStudentList():LiveData<List<Student>>
    @Query("select * from student where id = :id LIMIT 1")
    fun getByStudentId(id: Int):Student?
}