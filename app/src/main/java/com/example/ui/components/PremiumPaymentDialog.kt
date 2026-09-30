package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*
import com.example.ui.viewmodel.EduViewModel

@Composable
fun PremiumPaymentDialog(
    viewModel: EduViewModel,
    onDismiss: () -> Unit
) {
    val scrollState = rememberScrollState()

    var selectedPlanIndex by remember { mutableStateOf(2) } // 0: Mensuel, 1: Trimestriel, 2: Annuel
    var selectedProvider by remember { mutableStateOf("Wave") } // Wave, Orange Money, MTN MoMo, Moov Money, Carte Bancaire
    var phoneNumber by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    var feedbackMessage by remember { mutableStateOf<String?>(null) }
    var isSuccess by remember { mutableStateOf(false) }

    val plans = listOf(
        Triple("Mensuel", 2500, 30),
        Triple("Trimestriel", 6000, 90),
        Triple("Annuel (Promo)", 18000, 365)
    )

    val providers = listOf(
        "Wave" to "🌊 Wave (0% frais)",
        "Orange Money" to "🍊 Orange Money",
        "MTN MoMo" to "🟡 MTN MoMo",
        "Moov Money" to "🔵 Moov Money",
        "Carte Bancaire" to "💳 Carte / Djamo"
    )

    val currentPlan = plans[selectedPlanIndex]

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "👑", fontSize = 24.sp)
                        Column {
                            Text(
                                text = "EduCI Premium",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "L'excellence scolaire ivoirienne sans limite",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Fermer")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Benefits highlights
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        BenefitRow("✓ Corrigés détaillés de tous les examens officiels (CEPE, BEPC, BAC)")
                        BenefitRow("✓ Accès illimité au Professeur EduCI (Tuteur IA 24h/24)")
                        BenefitRow("✓ Téléchargement illimité des cours en mode hors-ligne")
                        BenefitRow("✓ Fiches de synthèse et barèmes officiels DECO")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Plan selection
                Text(
                    text = "1. Choisissez votre formule",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for ((index, plan) in plans.withIndex()) {
                        val isSelected = selectedPlanIndex == index
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedPlanIndex = index },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) EduCiGreenPrimary else MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = plan.first,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${plan.second}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = if (isSelected) EduCiGoldLight else MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "FCFA",
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.9f) else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Provider selection
                Text(
                    text = "2. Moyen de paiement (Côte d'Ivoire)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for ((key, label) in providers) {
                        val isSelected = selectedProvider == key
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedProvider = key },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedProvider = key },
                                    colors = RadioButtonDefaults.colors(selectedColor = EduCiGreenPrimary)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Phone number input
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Numéro Mobile Money (+225)") },
                    placeholder = { Text("Ex : 07 00 00 00 00") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                if (feedbackMessage != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = if (isSuccess) EduCiGreenContainer else MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = feedbackMessage ?: "",
                            color = if (isSuccess) EduCiGreenDark else MaterialTheme.colorScheme.onErrorContainer,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons
                Button(
                    onClick = {
                        if (phoneNumber.trim().length < 8) {
                            feedbackMessage = "Veuillez entrer un numéro de téléphone ivoirien valide."
                            isSuccess = false
                            return@Button
                        }
                        isProcessing = true
                        viewModel.processPayment(
                            provider = selectedProvider,
                            phone = phoneNumber.trim(),
                            planName = currentPlan.first,
                            amountFcfa = currentPlan.second,
                            durationDays = currentPlan.third
                        ) { success, msg ->
                            isProcessing = false
                            isSuccess = success
                            feedbackMessage = msg
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary),
                    enabled = !isProcessing
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text(
                            text = "Payer ${currentPlan.second} FCFA",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BenefitRow(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface
    )
}
