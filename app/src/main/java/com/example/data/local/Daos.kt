package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    fun getUserById(userId: Long): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserByIdOnce(userId: Long): UserEntity?

    @Query("SELECT * FROM users ORDER BY createdAt DESC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE users SET isPremium = :isPremium WHERE id = :userId")
    suspend fun updatePremiumStatus(userId: Long, isPremium: Boolean)

    @Query("UPDATE users SET xp = xp + :points, studyTimeMinutes = studyTimeMinutes + :minutes WHERE id = :userId")
    suspend fun addXpAndStudyTime(userId: Long, points: Int, minutes: Int)

    @Query("UPDATE users SET streak = streak + 1 WHERE id = :userId")
    suspend fun incrementStreak(userId: Long)

    @Query("SELECT COUNT(*) FROM users")
    fun countUsers(): Flow<Int>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Long)
}

@Dao
interface CourseDao {
    // Levels & Classes
    @Query("SELECT * FROM level_categories ORDER BY orderIndex ASC")
    fun getAllLevels(): Flow<List<LevelCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLevels(levels: List<LevelCategoryEntity>)

    @Query("SELECT * FROM grade_classes WHERE levelId = :levelId ORDER BY orderIndex ASC")
    fun getClassesByLevel(levelId: String): Flow<List<GradeClassEntity>>

    @Query("SELECT * FROM grade_classes ORDER BY orderIndex ASC")
    fun getAllClasses(): Flow<List<GradeClassEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClasses(classes: List<GradeClassEntity>)

    // Subjects
    @Query("SELECT * FROM subjects WHERE classId = :classId OR classId = 'all' ORDER BY name ASC")
    fun getSubjectsForClass(classId: String): Flow<List<SubjectEntity>>

    @Query("SELECT * FROM subjects ORDER BY name ASC")
    fun getAllSubjects(): Flow<List<SubjectEntity>>

    @Query("SELECT * FROM subjects WHERE id = :subjectId LIMIT 1")
    suspend fun getSubjectById(subjectId: Long): SubjectEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<SubjectEntity>)

    // Chapters
    @Query("SELECT * FROM chapters WHERE subjectId = :subjectId AND (classId = :classId OR classId = 'all') ORDER BY orderIndex ASC")
    fun getChapters(subjectId: Long, classId: String): Flow<List<ChapterEntity>>

    @Query("SELECT * FROM chapters ORDER BY title ASC")
    fun getAllChapters(): Flow<List<ChapterEntity>>

    @Query("SELECT * FROM chapters WHERE id = :chapterId LIMIT 1")
    suspend fun getChapterById(chapterId: Long): ChapterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapter: ChapterEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>)

    // Lessons
    @Query("SELECT * FROM lessons WHERE chapterId = :chapterId AND isDraft = 0 ORDER BY id ASC")
    fun getLessonsForChapter(chapterId: Long): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE isDraft = 0 ORDER BY updatedAt DESC")
    fun getAllPublishedLessons(): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons ORDER BY updatedAt DESC")
    fun getAllLessons(): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE id = :lessonId LIMIT 1")
    fun getLessonById(lessonId: Long): Flow<LessonEntity?>

    @Query("SELECT * FROM lessons WHERE id = :lessonId LIMIT 1")
    suspend fun getLessonByIdOnce(lessonId: Long): LessonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: LessonEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Update
    suspend fun updateLesson(lesson: LessonEntity)

    @Query("DELETE FROM lessons WHERE id = :lessonId")
    suspend fun deleteLesson(lessonId: Long)

    @Query("SELECT COUNT(*) FROM lessons WHERE isDraft = 0")
    fun countPublishedLessons(): Flow<Int>

    // Progress
    @Query("SELECT * FROM user_progress WHERE userId = :userId AND lessonId = :lessonId LIMIT 1")
    suspend fun getLessonProgress(userId: Long, lessonId: Long): UserProgressEntity?

    @Query("SELECT * FROM user_progress WHERE userId = :userId")
    fun getUserProgress(userId: Long): Flow<List<UserProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markLessonCompleted(progress: UserProgressEntity)

    @Query("SELECT COUNT(*) FROM user_progress WHERE userId = :userId AND isCompleted = 1")
    fun countCompletedLessons(userId: Long): Flow<Int>

    // Global Search
    @Query("""
        SELECT * FROM lessons 
        WHERE isDraft = 0 AND (title LIKE '%' || :query || '%' OR summary LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%')
    """)
    fun searchLessons(query: String): Flow<List<LessonEntity>>
}

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises WHERE classId = :classId OR classId = 'all' ORDER BY id ASC")
    fun getExercisesForClass(classId: String): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE subjectId = :subjectId AND (classId = :classId OR classId = 'all')")
    fun getExercisesBySubject(subjectId: Long, classId: String): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE chapterId = :chapterId")
    fun getExercisesByChapter(chapterId: Long): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises ORDER BY id DESC")
    fun getAllExercises(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE id = :exerciseId LIMIT 1")
    suspend fun getExerciseById(exerciseId: Long): ExerciseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)

    @Query("DELETE FROM exercises WHERE id = :exerciseId")
    suspend fun deleteExercise(exerciseId: Long)

    @Query("SELECT COUNT(*) FROM exercises")
    fun countExercises(): Flow<Int>

    // Attempts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun recordAttempt(attempt: UserExerciseAttemptEntity): Long

    @Query("SELECT * FROM user_exercise_attempts WHERE userId = :userId ORDER BY attemptedAt DESC")
    fun getUserAttempts(userId: Long): Flow<List<UserExerciseAttemptEntity>>

    @Query("SELECT COUNT(*) FROM user_exercise_attempts WHERE userId = :userId")
    fun countUserAttempts(userId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM user_exercise_attempts WHERE userId = :userId AND isCorrect = 1")
    fun countUserCorrectAttempts(userId: Long): Flow<Int>
}

