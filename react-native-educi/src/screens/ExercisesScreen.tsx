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

export const ExercisesScreen: React.FC = () => {
  const { colors } = useAppTheme();
  const { exercises } = useCurriculum();
  const [selectedAnswers, setSelectedAnswers] = useState<Record<string, number>>({});
  const [validated, setValidated] = useState<Record<string, boolean>>({});

  const handleSelectOption = (exerciseId: string, optionIndex: number) => {
    if (validated[exerciseId]) return;
    setSelectedAnswers(prev => ({ ...prev, [exerciseId]: optionIndex }));
  };

  const handleValidate = (exerciseId: string) => {
    if (selectedAnswers[exerciseId] !== undefined) {
      setValidated(prev => ({ ...prev, [exerciseId]: true }));
    }
  };

  const handleReset = (exerciseId: string) => {
    setSelectedAnswers(prev => {
      const copy = { ...prev };
      delete copy[exerciseId];
      return copy;
    });
    setValidated(prev => {
      const copy = { ...prev };
      delete copy[exerciseId];
      return copy;
    });
  };

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
      <ScrollView contentContainerStyle={styles.scrollContent}>
        <Text style={[styles.headerTitle, { color: colors.textPrimary }]}>Exercices & Quiz Auto-corrigés</Text>
        <Text style={[styles.headerSubtitle, { color: colors.textSecondary }]}>
          Testez vos connaissances en temps réel avec explications pédagogiques détaillées.
        </Text>

        {exercises.map((ex, index) => {
          const selected = selectedAnswers[ex.id];
          const isValidated = validated[ex.id];
          const isCorrect = selected === ex.correctOptionIndex;

          return (
            <View key={ex.id} style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
              <View style={styles.cardHeader}>
                <View style={[styles.badge, { backgroundColor: colors.badgeBg }]}>
                  <Text style={[styles.badgeText, { color: colors.primary }]}>
                    {ex.subject} • {ex.gradeClass}
                  </Text>
                </View>
                <Text style={[styles.difficultyText, { color: colors.accent }]}>Niveau : {ex.difficulty}</Text>
              </View>

              <Text style={[styles.questionNumber, { color: colors.primary }]}>Exercice #{index + 1}</Text>
              <Text style={[styles.questionText, { color: colors.textPrimary }]}>{ex.question}</Text>

              {/* Options */}
              <View style={styles.optionsList}>
                {ex.options.map((opt, optIdx) => {
                  const isOptSelected = selected === optIdx;
                  let optBg = colors.surfaceVariant;
                  let optBorder = colors.border;
                  let optTextColor = colors.textPrimary;

                  if (isValidated) {
                    if (optIdx === ex.correctOptionIndex) {
                      optBg = colors.badgeBg;
                      optBorder = colors.success;
                      optTextColor = colors.success;
                    } else if (isOptSelected && !isCorrect) {
                      optBg = '#FEE2E2';
                      optBorder = colors.error;
                      optTextColor = colors.error;
                    }
                  } else if (isOptSelected) {
                    optBg = colors.badgeBg;
                    optBorder = colors.primary;
                    optTextColor = colors.primary;
                  }

                  return (
                    <TouchableOpacity
                      key={optIdx}
                      style={[styles.optionItem, { backgroundColor: optBg, borderColor: optBorder }]}
                      onPress={() => handleSelectOption(ex.id, optIdx)}
                      disabled={isValidated}
                    >
                      <Text style={[styles.optionLabel, { color: optTextColor }]}>
                        {String.fromCharCode(65 + optIdx)}. {opt}
                      </Text>
                    </TouchableOpacity>
                  );
                })}
              </View>

              {/* Validation Feedback */}
              {isValidated && (
                <View
                  style={[
                    styles.feedbackBox,
                    {
                      backgroundColor: isCorrect ? colors.badgeBg : '#FEE2E2',
                      borderColor: isCorrect ? colors.success : colors.error,
                    },
                  ]}
                >
                  <Text style={[styles.feedbackTitle, { color: isCorrect ? colors.success : colors.error }]}>
                    {isCorrect ? '✓ Excellente réponse !' : '✗ Mauvaise réponse'}
                  </Text>
                  <Text style={[styles.explanationText, { color: colors.textPrimary }]}>
                    {ex.explanation}
                  </Text>
                </View>
              )}

              {/* Action Buttons */}
              <View style={styles.actionRow}>
                {!isValidated ? (
                  <TouchableOpacity
                    style={[
                      styles.actionBtn,
                      {
                        backgroundColor: selected !== undefined ? colors.primary : colors.border,
                      },
                    ]}
                    onPress={() => handleValidate(ex.id)}
                    disabled={selected === undefined}
                  >
                    <Text style={styles.actionBtnText}>Vérifier ma réponse</Text>
                  </TouchableOpacity>
                ) : (
                  <TouchableOpacity
                    style={[styles.actionBtn, { backgroundColor: colors.accent }]}
                    onPress={() => handleReset(ex.id)}
                  >
                    <Text style={styles.actionBtnText}>Recommencer</Text>
                  </TouchableOpacity>
                )}
              </View>
            </View>
          );
        })}
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  scrollContent: { padding: 16, paddingBottom: 40 },
  headerTitle: { fontSize: 20, fontWeight: '800', marginBottom: 4 },
  headerSubtitle: { fontSize: 13, marginBottom: 16 },
  card: { padding: 16, borderRadius: 16, borderWidth: 1, marginBottom: 16 },
  cardHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 10 },
  badge: { paddingHorizontal: 8, paddingVertical: 3, borderRadius: 6 },
  badgeText: { fontSize: 11, fontWeight: '700' },
  difficultyText: { fontSize: 12, fontWeight: '600' },
  questionNumber: { fontSize: 12, fontWeight: '700', marginBottom: 4 },
  questionText: { fontSize: 15, fontWeight: '600', lineHeight: 22, marginBottom: 14 },
  optionsList: { gap: 8, marginBottom: 14 },
  optionItem: { paddingVertical: 12, paddingHorizontal: 14, borderRadius: 10, borderWidth: 1 },
  optionLabel: { fontSize: 14, fontWeight: '500' },
  feedbackBox: { padding: 12, borderRadius: 10, borderWidth: 1, marginBottom: 14 },
  feedbackTitle: { fontSize: 14, fontWeight: '700', marginBottom: 4 },
  explanationText: { fontSize: 13, lineHeight: 18 },
  actionRow: { flexDirection: 'row', justifyContent: 'flex-end' },
  actionBtn: { paddingVertical: 10, paddingHorizontal: 18, borderRadius: 10 },
  actionBtnText: { color: '#FFFFFF', fontSize: 14, fontWeight: '700' },
});
