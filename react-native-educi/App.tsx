import React from 'react';
import { View, Text } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { StatusBar } from 'expo-status-bar';

import { ThemeProvider, useAppTheme } from './src/context/ThemeContext';
import { AuthProvider, useAuth } from './src/context/AuthContext';
import { CurriculumProvider } from './src/context/CurriculumContext';

import { HomeScreen } from './src/screens/HomeScreen';
import { CoursesScreen } from './src/screens/CoursesScreen';
import { ExercisesScreen } from './src/screens/ExercisesScreen';
import { ExamsScreen } from './src/screens/ExamsScreen';
import { AiTeacherScreen } from './src/screens/AiTeacherScreen';
import { ProfileScreen } from './src/screens/ProfileScreen';
import { AdminScreen } from './src/screens/AdminScreen';
import { AuthScreen } from './src/screens/AuthScreen';
import { InstallMobileScreen } from './src/screens/InstallMobileScreen';
import { SearchScreen } from './src/screens/SearchScreen';
import { NotificationsScreen } from './src/screens/NotificationsScreen';

const Tab = createBottomTabNavigator();
const Stack = createNativeStackNavigator();

function MainTabNavigator() {
  const { colors, isDark } = useAppTheme();
  const { isOwnerOrAdmin } = useAuth();

  return (
    <Tab.Navigator
      screenOptions={{
        headerShown: false,
        tabBarStyle: {
          backgroundColor: colors.surface,
          borderTopColor: colors.border,
          height: 60,
          paddingBottom: 8,
          paddingTop: 6,
        },
        tabBarActiveTintColor: colors.primary,
        tabBarInactiveTintColor: colors.textMuted,
        tabBarLabelStyle: {
          fontSize: 11,
          fontWeight: '600',
        },
      }}
    >
      <Tab.Screen
        name="HomeTab"
        component={HomeScreen}
        options={{
          tabBarLabel: 'Accueil',
          tabBarIcon: ({ color }) => <Text style={{ fontSize: 18, color }}>🏠</Text>,
        }}
      />
      <Tab.Screen
        name="CoursesTab"
        component={CoursesScreen}
        options={{
          tabBarLabel: 'Cours',
          tabBarIcon: ({ color }) => <Text style={{ fontSize: 18, color }}>📚</Text>,
        }}
      />
      <Tab.Screen
        name="ExercisesTab"
        component={ExercisesScreen}
        options={{
          tabBarLabel: 'Exercices',
          tabBarIcon: ({ color }) => <Text style={{ fontSize: 18, color }}>✍️</Text>,
        }}
      />
      <Tab.Screen
        name="ExamsTab"
        component={ExamsScreen}
        options={{
          tabBarLabel: 'Examens',
          tabBarIcon: ({ color }) => <Text style={{ fontSize: 18, color }}>🎓</Text>,
        }}
      />
      <Tab.Screen
        name="AiTab"
        component={AiTeacherScreen}
        options={{
          tabBarLabel: 'Tuteur IA',
          tabBarIcon: ({ color }) => <Text style={{ fontSize: 18, color }}>🤖</Text>,
        }}
      />
      <Tab.Screen
        name="ProfileTab"
        component={ProfileScreen}
        options={{
          tabBarLabel: isOwnerOrAdmin ? 'Propriétaire' : 'Profil',
          tabBarIcon: ({ color }) => <Text style={{ fontSize: 18, color }}>{isOwnerOrAdmin ? '👑' : '👤'}</Text>,
        }}
      />
    </Tab.Navigator>
  );
}

function NavigationRoot() {
  const { isDark } = useAppTheme();

  return (
    <NavigationContainer>
      <StatusBar style={isDark ? 'light' : 'dark'} />
      <Stack.Navigator screenOptions={{ headerShown: false }}>
        <Stack.Screen name="MainTabs" component={MainTabNavigator} />
        <Stack.Screen name="Admin" component={AdminScreen} />
        <Stack.Screen name="Auth" component={AuthScreen} />
        <Stack.Screen name="InstallMobile" component={InstallMobileScreen} />
        <Stack.Screen name="Search" component={SearchScreen} />
        <Stack.Screen name="Notifications" component={NotificationsScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

export default function App() {
  return (
    <ThemeProvider>
      <AuthProvider>
        <CurriculumProvider>
          <NavigationRoot />
        </CurriculumProvider>
      </AuthProvider>
    </ThemeProvider>
  );
}
