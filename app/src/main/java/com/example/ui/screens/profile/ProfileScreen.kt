package com.example.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

    var showClassDialog by remember { mutableStateOf(false) }
    var showPwaInstallDialog by remember { mutableStateOf(false) }
    var showOfflineDialog by remember { mutableStateOf(false) }

    val classesList = listOf("CP1", "CP2", "CE1", "CE2", "CM1", "CM2", "6e", "5e", "4e", "3e", "2nde", "1ère", "Terminale")

    val u = user ?: return

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

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "Classe : ${u.className}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (u.isPremium) EduCiGoldXp else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = if (u.isPremium) "⭐ Membre Premium" else "Compte Gratuit",
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

        // Theme Mode Selector Card (Vert-Blanc / Sombre / Système)
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
                text = "Mes Statistiques d'Apprentissage",
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
                    title = "Exercices faits",
                    value = "$attemptsCount",
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
                    title = "Leçons vues",
                    value = "$completedLessons",
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    iconColor = Color(0xFF2563EB)
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "XP Total",
                    value = "${u.xp} XP",
                    icon = Icons.Default.Star,
                    iconColor = EduCiGoldXp
                )
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
                                text = if (u.isPremium) "Accès illimité à tous les examens et à l'IA" else "Tous les corrigés officiels, examens et IA illimitée",
                                fontSize = 12.sp,
                                color = Color(0xFF92400E)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { viewModel.togglePremium() },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (u.isPremium) Color(0xFF92400E) else EduCiGoldXp
                        )
                    ) {
                        Text(
                            text = if (u.isPremium) "Désactiver Premium (Démo)" else "Activer EduCI Premium (1 500 FCFA/mois)",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Settings list
        item {
            Text(
                text = "Paramètres de l'Application",
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
                        subtitle = "Voir les alertes de nouveaux cours",
                        icon = Icons.Default.Notifications,
                        onClick = { viewModel.navigateTo(AppScreen.NOTIFICATIONS) }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ProfileMenuRow(
                        title = "Mode Hors Connexion",
                        subtitle = "Base locale SQLite synchronisée",
                        icon = Icons.Default.CloudDone,
                        onClick = { showOfflineDialog = true }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ProfileMenuRow(
                        title = "Installer sur mon Téléphone",
                        subtitle = "Télécharger l'APK, scanner le QR code ou installer PWA",
                        icon = Icons.Default.PhoneAndroid,
                        iconTint = MaterialTheme.colorScheme.primary,
                        onClick = { viewModel.navigateTo(AppScreen.INSTALL_MOBILE) }
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
                        title = "Déconnexion",
                        subtitle = "Quitter la session actuelle",
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

    // Dialog for PWA installation instructions
    if (showPwaInstallDialog) {
        AlertDialog(
            onDismissRequest = { showPwaInstallDialog = false },
            icon = { Icon(Icons.Default.InstallMobile, contentDescription = null, tint = EduCiGreenPrimary) },
            title = { Text("Installer EduCI sur ton smartphone") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("EduCI est une application installable pour tous les élèves :")
                    Text("1. Ouvre EduCI dans ton navigateur Chrome ou Safari.")
                    Text("2. Appuie sur le menu ⋮ (en haut à droite) ou sur le bouton Partager.")
                    Text("3. Sélectionne « Ajouter à l'écran d'accueil ».")
                    Text("4. L'icône EduCI apparaîtra sur ton écran d'accueil comme une application native !")
                }
            },
            confirmButton = {
                Button(
                    onClick = { showPwaInstallDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
                ) {
                    Text("J'ai compris")
                }
            }
        )
    }

    // Dialog for offline mode info
    if (showOfflineDialog) {
        AlertDialog(
            onDismissRequest = { showOfflineDialog = false },
            icon = { Icon(Icons.Default.CloudDone, contentDescription = null, tint = EduCiGreenPrimary) },
            title = { Text("Mode Hors Connexion Prêt") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Tous tes cours, exercices et sujets d'examens sont automatiquement sauvegardés dans la base de données interne de ton appareil.")
                    Text("Tu peux réviser sans connexion Internet même dans les zones sans réseau.")
                }
            },
            confirmButton = {
                Button(
                    onClick = { showOfflineDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = EduCiGreenPrimary)
                ) {
                    Text("Super !")
                }
            }
        )
    }
}

@Composable
fun ProfileMenuRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color = EduCiGreenPrimary,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(22.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(text = subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
