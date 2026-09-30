package com.example.ui.screens.exams

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import com.example.data.local.ExamEntity
import com.example.ui.components.RichContentRenderer
import com.example.ui.theme.*
import com.example.ui.viewmodel.EduViewModel
import kotlinx.coroutines.delay

@Composable
fun ExamsScreen(
    viewModel: EduViewModel,
    modifier: Modifier = Modifier
) {
    val selectedExamType by viewModel.selectedExamType.collectAsStateWithLifecycle()
    val exams by viewModel.exams.collectAsStateWithLifecycle()
    val activeExam by viewModel.activeExam.collectAsStateWithLifecycle()
    val showSolution by viewModel.showExamSolution.collectAsStateWithLifecycle()
    val user by viewModel.currentUser.collectAsStateWithLifecycle()

    val examTypes = listOf("BEPC", "BAC", "CEPE", "Devoir")

    // Live Exam State
    var isExamRunning by remember { mutableStateOf(false) }
    var remainingSeconds by remember { mutableIntStateOf(120 * 60) }
    var isSubmitted by remember { mutableStateOf(false) }
    var studentScore by remember { mutableFloatStateOf(15.5f) }
    var studentNotesInput by remember { mutableStateOf("") }

    LaunchedEffect(isExamRunning, remainingSeconds) {
        if (isExamRunning && remainingSeconds > 0) {
            delay(1000)
            remainingSeconds -= 1
        } else if (isExamRunning && remainingSeconds == 0) {
            isExamRunning = false
            isSubmitted = true
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Exam Type Tabs: BEPC, BAC, CEPE, Devoirs
        item {
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (type in examTypes) {
                    val isSelected = selectedExamType == type
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            viewModel.selectExamType(type)
                            viewModel.openExam(ExamEntity(title = "", examType = "", year = 0, subject = "", content = ""))
                            isExamRunning = false
                            isSubmitted = false
                        },
                        label = {
                            Text(
                                text = if (type == "Devoir") "Devoirs & Compositions" else type,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EduCiGreenPrimary,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }
        }

        // Active Exam Viewer if selected
        if (activeExam != null && activeExam?.title?.isNotEmpty() == true) {
            val exam = activeExam!!
            val isPremiumUser = user?.isPremium ?: false
            val isLocked = exam.isPremium && !isPremiumUser

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = EduCiOrangeLight
                            ) {
                                Text(
                                    text = "Session ${exam.year} • ${exam.series}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EduCiOrangeDark,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            IconButton(onClick = {
                                viewModel.openExam(ExamEntity(title = "", examType = "", year = 0, subject = "", content = ""))
                                isExamRunning = false
                                isSubmitted = false
                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Fermer")
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = exam.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            modifier = Modifier.padding(top = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(text = "Matière : ${exam.subject}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                            Text(text = "• Durée : ${exam.durationMinutes} min", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        if (isLocked) {
                            // Premium Lock Card
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = EduCiGoldLight)
                            ) {
                                Column(
                                    modifier = Modifier.padding(18.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(Icons.Default.Lock, contentDescription = null, tint = EduCiGoldXp, modifier = Modifier.size(32.dp))
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Ce sujet d'examen officiel est réservé aux membres Premium",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color(0xFF78350F)
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(
                                        onClick = { viewModel.openPremiumDialog() },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = EduCiGoldXp)
                                    ) {
                                        Text("Activer mon accès Premium (Wave/Orange/MTN)")
                                    }
                                }
                            }
                        } else {
                            // Interactive Chronometer Bar (Mode Examen Blanc)
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isExamRunning) Color(0xFFFEF2F2) else EduCiGreenContainer
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    val minutes = remainingSeconds / 60
                                    val seconds = remainingSeconds % 60
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Icon(
                                            imageVector = Icons.Default.Timer,
                                            contentDescription = "Chronomètre",
                                            tint = if (isExamRunning) Color(0xFFDC2626) else EduCiGreenDark
                                        )
                                        Column {
                                            Text(
                                                text = if (isExamRunning) "Épreuve en cours : %02d:%02d".format(minutes, seconds) else "Temps alloué : ${exam.durationMinutes} min",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = if (isExamRunning) Color(0xFFDC2626) else EduCiGreenDark
                                            )
                                            Text(
                                                text = if (isExamRunning) "Conditions réelles d'examen" else "Lance le chrono pour t'évaluer",
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }

                                    Button(
                                        onClick = {
                                            if (!isExamRunning && !isSubmitted) {
                                                remainingSeconds = exam.durationMinutes * 60
                                                isExamRunning = true
                                            } else if (isExamRunning) {
                                                isExamRunning = false
                                                isSubmitted = true
                                                viewModel.recordExamSubmission(exam, studentScore, 20f, (exam.durationMinutes * 60) - remainingSeconds)
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isExamRunning) Color(0xFFDC2626) else EduCiGreenPrimary
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = if (isExamRunning) "Rendre ma copie" else if (isSubmitted) "Épreuve rendue ✓" else "Démarrer chrono",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            if (isSubmitted) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFFF0FDF4),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, EduCiGreenPrimary.copy(alpha = 0.4f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Text("🎉", fontSize = 20.sp)
                                            Column {
                                                Text(
                                                    text = "Copie soumise avec succès !",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp,
                                                    color = EduCiGreenDark
                                                )
                                                Text(
                                                    text = "Note estimée : ${"%.1f".format(studentScore)}/20 • Mention Bien (+100 XP)",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = EduCiGreenPrimary
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Toggle between Énoncé and Corrigé
                            TabRow(
                                selectedTabIndex = if (showSolution) 1 else 0,
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.clip(RoundedCornerShape(10.dp))
                            ) {
                                Tab(
                                    selected = !showSolution,
                                    onClick = { if (showSolution) viewModel.toggleExamSolution() },
                                    text = { Text("Épreuve (Sujet)", fontWeight = if (!showSolution) FontWeight.Bold else FontWeight.Normal) }
                                )
                                Tab(
                                    selected = showSolution,
                                    onClick = { if (!showSolution) viewModel.toggleExamSolution() },
                                    text = { Text("Corrigé Officiel", fontWeight = if (showSolution) FontWeight.Bold else FontWeight.Normal) }
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            if (!showSolution) {
                                RichContentRenderer(content = exam.content)

                                if (isExamRunning) {
                                    Spacer(modifier = Modifier.height(14.dp))
                                    OutlinedTextField(
                                        value = studentNotesInput,
                                        onValueChange = { studentNotesInput = it },
                                        label = { Text("Brouillon / Réponses de l'élève") },
                                        placeholder = { Text("Saisis ici tes calculs ou éléments de réponse...") },
                                        modifier = Modifier.fillMaxWidth(),
                                        minLines = 4,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                }
                            } else {
                                if (exam.solution.isNotEmpty()) {
                                    if (exam.gradingScale.isNotEmpty()) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = EduCiGreenContainer,
                                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                                        ) {
                                            Text(
                                                text = "Barème officiel DECO : ${exam.gradingScale}",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = EduCiGreenDark,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }
                                    RichContentRenderer(content = exam.solution)
                                } else {
                                    Text(
                                        text = "Corrigé officiel en cours de validation par la commission pédagogique.",
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // List of past exams for current type
        item {
            Text(
                text = "Annales Officielles $selectedExamType (${exams.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (exams.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Aucune épreuve répertoriée pour cet examen.")
                    }
                }
            }
        } else {
            items(exams) { exam ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.openExam(exam) },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            color = EduCiGreenContainer
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${exam.year}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = EduCiGreenDark
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = exam.title,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(text = exam.subject, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                Text(text = "• ${exam.series}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(text = "• ${exam.durationMinutes} min", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        if (exam.isPremium) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = EduCiGoldLight
                            ) {
                                Text(
                                    text = "⭐ Premium",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EduCiGoldXp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
