import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
} from 'react-native';
import { useAppTheme } from '../context/ThemeContext';
import { useCurriculum } from '../context/CurriculumContext';

export const ExamsScreen: React.FC = () => {
  const { colors } = useAppTheme();
  const { exams } = useCurriculum();
  const [selectedType, setSelectedType] = useState<string>('BEPC');
  const [expandedSolutions, setExpandedSolutions] = useState<Record<string, boolean>>({});

  const examTypes = ['CEPE', 'BEPC', 'BAC A', 'BAC C', 'BAC D'];

  const filteredExams = exams.filter(e => e.examType === selectedType);

  const toggleSolution = (examId: string) => {
    setExpandedSolutions(prev => ({ ...prev, [examId]: !prev[examId] }));
  };

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
      {/* Types Bar */}
      <View style={[styles.typeBar, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
        <ScrollView horizontal showsHorizontalScrollIndicator={false}>
          {examTypes.map(t => {
            const active = selectedType === t;
            return (
              <TouchableOpacity
                key={t}
                style={[
                  styles.typeChip,
                  {
                    backgroundColor: active ? colors.primary : colors.surfaceVariant,
                    borderColor: active ? colors.primary : colors.border,
                  },
                ]}
                onPress={() => setSelectedType(t)}
              >
                <Text
                  style={[
                    styles.typeChipText,
                    { color: active ? '#FFFFFF' : colors.textPrimary, fontWeight: active ? '700' : '600' },
                  ]}
                >
                  {t}
                </Text>
              </TouchableOpacity>
            );
          })}
        </ScrollView>
      </View>

      <ScrollView contentContainerStyle={styles.scrollContent}>
        <Text style={[styles.headerTitle, { color: colors.textPrimary }]}>
          Annales & Épreuves Officielles de Côte d'Ivoire
        </Text>
        <Text style={[styles.headerSubtitle, { color: colors.textSecondary }]}>
          Sujets réels avec barèmes et corrigés détaillés pour maximiser vos chances de réussite.
        </Text>

        {filteredExams.length === 0 ? (
          <View style={[styles.emptyBox, { backgroundColor: colors.surface, borderColor: colors.border }]}>
            <Text style={[styles.emptyText, { color: colors.textMuted }]}>
              Aucun sujet disponible pour {selectedType} actuellement. Les épreuves seront bientôt ajoutées par l'administrateur.
            </Text>
          </View>
        ) : (
          filteredExams.map(exam => {
            const showSol = expandedSolutions[exam.id];
            return (
              <View key={exam.id} style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
                <View style={styles.cardHeader}>
                  <View style={[styles.badge, { backgroundColor: colors.badgeBg }]}>
                    <Text style={[styles.badgeText, { color: colors.primary }]}>{exam.examType} - {exam.year}</Text>
                  </View>
                  <Text style={[styles.durationText, { color: colors.textMuted }]}>
                    ⏱ {exam.durationMinutes} min • {exam.subject}
                  </Text>
                </View>

                <Text style={[styles.examTitle, { color: colors.textPrimary }]}>{exam.title}</Text>
                <Text style={[styles.instructions, { color: colors.accent }]}>
                  Consignes : {exam.instructions}
                </Text>

                {/* Content Box */}
                <View style={[styles.contentBox, { backgroundColor: colors.surfaceVariant, borderColor: colors.border }]}>
                  <Text style={[styles.contentText, { color: colors.textPrimary }]}>{exam.content}</Text>
                </View>

                {/* Solution Toggle Button */}
                <TouchableOpacity
                  style={[styles.solutionBtn, { backgroundColor: showSol ? colors.surfaceVariant : colors.accent, borderColor: colors.border }]}
                  onPress={() => toggleSolution(exam.id)}
                >
                  <Text style={[styles.solutionBtnText, { color: showSol ? colors.textPrimary : '#FFFFFF' }]}>
                    {showSol ? 'Masquer le corrigé détaillé' : '📖 Voir le corrigé officiel'}
                  </Text>
                </TouchableOpacity>

                {showSol && (
                  <View style={[styles.solutionBox, { backgroundColor: colors.badgeBg, borderColor: colors.primary }]}>
                    <Text style={[styles.solutionTitle, { color: colors.primary }]}>Corrigé & Barème Officiel :</Text>
                    <Text style={[styles.solutionText, { color: colors.textPrimary }]}>{exam.solution}</Text>
                  </View>
                )}
              </View>
            );
          })
        )}
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  typeBar: { paddingVertical: 10, paddingHorizontal: 16, borderBottomWidth: 1 },
  typeChip: { paddingHorizontal: 16, paddingVertical: 8, borderRadius: 16, borderWidth: 1, marginRight: 8 },
  typeChipText: { fontSize: 13 },
  scrollContent: { padding: 16, paddingBottom: 40 },
  headerTitle: { fontSize: 18, fontWeight: '800', marginBottom: 4 },
  headerSubtitle: { fontSize: 13, marginBottom: 16 },
  card: { padding: 16, borderRadius: 16, borderWidth: 1, marginBottom: 16 },
  cardHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 8 },
  badge: { paddingHorizontal: 8, paddingVertical: 3, borderRadius: 6 },
  badgeText: { fontSize: 11, fontWeight: '700' },
  durationText: { fontSize: 12 },
  examTitle: { fontSize: 16, fontWeight: '700', marginBottom: 4 },
  instructions: { fontSize: 12, fontWeight: '600', marginBottom: 12 },
  contentBox: { padding: 14, borderRadius: 10, borderWidth: 1, marginBottom: 14 },
  contentText: { fontSize: 13, lineHeight: 20 },
  solutionBtn: { paddingVertical: 10, borderRadius: 10, alignItems: 'center', borderWidth: 1 },
  solutionBtnText: { fontSize: 14, fontWeight: '700' },
  solutionBox: { padding: 14, borderRadius: 10, borderWidth: 1, marginTop: 12 },
  solutionTitle: { fontSize: 14, fontWeight: '700', marginBottom: 6 },
  solutionText: { fontSize: 13, lineHeight: 20 },
  emptyBox: { padding: 24, borderRadius: 14, borderWidth: 1, alignItems: 'center', marginTop: 20 },
  emptyText: { fontSize: 14, textAlign: 'center', lineHeight: 20 },
});
