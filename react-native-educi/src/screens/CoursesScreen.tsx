import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
  Modal,
} from 'react-native';
import { useAppTheme } from '../context/ThemeContext';
import { INITIAL_LESSONS, Lesson } from '../data/curriculumData';
import { ContenuRich } from '../components/ContenuRich';

type Level = 'primaire' | 'college' | 'lycee';

const CLASSES_BY_LEVEL: Record<Level, string[]> = {
  primaire: ['CI', 'CP1', 'CP2', 'CE1', 'CE2', 'CM1', 'CM2'],
  college: ['6e', '5e', '4e', '3e'],
  lycee: ['2nde', '1ère', 'Tle'],
};

const LEVEL_LABELS: Record<Level, string> = {
  primaire: 'Primaire',
  college: 'Collège',
  lycee: 'Lycée',
};

export const CoursesScreen: React.FC = () => {
  const { colors } = useAppTheme();
  const [selectedLevel, setSelectedLevel] = useState<Level>('college');
  const [selectedClass, setSelectedClass] = useState<string>('5e');
  const [selectedSubject, setSelectedSubject] = useState<string>('Tous');
  const [activeLesson, setActiveLesson] = useState<Lesson | null>(null);

  // Leçons de la classe sélectionnée
  const classLessons = INITIAL_LESSONS.filter(l => l.gradeClass === selectedClass);

  // Matières disponibles pour cette classe
  const availableSubjects = ['Tous', ...Array.from(new Set(classLessons.map(l => l.subject)))];

  const filteredLessons = selectedSubject === 'Tous'
    ? classLessons
    : classLessons.filter(l => l.subject === selectedSubject);

  const switchLevel = (level: Level) => {
    setSelectedLevel(level);
    setSelectedClass(CLASSES_BY_LEVEL[level][0]);
    setSelectedSubject('Tous');
  };

  const switchClass = (cls: string) => {
    setSelectedClass(cls);
    setSelectedSubject('Tous');
  };

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
      {/* Level tabs */}
      <View style={[styles.filterBar, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
        <View style={styles.levelRow}>
          {(['primaire', 'college', 'lycee'] as Level[]).map(level => {
            const active = selectedLevel === level;
            return (
              <TouchableOpacity
                key={level}
                style={[
                  styles.levelTab,
                  {
                    backgroundColor: active ? colors.primary : colors.surface,
                    borderColor: active ? colors.primary : colors.border,
                  },
                ]}
                onPress={() => switchLevel(level)}
              >
                <Text
                  style={[
                    styles.levelTabText,
                    { color: active ? '#FFFFFF' : colors.textSecondary, fontWeight: active ? '700' : '500' },
                  ]}
                >
                  {LEVEL_LABELS[level]}
                </Text>
              </TouchableOpacity>
            );
          })}
        </View>
      </View>

      {/* Class chips */}
      <View style={[styles.filterBar, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
        <ScrollView horizontal showsHorizontalScrollIndicator={false}>
          {CLASSES_BY_LEVEL[selectedLevel].map(cls => {
            const active = selectedClass === cls;
            return (
              <TouchableOpacity
                key={cls}
                style={[
                  styles.classChip,
                  {
                    backgroundColor: active ? colors.accent : colors.surfaceVariant,
                    borderColor: active ? colors.accent : colors.border,
                  },
                ]}
                onPress={() => switchClass(cls)}
              >
                <Text
                  style={[
                    styles.classChipText,
                    { color: active ? '#FFFFFF' : colors.textPrimary, fontWeight: active ? '700' : '600' },
                  ]}
                >
                  {cls}
                </Text>
              </TouchableOpacity>
            );
          })}
        </ScrollView>
      </View>

      {/* Subject filter (within class) */}
      {availableSubjects.length > 2 && (
        <View style={[styles.filterBar, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
          <ScrollView horizontal showsHorizontalScrollIndicator={false}>
            {availableSubjects.map(subj => {
              const active = selectedSubject === subj;
              return (
                <TouchableOpacity
                  key={subj}
                  style={[
                    styles.subjectChip,
                    {
                      backgroundColor: active ? colors.primary : colors.surfaceVariant,
                      borderColor: active ? colors.primary : colors.border,
                    },
                  ]}
                  onPress={() => setSelectedSubject(subj)}
                >
                  <Text
                    style={[
                      styles.subjectChipText,
                      { color: active ? '#FFFFFF' : colors.textPrimary, fontWeight: active ? '700' : '500' },
                    ]}
                  >
                    {subj}
                  </Text>
                </TouchableOpacity>
              );
            })}
          </ScrollView>
        </View>
      )}

      <ScrollView contentContainerStyle={styles.listContent}>
        <Text style={[styles.countText, { color: colors.textMuted }]}>
          {filteredLessons.length} leçon{filteredLessons.length > 1 ? 's' : ''} • {LEVEL_LABELS[selectedLevel]} — Classe de {selectedClass}
        </Text>

        {filteredLessons.length === 0 ? (
          <View style={styles.emptyState}>
            <Text style={styles.emptyIcon}>📭</Text>
            <Text style={[styles.emptyText, { color: colors.textMuted }]}>
              Aucune leçon disponible pour la classe de {selectedClass}.
            </Text>
          </View>
        ) : (
          filteredLessons.map(lesson => (
            <TouchableOpacity
              key={lesson.id}
              style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}
              onPress={() => setActiveLesson(lesson)}
            >
              <View style={styles.cardHeader}>
                <View style={[styles.badge, { backgroundColor: colors.badgeBg }]}>
                  <Text style={[styles.badgeText, { color: colors.primary }]}>{lesson.subject}</Text>
                </View>
                <Text style={[styles.metaText, { color: colors.textMuted }]}>
                  {lesson.gradeClass} • {lesson.durationMinutes} min
                </Text>
              </View>
              <Text style={[styles.title, { color: colors.textPrimary }]}>{lesson.title}</Text>
              <Text style={[styles.chapter, { color: colors.accent }]}>Chapitre : {lesson.chapter}</Text>
              <Text style={[styles.summary, { color: colors.textSecondary }]}>{lesson.summary}</Text>
            </TouchableOpacity>
          ))
        )}
      </ScrollView>

      {/* Lesson Reader Modal */}
      <Modal visible={!!activeLesson} animationType="slide" transparent={false}>
        <SafeAreaView style={[styles.modalContainer, { backgroundColor: colors.background }]}>
          <View style={[styles.modalHeader, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
            <TouchableOpacity onPress={() => setActiveLesson(null)} style={styles.closeBtn}>
              <Text style={[styles.closeBtnText, { color: colors.primary }]}>← Retour</Text>
            </TouchableOpacity>
            <Text style={[styles.modalHeaderTitle, { color: colors.textPrimary }]} numberOfLines={1}>
              {activeLesson?.subject} ({activeLesson?.gradeClass})
            </Text>
            <View style={{ width: 60 }} />
          </View>

          <ScrollView contentContainerStyle={styles.modalBody}>
            <Text style={[styles.modalLessonTitle, { color: colors.textPrimary }]}>{activeLesson?.title}</Text>
            <Text style={[styles.modalChapter, { color: colors.accent }]}>
              {activeLesson?.chapter} • Durée estimée : {activeLesson?.durationMinutes} min
            </Text>
            <View style={[styles.divider, { backgroundColor: colors.border }]} />
            {activeLesson?.blocks && activeLesson.blocks.length > 0 ? (
              <ContenuRich blocks={activeLesson.blocks} />
            ) : (
              <Text style={[styles.lessonContentText, { color: colors.textPrimary }]}>
                {activeLesson?.content}
              </Text>
            )}

            <TouchableOpacity
              style={[styles.completeBtn, { backgroundColor: colors.primary }]}
              onPress={() => {
                alert('Félicitations ! Leçon marquée comme comprise.');
                setActiveLesson(null);
              }}
            >
              <Text style={styles.completeBtnText}>✓ Marquer comme terminée</Text>
            </TouchableOpacity>
          </ScrollView>
        </SafeAreaView>
      </Modal>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  filterBar: { paddingVertical: 10, paddingHorizontal: 16, borderBottomWidth: 1 },
  levelRow: { flexDirection: 'row', gap: 8 },
  levelTab: { flex: 1, paddingVertical: 10, borderRadius: 10, borderWidth: 1, alignItems: 'center' },
  levelTabText: { fontSize: 14 },
  classChip: { paddingHorizontal: 16, paddingVertical: 8, borderRadius: 20, borderWidth: 1, marginRight: 8 },
  classChipText: { fontSize: 13 },
  subjectChip: { paddingHorizontal: 14, paddingVertical: 6, borderRadius: 16, borderWidth: 1, marginRight: 8 },
  subjectChipText: { fontSize: 13 },
  listContent: { padding: 16, paddingBottom: 40 },
  countText: { fontSize: 12, marginBottom: 12 },
  emptyState: { alignItems: 'center', paddingVertical: 60 },
  emptyIcon: { fontSize: 40, marginBottom: 12 },
  emptyText: { fontSize: 14, textAlign: 'center' },
  card: { padding: 16, borderRadius: 14, borderWidth: 1, marginBottom: 12 },
  cardHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 8 },
  badge: { paddingHorizontal: 8, paddingVertical: 3, borderRadius: 6 },
  badgeText: { fontSize: 11, fontWeight: '700' },
  metaText: { fontSize: 12 },
  title: { fontSize: 16, fontWeight: '700', marginBottom: 4 },
  chapter: { fontSize: 12, fontWeight: '600', marginBottom: 6 },
  summary: { fontSize: 13, lineHeight: 18 },
  modalContainer: { flex: 1 },
  modalHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 16,
    paddingVertical: 14,
    borderBottomWidth: 1,
  },
  closeBtn: { width: 70 },
  closeBtnText: { fontSize: 15, fontWeight: '700' },
  modalHeaderTitle: { fontSize: 15, fontWeight: '700', flex: 1, textAlign: 'center' },
  modalBody: { padding: 20, paddingBottom: 60 },
  modalLessonTitle: { fontSize: 20, fontWeight: '800', marginBottom: 6 },
  modalChapter: { fontSize: 13, fontWeight: '600', marginBottom: 14 },
  divider: { height: 1, marginVertical: 14 },
  lessonContentText: { fontSize: 15, lineHeight: 24, marginBottom: 24 },
  completeBtn: { paddingVertical: 14, borderRadius: 12, alignItems: 'center' },
  completeBtnText: { color: '#FFFFFF', fontSize: 15, fontWeight: '700' },
});
