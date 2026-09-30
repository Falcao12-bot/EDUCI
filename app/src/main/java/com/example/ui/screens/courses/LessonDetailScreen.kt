package com.example.ui.screens.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.ui.components.RichContentRenderer
import com.example.ui.navigation.AppScreen
import com.example.ui.theme.*
import com.example.ui.viewmodel.EduViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonDetailScreen(
    viewModel: EduViewModel,
    modifier: Modifier = Modifier
) {
    val lesson by viewModel.currentLesson.collectAsStateWithLifecycle()
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    var isCompleted by remember { mutableStateOf(false) }

    if (lesson == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Aucune leçon sélectionnée.")
        }
        return
    }

    val currentLesson = lesson!!

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Back bar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                }
                Text(
                    text = "Retour aux chapitres",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Lesson Title & Meta Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
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
                            color = EduCiGreenContainer
                        ) {
                            Text(
                                text = "⏱️ ${currentLesson.durationMinutes} min",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = EduCiGreenDark,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = when (currentLesson.difficulty) {
                                "Facile" -> Color(0xFFDCFCE7)
                                "Moyen" -> Color(0xFFFEF3C7)
                                else -> Color(0xFFFEE2E2)
                            }
                        ) {
                            Text(
                                text = "Niveau : ${currentLesson.difficulty}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = when (currentLesson.difficulty) {
                                    "Facile" -> Color(0xFF166534)
                                    "Moyen" -> Color(0xFF92400E)
                                    else -> Color(0xFF991B1B)
                                },
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = currentLesson.title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    if (currentLesson.summary.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentLesson.summary,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Pedagogical Objectives (Objectifs Pédagogiques)
        if (currentLesson.pedagogicalObjectives.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EduCiGreenPrimary, modifier = Modifier.size(20.dp))
                            Text(
                                text = "OBJECTIFS PÉDAGOGIQUES",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = EduCiGreenDark,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentLesson.pedagogicalObjectives,
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = Color(0xFF14532D)
                        )
                    }
                }
            }
        }

        // Rich Lesson Content
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    RichContentRenderer(content = currentLesson.content)
                }
            }
        }

        // Complete & Rewards Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = EduCiGreenContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isCompleted) "🎉 Leçon validée ! +25 XP ajoutés." else "As-tu bien assimilé cette leçon ?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = EduCiGreenDark
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            viewModel.completeCurrentLesson()
                            isCompleted = true
                        },
                        enabled = !isCompleted,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isCompleted) "Terminé ✓" else "Marquer comme terminé (+25 XP)")
                    }
                }
            }
        }

        // Action shortcuts: Ask AI / Exercise
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        viewModel.sendAiMessage("Explique-moi les points clés de la leçon « ${currentLesson.title} »")
                        viewModel.navigateTo(AppScreen.AI)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Psychology, contentDescription = null, tint = Color(0xFF9333EA))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Aide Professeur IA", fontSize = 12.sp)
                }

                Button(
                    onClick = { viewModel.navigateTo(AppScreen.EXERCISES) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EduCiOrangeAccent)
                ) {
                    Icon(Icons.Default.Quiz, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Faire un exercice", fontSize = 12.sp)
                }
            }
        }
    }
}
