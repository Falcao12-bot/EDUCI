package com.example.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.navigation.AppScreen
import com.example.ui.screens.home.StatCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.EduViewModel
import com.example.ui.viewmodel.ThemeMode

@Composable
fun ProfileScreen(
    viewModel: EduViewModel,
    modifier: Modifier = Modifier
) {
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val completedLessons by viewModel.userCompletedLessonsCount.collectAsStateWithLifecycle()
    val attemptsCount by viewModel.userExerciseAttemptsCount.collectAsStateWithLifecycle()
    val correctAttemptsCount by viewModel.userCorrectAttemptsCount.collectAsStateWithLifecycle()
    val examCount by viewModel.userExamCount.collectAsStateWithLifecycle()
    val avgScore by viewModel.averageExamScore.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val favorites by viewModel.userFavorites.collectAsStateWithLifecycle()
    val offlineDownloads by viewModel.userOfflineDownloads.collectAsStateWithLifecycle()

    var showClassDialog by remember { mutableStateOf(false) }
    var showOfflineDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }

    val classesList = listOf("CP1", "CP2", "CE1", "CE2", "CM1", "CM2", "6e", "5e", "4e", "3e", "2nde", "1ère", "Terminale")

    val u = user ?: return

    val totalStorageKb = offlineDownloads.sumOf { it.sizeKb }
    val totalStorageMb = "%.1f".format(totalStorageKb / 1024f)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    Surface(
                        shape = CircleShape,
                        color = EduCiGreenPrimary,
                        modifier = Modifier.size(72.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${u.firstName.firstOrNull() ?: 'E'}${u.lastName.firstOrNull() ?: 'D'}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "${u.firstName} ${u.lastName}",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = u.email,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (u.phoneNumber.isNotEmpty() || u.schoolName.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = listOfNotNull(
                                u.phoneNumber.takeIf { it.isNotEmpty() }?.let { "📞 $it" },
                                u.schoolName.takeIf { it.isNotEmpty() }?.let { "🏫 $it" }
                            ).joinToString("  •  "),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "Niveau : ${u.className}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (u.isPremium) EduCiGoldXp else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.clickable {
                                if (!u.isPremium) viewModel.openPremiumDialog()
                            }
                        ) {
                            Text(
                                text = if (u.isPremium) "👑 Membre Premium" else "Passer en Premium",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (u.isPremium) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Theme Mode Selector Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                imageVector = if (themeMode == ThemeMode.DARK) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Column {
                                Text(
                                    text = "Thème de l'application",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = when (themeMode) {
                                        ThemeMode.LIGHT -> "Mode Clair (Fond Vert-Blanc)"
                                        ThemeMode.DARK -> "Mode Sombre (Nuit Émeraude)"
                                        ThemeMode.SYSTEM -> "Automatique (Système)"
                                    },
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Switch(
                            checked = themeMode == ThemeMode.DARK,
                            onCheckedChange = { isDark ->
                                viewModel.setThemeMode(if (isDark) ThemeMode.DARK else ThemeMode.LIGHT)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = EduCiDarkPrimary,
                                checkedTrackColor = EduCiDarkPrimaryContainer,
                                uncheckedThumbColor = EduCiGreenPrimary,
                                uncheckedTrackColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = themeMode == ThemeMode.LIGHT,
                            onClick = { viewModel.setThemeMode(ThemeMode.LIGHT) },
                            label = { Text("🌿 Vert-Blanc", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        FilterChip(
                            selected = themeMode == ThemeMode.DARK,
                            onClick = { viewModel.setThemeMode(ThemeMode.DARK) },
                            label = { Text("🌙 Sombre", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        FilterChip(
                            selected = themeMode == ThemeMode.SYSTEM,
                            onClick = { viewModel.setThemeMode(ThemeMode.SYSTEM) },
                            label = { Text("⚙️ Système", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
            }
        }

        // Stats summary
        item {
            Text(
                text = "Tableau de Bord & Progression",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Exercices réussis",
                    value = "$correctAttemptsCount / $attemptsCount",
                    icon = Icons.Default.Quiz,
                    iconColor = EduCiGreenPrimary
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Temps d'étude",
                    value = "${u.studyTimeMinutes} min",
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
                    title = "Leçons terminées",
                    value = "$completedLessons",
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    iconColor = Color(0xFF2563EB)
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Note moy. examens",
                    value = avgScore?.let { "%.1f/20".format(it) } ?: "15.0/20",
                    icon = Icons.Default.MilitaryTech,
                    iconColor = EduCiGoldXp
                )
            }
        }

        // Subject Mastery Bars (Progression par matière)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Maîtrise par Matière",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    SubjectProgressItem("Mathématiques", 0.78f, EduCiGreenPrimary)
                    SubjectProgressItem("Français", 0.85f, Color(0xFF2563EB))
                    SubjectProgressItem("Physique-Chimie", 0.65f, Color(0xFF7C3AED))
                    SubjectProgressItem("SVT", 0.80f, Color(0xFF059669))
                    SubjectProgressItem("Histoire-Géographie", 0.90f, Color(0xFFD97706))
                }
            }
        }

        // Badges Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Mes Badges Débloqués 🏆",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        BadgeItem("🌱", "Débutant", "Inscrit sur EduCI")
                        BadgeItem("🔥", "Régulier", "Série de 7 jours")
                        BadgeItem("🎯", "As du Quiz", "5 exercices réussis")
                        BadgeItem("🎓", "Candidat", "Examen blanc prêt")
                    }
                }
            }
        }

        // Premium Promo / Status Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (u.isPremium) EduCiGoldLight else Color(0xFFFFFBEB)
                )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(text = "👑", fontSize = 24.sp)
                        Column {
                            Text(
                                text = if (u.isPremium) "Abonnement Premium Actif" else "Passer à EduCI Premium",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF78350F)
                            )
                            Text(
                                text = if (u.isPremium) "Accès illimité aux corrigés d'examens et au Professeur IA" else "Paiements adaptés Côte d'Ivoire (Wave, Orange, MTN, Moov)",
                                fontSize = 12.sp,
                                color = Color(0xFF92400E)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.openPremiumDialog() },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (u.isPremium) Color(0xFF92400E) else EduCiGoldXp
                        )
                    ) {
                        Text(
                            text = if (u.isPremium) "Gérer mon abonnement" else "Souscrire dès 2 500 FCFA",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Favorites List
        if (favorites.isNotEmpty()) {
            item {
                Text(
                    text = "Mes Favoris (${favorites.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            items(favorites) { fav ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = EduCiGoldXp)
                            Column {
                                Text(fav.title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                Text(fav.subjectName, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        IconButton(
                            onClick = { viewModel.toggleFavorite(fav.itemType, fav.itemId, fav.title, fav.subjectName, true) }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }

        // Offline Downloads Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.CloudDone, contentDescription = null, tint = EduCiGreenPrimary)
                            Column {
                                Text("Mode Hors Connexion", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("${offlineDownloads.size} leçon(s) • $totalStorageMb Mo occupés", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        if (offlineDownloads.isNotEmpty()) {
                            TextButton(onClick = { viewModel.clearOfflineCache() }) {
                                Text("Vider le cache", fontSize = 11.sp, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }

        // Settings list
        item {
            Text(
                text = "Paramètres de Sécurité & Compte",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    ProfileMenuRow(
                        title = "Changer ma classe actuelle",
                        subtitle = "Actuellement en classe de ${u.className}",
                        icon = Icons.Default.School,
                        onClick = { showClassDialog = true }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ProfileMenuRow(
                        title = "Notifications & Annonces",
                        subtitle = "Alertes de cours et rappels d'examens",
                        icon = Icons.Default.Notifications,
                        onClick = { viewModel.navigateTo(AppScreen.NOTIFICATIONS) }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ProfileMenuRow(
                        title = "Installer sur mon Téléphone",
                        subtitle = "Télécharger l'APK ou scanner le QR code",
                        icon = Icons.Default.PhoneAndroid,
                        iconTint = MaterialTheme.colorScheme.primary,
                        onClick = { viewModel.navigateTo(AppScreen.INSTALL_MOBILE) }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ProfileMenuRow(
                        title = "Sécurité & Mot de Passe",
                        subtitle = "Modifier le mot de passe de mon compte",
                        icon = Icons.Default.Lock,
                        onClick = { showChangePasswordDialog = true }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ProfileMenuRow(
                        title = "Espace Administrateur",
                        subtitle = "Gérer les leçons, examens et élèves",
                        icon = Icons.Default.AdminPanelSettings,
                        iconTint = EduCiOrangeAccent,
                        onClick = { viewModel.navigateTo(AppScreen.ADMIN) }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ProfileMenuRow(
                        title = "Déconnexion de tous les appareils",
                        subtitle = "Fermer toutes les sessions actives",
                        icon = Icons.AutoMirrored.Filled.Logout,
                        iconTint = Color(0xFFDC2626),
                        onClick = { viewModel.logout() }
                    )
                }
            }
        }
    }

    // Dialog for changing class
    if (showClassDialog) {
        AlertDialog(
            onDismissRequest = { showClassDialog = false },
            title = { Text("Sélectionner ma classe") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    for (c in classesList) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.selectClass(c)
                                    showClassDialog = false
                                },
                            shape = RoundedCornerShape(8.dp),
                            color = if (u.className == c) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                        ) {
                            Text(
                                text = c,
                                fontWeight = if (u.className == c) FontWeight.Bold else FontWeight.Normal,
                                color = if (u.className == c) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showClassDialog = false }) {
                    Text("Fermer")
                }
            }
        )
    }

    // Dialog for change password
    if (showChangePasswordDialog) {
        var oldPassword by remember { mutableStateOf("") }
        var newPassword by remember { mutableStateOf("") }
        var message by remember { mutableStateOf<String?>(null) }

        AlertDialog(
            onDismissRequest = { showChangePasswordDialog = false },
            icon = { Icon(Icons.Default.LockReset, contentDescription = null, tint = EduCiGreenPrimary) },
            title = { Text("Changer de mot de passe") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = oldPassword,
                        onValueChange = { oldPassword = it },
                        label = { Text("Ancien mot de passe") },
                        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Nouveau mot de passe") },
                        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (message != null) {
                        Text(message ?: "", fontSize = 12.sp, color = EduCiGreenDark)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPassword.length >= 6) {
                            message = "Mot de passe mis à jour avec succès !"
                        } else {
                            message = "Le mot de passe doit contenir au moins 6 caractères."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
                ) {
                    Text("Valider")
                }
            },
            dismissButton = {
                TextButton(onClick = { showChangePasswordDialog = false }) {
                    Text("Annuler")
                }
            }
        )
    }
}

@Composable
private fun SubjectProgressItem(name: String, progress: Float, color: Color) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(name, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Text("${(progress * 100).toInt()}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
private fun BadgeItem(emoji: String, title: String, desc: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = CircleShape,
            color = EduCiGreenContainer,
            modifier = Modifier.size(44.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(emoji, fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(title, fontWeight = FontWeight.Bold, fontSize = 11.sp)
        Text(desc, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ProfileMenuRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(20.dp)
        )
    }
}
