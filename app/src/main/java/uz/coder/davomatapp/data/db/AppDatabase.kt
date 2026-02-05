package uz.coder.davomatapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.coder.davomatapp.data.db.dao.UserDao
import uz.coder.davomatapp.data.db.model.UserDbModel
import javax.inject.Singleton

@Database(entities = [UserDbModel::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
@Singleton
abstract class AppDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
    companion object{
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "attendance.db"
        fun getInstance(context: Context): AppDatabase{
            return instance?:synchronized(LOCK){
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                instance = db
                db
            }
        }
    }
}