package com.example.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.data.local.ExerciseEntity
import com.example.ui.theme.*
import com.example.ui.viewmodel.EduViewModel

@Composable
fun ExercisesScreen(
    viewModel: EduViewModel,
    modifier: Modifier = Modifier
) {
    val exercises by viewModel.exercises.collectAsStateWithLifecycle()
    val activeExercise by viewModel.activeExercise.collectAsStateWithLifecycle()
    val result by viewModel.exerciseAnswerResult.collectAsStateWithLifecycle()
    val selectedClass by viewModel.selectedClass.collectAsStateWithLifecycle()

    var selectedOption by remember { mutableStateOf("") }
    var textAnswerInput by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "Exercices & Entraînement",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Évalue tes connaissances pour la classe de $selectedClass",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Active Exercise Modal/Card
        if (activeExercise != null) {
            val ex = activeExercise!!
            val options = if (ex.optionsJson.isNotEmpty()) ex.optionsJson.split(";;") else emptyList()

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                                    text = "⭐ ${ex.points} Points",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EduCiGreenDark,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            IconButton(onClick = {
                                viewModel.openExercise(ExerciseEntity(classId = "", subjectId = 0, chapterId = 0, title = "", instructions = "", question = "", correctAnswer = "", explanation = ""))
                                viewModel.clearExerciseResult()
                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Fermer")
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = ex.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        if (ex.instructions.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Consigne : ${ex.instructions}",
                                fontSize = 12.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Question Box
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = ex.question,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 22.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // If not answered yet: show choices or text input
                        if (result == null) {
                            if (options.isNotEmpty()) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    for (opt in options) {
                                        val isSelected = selectedOption == opt
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(12.dp))
                                                .clickable { selectedOption = opt }
                                                .border(
                                                    width = if (isSelected) 2.dp else 1.dp,
                                                    color = if (isSelected) EduCiGreenPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                                    shape = RoundedCornerShape(12.dp)
                                                ),
                                            color = if (isSelected) EduCiGreenContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(14.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                                            ) {
                                                RadioButton(
                                                    selected = isSelected,
                                                    onClick = { selectedOption = opt }
                                                )
                                                Text(
                                                    text = opt,
                                                    fontSize = 14.sp,
                                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }
                                }
                            } else {
                                OutlinedTextField(
                                    value = textAnswerInput,
                                    onValueChange = { textAnswerInput = it },
                                    label = { Text("Ta réponse") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    val ans = if (options.isNotEmpty()) selectedOption else textAnswerInput
                                    if (ans.isNotBlank()) {
                                        viewModel.submitExerciseAnswer(ans)
                                    }
                                },
                                enabled = (options.isNotEmpty() && selectedOption.isNotBlank()) || (options.isEmpty() && textAnswerInput.isNotBlank()),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
                            ) {
                                Text("Valider ma réponse", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            }
                        } else {
                            // Result display: Student answer, Correct answer, Explanation, Score
                            val res = result!!
                            val resultBg = if (res.isCorrect) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                            val resultBorder = if (res.isCorrect) Color(0xFF16A34A) else Color(0xFFDC2626)

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = resultBg)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .border(1.dp, resultBorder.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
                                        .padding(16.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Icon(
                                            imageVector = if (res.isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                            contentDescription = null,
                                            tint = if (res.isCorrect) EduCiGreenPrimary else Color(0xFFDC2626),
                                            modifier = Modifier.size(22.dp)
                                        )
                                        Text(
                                            text = if (res.isCorrect) "EXCELLENT ! (+${res.pointsEarned} XP)" else "CE N'EST PAS LA BONNE RÉPONSE",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = if (res.isCorrect) EduCiGreenDark else Color(0xFF991B1B)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Text(
                                        text = "Ta réponse : ${res.studentAnswer}",
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Bonne réponse : ${res.correctAnswer}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = EduCiGreenDark
                                    )

                                    if (res.explanation.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        HorizontalDivider(color = resultBorder.copy(alpha = 0.3f))
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "💡 Explication détaillée :",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = res.explanation,
                                            fontSize = 12.sp,
                                            lineHeight = 18.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            OutlinedButton(
                                onClick = {
                                    selectedOption = ""
                                    textAnswerInput = ""
                                    viewModel.clearExerciseResult()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Recommencer cet exercice")
                            }
                        }
                    }
                }
            }
        }

        // List of all exercises
        item {
            Text(
                text = "Liste des exercices disponibles (${exercises.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (exercises.isEmpty()) {
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
                        Icon(Icons.Default.Quiz, contentDescription = null, tint = EduCiOrangeAccent, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Aucun exercice trouvé pour la classe sélectionnée.")
                    }
                }
            }
        } else {
            items(exercises) { exercise ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedOption = ""
                            textAnswerInput = ""
                            viewModel.openExercise(exercise)
                        },
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
                            modifier = Modifier.size(42.dp),
                            shape = RoundedCornerShape(10.dp),
                            color = EduCiOrangeLight
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Psychology, contentDescription = null, tint = EduCiOrangeAccent)
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = exercise.title,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = exercise.question,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(text = "⭐ ${exercise.points} pts", fontSize = 11.sp, color = EduCiGoldXp, fontWeight = FontWeight.Bold)
                                Text(text = "• ${exercise.difficulty}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                Text(text = "• ${exercise.type.uppercase()}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Démarrer",
                            tint = EduCiGreenPrimary
                        )
                    }
                }
            }
        }
    }
}
