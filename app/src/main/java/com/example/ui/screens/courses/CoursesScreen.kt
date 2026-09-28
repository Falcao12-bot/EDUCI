package com.example.ui.screens.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.data.local.ChapterEntity
import com.example.data.local.LessonEntity
import com.example.data.local.SubjectEntity
import com.example.ui.theme.*
import com.example.ui.viewmodel.EduViewModel

@Composable
fun CoursesScreen(
    viewModel: EduViewModel,
    modifier: Modifier = Modifier
) {
    val selectedLevel by viewModel.selectedLevel.collectAsStateWithLifecycle()
    val selectedClass by viewModel.selectedClass.collectAsStateWithLifecycle()
    val subjects by viewModel.classSubjects.collectAsStateWithLifecycle()
    val selectedSubject by viewModel.selectedSubject.collectAsStateWithLifecycle()
    val selectedChapter by viewModel.selectedChapter.collectAsStateWithLifecycle()
    val chapterLessons by viewModel.currentChapterLessons.collectAsStateWithLifecycle()

    val currentSubjId = selectedSubject?.id ?: 0L
    val chaptersFlow = remember(currentSubjId, selectedClass) {
        viewModel.repository.getChapters(currentSubjId, selectedClass)
    }
    val chapters by chaptersFlow.collectAsStateWithLifecycle(emptyList())

    val levelCategories = listOf(
        "primaire" to "Primaire",
        "college" to "Collège",
        "lycee" to "Lycée"
    )

    val classesByLevel = when (selectedLevel) {
        "primaire" -> listOf("CP1", "CP2", "CE1", "CE2", "CM1", "CM2")
        "college" -> listOf("6e", "5e", "4e", "3e")
        "lycee" -> listOf("2nde", "1ère", "Terminale")
        else -> listOf("6e", "5e", "4e", "3e")
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Level Switcher (Primaire / Collège / Lycée)
        item {
            TabRow(
                selectedTabIndex = levelCategories.indexOfFirst { it.first == selectedLevel }.coerceAtLeast(0),
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {
                for (cat in levelCategories) {
                    val isSelected = selectedLevel == cat.first
                    Tab(
                        selected = isSelected,
                        onClick = {
                            viewModel.selectLevel(cat.first)
                            val firstClass = when (cat.first) {
                                "primaire" -> "CM2"
                                "college" -> "4e"
                                "lycee" -> "Terminale"
                                else -> "4e"
                            }
                            viewModel.selectClass(firstClass)
                        },
                        text = {
                            Text(
                                text = cat.second,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }
        }

        // 2. Class Selector horizontal chips
        item {
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (cls in classesByLevel) {
                    val isSelected = selectedClass.equals(cls, ignoreCase = true)
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.selectClass(cls) },
                        label = { Text(cls, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EduCiGreenPrimary,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }
        }

        // 3. Subject Selector or Chapter Selector Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (selectedSubject == null) "Matières disponibles en $selectedClass" else "Matière : ${selectedSubject?.name}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (selectedSubject != null) {
                    TextButton(onClick = { viewModel.selectSubject(SubjectEntity(name = "", classId = "")) }) {
                        Text("Toutes les matières", fontSize = 12.sp)
                    }
                }
            }
        }

        // 4. Subjects List (if none selected or as selector)
        if (selectedSubject == null || selectedSubject?.name?.isEmpty() == true) {
            if (subjects.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.School, contentDescription = null, tint = EduCiGreenPrimary, modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Sélectionne une matière pour voir ses chapitres.")
                        }
                    }
                }
            } else {
                items(subjects) { subject ->
                    SubjectCard(subject = subject) {
                        viewModel.selectSubject(subject)
                    }
                }
            }
        } else {
            // Subject selected: Show chapters & lessons
            if (chapters.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Aucun chapitre pour le moment dans cette matière.",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(chapters) { chapter ->
                    val isChapterSelected = selectedChapter?.id == chapter.id
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectChapter(chapter) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isChapterSelected) EduCiGreenContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = EduCiGreenPrimary,
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "${chapter.orderIndex}",
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                    Column {
                                        Text(
                                            text = chapter.title,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        if (chapter.summary.isNotEmpty()) {
                                            Text(
                                                text = chapter.summary,
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                                Icon(
                                    imageVector = if (isChapterSelected) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            // If chapter expanded: show lessons
                            if (isChapterSelected) {
                                Spacer(modifier = Modifier.height(14.dp))
                                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                                Spacer(modifier = Modifier.height(10.dp))

                                if (chapterLessons.isEmpty()) {
                                    Text(
                                        text = "Aucune leçon publiée dans ce chapitre.",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                } else {
                                    for (lesson in chapterLessons) {
                                        LessonItemRow(lesson = lesson) {
                                            viewModel.openLesson(lesson)
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
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
fun SubjectCard(
    subject: SubjectEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                modifier = Modifier.size(46.dp),
                shape = RoundedCornerShape(12.dp),
                color = EduCiGreenContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = when (subject.name.lowercase()) {
                            "mathématiques", "maths" -> Icons.Default.Calculate
                            "français" -> Icons.Default.MenuBook
                            "physique-chimie" -> Icons.Default.Science
                            "svt" -> Icons.Default.Eco
                            "histoire-géographie" -> Icons.Default.Public
                            "anglais" -> Icons.Default.Language
                            "philosophie" -> Icons.Default.Psychology
                            else -> Icons.Default.School
                        },
                        contentDescription = null,
                        tint = EduCiGreenPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = subject.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (subject.isNationalExamSubject) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = EduCiOrangeLight
                        ) {
                            Text(
                                text = "Examen",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = EduCiOrangeDark,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Cours, résumés, exercices et devoirs",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun LessonItemRow(
    lesson: LessonEntity,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PlayCircle,
                contentDescription = null,
                tint = EduCiGreenPrimary,
                modifier = Modifier.size(20.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "⏱️ ${lesson.durationMinutes} min", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(text = "•  ${lesson.difficulty}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                }
            }
            if (lesson.isPremium) {
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
            }
        }
    }
}
