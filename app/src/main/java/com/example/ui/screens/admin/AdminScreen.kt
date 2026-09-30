package com.example.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.DraftBackupEntity
import com.example.ui.components.RichContentRenderer
import com.example.ui.components.RichPedagogicalEditor
import com.example.ui.navigation.AppScreen
import com.example.ui.screens.home.StatCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.EduViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    viewModel: EduViewModel,
    modifier: Modifier = Modifier
) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val isOwnerOrAdmin = viewModel.isOwnerOrAdmin()

    if (!isOwnerOrAdmin) {
        AdminAccessDeniedView(
            onUnlock = { key, onResult -> viewModel.claimOwnerAccess(key, onResult) },
            onBack = { viewModel.navigateTo(AppScreen.HOME) },
            modifier = modifier
        )
        return
    }

    var adminSubTab by remember { mutableStateOf(0) }
    // 0: Dashboard, 1: Créer Leçon (Éditeur Riche), 2: Créer Exercice, 3: Examens, 4: Utilisateurs, 5: Journal

    val totalUsers by viewModel.totalUsersCount.collectAsStateWithLifecycle()
    val totalLessons by viewModel.totalLessonsCount.collectAsStateWithLifecycle()
    val totalExercises by viewModel.totalExercisesCount.collectAsStateWithLifecycle()
    val totalExams by viewModel.totalExamsCount.collectAsStateWithLifecycle()
    val allUsers by viewModel.allUsers.collectAsStateWithLifecycle()
    val adminLogs by viewModel.adminLogs.collectAsStateWithLifecycle()

    val subTabs = listOf(
        "Dashboard",
        "+ Leçon (Éditeur)",
        "+ Exercice",
        "+ Examen",
        "Élèves",
        "Journal"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Admin Header
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = EduCiOrangeAccent)
                        Text(
                            text = "Espace Administrateur",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Button(
                        onClick = { viewModel.navigateTo(AppScreen.HOME) },
                        colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Vue Élève", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Scrollable subtabs
                val scrollState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for ((index, tab) in subTabs.withIndex()) {
                        val isSelected = adminSubTab == index
                        FilterChip(
                            selected = isSelected,
                            onClick = { adminSubTab = index },
                            label = { Text(tab, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EduCiOrangeAccent,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
            }
        }

        // Subtab Views
        when (adminSubTab) {
            0 -> AdminDashboardView(
                totalUsers = totalUsers,
                totalLessons = totalLessons,
                totalExercises = totalExercises,
                totalExams = totalExams,
                onNavigate = { adminSubTab = it }
            )
            1 -> AdminRichLessonEditor(viewModel = viewModel)
            2 -> AdminCreateExerciseView(viewModel = viewModel)
            3 -> AdminCreateExamView(viewModel = viewModel)
            4 -> AdminUsersListView(users = allUsers, viewModel = viewModel)
            5 -> AdminAuditLogsView(logs = adminLogs)
        }
    }
}

@Composable
fun AdminDashboardView(
    totalUsers: Int,
    totalLessons: Int,
    totalExercises: Int,
    totalExams: Int,
    onNavigate: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Statistiques de la Plateforme EduCI",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Élèves inscrits",
                    value = "$totalUsers",
                    icon = Icons.Default.People,
                    iconColor = EduCiGreenPrimary
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Leçons publiées",
                    value = "$totalLessons",
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    iconColor = Color(0xFF2563EB)
                )
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Exercices créés",
                    value = "$totalExercises",
                    icon = Icons.Default.Quiz,
                    iconColor = EduCiOrangeAccent
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Sujets d'examens",
                    value = "$totalExams",
                    icon = Icons.AutoMirrored.Filled.Assignment,
                    iconColor = EduCiGoldXp
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Actions rapides de gestion",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(1) },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = EduCiGreenContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.EditNote, contentDescription = null, tint = EduCiGreenDark, modifier = Modifier.size(28.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Créer une nouvelle leçon", fontWeight = FontWeight.Bold, color = EduCiGreenDark)
                        Text("Éditeur riche, formules mathématiques et import de document", fontSize = 12.sp, color = EduCiGreenDark.copy(alpha = 0.8f))
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = EduCiGreenDark)
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(2) },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = EduCiOrangeLight)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = null, tint = EduCiOrangeDark, modifier = Modifier.size(28.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Ajouter un exercice ou quiz", fontWeight = FontWeight.Bold, color = EduCiOrangeDark)
                        Text("QCM, vrai/faux, points et explications détaillées", fontSize = 12.sp, color = EduCiOrangeDark.copy(alpha = 0.8f))
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = EduCiOrangeDark)
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(3) },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.PostAdd, contentDescription = null, tint = Color(0xFF1D4ED8), modifier = Modifier.size(28.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Ajouter un sujet d'examen officiel", fontWeight = FontWeight.Bold, color = Color(0xFF1D4ED8))
                        Text("CEPE, BEPC, BAC avec corrigé type et barème", fontSize = 12.sp, color = Color(0xFF1E40AF))
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF1D4ED8))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRichLessonEditor(viewModel: EduViewModel) {
    var title by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var objectives by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("25") }
    var difficulty by remember { mutableStateOf("Moyen") }
    var isPremium by remember { mutableStateOf(false) }

    var selectedClass by remember { mutableStateOf("4e") }
    var chapterId by remember { mutableStateOf(1L) }

    var isPreviewMode by remember { mutableStateOf(false) }
    var draftSavedNotice by remember { mutableStateOf(false) }
    var draftRestoredNotice by remember { mutableStateOf(false) }
    var successPublishNotice by remember { mutableStateOf(false) }
    var showDocImportDialog by remember { mutableStateOf(false) }

    // Check draft restoration on mount
    LaunchedEffect(Unit) {
        val draft = viewModel.loadEditorDraft()
        if (draft != null && draft.title.isNotEmpty()) {
            title = draft.title
            summary = draft.summary
            objectives = draft.objectives
            content = draft.content
            duration = draft.duration.toString()
            difficulty = draft.difficulty
            isPremium = draft.isPremium
            draftRestoredNotice = true
        }
    }

    // Auto-save draft periodically when content changes
    LaunchedEffect(title, summary, objectives, content) {
        if (title.isNotEmpty() || content.isNotEmpty()) {
            delay(1500)
            viewModel.autoSaveDraft(
                DraftBackupEntity(
                    draftKey = "lesson_editor_current",
                    className = selectedClass,
                    title = title,
                    summary = summary,
                    objectives = objectives,
                    content = content,
                    duration = duration.toIntOrNull() ?: 20,
                    difficulty = difficulty,
                    isPremium = isPremium
                )
            )
            draftSavedNotice = true
            delay(2000)
            draftSavedNotice = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Notification Pill
        if (draftRestoredNotice) {
            item {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFEFF6FF),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("📋 Brouillon précédent restauré avec succès !", fontSize = 12.sp, color = Color(0xFF1D4ED8))
                        IconButton(onClick = { draftRestoredNotice = false }, modifier = Modifier.size(20.dp)) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = Color(0xFF1D4ED8))
                        }
                    }
                }
            }
        }

        if (draftSavedNotice) {
            item {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = EduCiGreenContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "💾 Sauvegarde automatique effectuée",
                        fontSize = 11.sp,
                        color = EduCiGreenDark,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        if (successPublishNotice) {
            item {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = EduCiGreenContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "🎉 Leçon publiée avec succès dans le programme élève !",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = EduCiGreenDark,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        // Preview Toggle & Import Document Button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { showDocImportDialog = true },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Importer document (Word/PDF/MD)", fontSize = 12.sp)
                }

                FilterChip(
                    selected = isPreviewMode,
                    onClick = { isPreviewMode = !isPreviewMode },
                    label = { Text(if (isPreviewMode) "Mode Édition" else "Aperçu Élève Exact") },
                    leadingIcon = { Icon(if (isPreviewMode) Icons.Default.Edit else Icons.Default.Visibility, contentDescription = null) },
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        if (!isPreviewMode) {
            // Fields: Class, Title, Duration, Difficulty, Premium
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Titre de la leçon *") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = duration,
                        onValueChange = { duration = it },
                        label = { Text("Durée (min)") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    OutlinedTextField(
                        value = difficulty,
                        onValueChange = { difficulty = it },
                        label = { Text("Difficulté (Facile/Moyen/Difficile)") },
                        modifier = Modifier.weight(1.5f),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = isPremium, onCheckedChange = { isPremium = it })
                        Text("Contenu réservé Premium", fontSize = 13.sp)
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = summary,
                    onValueChange = { summary = it },
                    label = { Text("Résumé rapide pour l'élève") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = objectives,
                    onValueChange = { objectives = it },
                    label = { Text("Objectifs pédagogiques (1 par ligne)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    minLines = 2
                )
            }

            // PROFESSIONAL PEDAGOGICAL RICH CONTENT EDITOR
            item {
                Text(
                    text = "Éditeur Pédagogique Professionnel (Norme MENA Côte d'Ivoire)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
                RichPedagogicalEditor(
                    content = content,
                    onContentChange = { content = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Action Buttons: Save draft / Publish
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            viewModel.autoSaveDraft(
                                DraftBackupEntity(
                                    draftKey = "lesson_editor_current",
                                    className = selectedClass,
                                    title = title,
                                    summary = summary,
                                    objectives = objectives,
                                    content = content,
                                    duration = duration.toIntOrNull() ?: 20,
                                    difficulty = difficulty,
                                    isPremium = isPremium
                                )
                            )
                            draftSavedNotice = true
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Enregistrer Brouillon")
                    }

                    Button(
                        onClick = {
                            if (title.isBlank() || content.isBlank()) return@Button
                            viewModel.publishLesson(
                                chapterId = chapterId,
                                title = title,
                                summary = summary,
                                objectives = objectives,
                                content = content,
                                duration = duration.toIntOrNull() ?: 20,
                                difficulty = difficulty,
                                isPremium = isPremium,
                                isDraft = false,
                                onSuccess = {
                                    successPublishNotice = true
                                    title = ""
                                    summary = ""
                                    objectives = ""
                                    content = ""
                                }
                            )
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
                    ) {
                        Text("Publier la Leçon", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            // EXACT STUDENT PREVIEW
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = if (title.isNotEmpty()) title else "Titre de la leçon",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (summary.isNotEmpty()) {
                            Text(text = summary, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        RichContentRenderer(content = content)
                    }
                }
            }
        }
    }

    // Document Import Dialog (Word / PDF / Markdown parser simulator)
    if (showDocImportDialog) {
        var docTextInput by remember { mutableStateOf("") }
        var preserveFormatting by remember { mutableStateOf(true) }

        AlertDialog(
            onDismissRequest = { showDocImportDialog = false },
            title = { Text("Importer un document de cours") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Colle ici le texte provenant de ton Word, Google Docs, PDF ou ChatGPT. Notre moteur intelligent extrait les titres, listes, formules et tableaux.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = preserveFormatting, onCheckedChange = { preserveFormatting = it })
                        Text(if (preserveFormatting) "Conserver la mise en forme" else "Coller sans mise en forme", fontSize = 12.sp)
                    }

                    OutlinedTextField(
                        value = docTextInput,
                        onValueChange = { docTextInput = it },
                        placeholder = { Text("Colle ton document ici...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 6,
                        maxLines = 12
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (docTextInput.isNotBlank()) {
                            // Extract title if starts with #
                            val lines = docTextInput.lines()
                            val firstLine = lines.firstOrNull()?.trim() ?: ""
                            if (firstLine.startsWith("# ")) {
                                title = firstLine.removePrefix("# ")
                                content = lines.drop(1).joinToString("\n")
                            } else {
                                content = docTextInput
                            }
                            showDocImportDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
                ) {
                    Text("Importer dans l'éditeur")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDocImportDialog = false }) {
                    Text("Annuler")
                }
            }
        )
    }
}

@Composable
fun EditorToolBtn(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
    ) {
        Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun AdminCreateExerciseView(viewModel: EduViewModel) {
    var title by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("Choisis la bonne réponse.") }
    var question by remember { mutableStateOf("") }
    var options by remember { mutableStateOf("Option 1;;Option 2;;Option 3;;Option 4") }
    var correctAnswer by remember { mutableStateOf("") }
    var explanation by remember { mutableStateOf("") }
    var points by remember { mutableStateOf("10") }
    var difficulty by remember { mutableStateOf("Moyen") }
    var targetClass by remember { mutableStateOf("4e") }
    var isSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Créer un nouvel exercice d'entraînement", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        if (isSuccess) {
            item {
                Surface(color = EduCiGreenContainer, shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Text("Exercice enregistré avec succès !", color = EduCiGreenDark, modifier = Modifier.padding(12.dp))
                }
            }
        }

        item {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Titre de l'exercice *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = targetClass,
                onValueChange = { targetClass = it },
                label = { Text("Classe ciblée (ex : 4e, 3e, Terminale)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = question,
                onValueChange = { question = it },
                label = { Text("Énoncé de la question *") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                shape = RoundedCornerShape(10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = options,
                onValueChange = { options = it },
                label = { Text("Options de réponse (séparées par ;;) *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = correctAnswer,
                onValueChange = { correctAnswer = it },
                label = { Text("Bonne réponse exacte *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = explanation,
                onValueChange = { explanation = it },
                label = { Text("Explication pédagogique affichée à l'élève") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                shape = RoundedCornerShape(10.dp)
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = points,
                    onValueChange = { points = it },
                    label = { Text("Points") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                )
                OutlinedTextField(
                    value = difficulty,
                    onValueChange = { difficulty = it },
                    label = { Text("Difficulté") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        item {
            Button(
                onClick = {
                    if (title.isBlank() || question.isBlank() || correctAnswer.isBlank()) return@Button
                    viewModel.createExerciseAdmin(
                        classId = targetClass,
                        subjectId = 1L,
                        chapterId = 1L,
                        title = title,
                        instructions = instructions,
                        question = question,
                        type = "qcm",
                        options = options,
                        correctAnswer = correctAnswer,
                        explanation = explanation,
                        points = points.toIntOrNull() ?: 10,
                        difficulty = difficulty,
                        isPremium = false,
                        onSuccess = {
                            isSuccess = true
                            title = ""
                            question = ""
                            correctAnswer = ""
                            explanation = ""
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EduCiOrangeAccent)
            ) {
                Text("Enregistrer l'exercice")
            }
        }
    }
}

@Composable
fun AdminCreateExamView(viewModel: EduViewModel) {
    var title by remember { mutableStateOf("") }
    var examType by remember { mutableStateOf("BEPC") }
    var series by remember { mutableStateOf("Général") }
    var year by remember { mutableStateOf("2024") }
    var subject by remember { mutableStateOf("Mathématiques") }
    var duration by remember { mutableStateOf("120") }
    var content by remember { mutableStateOf("") }
    var solution by remember { mutableStateOf("") }
    var gradingScale by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Ajouter une épreuve officielle d'examen (DECO)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        if (isSuccess) {
            item {
                Surface(color = EduCiGreenContainer, shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Text("Épreuve d'examen enregistrée avec succès !", color = EduCiGreenDark, modifier = Modifier.padding(12.dp))
                }
            }
        }

        item {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Titre de l'épreuve (ex : BEPC 2024 Mathématiques) *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = examType,
                    onValueChange = { examType = it },
                    label = { Text("Examen (CEPE/BEPC/BAC)") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                )
                OutlinedTextField(
                    value = year,
                    onValueChange = { year = it },
                    label = { Text("Année") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Matière") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                )
                OutlinedTextField(
                    value = series,
                    onValueChange = { series = it },
                    label = { Text("Série (Général, A, C, D)") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        item {
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Énoncé officiel complet (Markdown/KaTeX) *") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5,
                shape = RoundedCornerShape(10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = solution,
                onValueChange = { solution = it },
                label = { Text("Corrigé type officiel détaillé") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                shape = RoundedCornerShape(10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = gradingScale,
                onValueChange = { gradingScale = it },
                label = { Text("Barème détaillé des points") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )
        }

        item {
            Button(
                onClick = {
                    if (title.isBlank() || content.isBlank()) return@Button
                    viewModel.createExamAdmin(
                        title = title,
                        examType = examType,
                        series = series,
                        year = year.toIntOrNull() ?: 2024,
                        subject = subject,
                        durationMinutes = duration.toIntOrNull() ?: 120,
                        instructions = "Session officielle de Juin",
                        content = content,
                        solution = solution,
                        gradingScale = gradingScale,
                        isPremium = false,
                        onSuccess = {
                            isSuccess = true
                            title = ""
                            content = ""
                            solution = ""
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
            ) {
                Text("Publier l'épreuve officielle")
            }
        }
    }
}

@Composable
fun AdminUsersListView(users: List<com.example.data.local.UserEntity>, viewModel: EduViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredUsers = remember(users, searchQuery) {
        if (searchQuery.isBlank()) users
        else users.filter {
            it.firstName.contains(searchQuery, ignoreCase = true) ||
            it.lastName.contains(searchQuery, ignoreCase = true) ||
            it.email.contains(searchQuery, ignoreCase = true) ||
            it.phoneNumber.contains(searchQuery, ignoreCase = true) ||
            it.className.contains(searchQuery, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Utilisateurs (${users.size})", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = EduCiGreenContainer
                ) {
                    Text(
                        text = "${users.count { it.isPremium }} Premium",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = EduCiGreenDark,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }

        // Search Bar for Users
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Rechercher un élève (nom, email, tél, classe)...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Effacer")
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        if (filteredUsers.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                        Text("Aucun utilisateur correspondant trouvé.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        } else {
            items(filteredUsers) { u ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (u.isSuspended) Color(0xFFFEF2F2) else MaterialTheme.colorScheme.surface
                    ),
                    border = if (u.isSuspended) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.5f)) else null
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("${u.firstName} ${u.lastName}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                if (u.isSuspended) {
                                    Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFFEF4444)) {
                                        Text("SUSPENDU", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                                    }
                                }
                            }
                            Text(u.email, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            if (u.phoneNumber.isNotEmpty() || u.schoolName.isNotEmpty()) {
                                Text(
                                    text = listOfNotNull(
                                        u.phoneNumber.takeIf { it.isNotEmpty() }?.let { "📞 $it" },
                                        u.schoolName.takeIf { it.isNotEmpty() }?.let { "🏫 $it" }
                                    ).joinToString(" • "),
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Surface(shape = RoundedCornerShape(6.dp), color = EduCiGreenContainer) {
                                    Text("Classe : ${u.className}", fontSize = 10.sp, color = EduCiGreenDark, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                                Surface(shape = RoundedCornerShape(6.dp), color = if (u.isPremium) EduCiGoldLight else Color(0xFFE2E8F0)) {
                                    Text(if (u.isPremium) "⭐ Premium" else "Gratuit", fontSize = 10.sp, color = if (u.isPremium) EduCiGoldXp else Color.DarkGray, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                                Surface(shape = RoundedCornerShape(6.dp), color = if (u.role == "admin" || u.role == "owner") EduCiOrangeLight else Color(0xFFF1F5F9)) {
                                    Text(u.role.uppercase(), fontSize = 10.sp, color = if (u.role == "admin" || u.role == "owner") EduCiOrangeDark else Color.DarkGray, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                        }

                        // Suspend / Reactivate Action & Delete action (owner only, cannot delete owner)
                        if (u.role != "owner" && !u.email.equals(com.example.ui.viewmodel.OWNER_EMAIL, ignoreCase = true)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { viewModel.toggleUserSuspension(u.id, !u.isSuspended) }
                                ) {
                                    Icon(
                                        imageVector = if (u.isSuspended) Icons.Default.CheckCircle else Icons.Default.Block,
                                        contentDescription = if (u.isSuspended) "Réactiver le compte" else "Suspendre le compte",
                                        tint = if (u.isSuspended) EduCiGreenPrimary else EduCiOrangeAccent
                                    )
                                }
                                IconButton(
                                    onClick = { viewModel.deleteUserAdmin(u.id) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Supprimer l'utilisateur",
                                        tint = Color(0xFFDC2626)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminAuditLogsView(logs: List<com.example.data.local.AdminLogEntity>) {
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("Journal d'audit administratif (${logs.size} actions)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        if (logs.isEmpty()) {
            item {
                Text("Aucune action enregistrée pour le moment.", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
            }
        } else {
            items(logs) { log ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(log.action, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = EduCiOrangeAccent)
                            Text(dateFormat.format(Date(log.timestamp)), fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text("Élément : ${log.targetItem}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                        if (log.details.isNotEmpty()) {
                            Text("Détails : ${log.details}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminAccessDeniedView(
    onUnlock: (String, (Boolean, String) -> Unit) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var secretKey by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isChecking by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = EduCiOrangeLight,
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = EduCiOrangeAccent,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Accès Réservé au Propriétaire",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Seul l'administrateur propriétaire est autorisé à modifier le contenu de l'application (cours, leçons, exercices, examens officiels et élèves).",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = secretKey,
                    onValueChange = {
                        secretKey = it
                        errorMessage = null
                    },
                    label = { Text("Clé secrète d'administration") },
                    placeholder = { Text("Saisir la clé propriétaire") },
                    leadingIcon = { Icon(Icons.Default.Key, contentDescription = null, tint = EduCiOrangeAccent) },
                    visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (secretKey.isBlank()) {
                            errorMessage = "Veuillez saisir votre clé secrète."
                            return@Button
                        }
                        isChecking = true
                        onUnlock(secretKey) { success, msg ->
                            isChecking = false
                            if (!success) {
                                errorMessage = msg
                            }
                        }
                    },
                    enabled = !isChecking,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EduCiOrangeAccent)
                ) {
                    if (isChecking) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text("Déverrouiller l'accès Administrateur", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Retour à l'Espace Élève")
                }
            }
        }
    }
}
