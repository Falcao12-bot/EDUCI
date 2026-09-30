package com.example.data.repository

import android.content.Context
import com.example.data.local.*
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest

class EduRepository(context: Context) {
    private val db = AppDatabase.getInstance(context)
    val userDao = db.userDao()
    val courseDao = db.courseDao()
    val exerciseDao = db.exerciseDao()
    val examDao = db.examDao()
    val notificationDao = db.notificationDao()
    val adminLogDao = db.adminLogDao()
    val draftDao = db.draftBackupDao()
    val favoriteDao = db.favoriteDao()
    val offlineDao = db.offlineDownloadDao()
    val paymentDao = db.paymentDao()
    val examSubmissionDao = db.examSubmissionDao()

    // Database accessor for seeding
    fun getDatabase(): AppDatabase = db

    companion object {
        fun hashPassword(password: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }

    // Users
    fun getUserById(userId: Long): Flow<UserEntity?> = userDao.getUserById(userId)
    suspend fun getUserByEmail(email: String): UserEntity? = userDao.getUserByEmail(email)
    suspend fun getUserByEmailOrPhone(identifier: String): UserEntity? = userDao.getUserByEmailOrPhone(identifier)
    suspend fun registerUser(user: UserEntity): Long = userDao.insertUser(user)
    suspend fun updateUser(user: UserEntity) = userDao.updateUser(user)
    suspend fun updatePremium(userId: Long, isPremium: Boolean) = userDao.updatePremiumStatus(userId, isPremium)
    suspend fun setSuspendedStatus(userId: Long, isSuspended: Boolean) = userDao.setSuspendedStatus(userId, isSuspended)
    suspend fun addXpAndStudyTime(userId: Long, xp: Int, minutes: Int) = userDao.addXpAndStudyTime(userId, xp, minutes)
    suspend fun incrementStreak(userId: Long) = userDao.incrementStreak(userId)
    fun getAllUsers(): Flow<List<UserEntity>> = userDao.getAllUsers()
    fun countUsers(): Flow<Int> = userDao.countUsers()
    suspend fun deleteUser(userId: Long) = userDao.deleteUserById(userId)

    // Courses & Curriculum
    fun getAllLevels(): Flow<List<LevelCategoryEntity>> = courseDao.getAllLevels()
    fun getClassesByLevel(levelId: String): Flow<List<GradeClassEntity>> = courseDao.getClassesByLevel(levelId)
    fun getAllClasses(): Flow<List<GradeClassEntity>> = courseDao.getAllClasses()
    fun getSubjectsForClass(classId: String): Flow<List<SubjectEntity>> = courseDao.getSubjectsForClass(classId)
    fun getAllSubjects(): Flow<List<SubjectEntity>> = courseDao.getAllSubjects()
    fun getChapters(subjectId: Long, classId: String): Flow<List<ChapterEntity>> = courseDao.getChapters(subjectId, classId)
    fun getAllChapters(): Flow<List<ChapterEntity>> = courseDao.getAllChapters()
    fun getLessonsForChapter(chapterId: Long): Flow<List<LessonEntity>> = courseDao.getLessonsForChapter(chapterId)
    fun getAllPublishedLessons(): Flow<List<LessonEntity>> = courseDao.getAllPublishedLessons()
    fun getAllLessons(): Flow<List<LessonEntity>> = courseDao.getAllLessons()
    fun getLessonById(lessonId: Long): Flow<LessonEntity?> = courseDao.getLessonById(lessonId)
    suspend fun getLessonByIdOnce(lessonId: Long): LessonEntity? = courseDao.getLessonByIdOnce(lessonId)
    suspend fun insertLesson(lesson: LessonEntity): Long = courseDao.insertLesson(lesson)
    suspend fun updateLesson(lesson: LessonEntity) = courseDao.updateLesson(lesson)
    suspend fun deleteLesson(lessonId: Long) = courseDao.deleteLesson(lessonId)
    fun countPublishedLessons(): Flow<Int> = courseDao.countPublishedLessons()

    // Admin Course Management
    suspend fun insertSubject(subject: SubjectEntity): Long = courseDao.insertSubject(subject)
    suspend fun deleteSubject(subjectId: Long) = courseDao.deleteSubject(subjectId)
    suspend fun insertChapter(chapter: ChapterEntity): Long = courseDao.insertChapter(chapter)
    suspend fun deleteChapter(chapterId: Long) = courseDao.deleteChapter(chapterId)

    // Progress
    fun getUserProgress(userId: Long): Flow<List<UserProgressEntity>> = courseDao.getUserProgress(userId)
    suspend fun markLessonCompleted(userId: Long, lessonId: Long) {
        courseDao.markLessonCompleted(UserProgressEntity(userId = userId, lessonId = lessonId, isCompleted = true))
        userDao.addXpAndStudyTime(userId, points = 25, minutes = 15)
    }
    fun countCompletedLessons(userId: Long): Flow<Int> = courseDao.countCompletedLessons(userId)

    // Exercises
    fun getExercisesForClass(classId: String): Flow<List<ExerciseEntity>> = exerciseDao.getExercisesForClass(classId)
    fun getExercisesByChapter(chapterId: Long): Flow<List<ExerciseEntity>> = exerciseDao.getExercisesByChapter(chapterId)
    fun getAllExercises(): Flow<List<ExerciseEntity>> = exerciseDao.getAllExercises()
    suspend fun getExerciseById(exerciseId: Long): ExerciseEntity? = exerciseDao.getExerciseById(exerciseId)
    suspend fun insertExercise(exercise: ExerciseEntity): Long = exerciseDao.insertExercise(exercise)
    suspend fun deleteExercise(exerciseId: Long) = exerciseDao.deleteExercise(exerciseId)
    fun countExercises(): Flow<Int> = exerciseDao.countExercises()

    suspend fun recordAttempt(userId: Long, exerciseId: Long, studentAnswer: String, isCorrect: Boolean, points: Int): Long {
        val id = exerciseDao.recordAttempt(
            UserExerciseAttemptEntity(
                userId = userId,
                exerciseId = exerciseId,
                studentAnswer = studentAnswer,
                isCorrect = isCorrect,
                score = if (isCorrect) points else 0
            )
        )
        if (isCorrect) {
            userDao.addXpAndStudyTime(userId, points = points, minutes = 3)
        }
        return id
    }
    fun getUserAttempts(userId: Long): Flow<List<UserExerciseAttemptEntity>> = exerciseDao.getUserAttempts(userId)
    fun countUserAttempts(userId: Long): Flow<Int> = exerciseDao.countUserAttempts(userId)
    fun countUserCorrectAttempts(userId: Long): Flow<Int> = exerciseDao.countUserCorrectAttempts(userId)

    // Exams
    fun getExamsByType(examType: String): Flow<List<ExamEntity>> = examDao.getExamsByType(examType)
    fun getAllExams(): Flow<List<ExamEntity>> = examDao.getAllExams()
    fun getExamById(examId: Long): Flow<ExamEntity?> = examDao.getExamById(examId)
    suspend fun getExamByIdOnce(examId: Long): ExamEntity? = examDao.getExamByIdOnce(examId)
    suspend fun insertExam(exam: ExamEntity): Long = examDao.insertExam(exam)
    suspend fun deleteExam(examId: Long) = examDao.deleteExam(examId)
    fun countExams(): Flow<Int> = examDao.countExams()
    fun searchExams(query: String): Flow<List<ExamEntity>> = examDao.searchExams(query)

    // Exam Submissions
    fun getExamSubmissions(userId: Long): Flow<List<ExamSubmissionEntity>> = examSubmissionDao.getSubmissionsByUser(userId)
    fun countUserExamSubmissions(userId: Long): Flow<Int> = examSubmissionDao.countUserSubmissions(userId)
    fun getAverageExamScore(userId: Long): Flow<Float?> = examSubmissionDao.getAverageExamScore(userId)
    suspend fun recordExamSubmission(submission: ExamSubmissionEntity): Long {
        val id = examSubmissionDao.insertSubmission(submission)
        userDao.addXpAndStudyTime(submission.userId, points = (submission.score * 5).toInt(), minutes = submission.timeSpentSeconds / 60)
        return id
    }

    // Favorites
    fun getFavorites(userId: Long): Flow<List<FavoriteEntity>> = favoriteDao.getFavoritesByUser(userId)
    fun isFavorite(userId: Long, itemType: String, itemId: Long): Flow<Boolean> = favoriteDao.isFavorite(userId, itemType, itemId)
    suspend fun addFavorite(userId: Long, itemType: String, itemId: Long, title: String, subjectName: String) =
        favoriteDao.addFavorite(FavoriteEntity(userId = userId, itemType = itemType, itemId = itemId, title = title, subjectName = subjectName))
    suspend fun removeFavorite(userId: Long, itemType: String, itemId: Long) = favoriteDao.removeFavorite(userId, itemType, itemId)

    // Offline Downloads
    fun getOfflineDownloads(userId: Long): Flow<List<OfflineDownloadEntity>> = offlineDao.getDownloadsByUser(userId)
    fun isDownloaded(userId: Long, itemType: String, itemId: Long): Flow<Boolean> = offlineDao.isDownloaded(userId, itemType, itemId)
    suspend fun addOfflineDownload(userId: Long, itemType: String, itemId: Long, title: String, subjectName: String, summary: String = "", content: String = "") =
        offlineDao.addDownload(OfflineDownloadEntity(userId = userId, itemType = itemType, itemId = itemId, title = title, subjectName = subjectName, summary = summary, content = content))
    suspend fun removeOfflineDownload(userId: Long, itemType: String, itemId: Long) = offlineDao.removeDownload(userId, itemType, itemId)
    suspend fun clearOfflineDownloads(userId: Long) = offlineDao.clearAllDownloads(userId)

    // Payments
    fun getUserTransactions(userId: Long): Flow<List<PaymentTransactionEntity>> = paymentDao.getTransactionsByUser(userId)
    fun getAllTransactions(): Flow<List<PaymentTransactionEntity>> = paymentDao.getAllTransactions()
    suspend fun recordPayment(transaction: PaymentTransactionEntity): Long {
        val id = paymentDao.insertTransaction(transaction)
        if (transaction.status == "VALIDEE") {
            userDao.updatePremiumStatus(transaction.userId, true)
        }
        return id
    }

    // Notifications
    fun getAllNotifications(): Flow<List<NotificationEntity>> = notificationDao.getAllNotifications()
    fun countUnreadNotifications(): Flow<Int> = notificationDao.countUnreadNotifications()
    suspend fun insertNotification(notification: NotificationEntity): Long = notificationDao.insertNotification(notification)
    suspend fun markNotificationAsRead(id: Long) = notificationDao.markAsRead(id)
    suspend fun markAllNotificationsAsRead() = notificationDao.markAllAsRead()

    // Admin Logs
    fun getRecentLogs(): Flow<List<AdminLogEntity>> = adminLogDao.getRecentLogs()
    suspend fun logAdminAction(adminName: String, action: String, targetItem: String, details: String = "") {
        adminLogDao.insertLog(AdminLogEntity(adminName = adminName, action = action, targetItem = targetItem, details = details))
    }

    // Draft Autosave
    suspend fun saveDraft(draft: DraftBackupEntity) = draftDao.saveDraft(draft)
    suspend fun getDraft(key: String): DraftBackupEntity? = draftDao.getDraft(key)
    suspend fun clearDraft(key: String) = draftDao.clearDraft(key)

    // Search
    fun searchLessons(query: String): Flow<List<LessonEntity>> = courseDao.searchLessons(query)
}
