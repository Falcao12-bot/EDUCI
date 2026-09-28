package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        LevelCategoryEntity::class,
        GradeClassEntity::class,
        SubjectEntity::class,
        ChapterEntity::class,
        LessonEntity::class,
        ExerciseEntity::class,
        ExamEntity::class,
        UserProgressEntity::class,
        UserExerciseAttemptEntity::class,
        NotificationEntity::class,
        AdminLogEntity::class,
        DraftBackupEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun courseDao(): CourseDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun examDao(): ExamDao
    abstract fun notificationDao(): NotificationDao
    abstract fun adminLogDao(): AdminLogDao
    abstract fun draftBackupDao(): DraftBackupDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "educi_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
