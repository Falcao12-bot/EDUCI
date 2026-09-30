package com.example.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.local.LessonEntity
import com.example.data.local.SubjectEntity
import com.example.ui.navigation.AppScreen
import com.example.ui.theme.*
import com.example.ui.viewmodel.EduViewModel

@Composable
fun HomeScreen(
    viewModel: EduViewModel,
    modifier: Modifier = Modifier
) {
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val completedLessonsCount by viewModel.userCompletedLessonsCount.collectAsStateWithLifecycle()
    val attemptsCount by viewModel.userExerciseAttemptsCount.collectAsStateWithLifecycle()
    val correctAttemptsCount by viewModel.userCorrectAttemptsCount.collectAsStateWithLifecycle()
    val publishedLessons by viewModel.allPublishedLessons.collectAsStateWithLifecycle()
    val classSubjects by viewModel.classSubjects.collectAsStateWithLifecycle()
    val exercises by viewModel.exercises.collectAsStateWithLifecycle()
    val exams by viewModel.exams.collectAsStateWithLifecycle()
    val avgExamScore by viewModel.averageExamScore.collectAsStateWithLifecycle()

    val firstName = user?.firstName ?: "Élève"
    val className = user?.className ?: "4e"
    val isPremium = user?.isPremium ?: false
    val streak = user?.streak ?: 1
    val xp = user?.xp ?: 0
    val studyTime = user?.studyTimeMinutes ?: 0
    val level = 1 + (xp / 100)

    val dailyTarget = 5
    val dailyProgress = (attemptsCount % (dailyTarget + 1)).coerceAtMost(dailyTarget)
    val progressFraction = dailyProgress.toFloat() / dailyTarget.toFloat()

    val successRate = if (attemptsCount > 0) ((correctAttemptsCount.toFloat() / attemptsCount.toFloat()) * 100).toInt() else 85
    val lastLesson = publishedLessons.firstOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header greeting & Student Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = EduCiGreenPrimary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Bonjour,",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                            Text(
                                text = "$firstName !",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }

                        // Class & Badge
                        Column(horizontalAlignment = Alignment.End) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "Classe : $className",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isPremium) EduCiGoldXp else Color.White,
                                modifier = Modifier.clickable {
                                    if (!isPremium) viewModel.openPremiumDialog()
                                }
                            ) {
                                Text(
                                    text = if (isPremium) "👑 Premium" else "Passer en Premium",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isPremium) Color.White else EduCiGreenDark,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Streaks and XP chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White.copy(alpha = 0.15f),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(text = "🔥", fontSize = 20.sp)
                                Column {
                                    Text(
                                        text = "$streak jours",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                    Text(text = "Série active", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                                }
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White.copy(alpha = 0.15f),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(text = "⭐", fontSize = 20.sp)
                                Column {
                                    Text(
                                        text = "$xp XP",
                                        fontWeight = FontWeight.Bold,
                                        color = EduCiGoldLight,
                                        fontSize = 14.sp
                                    )
                                    Text(text = "Niveau $level", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Section "Continuer mon apprentissage"
        if (lastLesson != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.PlayCircle, contentDescription = null, tint = EduCiGreenPrimary, modifier = Modifier.size(20.dp))
                                Text(
                                    text = "CONTINUER MON APPRENTISSAGE",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = EduCiGreenPrimary,
                                    letterSpacing = 0.5.sp
                                )
                            }
                            Text(
                                text = "En cours",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = lastLesson.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = lastLesson.summary,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⏱️ ${lastLesson.durationMinutes} min • ${lastLesson.difficulty}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Button(
                                onClick = { viewModel.openLesson(lastLesson) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text("Continuer", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }

        // 3. Stats Grid: Taux de réussite, Temps d'étude, Exercices, Note Moyenne
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Taux de réussite",
                    value = "$successRate%",
                    icon = Icons.AutoMirrored.Filled.TrendingUp,
                    iconColor = EduCiGreenPrimary
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Temps d'étude",
                    value = "${studyTime}m",
                    icon = Icons.Default.Schedule,
                    iconColor = EduCiOrangeAccent
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Exercices validés",
                    value = "$correctAttemptsCount / $attemptsCount",
                    icon = Icons.Default.Quiz,
                    iconColor = Color(0xFF2563EB)
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Note moyenne exam.",
                    value = avgExamScore?.let { "%.1f/20".format(it) } ?: "14.5/20",
                    icon = Icons.Default.MilitaryTech,
                    iconColor = EduCiGoldXp
                )
            }
        }

        // 4. Section "Mes matières"
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mes matières (${className})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = { viewModel.navigateTo(AppScreen.COURSES) }) {
                    Text("Toutes les matières", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        item {
            if (classSubjects.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(horizontal = 2.dp)
                ) {
                    items(classSubjects) { subj ->
                        SubjectChipCard(
                            subject = subj,
                            onClick = {
                                viewModel.selectSubject(subj)
                                viewModel.navigateTo(AppScreen.COURSES)
                            }
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SubjectChipPlaceholder("Mathématiques", "📐", EduCiGreenContainer, EduCiGreenPrimary) { viewModel.navigateTo(AppScreen.COURSES) }
                    SubjectChipPlaceholder("Français", "📖", Color(0xFFEFF6FF), Color(0xFF2563EB)) { viewModel.navigateTo(AppScreen.COURSES) }
                    SubjectChipPlaceholder("Physique-Chimie", "⚗️", Color(0xFFFAF5FF), Color(0xFF7C3AED)) { viewModel.navigateTo(AppScreen.COURSES) }
                }
            }
        }

        // 5. OBJECTIF DU JOUR
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                imageVector = Icons.Default.Flag,
                                contentDescription = null,
                                tint = EduCiOrangeAccent,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "OBJECTIF DU JOUR",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                letterSpacing = 0.5.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(
                            text = "Exercices : $dailyProgress / $dailyTarget",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = if (dailyProgress >= dailyTarget) EduCiGreenPrimary else EduCiOrangeAccent
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { progressFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = if (dailyProgress >= dailyTarget) EduCiGreenPrimary else EduCiOrangeAccent,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (dailyProgress >= dailyTarget)
                            "🎉 Objectif du jour atteint ! +50 XP bonus ajoutés."
                        else
                            "Encore ${dailyTarget - dailyProgress} exercice(s) pour valider ta série du jour !",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // 6. Section "À découvrir" (Nouveautés leçons, exercices, examens)
        item {
            Text(
                text = "À découvrir",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DiscoveryCard(
                    title = "Annales BEPC & BAC",
                    subtitle = "Sujets & corrigés officiels DECO",
                    tag = "EXAMENS",
                    tagColor = Color(0xFF2563EB),
                    icon = Icons.AutoMirrored.Filled.Assignment,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.navigateTo(AppScreen.EXAMS)
                }

                DiscoveryCard(
                    title = "Tuteur IA EduCI",
                    subtitle = "Méthode pas-à-pas & résolution",
                    tag = "INTELLIGENCE",
                    tagColor = Color(0xFF9333EA),
                    icon = Icons.Default.Psychology,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.navigateTo(AppScreen.AI)
                }
            }
        }

        // 7. Recommandations d'apprentissage personnalisées
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.TipsAndUpdates, contentDescription = null, tint = EduCiOrangeAccent)
                        Text(
                            text = "Recommandations pour toi ($className)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    RecommendationRow(
                        title = "Maîtriser le calcul littéral et identités",
                        category = "Mathématiques • Chapitre 1",
                        badge = "Indispensable examen"
                    ) {
                        viewModel.navigateTo(AppScreen.COURSES)
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                    RecommendationRow(
                        title = "Tester tes réflexes sur 5 QCM rapides",
                        category = "Quiz auto-corrigés • 5 min",
                        badge = "+30 XP"
                    ) {
                        viewModel.navigateTo(AppScreen.EXERCISES)
                    }
                }
            }
        }

        // 8. Premium CTA Banner
        if (!isPremium) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.openPremiumDialog() },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = EduCiGoldLight),
                    border = androidx.compose.foundation.BorderStroke(1.dp, EduCiGoldXp.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = "👑", fontSize = 28.sp)
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Passez à EduCI Premium",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF78350F)
                            )
                            Text(
                                text = "Accédez aux corrigés officiels d'examens et à l'IA illimitée (Wave, Orange, MTN, Moov).",
                                fontSize = 12.sp,
                                color = Color(0xFF92400E)
                            )
                        }
                        Button(
                            onClick = { viewModel.openPremiumDialog() },
                            colors = ButtonDefaults.buttonColors(containerColor = EduCiGoldXp),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Voir l'offre", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SubjectChipCard(
    subject: SubjectEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(130.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = EduCiGreenContainer,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = EduCiGreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = subject.name,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = if (subject.isNationalExamSubject) "Matière examen" else "Programme officiel",
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SubjectChipPlaceholder(
    title: String,
    emoji: String,
    bg: Color,
    tint: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bg)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(emoji, fontSize = 16.sp)
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = tint)
        }
    }
}

@Composable
private fun DiscoveryCard(
    title: String,
    subtitle: String,
    tag: String,
    tagColor: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = tagColor.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = tag,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = tagColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Icon(imageVector = icon, contentDescription = null, tint = tagColor, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(text = subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
        }
    }
}

@Composable
private fun RecommendationRow(
    title: String,
    category: String,
    badge: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(text = category, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Surface(
            shape = RoundedCornerShape(6.dp),
            color = EduCiGreenContainer
        ) {
            Text(
                text = badge,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = EduCiGreenDark,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(10.dp),
                color = iconColor.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
                }
            }
            Column {
                Text(
                    text = value,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = title,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
