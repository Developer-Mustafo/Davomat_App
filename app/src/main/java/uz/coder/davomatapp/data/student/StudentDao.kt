package uz.coder.davomatapp.data.student

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(student: StudentDbModel)

    @Delete
    suspend fun delete(student: StudentDbModel)

    @Query("select * from student")
    fun getAllStudentList():LiveData<List<StudentDbModel>>
    @Query("select * from student where id = :id")
    suspend fun getByStudentId(id: Int): StudentDbModel
}