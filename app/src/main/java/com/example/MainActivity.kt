package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.EduBottomBar
import com.example.ui.components.EduTopBar
import com.example.ui.navigation.AppScreen
import com.example.ui.screens.admin.AdminScreen
import com.example.ui.screens.ai.AiTeacherScreen
import com.example.ui.screens.auth.AuthScreen
import com.example.ui.screens.courses.CoursesScreen
import com.example.ui.screens.courses.LessonDetailScreen
import com.example.ui.screens.exams.ExamsScreen
import com.example.ui.screens.exercises.ExercisesScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.notifications.NotificationsScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.search.SearchScreen
import com.example.ui.theme.EduCiTheme
import com.example.ui.viewmodel.EduViewModel
import com.example.ui.viewmodel.ThemeMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: EduViewModel = viewModel()
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
            val systemInDark = isSystemInDarkTheme()
            val isDarkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> systemInDark
            }

            EduCiTheme(darkTheme = isDarkTheme) {
                EduCiApp(viewModel = viewModel, isDarkTheme = isDarkTheme)
            }
        }
    }
}

@Composable
fun EduCiApp(
    viewModel: EduViewModel = viewModel(),
    isDarkTheme: Boolean = false
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val unreadNotifications by viewModel.unreadNotificationsCount.collectAsStateWithLifecycle()

    val userClass = currentUser?.className ?: "4e"
    val isPremium = currentUser?.isPremium ?: false
    val isAdmin = currentUser?.role in listOf("owner", "admin") || currentUser?.email.equals(com.example.ui.viewmodel.OWNER_EMAIL, ignoreCase = true)

    val showPremiumDialog by viewModel.showPremiumDialog.collectAsStateWithLifecycle()

    if (showPremiumDialog) {
        com.example.ui.components.PremiumPaymentDialog(
            viewModel = viewModel,
            onDismiss = { viewModel.closePremiumDialog() }
        )
    }

    // BackHandler for secondary screens
    BackHandler(enabled = currentScreen != AppScreen.HOME && currentScreen != AppScreen.AUTH) {
        val handled = viewModel.navigateBack()
        if (!handled) {
            viewModel.navigateTo(AppScreen.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentScreen != AppScreen.AUTH) {
                EduTopBar(
                    currentScreen = currentScreen,
                    userClass = userClass,
                    isPremium = isPremium,
                    unreadNotifications = unreadNotifications,
                    isAdmin = isAdmin,
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { viewModel.toggleTheme() },
                    onSearchClick = { viewModel.navigateTo(AppScreen.SEARCH) },
                    onNotificationsClick = { viewModel.navigateTo(AppScreen.NOTIFICATIONS) },
                    onAdminToggleClick = {
                        if (currentScreen == AppScreen.ADMIN) {
                            viewModel.navigateTo(AppScreen.HOME)
                        } else {
                            viewModel.navigateTo(AppScreen.ADMIN)
                        }
                    },
                    onInstallMobileClick = {
                        if (currentScreen == AppScreen.INSTALL_MOBILE) {
                            viewModel.navigateTo(AppScreen.HOME)
                        } else {
                            viewModel.navigateTo(AppScreen.INSTALL_MOBILE)
                        }
                    },
                    onBackClick = if (currentScreen in listOf(AppScreen.LESSON_DETAIL, AppScreen.NOTIFICATIONS, AppScreen.SEARCH, AppScreen.INSTALL_MOBILE)) {
                        { viewModel.navigateBack() }
                    } else null
                )
            }
        },
        bottomBar = {
            if (currentScreen != AppScreen.AUTH && currentScreen != AppScreen.ADMIN && currentScreen != AppScreen.SEARCH && currentScreen != AppScreen.INSTALL_MOBILE) {
                EduBottomBar(
                    currentScreen = currentScreen,
                    onTabSelected = { screen -> viewModel.navigateTo(screen) }
                )
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)

        when (currentScreen) {
            AppScreen.AUTH -> AuthScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.HOME -> HomeScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.COURSES -> CoursesScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.LESSON_DETAIL -> LessonDetailScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.EXERCISES -> ExercisesScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.EXAMS -> ExamsScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.AI -> AiTeacherScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.PROFILE -> ProfileScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.ADMIN -> AdminScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.NOTIFICATIONS -> NotificationsScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.SEARCH -> SearchScreen(viewModel = viewModel, modifier = modifier)
            AppScreen.INSTALL_MOBILE -> com.example.ui.screens.install.InstallMobileScreen(viewModel = viewModel, modifier = modifier)
        }
    }
}
