package uz.coder.davomatapp.data.course

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCourse(course: CourseDbModel)
    @Query("delete from course where id = :id")
    suspend fun deleteCourse(id:Int)
    @Query("select *from course where id = :id")
    suspend fun getByIdCourse(id: Int): CourseDbModel
    @Query("select * from course")
    fun getCourseList(): LiveData<List<CourseDbModel>>
}