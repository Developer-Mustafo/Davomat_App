package uz.coder.davomatapp.data.student

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(student: StudentDbModel)

    @Query("delete from student where id = :id")
    suspend fun delete(id: Int)

    @Query("select * from student")
    fun getAllStudentList():LiveData<List<StudentDbModel>>
    @Query("select * from student where id = :id limit 1")
    suspend fun getByStudentId(id: Int): StudentDbModel
}