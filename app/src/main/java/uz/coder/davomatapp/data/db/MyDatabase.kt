package uz.coder.davomatapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.coder.davomatapp.domain.student.Student
import uz.coder.davomatapp.domain.student.StudentDao

@Database(entities = [Student::class], version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun studentDao(): StudentDao
    companion object{
        private var myDatabase: MyDatabase? = null
        fun getInstanse(context: Context): MyDatabase {
            if (myDatabase == null){
                myDatabase = Room.databaseBuilder(context, MyDatabase::class.java,"my_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return myDatabase!!
        }
    }
}