import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
  StatusBar,
} from 'react-native';
import { useAppTheme } from '../context/ThemeContext';
import { useAuth } from '../context/AuthContext';
import { INITIAL_LESSONS } from '../data/curriculumData';

export const HomeScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { colors, isDark, toggleTheme } = useAppTheme();
  const { currentUser, isOwnerOrAdmin } = useAuth();

  const [selectedLevel, setSelectedLevel] = useState<'primaire' | 'college' | 'lycee'>('college');
  const [selectedClass, setSelectedClass] = useState<string>('4e');

  const classesByLevel = {
    primaire: ['CI', 'CP1', 'CP2', 'CE1', 'CE2', 'CM1', 'CM2'],
    college: ['6e', '5e', '4e', '3e'],
    lycee: ['2nde', '1ère', 'Tle'],
  };

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
      <StatusBar barStyle={isDark ? 'light-content' : 'dark-content'} />

      {/* Top Header */}
      <View style={[styles.header, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
        <View>
          <View style={styles.brandRow}>
            <View style={[styles.flagStrip, { backgroundColor: colors.accent }]} />
            <View style={[styles.flagStrip, { backgroundColor: '#FFFFFF' }]} />
            <View style={[styles.flagStrip, { backgroundColor: colors.primary }]} />
            <Text style={[styles.logoText, { color: colors.primary }]}>EduCI</Text>
          </View>
          <Text style={[styles.subLogoText, { color: colors.textMuted }]}>
            Portail National de Réussite Scolaire
          </Text>
        </View>

        <View style={styles.headerActions}>
          <TouchableOpacity
            style={[styles.themeBtn, { backgroundColor: colors.surfaceVariant, borderColor: colors.border }]}
            onPress={toggleTheme}
            accessibilityLabel="Changer de thème"
          >
            <Text style={styles.themeBtnText}>{isDark ? '☀️' : '🌙'}</Text>
          </TouchableOpacity>
        </View>
      </View>

      <ScrollView contentContainerStyle={styles.scrollContent} showsVerticalScrollIndicator={false}>
        {/* User Greeting / Owner Badge */}
        <View style={[styles.welcomeCard, { backgroundColor: colors.surface, borderColor: colors.border }]}>
          <View style={styles.welcomeRow}>
            <View style={styles.welcomeTextCol}>
              <Text style={[styles.greeting, { color: colors.textPrimary }]}>
                {currentUser ? `Akwaaba, ${currentUser.name}` : 'Bienvenue sur EduCI'}
              </Text>
              <Text style={[styles.welcomeSub, { color: colors.textSecondary }]}>
                {currentUser
                  ? `Classe : ${currentUser.gradeClass} • ${currentUser.schoolName}`
                  : 'Apprenez, révisez et préparez vos examens officiels'}
              </Text>
            </View>
            {isOwnerOrAdmin && (
              <View style={[styles.ownerBadge, { backgroundColor: colors.badgeBg, borderColor: colors.primary }]}>
                <Text style={[styles.ownerBadgeText, { color: colors.primary }]}>👑 Propriétaire</Text>
              </View>
            )}
          </View>
        </View>

        {/* Level Switcher */}
        <Text style={[styles.sectionTitle, { color: colors.textPrimary }]}>Mon Niveau Scolaire</Text>
        <View style={styles.levelRow}>
          {(['primaire', 'college', 'lycee'] as const).map(level => {
            const isActive = selectedLevel === level;
            const labels = { primaire: 'Primaire', college: 'Collège', lycee: 'Lycée' };
            return (
              <TouchableOpacity
                key={level}
                style={[
                  styles.levelTab,
                  {
                    backgroundColor: isActive ? colors.primary : colors.surface,
                    borderColor: isActive ? colors.primary : colors.border,
                  },
                ]}
                onPress={() => {
                  setSelectedLevel(level);
                  setSelectedClass(classesByLevel[level][0]);
                }}
              >
                <Text
                  style={[
                    styles.levelTabText,
                    { color: isActive ? '#FFFFFF' : colors.textSecondary, fontWeight: isActive ? '700' : '500' },
                  ]}
                >
                  {labels[level]}
                </Text>
              </TouchableOpacity>
            );
          })}
        </View>

        {/* Classes Horizontal Picker */}
        <ScrollView horizontal showsHorizontalScrollIndicator={false} style={styles.classesScroll}>
          {classesByLevel[selectedLevel].map(cls => {
            const isClsActive = selectedClass === cls;
            return (
              <TouchableOpacity
                key={cls}
                style={[
                  styles.classChip,
                  {
                    backgroundColor: isClsActive ? colors.accent : colors.surface,
                    borderColor: isClsActive ? colors.accent : colors.border,
                  },
                ]}
                onPress={() => setSelectedClass(cls)}
              >
                <Text
                  style={[
                    styles.classChipText,
                    { color: isClsActive ? '#FFFFFF' : colors.textPrimary, fontWeight: isClsActive ? '700' : '600' },
                  ]}
                >
                  Classe de {cls}
                </Text>
              </TouchableOpacity>
            );
          })}
        </ScrollView>

        {/* Install on Mobile Banner */}
        <TouchableOpacity
          style={[styles.installBanner, { backgroundColor: colors.surfaceVariant, borderColor: colors.primary }]}
          onPress={() => navigation.navigate('InstallMobile')}
        >
          <Text style={styles.installBannerIcon}>📲</Text>
          <View style={{ flex: 1 }}>
            <View style={{ flexDirection: 'row', alignItems: 'center', gap: 6 }}>
              <Text style={[styles.installBannerTitle, { color: colors.textPrimary }]}>
                Installer sur Téléphone
              </Text>
              <View style={[styles.installBadge, { backgroundColor: colors.accent }]}>
                <Text style={styles.installBadgeText}>INSTALLER</Text>
              </View>
            </View>
            <Text style={[styles.installBannerSub, { color: colors.textSecondary }]}>
              QR Code, Fichier APK Android ou Raccourci écran d'accueil
            </Text>
          </View>
          <Text style={{ fontSize: 16, color: colors.primary, fontWeight: 'bold' }}>➔</Text>
        </TouchableOpacity>

        {/* Quick Hub Navigation Cards */}
        <Text style={[styles.sectionTitle, { color: colors.textPrimary }]}>Espaces d'Apprentissage</Text>
        <View style={styles.grid}>
          <TouchableOpacity
            style={[styles.hubCard, { backgroundColor: colors.surface, borderColor: colors.border }]}
            onPress={() => navigation.navigate('Courses')}
          >
            <Text style={styles.hubIcon}>📚</Text>
            <Text style={[styles.hubTitle, { color: colors.textPrimary }]}>Cours & Leçons</Text>
            <Text style={[styles.hubSub, { color: colors.textMuted }]}>Programme national CI</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={[styles.hubCard, { backgroundColor: colors.surface, borderColor: colors.border }]}
            onPress={() => navigation.navigate('Exercises')}
          >
            <Text style={styles.hubIcon}>✍️</Text>
            <Text style={[styles.hubTitle, { color: colors.textPrimary }]}>Exercices</Text>
            <Text style={[styles.hubSub, { color: colors.textMuted }]}>Quiz auto-corrigés</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={[styles.hubCard, { backgroundColor: colors.surface, borderColor: colors.border }]}
            onPress={() => navigation.navigate('Exams')}
          >
            <Text style={styles.hubIcon}>🎓</Text>
            <Text style={[styles.hubTitle, { color: colors.textPrimary }]}>Examens CI</Text>
            <Text style={[styles.hubSub, { color: colors.textMuted }]}>CEPE, BEPC, BAC</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={[styles.hubCard, { backgroundColor: colors.surface, borderColor: colors.border }]}
            onPress={() => navigation.navigate('AiTeacher')}
          >
            <Text style={styles.hubIcon}>🤖</Text>
            <Text style={[styles.hubTitle, { color: colors.textPrimary }]}>Tuteur IA</Text>
            <Text style={[styles.hubSub, { color: colors.textMuted }]}>Professeur personnel</Text>
          </TouchableOpacity>
        </View>

        {/* Featured Lesson */}
        <Text style={[styles.sectionTitle, { color: colors.textPrimary }]}>Leçon Recommandée</Text>
        {INITIAL_LESSONS.slice(0, 1).map(lesson => (
          <TouchableOpacity
            key={lesson.id}
            style={[styles.lessonCard, { backgroundColor: colors.surface, borderColor: colors.border }]}
            onPress={() => navigation.navigate('Courses', { lessonId: lesson.id })}
          >
            <View style={styles.lessonMetaRow}>
              <View style={[styles.subjectTag, { backgroundColor: colors.badgeBg }]}>
                <Text style={[styles.subjectTagText, { color: colors.primary }]}>{lesson.subject}</Text>
              </View>
              <Text style={[styles.durationText, { color: colors.textMuted }]}>⏱ {lesson.durationMinutes} min</Text>
            </View>
            <Text style={[styles.lessonTitle, { color: colors.textPrimary }]}>{lesson.title}</Text>
            <Text style={[styles.lessonSummary, { color: colors.textSecondary }]}>{lesson.summary}</Text>
          </TouchableOpacity>
        ))}
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  header: {
    paddingHorizontal: 16,
    paddingVertical: 12,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    borderBottomWidth: 1,
  },
  brandRow: { flexDirection: 'row', alignItems: 'center', gap: 4 },
  flagStrip: { width: 5, height: 18, borderRadius: 2 },
  logoText: { fontSize: 22, fontWeight: '800', marginLeft: 6, letterSpacing: 0.5 },
  subLogoText: { fontSize: 11, marginTop: 2 },
  headerActions: { flexDirection: 'row', alignItems: 'center', gap: 8 },
  themeBtn: { width: 40, height: 40, borderRadius: 20, borderWidth: 1, alignItems: 'center', justifyContent: 'center' },
  themeBtnText: { fontSize: 18 },
  scrollContent: { padding: 16, paddingBottom: 40 },
  welcomeCard: { padding: 16, borderRadius: 16, borderWidth: 1, marginBottom: 18 },
  welcomeRow: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'flex-start' },
  welcomeTextCol: { flex: 1 },
  greeting: { fontSize: 18, fontWeight: '700' },
  welcomeSub: { fontSize: 13, marginTop: 4 },
  ownerBadge: { paddingHorizontal: 10, paddingVertical: 4, borderRadius: 12, borderWidth: 1, marginLeft: 8 },
  ownerBadgeText: { fontSize: 11, fontWeight: '700' },
  sectionTitle: { fontSize: 16, fontWeight: '700', marginBottom: 10, marginTop: 6 },
  installBanner: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 14,
    borderRadius: 14,
    borderWidth: 1,
    marginBottom: 16,
    gap: 12,
  },
  installBannerIcon: { fontSize: 28 },
  installBannerTitle: { fontSize: 14, fontWeight: '700' },
  installBadge: { paddingHorizontal: 6, paddingVertical: 2, borderRadius: 6 },
  installBadgeText: { color: '#FFFFFF', fontSize: 9, fontWeight: '800' },
  installBannerSub: { fontSize: 11, marginTop: 2 },
  levelRow: { flexDirection: 'row', gap: 8, marginBottom: 12 },
  levelTab: { flex: 1, paddingVertical: 10, borderRadius: 10, borderWidth: 1, alignItems: 'center' },
  levelTabText: { fontSize: 14 },
  classesScroll: { marginBottom: 18 },
  classChip: { paddingHorizontal: 16, paddingVertical: 8, borderRadius: 20, borderWidth: 1, marginRight: 8 },
  classChipText: { fontSize: 13 },
  grid: { flexDirection: 'row', flexWrap: 'wrap', gap: 12, marginBottom: 20 },
  hubCard: { width: '48%', padding: 16, borderRadius: 14, borderWidth: 1, alignItems: 'flex-start' },
  hubIcon: { fontSize: 28, marginBottom: 8 },
  hubTitle: { fontSize: 15, fontWeight: '700', marginBottom: 2 },
  hubSub: { fontSize: 11 },
  lessonCard: { padding: 16, borderRadius: 14, borderWidth: 1 },
  lessonMetaRow: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 8 },
  subjectTag: { paddingHorizontal: 8, paddingVertical: 3, borderRadius: 6 },
  subjectTagText: { fontSize: 11, fontWeight: '700' },
  durationText: { fontSize: 11 },
  lessonTitle: { fontSize: 16, fontWeight: '700', marginBottom: 6 },
  lessonSummary: { fontSize: 13, lineHeight: 18 },
});
