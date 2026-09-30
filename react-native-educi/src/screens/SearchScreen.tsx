import React, { useState, useMemo } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TextInput,
  TouchableOpacity,
  SafeAreaView,
  Modal,
} from 'react-native';
import { useAppTheme } from '../context/ThemeContext';
import { INITIAL_LESSONS, INITIAL_EXAMS, INITIAL_EXERCISES, Lesson, Exam } from '../data/curriculumData';

export const SearchScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { colors } = useAppTheme();
  const [query, setQuery] = useState('');
  const [activeLesson, setActiveLesson] = useState<Lesson | null>(null);
  const [activeExam, setActiveExam] = useState<Exam | null>(null);

  const trimmed = query.trim().toLowerCase();

  const lessonResults = useMemo(() => {
    if (!trimmed) return [];
    return INITIAL_LESSONS.filter(
      l =>
        l.title.toLowerCase().includes(trimmed) ||
        l.subject.toLowerCase().includes(trimmed) ||
        l.chapter.toLowerCase().includes(trimmed) ||
        l.summary.toLowerCase().includes(trimmed)
    );
  }, [trimmed]);

  const examResults = useMemo(() => {
    if (!trimmed) return [];
    return INITIAL_EXAMS.filter(
      e =>
        e.title.toLowerCase().includes(trimmed) ||
        e.subject.toLowerCase().includes(trimmed) ||
        e.examType.toLowerCase().includes(trimmed) ||
        e.content.toLowerCase().includes(trimmed)
    );
  }, [trimmed]);

  const exerciseResults = useMemo(() => {
    if (!trimmed) return [];
    return INITIAL_EXERCISES.filter(
      ex =>
        ex.question.toLowerCase().includes(trimmed) ||
        ex.subject.toLowerCase().includes(trimmed) ||
        ex.title.toLowerCase().includes(trimmed)
    );
  }, [trimmed]);

  const totalResults = lessonResults.length + examResults.length + exerciseResults.length;

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
      {/* Search Header */}
      <View style={[styles.header, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
        <TouchableOpacity onPress={() => navigation.goBack()} style={styles.backBtn}>
          <Text style={[styles.backBtnText, { color: colors.primary }]}>←</Text>
        </TouchableOpacity>
        <View style={[styles.searchBar, { backgroundColor: colors.surfaceVariant, borderColor: colors.border }]}>
          <Text style={styles.searchIcon}>🔍</Text>
          <TextInput
            style={[styles.input, { color: colors.textPrimary }]}
            placeholder="Rechercher cours, formule, épreuve..."
            placeholderTextColor={colors.textMuted}
            value={query}
            onChangeText={setQuery}
            autoFocus
          />
          {query.length > 0 && (
            <TouchableOpacity onPress={() => setQuery('')}>
              <Text style={[styles.clearBtn, { color: colors.textMuted }]}>✕</Text>
            </TouchableOpacity>
          )}
        </View>
      </View>

      <ScrollView contentContainerStyle={styles.content}>
        {query.trim().length === 0 ? (
          <View style={styles.emptyPrompt}>
            <Text style={{ fontSize: 44, marginBottom: 12 }}>🔍</Text>
            <Text style={[styles.emptyPromptTitle, { color: colors.textPrimary }]}>
              Recherche dans tout le programme
            </Text>
            <Text style={[styles.emptyPromptSub, { color: colors.textMuted }]}>
              Tapez un mot clé pour explorer simultanément les leçons, les épreuves d'examen et les exercices corrigés.
            </Text>
            <View style={styles.suggestChips}>
              {['Pythagore', 'BEPC', 'Physique', 'BAC D', 'Accord'].map(tag => (
                <TouchableOpacity
                  key={tag}
                  style={[styles.tagChip, { backgroundColor: colors.badgeBg, borderColor: colors.primary }]}
                  onPress={() => setQuery(tag)}
                >
                  <Text style={[styles.tagText, { color: colors.primary }]}>{tag}</Text>
                </TouchableOpacity>
              ))}
            </View>
          </View>
        ) : (
          <>
            <Text style={[styles.resultCount, { color: colors.textMuted }]}>
              {totalResults} résultat(s) trouvé(s) pour « {query} »
            </Text>

            {/* Leçons */}
            {lessonResults.length > 0 && (
              <View style={styles.section}>
                <Text style={[styles.sectionTitle, { color: colors.primary }]}>
                  📚 Leçons & Cours ({lessonResults.length})
                </Text>
                {lessonResults.map(l => (
                  <TouchableOpacity
                    key={l.id}
                    style={[styles.resultCard, { backgroundColor: colors.surface, borderColor: colors.border }]}
                    onPress={() => setActiveLesson(l)}
                  >
                    <View style={styles.cardHeader}>
                      <View style={[styles.badge, { backgroundColor: colors.badgeBg }]}>
                        <Text style={[styles.badgeText, { color: colors.primary }]}>{l.subject}</Text>
                      </View>
                      <Text style={[styles.gradeText, { color: colors.textMuted }]}>{l.gradeClass}</Text>
                    </View>
                    <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>{l.title}</Text>
                    <Text style={[styles.cardChapter, { color: colors.accent }]}>Chapitre : {l.chapter}</Text>
                    <Text style={[styles.cardSummary, { color: colors.textSecondary }]}>{l.summary}</Text>
                  </TouchableOpacity>
                ))}
              </View>
            )}

            {/* Examens */}
            {examResults.length > 0 && (
              <View style={styles.section}>
                <Text style={[styles.sectionTitle, { color: colors.accent }]}>
                  🎓 Sujets d'Examens ({examResults.length})
                </Text>
                {examResults.map(e => (
                  <TouchableOpacity
                    key={e.id}
                    style={[styles.resultCard, { backgroundColor: colors.surface, borderColor: colors.border }]}
                    onPress={() => setActiveExam(e)}
                  >
                    <View style={styles.cardHeader}>
                      <View style={[styles.badge, { backgroundColor: colors.badgeBg }]}>
                        <Text style={[styles.badgeText, { color: colors.accent }]}>{e.examType} - {e.year}</Text>
                      </View>
                      <Text style={[styles.gradeText, { color: colors.textMuted }]}>{e.subject}</Text>
                    </View>
                    <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>{e.title}</Text>
                    <Text style={[styles.cardSummary, { color: colors.textSecondary }]} numberOfLines={2}>
                      {e.instructions}
                    </Text>
                  </TouchableOpacity>
                ))}
              </View>
            )}

            {/* Exercices */}
            {exerciseResults.length > 0 && (
              <View style={styles.section}>
                <Text style={[styles.sectionTitle, { color: colors.primary }]}>
                  ✍️ Exercices & Quiz ({exerciseResults.length})
                </Text>
                {exerciseResults.map(ex => (
                  <View
                    key={ex.id}
                    style={[styles.resultCard, { backgroundColor: colors.surface, borderColor: colors.border }]}
                  >
                    <View style={styles.cardHeader}>
                      <View style={[styles.badge, { backgroundColor: colors.badgeBg }]}>
                        <Text style={[styles.badgeText, { color: colors.primary }]}>{ex.subject} • {ex.gradeClass}</Text>
                      </View>
                    </View>
                    <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>{ex.question}</Text>
                    <Text style={[styles.cardSummary, { color: colors.textSecondary }]}>
                      Réponse correcte : {ex.options[ex.correctOptionIndex]}
                    </Text>
                  </View>
                ))}
              </View>
            )}

            {totalResults === 0 && (
              <View style={styles.noResultsBox}>
                <Text style={{ fontSize: 32, marginBottom: 8 }}>😕</Text>
                <Text style={[styles.noResultsText, { color: colors.textMuted }]}>
                  Aucun résultat ne correspond à « {query} ». Essayez avec d'autres mots.
                </Text>
              </View>
            )}
          </>
        )}
      </ScrollView>

      {/* Lesson Reader Modal */}
      <Modal visible={!!activeLesson} animationType="slide">
        <SafeAreaView style={[styles.modalContainer, { backgroundColor: colors.background }]}>
          <View style={[styles.modalHeader, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
            <TouchableOpacity onPress={() => setActiveLesson(null)} style={styles.backBtn}>
              <Text style={[styles.backBtnText, { color: colors.primary }]}>← Fermer</Text>
            </TouchableOpacity>
            <Text style={[styles.modalHeaderTitle, { color: colors.textPrimary }]} numberOfLines={1}>
              {activeLesson?.title}
            </Text>
          </View>
          <ScrollView contentContainerStyle={styles.modalBody}>
            <Text style={[styles.modalChapter, { color: colors.accent }]}>
              {activeLesson?.subject} • {activeLesson?.gradeClass} • {activeLesson?.chapter}
            </Text>
            <View style={[styles.modalDivider, { backgroundColor: colors.border }]} />
            <Text style={[styles.modalContent, { color: colors.textPrimary }]}>
              {activeLesson?.content}
            </Text>
          </ScrollView>
        </SafeAreaView>
      </Modal>

      {/* Exam Reader Modal */}
      <Modal visible={!!activeExam} animationType="slide">
        <SafeAreaView style={[styles.modalContainer, { backgroundColor: colors.background }]}>
          <View style={[styles.modalHeader, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
            <TouchableOpacity onPress={() => setActiveExam(null)} style={styles.backBtn}>
              <Text style={[styles.backBtnText, { color: colors.primary }]}>← Fermer</Text>
            </TouchableOpacity>
            <Text style={[styles.modalHeaderTitle, { color: colors.textPrimary }]} numberOfLines={1}>
              {activeExam?.title}
            </Text>
          </View>
          <ScrollView contentContainerStyle={styles.modalBody}>
            <Text style={[styles.modalChapter, { color: colors.accent }]}>
              {activeExam?.examType} ({activeExam?.year}) • {activeExam?.subject}
            </Text>
            <Text style={[styles.instructions, { color: colors.textSecondary }]}>
              {activeExam?.instructions}
            </Text>
            <View style={[styles.modalDivider, { backgroundColor: colors.border }]} />
            <Text style={[styles.modalContent, { color: colors.textPrimary }]}>
              {activeExam?.content}
            </Text>
            <View style={[styles.solutionBox, { backgroundColor: colors.surfaceVariant, borderColor: colors.border }]}>
              <Text style={[styles.solutionTitle, { color: colors.primary }]}>Corrigé indicatif :</Text>
              <Text style={[styles.modalContent, { color: colors.textPrimary }]}>
                {activeExam?.solution}
              </Text>
            </View>
          </ScrollView>
        </SafeAreaView>
      </Modal>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 12,
    paddingVertical: 10,
    borderBottomWidth: 1,
    gap: 8,
  },
  backBtn: { padding: 8 },
  backBtnText: { fontSize: 20, fontWeight: '700' },
  searchBar: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    borderRadius: 24,
    borderWidth: 1,
    paddingHorizontal: 12,
    height: 42,
  },
  searchIcon: { fontSize: 16, marginRight: 6 },
  input: { flex: 1, fontSize: 14 },
  clearBtn: { fontSize: 14, padding: 4 },
  content: { padding: 16, paddingBottom: 40 },
  emptyPrompt: { alignItems: 'center', paddingVertical: 40, paddingHorizontal: 20 },
  emptyPromptTitle: { fontSize: 18, fontWeight: '700', marginBottom: 8, textAlign: 'center' },
  emptyPromptSub: { fontSize: 13, textAlign: 'center', lineHeight: 20, marginBottom: 20 },
  suggestChips: { flexDirection: 'row', flexWrap: 'wrap', gap: 8, justifyContent: 'center' },
  tagChip: { paddingHorizontal: 14, paddingVertical: 6, borderRadius: 16, borderWidth: 1 },
  tagText: { fontSize: 12, fontWeight: '600' },
  resultCount: { fontSize: 13, marginBottom: 16 },
  section: { marginBottom: 24 },
  sectionTitle: { fontSize: 16, fontWeight: '700', marginBottom: 12 },
  resultCard: {
    padding: 14,
    borderRadius: 12,
    borderWidth: 1,
    marginBottom: 10,
  },
  cardHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 6 },
  badge: { paddingHorizontal: 8, paddingVertical: 3, borderRadius: 6 },
  badgeText: { fontSize: 11, fontWeight: '700' },
  gradeText: { fontSize: 12 },
  cardTitle: { fontSize: 15, fontWeight: '700', marginBottom: 4 },
  cardChapter: { fontSize: 12, fontWeight: '600', marginBottom: 6 },
  cardSummary: { fontSize: 13, lineHeight: 18 },
  noResultsBox: { alignItems: 'center', paddingVertical: 40 },
  noResultsText: { fontSize: 14, textAlign: 'center' },
  modalContainer: { flex: 1 },
  modalHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 14,
    borderBottomWidth: 1,
    gap: 12,
  },
  modalHeaderTitle: { fontSize: 15, fontWeight: '700', flex: 1 },
  modalBody: { padding: 18, paddingBottom: 40 },
  modalChapter: { fontSize: 14, fontWeight: '600', marginBottom: 6 },
  instructions: { fontSize: 13, fontStyle: 'italic', marginBottom: 12 },
  modalDivider: { height: 1, marginVertical: 14 },
  modalContent: { fontSize: 14, lineHeight: 22 },
  solutionBox: { marginTop: 20, padding: 14, borderRadius: 10, borderWidth: 1 },
  solutionTitle: { fontSize: 14, fontWeight: '700', marginBottom: 8 },
});
