package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.navigation.AppScreen
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EduTopBar(
    currentScreen: AppScreen,
    userClass: String,
    isPremium: Boolean,
    unreadNotifications: Int,
    isAdmin: Boolean,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onAdminToggleClick: () -> Unit,
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (onBackClick != null) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "EduCI",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 20.sp
                            )
                        )
                        // Premium / Gratuit Badge
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isPremium) EduCiGoldXp else MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = if (isPremium) "⭐ PREMIUM" else "GRATUIT",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isPremium) Color.White else MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "Apprendre. Progresser. Réussir.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        actions = {
            // Theme Toggle Button (Light Mode / Dark Mode)
            IconButton(onClick = onToggleTheme) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = if (isDarkTheme) "Passer au mode clair (Vert-Blanc)" else "Passer au mode sombre",
                    tint = if (isDarkTheme) EduCiGoldXp else MaterialTheme.colorScheme.primary
                )
            }

            // Search button
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Rechercher",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            // Notification Bell with Badge
            IconButton(onClick = onNotificationsClick) {
                BadgedBox(
                    badge = {
                        if (unreadNotifications > 0) {
                            Badge(containerColor = EduCiOrangeAccent) {
                                Text(text = "$unreadNotifications")
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Admin Toggle Button if user is admin
            if (isAdmin) {
                IconButton(onClick = onAdminToggleClick) {
                    Icon(
                        imageVector = if (currentScreen == AppScreen.ADMIN) Icons.Default.School else Icons.Default.AdminPanelSettings,
                        contentDescription = "Administration",
                        tint = if (currentScreen == AppScreen.ADMIN) EduCiOrangeAccent else MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun EduBottomBar(
    currentScreen: AppScreen,
    onTabSelected: (AppScreen) -> Unit
) {
    val items = listOf(
        NavigationItem(AppScreen.HOME, "Accueil", Icons.Filled.Home, Icons.Outlined.Home),
        NavigationItem(AppScreen.COURSES, "Cours", Icons.AutoMirrored.Filled.MenuBook, Icons.AutoMirrored.Outlined.MenuBook),
        NavigationItem(AppScreen.EXERCISES, "Exercices", Icons.Filled.Quiz, Icons.Outlined.Quiz),
        NavigationItem(AppScreen.EXAMS, "Examens", Icons.AutoMirrored.Filled.Assignment, Icons.AutoMirrored.Outlined.Assignment),
        NavigationItem(AppScreen.AI, "IA", Icons.Filled.Psychology, Icons.Outlined.Psychology),
        NavigationItem(AppScreen.PROFILE, "Profil", Icons.Filled.Person, Icons.Outlined.Person)
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        for (item in items) {
            val selected = currentScreen == item.screen
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(item.screen) },
                icon = {
                    Icon(
                        imageVector = if (selected) item.filledIcon else item.outlineIcon,
                        contentDescription = item.label,
                        tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

private data class NavigationItem(
    val screen: AppScreen,
    val label: String,
    val filledIcon: ImageVector,
    val outlineIcon: ImageVector
)
