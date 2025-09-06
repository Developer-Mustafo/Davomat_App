package uz.coder.davomatapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.coder.davomatapp.db.dao.UserDao
import uz.coder.davomatapp.db.model.UserDbModel

@Database(entities = [UserDbModel::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
    companion object{
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "attendance.db"
        fun getInstance(context: Context): AppDatabase{
            instance?.let {
                return it
            }
            synchronized(LOCK){
                instance?.let {
                    return it
                }
            }
            val db = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
            instance = db
            return db
        }
    }
}