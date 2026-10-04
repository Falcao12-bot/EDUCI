import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TextInput,
  TouchableOpacity,
  SafeAreaView,
  Alert,
} from 'react-native';
import { useAppTheme } from '../context/ThemeContext';
import { useAuth, OWNER_EMAIL, OWNER_MASTER_KEY } from '../context/AuthContext';
import { INITIAL_LESSONS, Lesson, INITIAL_EXERCISES } from '../data/curriculumData';
import { EditeurBlocs } from '../components/EditeurBlocs';
import { RichBlock } from '../data/richBlocks';

export const AdminScreen: React.FC = () => {
  const { colors } = useAppTheme();
  const { currentUser, isOwnerOrAdmin, claimOwnerAccess } = useAuth();

  const [inputKey, setInputKey] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  // Active admin tab: 'lessons' | 'exercises' | 'users'
  const [activeTab, setActiveTab] = useState<'lessons' | 'exercises' | 'users'>('lessons');

  // New lesson form state
  const [lessonTitle, setLessonTitle] = useState('');
  const [lessonSubject, setLessonSubject] = useState('Mathématiques');
  const [lessonClass, setLessonClass] = useState('4e');
  const [lessonChapter, setLessonChapter] = useState('');
  const [lessonSummary, setLessonSummary] = useState('');
  const [lessonBlocks, setLessonBlocks] = useState<RichBlock[]>([]);

  const [lessonsList, setLessonsList] = useState<Lesson[]>(INITIAL_LESSONS);

  // Security gate: If not verified owner/admin
  if (!isOwnerOrAdmin) {
    const handleUnlock = () => {
      if (inputKey.trim() === OWNER_MASTER_KEY) {
        const success = claimOwnerAccess(inputKey.trim());
        if (success) {
          setErrorMessage('');
          Alert.alert('Succès', 'Accès Propriétaire déverrouillé avec succès !');
        }
      } else {
        setErrorMessage('Clé secrète incorrecte. Seul le propriétaire légitime possède cette clé.');
      }
    };

    return (
      <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
        <ScrollView contentContainerStyle={styles.gateScroll}>
          <View style={[styles.gateCard, { backgroundColor: colors.surface, borderColor: colors.border }]}>
            <Text style={styles.gateLockIcon}>🔒</Text>
            <Text style={[styles.gateTitle, { color: colors.textPrimary }]}>
              Accès Propriétaire Exclusif
            </Text>
            <Text style={[styles.gateSubtitle, { color: colors.textSecondary }]}>
              Conformément à la politique de sécurité d'EduCI, seul le propriétaire fondateur ({OWNER_EMAIL}) est autorisé à apporter des modifications au contenu de l'application (cours, exercices, examens et comptes).
            </Text>

            <View style={styles.keyInputContainer}>
              <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>
                Clé Maître d'Administration :
              </Text>
              <TextInput
                style={[
                  styles.textInput,
                  {
                    backgroundColor: colors.surfaceVariant,
                    borderColor: errorMessage ? colors.error : colors.border,
                    color: colors.textPrimary,
                  },
                ]}
                placeholder="Entrez votre clé secrète propriétaire..."
                placeholderTextColor={colors.textMuted}
                secureTextEntry
                value={inputKey}
                onChangeText={text => {
                  setInputKey(text);
                  setErrorMessage('');
                }}
              />
            </View>

            {errorMessage ? (
              <Text style={[styles.errorText, { color: colors.error }]}>{errorMessage}</Text>
            ) : null}

            <TouchableOpacity
              style={[styles.unlockBtn, { backgroundColor: colors.primary }]}
              onPress={handleUnlock}
            >
              <Text style={styles.unlockBtnText}>🔑 Déverrouiller le Panneau Propriétaire</Text>
            </TouchableOpacity>

            <View style={[styles.infoBanner, { backgroundColor: colors.badgeBg, borderColor: colors.primary }]}>
              <Text style={[styles.infoBannerText, { color: colors.primary }]}>
                🔒 La clé propriétaire est confidentielle. Seul le fondateur d'EduCI la possède.
              </Text>
            </View>
          </View>
        </ScrollView>
      </SafeAreaView>
    );
  }

  // Admin / Owner Dashboard
  const handleAddLesson = () => {
    if (!lessonTitle.trim() || !lessonChapter.trim()) {
      Alert.alert('Erreur', 'Veuillez remplir au moins le titre et le chapitre.');
      return;
    }
    if (lessonBlocks.length === 0) {
      Alert.alert('Erreur', 'Ajoutez au moins un bloc pédagogique (titre, texte, tableau...) avec l\'éditeur riche.');
      return;
    }

    // Texte en clair généré à partir des blocs pour compatibilité.
    const plainText = lessonBlocks
      .map(b => ('text' in b ? b.text : ''))
      .filter(t => t.trim())
      .join('\n');

    const newLesson: Lesson = {
      id: `lesson_${Date.now()}`,
      title: lessonTitle.trim(),
      subject: lessonSubject,
      gradeClass: lessonClass,
      chapter: lessonChapter.trim(),
      summary: lessonSummary.trim() || 'Leçon ajoutée par le propriétaire.',
      content: plainText || 'Leçon rédigée avec l\'éditeur riche.',
      durationMinutes: 25,
      published: true,
      blocks: lessonBlocks,
    };

    setLessonsList(prev => [newLesson, ...prev]);
    setLessonTitle('');
    setLessonChapter('');
    setLessonSummary('');
    setLessonBlocks([]);
    Alert.alert('Succès', 'Nouvelle leçon publiée avec succès dans le programme !');
  };

  const handleDeleteLesson = (id: string) => {
    Alert.alert(
      'Confirmer la suppression',
      'Êtes-vous sûr de vouloir supprimer cette leçon ?',
      [
        { text: 'Annuler', style: 'cancel' },
        {
          text: 'Supprimer',
          style: 'destructive',
          onPress: () => {
            setLessonsList(prev => prev.filter(l => l.id !== id));
            Alert.alert('Succès', 'Leçon supprimée.');
          },
        },
      ]
    );
  };

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
      {/* Header */}
      <View style={[styles.adminHeader, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
        <View>
          <Text style={[styles.adminHeaderTitle, { color: colors.primary }]}>Console Propriétaire EduCI</Text>
          <Text style={[styles.adminHeaderSub, { color: colors.textMuted }]}>
            Connecté : {currentUser?.email || OWNER_EMAIL}
          </Text>
        </View>
        <View style={[styles.verifiedBadge, { backgroundColor: colors.badgeBg }]}>
          <Text style={[styles.verifiedText, { color: colors.primary }]}>✓ Accès Maître</Text>
        </View>
      </View>

      {/* Tabs */}
      <View style={[styles.tabBar, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
        {(['lessons', 'exercises', 'users'] as const).map(tab => {
          const active = activeTab === tab;
          const labels = { lessons: 'Cours & Leçons', exercises: 'Exercices', users: 'Utilisateurs' };
          return (
            <TouchableOpacity
              key={tab}
              style={[
                styles.tabItem,
                { borderBottomColor: active ? colors.primary : 'transparent', borderBottomWidth: 3 },
              ]}
              onPress={() => setActiveTab(tab)}
            >
              <Text
                style={[
                  styles.tabText,
                  { color: active ? colors.primary : colors.textMuted, fontWeight: active ? '700' : '500' },
                ]}
              >
                {labels[tab]}
              </Text>
            </TouchableOpacity>
          );
        })}
      </View>

      <ScrollView contentContainerStyle={styles.scrollContent}>
        {activeTab === 'lessons' && (
          <View>
            {/* Form */}
            <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
              <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>➕ Ajouter une Leçon Officielle</Text>
              
              <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Titre de la leçon :</Text>
              <TextInput
                style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
                placeholder="Ex: Théorème de Thalès"
                placeholderTextColor={colors.textMuted}
                value={lessonTitle}
                onChangeText={setLessonTitle}
              />

              <View style={styles.formRow}>
                <View style={{ flex: 1, marginRight: 8 }}>
                  <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Matière :</Text>
                  <TextInput
                    style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
                    value={lessonSubject}
                    onChangeText={setLessonSubject}
                  />
                </View>
                <View style={{ flex: 1, marginLeft: 8 }}>
                  <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Classe :</Text>
                  <TextInput
                    style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
                    value={lessonClass}
                    onChangeText={setLessonClass}
                  />
                </View>
              </View>

              <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Chapitre :</Text>
              <TextInput
                style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
                placeholder="Ex: Géométrie vectorielle"
                placeholderTextColor={colors.textMuted}
                value={lessonChapter}
                onChangeText={setLessonChapter}
              />

              <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Résumé court :</Text>
              <TextInput
                style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
                placeholder="Objectifs de la leçon..."
                placeholderTextColor={colors.textMuted}
                value={lessonSummary}
                onChangeText={setLessonSummary}
              />

              <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Contenu complet du cours (Éditeur Riche) :</Text>
              <EditeurBlocs initialBlocks={lessonBlocks} onChange={setLessonBlocks} />

              <TouchableOpacity style={[styles.submitBtn, { backgroundColor: colors.primary }]} onPress={handleAddLesson}>
                <Text style={styles.submitBtnText}>Publier la Leçon</Text>
              </TouchableOpacity>
            </View>

            {/* Existing Lessons List with Delete */}
            <Text style={[styles.sectionTitle, { color: colors.textPrimary }]}>
              Gestion des Leçons ({lessonsList.length})
            </Text>
            {lessonsList.map(item => (
              <View key={item.id} style={[styles.itemRow, { backgroundColor: colors.surface, borderColor: colors.border }]}>
                <View style={{ flex: 1 }}>
                  <Text style={[styles.itemTitle, { color: colors.textPrimary }]}>{item.title}</Text>
                  <Text style={[styles.itemSub, { color: colors.textMuted }]}>
                    {item.subject} • {item.gradeClass} • {item.chapter}
                  </Text>
                </View>
                <TouchableOpacity
                  style={[styles.deleteBtn, { backgroundColor: '#FEE2E2' }]}
                  onPress={() => handleDeleteLesson(item.id)}
                >
                  <Text style={{ color: colors.error, fontWeight: '700' }}>Supprimer</Text>
                </TouchableOpacity>
              </View>
            ))}
          </View>
        )}

        {activeTab === 'exercises' && (
          <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
            <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>Exercices Pédagogiques ({INITIAL_EXERCISES.length})</Text>
            <Text style={[styles.itemSub, { color: colors.textSecondary, marginBottom: 12 }]}>
              Vous pouvez ajouter de nouveaux quiz ou modifier les énoncés existants.
            </Text>
            {INITIAL_EXERCISES.map(ex => (
              <View key={ex.id} style={[styles.itemRow, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, marginBottom: 8 }]}>
                <View style={{ flex: 1 }}>
                  <Text style={[styles.itemTitle, { color: colors.textPrimary }]}>{ex.title}</Text>
                  <Text style={[styles.itemSub, { color: colors.textMuted }]}>{ex.subject} ({ex.gradeClass}) - Difficulté : {ex.difficulty}</Text>
                </View>
              </View>
            ))}
          </View>
        )}

        {activeTab === 'users' && (
          <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
            <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>Gestion des Comptes Utilisateurs</Text>
            <Text style={[styles.itemSub, { color: colors.textSecondary, marginBottom: 14 }]}>
              Règle stricte EduCI : Aucun compte par défaut. Seuls les vrais utilisateurs inscrits apparaissent ici.
            </Text>
            <View style={[styles.infoBanner, { backgroundColor: colors.badgeBg, borderColor: colors.primary }]}>
              <Text style={[styles.infoBannerText, { color: colors.primary }]}>
                Compte Maître Fondateur : {OWNER_EMAIL} (Rôle : Propriétaire Unique).
              </Text>
            </View>
          </View>
        )}
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  gateScroll: { flexGrow: 1, justifyContent: 'center', padding: 20 },
  gateCard: { padding: 24, borderRadius: 20, borderWidth: 1, alignItems: 'center' },
  gateLockIcon: { fontSize: 48, marginBottom: 12 },
  gateTitle: { fontSize: 20, fontWeight: '800', textAlign: 'center', marginBottom: 8 },
  gateSubtitle: { fontSize: 13, textAlign: 'center', lineHeight: 20, marginBottom: 20 },
  keyInputContainer: { width: '100%', marginBottom: 14 },
  inputLabel: { fontSize: 13, fontWeight: '700', marginBottom: 6 },
  textInput: { height: 46, borderRadius: 10, borderWidth: 1, paddingHorizontal: 12, fontSize: 14, marginBottom: 10 },
  textArea: { height: 110, borderRadius: 10, borderWidth: 1, padding: 12, fontSize: 14, textAlignVertical: 'top', marginBottom: 14 },
  errorText: { fontSize: 13, textAlign: 'center', marginBottom: 12 },
  unlockBtn: { width: '100%', paddingVertical: 14, borderRadius: 12, alignItems: 'center', marginBottom: 16 },
  unlockBtnText: { color: '#FFFFFF', fontSize: 15, fontWeight: '700' },
  infoBanner: { padding: 12, borderRadius: 10, borderWidth: 1, width: '100%' },
  infoBannerText: { fontSize: 12, textAlign: 'center', lineHeight: 18 },
  adminHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', padding: 16, borderBottomWidth: 1 },
  adminHeaderTitle: { fontSize: 18, fontWeight: '800' },
  adminHeaderSub: { fontSize: 12, marginTop: 2 },
  verifiedBadge: { paddingHorizontal: 10, paddingVertical: 4, borderRadius: 12 },
  verifiedText: { fontSize: 11, fontWeight: '700' },
  tabBar: { flexDirection: 'row', borderBottomWidth: 1 },
  tabItem: { flex: 1, paddingVertical: 12, alignItems: 'center' },
  tabText: { fontSize: 13 },
  scrollContent: { padding: 16, paddingBottom: 40 },
  card: { padding: 16, borderRadius: 16, borderWidth: 1, marginBottom: 20 },
  cardTitle: { fontSize: 16, fontWeight: '700', marginBottom: 14 },
  formRow: { flexDirection: 'row' },
  submitBtn: { paddingVertical: 12, borderRadius: 10, alignItems: 'center', marginTop: 4 },
  submitBtnText: { color: '#FFFFFF', fontSize: 15, fontWeight: '700' },
  sectionTitle: { fontSize: 16, fontWeight: '700', marginBottom: 10 },
  itemRow: { flexDirection: 'row', alignItems: 'center', padding: 12, borderRadius: 10, borderWidth: 1, marginBottom: 8 },
  itemTitle: { fontSize: 14, fontWeight: '700' },
  itemSub: { fontSize: 12, marginTop: 2 },
  deleteBtn: { paddingHorizontal: 12, paddingVertical: 6, borderRadius: 8 },
});
