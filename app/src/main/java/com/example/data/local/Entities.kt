package com.example.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["email"], unique = true),
        Index(value = ["phoneNumber"])
    ]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val email: String,
    val passwordHash: String,
    val firstName: String,
    val lastName: String,
    val className: String, // e.g., "4e", "3e", "Terminale D", "CM2"
    val phoneNumber: String = "",
    val schoolName: String = "",
    val role: String = "student", // "student", "teacher", "admin", "owner"
    val isPremium: Boolean = false,
    val isSuspended: Boolean = false,
    val xp: Int = 0,
    val streak: Int = 1,
    val studyTimeMinutes: Int = 0,
    val level: Int = 1,
    val avatarUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "level_categories")
data class LevelCategoryEntity(
    @PrimaryKey val id: String, // "primaire", "college", "lycee"
    val name: String,
    val orderIndex: Int
)

@Entity(tableName = "grade_classes")
data class GradeClassEntity(
    @PrimaryKey val id: String, // "cp1", "cp2", "6e", "5e", "4e", "3e", "2nde", "1ere", "terminale"
    val levelId: String,
    val displayName: String,
    val orderIndex: Int
)

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val classId: String, // "all" or specific class "4e", "3e", etc.
    val name: String, // "Mathématiques", "Français", "Anglais", "SVT", etc.
    val iconName: String = "book",
    val colorHex: String = "#0A7E48",
    val isNationalExamSubject: Boolean = false
)

@Entity(tableName = "chapters")
data class ChapterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectId: Long,
    val classId: String,
    val title: String,
    val summary: String = "",
    val orderIndex: Int = 0
)

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val chapterId: Long,
    val title: String,
    val summary: String,
    val pedagogicalObjectives: String,
    val content: String,
    val imagesJson: String = "",
    val durationMinutes: Int = 20,
    val difficulty: String = "Moyen", // "Facile", "Moyen", "Difficile"
    val isPremium: Boolean = false,
    val isDraft: Boolean = false,
    val sourceReference: String = "Programme officiel MENA Côte d'Ivoire",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val classId: String,
    val subjectId: Long,
    val chapterId: Long,
    val title: String,
    val instructions: String,
    val question: String,
    val type: String = "qcm", // "qcm", "true_false", "short_answer", "numeric", "multi_choice"
    val optionsJson: String = "", // separated by ";;"
    val correctAnswer: String,
    val explanation: String,
    val points: Int = 10,
    val durationMinutes: Int = 5,
    val difficulty: String = "Moyen",
    val isPremium: Boolean = false
)

@Entity(tableName = "exams")
data class ExamEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val examType: String, // "CEPE", "BEPC", "BAC", "Devoir", "Composition"
    val series: String = "Général", // "Général", "Série A", "Série C", "Série D"
    val year: Int,
    val subject: String,
    val durationMinutes: Int = 120,
    val instructions: String = "",
    val content: String,
    val solution: String = "",
    val gradingScale: String = "",
    val isPremium: Boolean = false,
    val sourceReference: String = "Direction des Examens et Concours (DECO - Côte d'Ivoire)"
)

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val lessonId: Long,
    val isCompleted: Boolean = true,
    val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_exercise_attempts")
data class UserExerciseAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val exerciseId: Long,
    val studentAnswer: String,
    val isCorrect: Boolean,
    val score: Int,
    val attemptedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val message: String,
    val type: String = "announcement", // "new_lesson", "new_exercise", "exam_ready", "announcement", "reminder", "premium"
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)

@Entity(tableName = "admin_logs")
data class AdminLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val adminName: String,
    val action: String,
    val targetItem: String,
    val details: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "draft_backups")
data class DraftBackupEntity(
    @PrimaryKey val draftKey: String, // "lesson_editor_current"
    val level: String = "",
    val className: String = "",
    val subjectName: String = "",
    val chapterTitle: String = "",
    val title: String = "",
    val summary: String = "",
    val objectives: String = "",
    val content: String = "",
    val duration: Int = 20,
    val difficulty: String = "Moyen",
    val isPremium: Boolean = false,
    val lastSavedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "favorites",
    indices = [Index(value = ["userId", "itemType", "itemId"], unique = true)]
)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val itemType: String, // "lesson", "exercise", "exam"
    val itemId: Long,
    val title: String,
    val subjectName: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "offline_downloads",
    indices = [Index(value = ["userId", "itemType", "itemId"], unique = true)]
)
data class OfflineDownloadEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val itemType: String, // "lesson", "exercise", "exam"
    val itemId: Long,
    val title: String,
    val subjectName: String,
    val summary: String = "",
    val content: String = "",
    val sizeKb: Int = 125,
    val downloadedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "payment_transactions")
data class PaymentTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val transactionRef: String,
    val provider: String, // "Wave", "Orange Money", "MTN MoMo", "Moov Money", "Carte Bancaire"
    val phoneNumber: String,
    val amountFcfa: Int,
    val planName: String, // "Mensuel (2 500 FCFA)", "Trimestriel (6 000 FCFA)", "Annuel (18 000 FCFA)"
    val durationDays: Int,
    val status: String = "VALIDEE", // "VALIDEE", "EN_ATTENTE", "ECHOUEE"
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + (30L * 24 * 3600 * 1000)
)

@Entity(tableName = "exam_submissions")
data class ExamSubmissionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val examId: Long,
    val examTitle: String,
    val subject: String,
    val score: Float, // e.g. 16.5
    val maxScore: Float = 20f,
    val timeSpentSeconds: Int = 0,
    val isPassed: Boolean = true,
    val submittedAt: Long = System.currentTimeMillis()
)