@Dao
interface ExamDao {
    @Query("SELECT * FROM exams WHERE examType = :examType ORDER BY year DESC, title ASC")
    fun getExamsByType(examType: String): Flow<List<ExamEntity>>

    @Query("SELECT * FROM exams ORDER BY year DESC, examType ASC")
    fun getAllExams(): Flow<List<ExamEntity>>

    @Query("SELECT * FROM exams WHERE id = :examId LIMIT 1")
    fun getExamById(examId: Long): Flow<ExamEntity?>

    @Query("SELECT * FROM exams WHERE id = :examId LIMIT 1")
    suspend fun getExamByIdOnce(examId: Long): ExamEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExam(exam: ExamEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExams(exams: List<ExamEntity>)

    @Query("DELETE FROM exams WHERE id = :examId")
    suspend fun deleteExam(examId: Long)

    @Query("SELECT COUNT(*) FROM exams")
    fun countExams(): Flow<Int>

    @Query("""
        SELECT * FROM exams 
        WHERE title LIKE '%' || :query || '%' OR subject LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'
    """)
    fun searchExams(query: String): Flow<List<ExamEntity>>
}

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Query("SELECT COUNT(*) FROM notifications WHERE isRead = 0")
    fun countUnreadNotifications(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllAsRead()
}

@Dao
interface AdminLogDao {
    @Query("SELECT * FROM admin_logs ORDER BY timestamp DESC LIMIT 100")
    fun getRecentLogs(): Flow<List<AdminLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: AdminLogEntity): Long
}

@Dao
interface DraftBackupDao {
    @Query("SELECT * FROM draft_backups WHERE draftKey = :key LIMIT 1")
    suspend fun getDraft(key: String): DraftBackupEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDraft(draft: DraftBackupEntity)

    @Query("DELETE FROM draft_backups WHERE draftKey = :key")
    suspend fun clearDraft(key: String)
}
