package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.GeminiAiService
import com.example.data.local.*
import com.example.data.repository.EduRepository
import com.example.data.seed.DataSeeder
import com.example.ui.navigation.AppScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class ThemeMode {
    LIGHT, // Vert-Blanc
    DARK,  // Mode Sombre
    SYSTEM // Système
}

const val OWNER_EMAIL = "horizonprogrammeur@gmail.com"
const val OWNER_MASTER_KEY = "EDUCI-PROPRIETAIRE-2026"

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class EduViewModel(application: Application) : AndroidViewModel(application) {
    val repository = EduRepository(application)

    // Theme Mode
    private val _themeMode = MutableStateFlow(ThemeMode.LIGHT)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    fun toggleTheme() {
        _themeMode.value = if (_themeMode.value == ThemeMode.DARK) ThemeMode.LIGHT else ThemeMode.DARK
    }

    // Current navigation screen and previous screen stack
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val screenStack = mutableListOf<AppScreen>()

    // Current logged-in user
    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    // Curriculum flows
    val levels: StateFlow<List<LevelCategoryEntity>> = repository.getAllLevels()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val classes: StateFlow<List<GradeClassEntity>> = repository.getAllClasses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selection states
    private val _selectedLevel = MutableStateFlow("college")
    val selectedLevel: StateFlow<String> = _selectedLevel.asStateFlow()

    private val _selectedClass = MutableStateFlow("4e")
    val selectedClass: StateFlow<String> = _selectedClass.asStateFlow()

    private val _selectedSubject = MutableStateFlow<SubjectEntity?>(null)
    val selectedSubject: StateFlow<SubjectEntity?> = _selectedSubject.asStateFlow()

    private val _selectedChapter = MutableStateFlow<ChapterEntity?>(null)
    val selectedChapter: StateFlow<ChapterEntity?> = _selectedChapter.asStateFlow()

    private val _currentLesson = MutableStateFlow<LessonEntity?>(null)
    val currentLesson: StateFlow<LessonEntity?> = _currentLesson.asStateFlow()

    // Lessons for selected chapter
    val currentChapterLessons: StateFlow<List<LessonEntity>> = _selectedChapter
        .flatMapLatest { chapter ->
            if (chapter != null) repository.getLessonsForChapter(chapter.id)
            else flowOf(emptyList())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // All published lessons for home / recent
    val allPublishedLessons: StateFlow<List<LessonEntity>> = repository.getAllPublishedLessons()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Subjects for current class
    val classSubjects: StateFlow<List<SubjectEntity>> = _selectedClass
        .flatMapLatest { cls -> repository.getSubjectsForClass(cls) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Exercises
    val exercises: StateFlow<List<ExerciseEntity>> = _selectedClass
        .flatMapLatest { cls -> repository.getExercisesForClass(cls) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _activeExercise = MutableStateFlow<ExerciseEntity?>(null)
    val activeExercise: StateFlow<ExerciseEntity?> = _activeExercise.asStateFlow()

    private val _exerciseAnswerResult = MutableStateFlow<ExerciseResult?>(null)
    val exerciseAnswerResult: StateFlow<ExerciseResult?> = _exerciseAnswerResult.asStateFlow()

    // National Exams
    private val _selectedExamType = MutableStateFlow("BEPC")
    val selectedExamType: StateFlow<String> = _selectedExamType.asStateFlow()

    val exams: StateFlow<List<ExamEntity>> = _selectedExamType
        .flatMapLatest { type -> repository.getExamsByType(type) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _activeExam = MutableStateFlow<ExamEntity?>(null)
    val activeExam: StateFlow<ExamEntity?> = _activeExam.asStateFlow()

    private val _showExamSolution = MutableStateFlow(false)
    val showExamSolution: StateFlow<Boolean> = _showExamSolution.asStateFlow()

    // AI Chat
    private val _aiMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                sender = "ai",
                text = "Bonjour ! Je suis le Professeur EduCI, ton tuteur personnel. Quelle notion ou exercice souhaites-tu travailler ensemble ?"
            )
        )
    )
    val aiMessages: StateFlow<List<ChatMessage>> = _aiMessages.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    // Notifications
    val notifications: StateFlow<List<NotificationEntity>> = repository.getAllNotifications()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadNotificationsCount: StateFlow<Int> = repository.countUnreadNotifications()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // User Progress
    val userCompletedLessonsCount: StateFlow<Int> = _currentUser
        .flatMapLatest { user ->
            if (user != null) repository.countCompletedLessons(user.id)
            else flowOf(0)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val userExerciseAttemptsCount: StateFlow<Int> = _currentUser
        .flatMapLatest { user ->
            if (user != null) repository.countUserAttempts(user.id)
            else flowOf(0)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val searchLessonResults: StateFlow<List<LessonEntity>> = _searchQuery
        .debounce(250)
        .flatMapLatest { query ->
            if (query.isBlank()) flowOf(emptyList())
            else repository.searchLessons(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchExamResults: StateFlow<List<ExamEntity>> = _searchQuery
        .debounce(250)
        .flatMapLatest { query ->
            if (query.isBlank()) flowOf(emptyList())
            else repository.searchExams(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Admin flows & stats
    val allUsers: StateFlow<List<UserEntity>> = repository.getAllUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalUsersCount: StateFlow<Int> = repository.countUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalLessonsCount: StateFlow<Int> = repository.countPublishedLessons()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalExercisesCount: StateFlow<Int> = repository.countExercises()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalExamsCount: StateFlow<Int> = repository.countExams()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val adminLogs: StateFlow<List<AdminLogEntity>> = repository.getRecentLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Admin Course Editor Draft state
    private val _editorDraftRestored = MutableStateFlow(false)
    val editorDraftRestored: StateFlow<Boolean> = _editorDraftRestored.asStateFlow()

    init {
        viewModelScope.launch {
            // Seed DB (curriculum, classes, subjects, exams)
            DataSeeder.seedIfNeeded(repository.getDatabase())
            // No default user auto-login: start on AuthScreen
            _currentUser.value = null
            _currentScreen.value = AppScreen.AUTH
        }
    }

    // Role & Owner Permissions Check
    fun isOwnerOrAdmin(): Boolean {
        val role = _currentUser.value?.role
        val email = _currentUser.value?.email
        return role == "owner" || role == "admin" || (email != null && email.equals(OWNER_EMAIL, ignoreCase = true))
    }

    // Navigation
    fun navigateTo(screen: AppScreen) {
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenStack.isNotEmpty()) {
            _currentScreen.value = screenStack.removeAt(screenStack.size - 1)
            return true
        }
        return false
    }

    // Auth
    fun login(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email.trim().lowercase())
            if (user != null && user.passwordHash == pass) {
                _currentUser.value = user
                _selectedClass.value = user.className
                _currentScreen.value = if (user.role in listOf("owner", "admin")) AppScreen.ADMIN else AppScreen.HOME
                screenStack.clear()
                onResult(true, "Connexion réussie")
            } else {
                onResult(false, "Identifiants invalides")
            }
        }
    }

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        pass: String,
        className: String,
        ownerPasscode: String = "",
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            val cleanEmail = email.trim().lowercase()
            val existing = repository.getUserByEmail(cleanEmail)
            if (existing != null) {
                onResult(false, "Un compte existe déjà avec cet email")
                return@launch
            }
            val isOwner = cleanEmail == OWNER_EMAIL.lowercase() || ownerPasscode.trim() == OWNER_MASTER_KEY
            val role = if (isOwner) "owner" else "student"
            val newUser = UserEntity(
                email = cleanEmail,
                passwordHash = pass,
                firstName = firstName.trim(),
                lastName = lastName.trim(),
                className = if (isOwner) "Direction" else className,
                role = role,
                isPremium = isOwner,
                xp = if (isOwner) 1000 else 50,
                streak = 1,
                studyTimeMinutes = 0
            )
            val newId = repository.registerUser(newUser)
            val created = newUser.copy(id = newId)
            _currentUser.value = created
            _selectedClass.value = if (isOwner) "4e" else className
            _currentScreen.value = if (isOwner) AppScreen.ADMIN else AppScreen.HOME
            screenStack.clear()
            val msg = if (isOwner) "Compte Propriétaire activé avec succès !" else "Compte créé avec succès ! Bienvenue sur EduCI."
            onResult(true, msg)
        }
    }

    fun claimOwnerAccess(passcode: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            if (passcode.trim() == OWNER_MASTER_KEY) {
                val user = _currentUser.value
                if (user != null) {
                    val updated = user.copy(role = "owner", isPremium = true)
                    repository.updateUser(updated)
                    _currentUser.value = updated
                    repository.logAdminAction(
                        adminName = "${user.firstName} ${user.lastName}",
                        action = "Authentification Propriétaire",
                        targetItem = "Droits Propriétaire accordés",
                        details = "Clé maître propriétaire validée"
                    )
                    navigateTo(AppScreen.ADMIN)
                    onResult(true, "Accès Propriétaire déverrouillé avec succès !")
                } else {
                    val ownerUser = UserEntity(
                        email = OWNER_EMAIL,
                        passwordHash = "admin",
                        firstName = "Propriétaire",
                        lastName = "EduCI",
                        className = "Direction",
                        role = "owner",
                        isPremium = true,
                        xp = 2500,
                        streak = 30
                    )
                    val id = repository.registerUser(ownerUser)
                    _currentUser.value = ownerUser.copy(id = id)
                    navigateTo(AppScreen.ADMIN)
                    onResult(true, "Session Propriétaire EduCI ouverte avec succès !")
                }
            } else {
                onResult(false, "Clé d'administration invalide. Accès refusé.")
            }
        }
    }

    fun togglePremium() {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val newPremium = !user.isPremium
            repository.updatePremium(user.id, newPremium)
            val updated = user.copy(isPremium = newPremium)
            _currentUser.value = updated
        }
    }

    fun logout() {
        _currentUser.value = null
        _currentScreen.value = AppScreen.AUTH
        screenStack.clear()
    }

    // Courses selection
    fun selectLevel(levelId: String) {
        _selectedLevel.value = levelId
    }

    fun selectClass(cls: String) {
        _selectedClass.value = cls
        val user = _currentUser.value
        if (user != null && user.className != cls) {
            viewModelScope.launch {
                val updated = user.copy(className = cls)
                repository.updateUser(updated)
                _currentUser.value = updated
            }
        }
    }

    fun selectSubject(subject: SubjectEntity) {
        _selectedSubject.value = subject
        _selectedChapter.value = null
        _currentLesson.value = null
    }

    fun selectChapter(chapter: ChapterEntity) {
        _selectedChapter.value = chapter
    }

    fun openLesson(lesson: LessonEntity) {
        _currentLesson.value = lesson
        navigateTo(AppScreen.LESSON_DETAIL)
    }

    fun completeCurrentLesson() {
        val user = _currentUser.value ?: return
        val lesson = _currentLesson.value ?: return
        viewModelScope.launch {
            repository.markLessonCompleted(user.id, lesson.id)
            // Reload user
            val updated = repository.getUserByEmail(user.email)
            if (updated != null) _currentUser.value = updated
        }
    }

    // Exercises
    fun openExercise(exercise: ExerciseEntity) {
        _activeExercise.value = exercise
        _exerciseAnswerResult.value = null
    }

    fun submitExerciseAnswer(studentAnswer: String) {
        val user = _currentUser.value ?: return
        val exercise = _activeExercise.value ?: return
        val isCorrect = studentAnswer.trim().equals(exercise.correctAnswer.trim(), ignoreCase = true)

        viewModelScope.launch {
            repository.recordAttempt(
                userId = user.id,
                exerciseId = exercise.id,
                studentAnswer = studentAnswer,
                isCorrect = isCorrect,
                points = exercise.points
            )
            _exerciseAnswerResult.value = ExerciseResult(
                studentAnswer = studentAnswer,
                correctAnswer = exercise.correctAnswer,
                isCorrect = isCorrect,
                explanation = exercise.explanation,
                pointsEarned = if (isCorrect) exercise.points else 0
            )
            // Reload user stats
            val updated = repository.getUserByEmail(user.email)
            if (updated != null) _currentUser.value = updated
        }
    }

    fun clearExerciseResult() {
        _exerciseAnswerResult.value = null
    }

    // National Exams
    fun selectExamType(type: String) {
        _selectedExamType.value = type
    }

    fun openExam(exam: ExamEntity) {
        _activeExam.value = exam
        _showExamSolution.value = false
    }

    fun toggleExamSolution() {
        _showExamSolution.value = !_showExamSolution.value
    }

    // AI Pedagogical Assistant
    fun sendAiMessage(message: String) {
        val cleanMsg = message.trim()
        if (cleanMsg.isBlank()) return

        val user = _currentUser.value
        val studentClass = user?.className ?: _selectedClass.value
        val lessonContext = _currentLesson.value?.let { "${it.title}: ${it.summary}" }

        val newHistory = _aiMessages.value + ChatMessage(sender = "user", text = cleanMsg)
        _aiMessages.value = newHistory
        _isAiLoading.value = true

        viewModelScope.launch {
            val response = GeminiAiService.askTeacher(
                userMessage = cleanMsg,
                studentClass = studentClass,
                lessonContext = lessonContext,
                history = newHistory.dropLast(1).map { it.sender to it.text }
            )
            _aiMessages.value = _aiMessages.value + ChatMessage(sender = "ai", text = response)
            _isAiLoading.value = false
        }
    }

    // Search
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Notifications
    fun markNotificationRead(id: Long) {
        viewModelScope.launch {
            repository.markNotificationAsRead(id)
        }
    }

    fun markAllNotificationsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsAsRead()
        }
    }

    // Admin Course Creation & AutoSave
    fun autoSaveDraft(draft: DraftBackupEntity) {
        viewModelScope.launch {
            repository.saveDraft(draft)
        }
    }

    suspend fun loadEditorDraft(): DraftBackupEntity? {
        val draft = repository.getDraft("lesson_editor_current")
        if (draft != null) {
            _editorDraftRestored.value = true
        }
        return draft
    }

    fun clearDraftNotification() {
        _editorDraftRestored.value = false
    }

    fun publishLesson(
        chapterId: Long,
        title: String,
        summary: String,
        objectives: String,
        content: String,
        duration: Int,
        difficulty: String,
        isPremium: Boolean,
        isDraft: Boolean,
        onSuccess: () -> Unit
    ) {
        if (!isOwnerOrAdmin()) return
        val adminName = _currentUser.value?.let { "${it.firstName} ${it.lastName}" } ?: "Propriétaire"
        viewModelScope.launch {
            val lesson = LessonEntity(
                chapterId = chapterId,
                title = title,
                summary = summary,
                pedagogicalObjectives = objectives,
                content = content,
                durationMinutes = duration,
                difficulty = difficulty,
                isPremium = isPremium,
                isDraft = isDraft
            )
            val newId = repository.insertLesson(lesson)
            repository.clearDraft("lesson_editor_current")
            repository.logAdminAction(
                adminName = adminName,
                action = if (isDraft) "Enregistrement brouillon" else "Publication leçon",
                targetItem = "Leçon #$newId: $title",
                details = "Matière ch#$chapterId, durée ${duration}min, premium=$isPremium"
            )
            // Send announcement notification to all students
            if (!isDraft) {
                repository.insertNotification(
                    NotificationEntity(
                        title = "Nouvelle leçon disponible !",
                        message = "Découvre « $title » dans tes cours dès maintenant.",
                        type = "new_lesson"
                    )
                )
            }
            onSuccess()
        }
    }

    fun createExerciseAdmin(
        classId: String,
        subjectId: Long,
        chapterId: Long,
        title: String,
        instructions: String,
        question: String,
        type: String,
        options: String,
        correctAnswer: String,
        explanation: String,
        points: Int,
        difficulty: String,
        isPremium: Boolean,
        onSuccess: () -> Unit
    ) {
        if (!isOwnerOrAdmin()) return
        val adminName = _currentUser.value?.let { "${it.firstName} ${it.lastName}" } ?: "Propriétaire"
        viewModelScope.launch {
            val ex = ExerciseEntity(
                classId = classId,
                subjectId = subjectId,
                chapterId = chapterId,
                title = title,
                instructions = instructions,
                question = question,
                type = type,
                optionsJson = options,
                correctAnswer = correctAnswer,
                explanation = explanation,
                points = points,
                difficulty = difficulty,
                isPremium = isPremium
            )
            val id = repository.insertExercise(ex)
            repository.logAdminAction(
                adminName = adminName,
                action = "Création exercice",
                targetItem = "Exercice #$id: $title",
                details = "Classe $classId, $points pts"
            )
            onSuccess()
        }
    }

    fun createExamAdmin(
        title: String,
        examType: String,
        series: String,
        year: Int,
        subject: String,
        durationMinutes: Int,
        instructions: String,
        content: String,
        solution: String,
        gradingScale: String,
        isPremium: Boolean,
        onSuccess: () -> Unit
    ) {
        if (!isOwnerOrAdmin()) return
        val adminName = _currentUser.value?.let { "${it.firstName} ${it.lastName}" } ?: "Propriétaire"
        viewModelScope.launch {
            val exam = ExamEntity(
                title = title,
                examType = examType,
                series = series,
                year = year,
                subject = subject,
                durationMinutes = durationMinutes,
                instructions = instructions,
                content = content,
                solution = solution,
                gradingScale = gradingScale,
                isPremium = isPremium
            )
            val id = repository.insertExam(exam)
            repository.logAdminAction(
                adminName = adminName,
                action = "Ajout sujet officiel",
                targetItem = "Examen #$id: $title ($year)",
                details = "Type $examType, Série $series"
            )
            onSuccess()
        }
    }

    fun deleteLessonAdmin(lessonId: Long, onSuccess: () -> Unit = {}) {
        if (!isOwnerOrAdmin()) return
        val adminName = _currentUser.value?.let { "${it.firstName} ${it.lastName}" } ?: "Propriétaire"
        viewModelScope.launch {
            repository.deleteLesson(lessonId)
            repository.logAdminAction(
                adminName = adminName,
                action = "Suppression leçon",
                targetItem = "Leçon #$lessonId"
            )
            onSuccess()
        }
    }

    fun deleteExerciseAdmin(exerciseId: Long, onSuccess: () -> Unit = {}) {
        if (!isOwnerOrAdmin()) return
        val adminName = _currentUser.value?.let { "${it.firstName} ${it.lastName}" } ?: "Propriétaire"
        viewModelScope.launch {
            repository.deleteExercise(exerciseId)
            repository.logAdminAction(
                adminName = adminName,
                action = "Suppression exercice",
                targetItem = "Exercice #$exerciseId"
            )
            onSuccess()
        }
    }

    fun deleteExamAdmin(examId: Long, onSuccess: () -> Unit = {}) {
        if (!isOwnerOrAdmin()) return
        val adminName = _currentUser.value?.let { "${it.firstName} ${it.lastName}" } ?: "Propriétaire"
        viewModelScope.launch {
            repository.deleteExam(examId)
            repository.logAdminAction(
                adminName = adminName,
                action = "Suppression examen",
                targetItem = "Examen #$examId"
            )
            onSuccess()
        }
    }

    fun deleteUserAdmin(userId: Long, onSuccess: () -> Unit = {}) {
        if (!isOwnerOrAdmin()) return
        val adminName = _currentUser.value?.let { "${it.firstName} ${it.lastName}" } ?: "Propriétaire"
        viewModelScope.launch {
            repository.deleteUser(userId)
            repository.logAdminAction(
                adminName = adminName,
                action = "Suppression utilisateur",
                targetItem = "Utilisateur #$userId"
            )
            onSuccess()
        }
    }

    fun switchRoleForDemo() {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val newRole = if (user.role in listOf("owner", "admin")) "student" else "owner"
            val updated = user.copy(role = newRole, isPremium = newRole == "owner")
            repository.updateUser(updated)
            _currentUser.value = updated
        }
    }
}

data class ChatMessage(
    val sender: String, // "user" or "ai"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class ExerciseResult(
    val studentAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean,
    val explanation: String,
    val pointsEarned: Int
)
