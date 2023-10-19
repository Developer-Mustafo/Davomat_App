package uz.coder.davomatapp.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.coder.davomatapp.domain.student.Student
import uz.coder.davomatapp.data.student.StudentDao
import uz.coder.davomatapp.data.student.StudentDbModel

@Database(entities = [StudentDbModel::class], version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun studentDao(): StudentDao
    companion object{
        private var myDatabase: MyDatabase? = null
        private val LOCK = Any()
        private const val name = "my_Student.db"
        fun myDatabase(application: Application): MyDatabase {
            myDatabase?.let {
                return it
            }
            synchronized(LOCK){
                myDatabase?.let {
                    return it
                }
            }
            val db = Room.databaseBuilder(application,MyDatabase::class.java, name).fallbackToDestructiveMigration().build()
            myDatabase = db
            return db
        }
    }
}