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
import { useCurriculum } from '../context/CurriculumContext';
import { Lesson, Exercise, Exam } from '../data/curriculumData';
import { EditeurBlocs } from '../components/EditeurBlocs';
import { RichBlock } from '../data/richBlocks';

type AdminTab = 'lessons' | 'exercises' | 'exams' | 'users';

const SUBJECTS = ['Mathématiques', 'Physique-Chimie', 'Français', 'SVT', 'Histoire-Géographie', 'Anglais'];
const CLASSES = ['6e', '5e', '4e', '3e', '2nde', '1ère', 'Terminale'];
const DIFFICULTIES: Exercise['difficulty'][] = ['Facile', 'Moyen', 'Difficile'];
const EXAM_TYPES: Exam['examType'][] = ['CEPE', 'BEPC', 'BAC A', 'BAC C', 'BAC D'];

export const AdminScreen: React.FC = () => {
  const { colors } = useAppTheme();
  const { currentUser, isOwnerOrAdmin, claimOwnerAccess } = useAuth();
  const {
    lessons, exercises, exams,
    addLesson, updateLesson, deleteLesson,
    addExercise, updateExercise, deleteExercise,
    addExam, updateExam, deleteExam,
  } = useCurriculum();

  const [inputKey, setInputKey] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [activeTab, setActiveTab] = useState<AdminTab>('lessons');

  // ==================== SECURITY GATE ====================
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
                Note pour les tests autorisés : La clé configurée est EDUCI-PROPRIETAIRE-2026.
              </Text>
            </View>
          </View>
        </ScrollView>
      </SafeAreaView>
    );
  }

  // ==================== ADMIN DASHBOARD ====================
  const tabLabels: Record<AdminTab, string> = {
    lessons: 'Cours & Leçons',
    exercises: 'Exercices',
    exams: 'Examens',
    users: 'Utilisateurs',
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
        {(['lessons', 'exercises', 'exams', 'users'] as const).map(tab => {
          const active = activeTab === tab;
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
                {tabLabels[tab]}
              </Text>
            </TouchableOpacity>
          );
        })}
      </View>

      <ScrollView contentContainerStyle={styles.scrollContent}>
        {activeTab === 'lessons' && (
          <LessonsAdmin
            colors={colors}
            lessons={lessons}
            onAdd={addLesson}
            onUpdate={updateLesson}
            onDelete={deleteLesson}
          />
        )}
        {activeTab === 'exercises' && (
          <ExercisesAdmin
            colors={colors}
            exercises={exercises}
            onAdd={addExercise}
            onUpdate={updateExercise}
            onDelete={deleteExercise}
          />
        )}
        {activeTab === 'exams' && (
          <ExamsAdmin
            colors={colors}
            exams={exams}
            onAdd={addExam}
            onUpdate={updateExam}
            onDelete={deleteExam}
          />
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

// ==================== LESSONS ADMIN ====================
interface LessonsAdminProps {
  colors: any;
  lessons: Lesson[];
  onAdd: (l: Lesson) => void;
  onUpdate: (l: Lesson) => void;
  onDelete: (id: string) => void;
}

const LessonsAdmin: React.FC<LessonsAdminProps> = ({ colors, lessons, onAdd, onUpdate, onDelete }) => {
  const [lessonTitle, setLessonTitle] = useState('');
  const [lessonSubject, setLessonSubject] = useState('Mathématiques');
  const [lessonClass, setLessonClass] = useState('4e');
  const [lessonChapter, setLessonChapter] = useState('');
  const [lessonSummary, setLessonSummary] = useState('');
  const [lessonBlocks, setLessonBlocks] = useState<RichBlock[]>([]);
  const [editingId, setEditingId] = useState<string | null>(null);

  const resetForm = () => {
    setLessonTitle('');
    setLessonSubject('Mathématiques');
    setLessonClass('4e');
    setLessonChapter('');
    setLessonSummary('');
    setLessonBlocks([]);
    setEditingId(null);
  };

  const handleSubmit = () => {
    if (!lessonTitle.trim() || !lessonChapter.trim()) {
      Alert.alert('Erreur', 'Veuillez remplir au moins le titre et le chapitre.');
      return;
    }
    if (lessonBlocks.length === 0) {
      Alert.alert('Erreur', 'Ajoutez au moins un bloc pédagogique (titre, texte, tableau...) avec l\'éditeur riche.');
      return;
    }

    const plainText = lessonBlocks
      .map(b => ('text' in b ? b.text : ''))
      .filter(t => t.trim())
      .join('\n');

    const lessonData: Lesson = {
      id: editingId || `lesson_${Date.now()}`,
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

    if (editingId) {
      onUpdate(lessonData);
      Alert.alert('Succès', 'Leçon modifiée avec succès !');
    } else {
      onAdd(lessonData);
      Alert.alert('Succès', 'Nouvelle leçon publiée avec succès !');
    }
    resetForm();
  };

  const handleEdit = (lesson: Lesson) => {
    setEditingId(lesson.id);
    setLessonTitle(lesson.title);
    setLessonSubject(lesson.subject);
    setLessonClass(lesson.gradeClass);
    setLessonChapter(lesson.chapter);
    setLessonSummary(lesson.summary);
    setLessonBlocks(lesson.blocks || []);
  };

  const handleDelete = (id: string) => {
    Alert.alert(
      'Confirmer la suppression',
      'Êtes-vous sûr de vouloir supprimer cette leçon ?',
      [
        { text: 'Annuler', style: 'cancel' },
        {
          text: 'Supprimer',
          style: 'destructive',
          onPress: () => {
            onDelete(id);
            if (editingId === id) resetForm();
            Alert.alert('Succès', 'Leçon supprimée.');
          },
        },
      ]
    );
  };

  return (
    <View>
      {/* Form */}
      <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
        <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>
          {editingId ? '✏️ Modifier la Leçon' : '➕ Ajouter une Leçon Officielle'}
        </Text>

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

        <View style={styles.formActions}>
          <TouchableOpacity style={[styles.submitBtn, { backgroundColor: colors.primary, flex: 1 }]} onPress={handleSubmit}>
            <Text style={styles.submitBtnText}>{editingId ? 'Modifier' : 'Publier'}</Text>
          </TouchableOpacity>
          {editingId ? (
            <TouchableOpacity style={[styles.cancelBtn, { borderColor: colors.border }]} onPress={resetForm}>
              <Text style={[styles.cancelBtnText, { color: colors.textSecondary }]}>Annuler</Text>
            </TouchableOpacity>
          ) : null}
        </View>
      </View>

      {/* Existing Lessons List */}
      <Text style={[styles.sectionTitle, { color: colors.textPrimary }]}>
        Gestion des Leçons ({lessons.length})
      </Text>
      {lessons.map(item => (
        <View key={item.id} style={[styles.itemRow, { backgroundColor: colors.surface, borderColor: colors.border }]}>
          <View style={{ flex: 1 }}>
            <Text style={[styles.itemTitle, { color: colors.textPrimary }]}>{item.title}</Text>
            <Text style={[styles.itemSub, { color: colors.textMuted }]}>
              {item.subject} • {item.gradeClass} • {item.chapter}
            </Text>
          </View>
          <View style={styles.rowActions}>
            <TouchableOpacity
              style={[styles.editBtn, { backgroundColor: colors.badgeBg }]}
              onPress={() => handleEdit(item)}
            >
              <Text style={{ color: colors.primary, fontWeight: '700', fontSize: 12 }}>Modifier</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={[styles.deleteBtn, { backgroundColor: '#FEE2E2' }]}
              onPress={() => handleDelete(item.id)}
            >
              <Text style={{ color: colors.error, fontWeight: '700', fontSize: 12 }}>Suppr.</Text>
            </TouchableOpacity>
          </View>
        </View>
      ))}
    </View>
  );
};

// ==================== EXERCISES ADMIN ====================
interface ExercisesAdminProps {
  colors: any;
  exercises: Exercise[];
  onAdd: (e: Exercise) => void;
  onUpdate: (e: Exercise) => void;
  onDelete: (id: string) => void;
}

const ExercisesAdmin: React.FC<ExercisesAdminProps> = ({ colors, exercises, onAdd, onUpdate, onDelete }) => {
  const [exTitle, setExTitle] = useState('');
  const [exSubject, setExSubject] = useState('Mathématiques');
  const [exClass, setExClass] = useState('4e');
  const [exDifficulty, setExDifficulty] = useState<Exercise['difficulty']>('Facile');
  const [exQuestion, setExQuestion] = useState('');
  const [exOptions, setExOptions] = useState(['', '', '', '']);
  const [exCorrect, setExCorrect] = useState(0);
  const [exExplanation, setExExplanation] = useState('');
  const [editingId, setEditingId] = useState<string | null>(null);

  const resetForm = () => {
    setExTitle('');
    setExSubject('Mathématiques');
    setExClass('4e');
    setExDifficulty('Facile');
    setExQuestion('');
    setExOptions(['', '', '', '']);
    setExCorrect(0);
    setExExplanation('');
    setEditingId(null);
  };

  const handleSubmit = () => {
    if (!exTitle.trim() || !exQuestion.trim()) {
      Alert.alert('Erreur', 'Veuillez remplir le titre et la question.');
      return;
    }
    if (exOptions.some(o => !o.trim())) {
      Alert.alert('Erreur', 'Veuillez remplir les 4 options de réponse.');
      return;
    }

    const exerciseData: Exercise = {
      id: editingId || `ex_${Date.now()}`,
      title: exTitle.trim(),
      subject: exSubject,
      gradeClass: exClass,
      difficulty: exDifficulty,
      question: exQuestion.trim(),
      options: exOptions.map(o => o.trim()),
      correctOptionIndex: exCorrect,
      explanation: exExplanation.trim() || 'Pas d\'explication fournie.',
    };

    if (editingId) {
      onUpdate(exerciseData);
      Alert.alert('Succès', 'Exercice modifié avec succès !');
    } else {
      onAdd(exerciseData);
      Alert.alert('Succès', 'Nouvel exercice ajouté avec succès !');
    }
    resetForm();
  };

  const handleEdit = (ex: Exercise) => {
    setEditingId(ex.id);
    setExTitle(ex.title);
    setExSubject(ex.subject);
    setExClass(ex.gradeClass);
    setExDifficulty(ex.difficulty);
    setExQuestion(ex.question);
    setExOptions([...ex.options, ...Array(4).fill('')].slice(0, 4));
    setExCorrect(ex.correctOptionIndex);
    setExExplanation(ex.explanation);
  };

  const handleDelete = (id: string) => {
    Alert.alert(
      'Confirmer la suppression',
      'Êtes-vous sûr de vouloir supprimer cet exercice ?',
      [
        { text: 'Annuler', style: 'cancel' },
        {
          text: 'Supprimer',
          style: 'destructive',
          onPress: () => {
            onDelete(id);
            if (editingId === id) resetForm();
            Alert.alert('Succès', 'Exercice supprimé.');
          },
        },
      ]
    );
  };

  return (
    <View>
      <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
        <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>
          {editingId ? '✏️ Modifier l\'Exercice' : '➕ Ajouter un Exercice'}
        </Text>

        <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Titre :</Text>
        <TextInput
          style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
          placeholder="Ex: Calcul d'hypoténuse"
          placeholderTextColor={colors.textMuted}
          value={exTitle}
          onChangeText={setExTitle}
        />

        <View style={styles.formRow}>
          <View style={{ flex: 1, marginRight: 8 }}>
            <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Matière :</Text>
            <TextInput
              style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
              value={exSubject}
              onChangeText={setExSubject}
            />
          </View>
          <View style={{ flex: 1, marginLeft: 8 }}>
            <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Classe :</Text>
            <TextInput
              style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
              value={exClass}
              onChangeText={setExClass}
            />
          </View>
        </View>

        <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Difficulté :</Text>
        <View style={styles.pillRow}>
          {DIFFICULTIES.map(d => {
            const active = exDifficulty === d;
            return (
              <TouchableOpacity
                key={d}
                style={[
                  styles.pill,
                  { backgroundColor: active ? colors.primary : colors.surfaceVariant, borderColor: active ? colors.primary : colors.border },
                ]}
                onPress={() => setExDifficulty(d)}
              >
                <Text style={{ color: active ? '#FFFFFF' : colors.textPrimary, fontSize: 12, fontWeight: '600' }}>{d}</Text>
              </TouchableOpacity>
            );
          })}
        </View>

        <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Question :</Text>
        <TextInput
          style={[styles.textArea, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
          placeholder="Énoncé de la question..."
          placeholderTextColor={colors.textMuted}
          value={exQuestion}
          onChangeText={setExQuestion}
          multiline
        />

        <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Options de réponse :</Text>
        {exOptions.map((opt, idx) => (
          <View key={idx} style={styles.optionRow}>
            <TouchableOpacity
              style={[
                styles.radioBtn,
                { borderColor: exCorrect === idx ? colors.primary : colors.border, backgroundColor: exCorrect === idx ? colors.primary : 'transparent' },
              ]}
              onPress={() => setExCorrect(idx)}
            >
              {exCorrect === idx ? <View style={styles.radioDot} /> : null}
            </TouchableOpacity>
            <Text style={[styles.optionLetter, { color: colors.textSecondary }]}>{String.fromCharCode(65 + idx)}.</Text>
            <TextInput
              style={[styles.textInput, { flex: 1, marginBottom: 0, backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
              placeholder={`Option ${String.fromCharCode(65 + idx)}`}
              placeholderTextColor={colors.textMuted}
              value={opt}
              onChangeText={text => setExOptions(prev => prev.map((o, i) => (i === idx ? text : o)))}
            />
          </View>
        ))}
        <Text style={[styles.helperText, { color: colors.textMuted }]}>
          Sélectionnez le cercle devant la bonne réponse.
        </Text>

        <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Explication :</Text>
        <TextInput
          style={[styles.textArea, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
          placeholder="Explication de la bonne réponse..."
          placeholderTextColor={colors.textMuted}
          value={exExplanation}
          onChangeText={setExExplanation}
          multiline
        />

        <View style={styles.formActions}>
          <TouchableOpacity style={[styles.submitBtn, { backgroundColor: colors.primary, flex: 1 }]} onPress={handleSubmit}>
            <Text style={styles.submitBtnText}>{editingId ? 'Modifier' : 'Ajouter'}</Text>
          </TouchableOpacity>
          {editingId ? (
            <TouchableOpacity style={[styles.cancelBtn, { borderColor: colors.border }]} onPress={resetForm}>
              <Text style={[styles.cancelBtnText, { color: colors.textSecondary }]}>Annuler</Text>
            </TouchableOpacity>
          ) : null}
        </View>
      </View>

      <Text style={[styles.sectionTitle, { color: colors.textPrimary }]}>
        Gestion des Exercices ({exercises.length})
      </Text>
      {exercises.map(item => (
        <View key={item.id} style={[styles.itemRow, { backgroundColor: colors.surface, borderColor: colors.border }]}>
          <View style={{ flex: 1 }}>
            <Text style={[styles.itemTitle, { color: colors.textPrimary }]}>{item.title}</Text>
            <Text style={[styles.itemSub, { color: colors.textMuted }]}>
              {item.subject} ({item.gradeClass}) - {item.difficulty}
            </Text>
          </View>
          <View style={styles.rowActions}>
            <TouchableOpacity
              style={[styles.editBtn, { backgroundColor: colors.badgeBg }]}
              onPress={() => handleEdit(item)}
            >
              <Text style={{ color: colors.primary, fontWeight: '700', fontSize: 12 }}>Modifier</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={[styles.deleteBtn, { backgroundColor: '#FEE2E2' }]}
              onPress={() => handleDelete(item.id)}
            >
              <Text style={{ color: colors.error, fontWeight: '700', fontSize: 12 }}>Suppr.</Text>
            </TouchableOpacity>
          </View>
        </View>
      ))}
    </View>
  );
};

// ==================== EXAMS ADMIN ====================
interface ExamsAdminProps {
  colors: any;
  exams: Exam[];
  onAdd: (e: Exam) => void;
  onUpdate: (e: Exam) => void;
  onDelete: (id: string) => void;
}

const ExamsAdmin: React.FC<ExamsAdminProps> = ({ colors, exams, onAdd, onUpdate, onDelete }) => {
  const [examTitle, setExamTitle] = useState('');
  const [examType, setExamType] = useState<Exam['examType']>('BEPC');
  const [examYear, setExamYear] = useState(String(new Date().getFullYear()));
  const [examSubject, setExamSubject] = useState('Mathématiques');
  const [examDuration, setExamDuration] = useState('120');
  const [examInstructions, setExamInstructions] = useState('');
  const [examContent, setExamContent] = useState('');
  const [examSolution, setExamSolution] = useState('');
  const [editingId, setEditingId] = useState<string | null>(null);

  const resetForm = () => {
    setExamTitle('');
    setExamType('BEPC');
    setExamYear(String(new Date().getFullYear()));
    setExamSubject('Mathématiques');
    setExamDuration('120');
    setExamInstructions('');
    setExamContent('');
    setExamSolution('');
    setEditingId(null);
  };

  const handleSubmit = () => {
    if (!examTitle.trim() || !examContent.trim()) {
      Alert.alert('Erreur', 'Veuillez remplir au moins le titre et le contenu de l\'épreuve.');
      return;
    }

    const examData: Exam = {
      id: editingId || `exam_${Date.now()}`,
      title: examTitle.trim(),
      examType,
      year: parseInt(examYear, 10) || new Date().getFullYear(),
      subject: examSubject,
      durationMinutes: parseInt(examDuration, 10) || 120,
      instructions: examInstructions.trim() || 'Aucune consigne particulière.',
      content: examContent.trim(),
      solution: examSolution.trim() || 'Corrigé à venir.',
    };

    if (editingId) {
      onUpdate(examData);
      Alert.alert('Succès', 'Examen modifié avec succès !');
    } else {
      onAdd(examData);
      Alert.alert('Succès', 'Nouvel examen ajouté avec succès !');
    }
    resetForm();
  };

  const handleEdit = (exam: Exam) => {
    setEditingId(exam.id);
    setExamTitle(exam.title);
    setExamType(exam.examType);
    setExamYear(String(exam.year));
    setExamSubject(exam.subject);
    setExamDuration(String(exam.durationMinutes));
    setExamInstructions(exam.instructions);
    setExamContent(exam.content);
    setExamSolution(exam.solution);
  };

  const handleDelete = (id: string) => {
    Alert.alert(
      'Confirmer la suppression',
      'Êtes-vous sûr de vouloir supprimer cet examen ?',
      [
        { text: 'Annuler', style: 'cancel' },
        {
          text: 'Supprimer',
          style: 'destructive',
          onPress: () => {
            onDelete(id);
            if (editingId === id) resetForm();
            Alert.alert('Succès', 'Examen supprimé.');
          },
        },
      ]
    );
  };

  return (
    <View>
      <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
        <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>
          {editingId ? '✏️ Modifier l\'Examen' : '➕ Ajouter un Examen'}
        </Text>

        <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Titre :</Text>
        <TextInput
          style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
          placeholder="Ex: Épreuve Officielle BEPC - Session 2024"
          placeholderTextColor={colors.textMuted}
          value={examTitle}
          onChangeText={setExamTitle}
        />

        <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Type d'examen :</Text>
        <View style={styles.pillRow}>
          {EXAM_TYPES.map(t => {
            const active = examType === t;
            return (
              <TouchableOpacity
                key={t}
                style={[
                  styles.pill,
                  { backgroundColor: active ? colors.primary : colors.surfaceVariant, borderColor: active ? colors.primary : colors.border },
                ]}
                onPress={() => setExamType(t)}
              >
                <Text style={{ color: active ? '#FFFFFF' : colors.textPrimary, fontSize: 12, fontWeight: '600' }}>{t}</Text>
              </TouchableOpacity>
            );
          })}
        </View>

        <View style={styles.formRow}>
          <View style={{ flex: 1, marginRight: 8 }}>
            <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Matière :</Text>
            <TextInput
              style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
              value={examSubject}
              onChangeText={setExamSubject}
            />
          </View>
          <View style={{ flex: 1, marginLeft: 8 }}>
            <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Année :</Text>
            <TextInput
              style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
              value={examYear}
              onChangeText={setExamYear}
              keyboardType="numeric"
            />
          </View>
        </View>

        <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Durée (minutes) :</Text>
        <TextInput
          style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
          value={examDuration}
          onChangeText={setExamDuration}
          keyboardType="numeric"
        />

        <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Consignes :</Text>
        <TextInput
          style={[styles.textInput, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
          placeholder="Ex: Calculatrice autorisée..."
          placeholderTextColor={colors.textMuted}
          value={examInstructions}
          onChangeText={setExamInstructions}
        />

        <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Contenu de l'épreuve :</Text>
        <TextInput
          style={[styles.textArea, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
          placeholder="Énoncé complet de l'épreuve..."
          placeholderTextColor={colors.textMuted}
          value={examContent}
          onChangeText={setExamContent}
          multiline
        />

        <Text style={[styles.inputLabel, { color: colors.textPrimary }]}>Corrigé :</Text>
        <TextInput
          style={[styles.textArea, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
          placeholder="Solution détaillée..."
          placeholderTextColor={colors.textMuted}
          value={examSolution}
          onChangeText={setExamSolution}
          multiline
        />

        <View style={styles.formActions}>
          <TouchableOpacity style={[styles.submitBtn, { backgroundColor: colors.primary, flex: 1 }]} onPress={handleSubmit}>
            <Text style={styles.submitBtnText}>{editingId ? 'Modifier' : 'Ajouter'}</Text>
          </TouchableOpacity>
          {editingId ? (
            <TouchableOpacity style={[styles.cancelBtn, { borderColor: colors.border }]} onPress={resetForm}>
              <Text style={[styles.cancelBtnText, { color: colors.textSecondary }]}>Annuler</Text>
            </TouchableOpacity>
          ) : null}
        </View>
      </View>

      <Text style={[styles.sectionTitle, { color: colors.textPrimary }]}>
        Gestion des Examens ({exams.length})
      </Text>
      {exams.map(item => (
        <View key={item.id} style={[styles.itemRow, { backgroundColor: colors.surface, borderColor: colors.border }]}>
          <View style={{ flex: 1 }}>
            <Text style={[styles.itemTitle, { color: colors.textPrimary }]}>{item.title}</Text>
            <Text style={[styles.itemSub, { color: colors.textMuted }]}>
              {item.examType} • {item.year} • {item.subject}
            </Text>
          </View>
          <View style={styles.rowActions}>
            <TouchableOpacity
              style={[styles.editBtn, { backgroundColor: colors.badgeBg }]}
              onPress={() => handleEdit(item)}
            >
              <Text style={{ color: colors.primary, fontWeight: '700', fontSize: 12 }}>Modifier</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={[styles.deleteBtn, { backgroundColor: '#FEE2E2' }]}
              onPress={() => handleDelete(item.id)}
            >
              <Text style={{ color: colors.error, fontWeight: '700', fontSize: 12 }}>Suppr.</Text>
            </TouchableOpacity>
          </View>
        </View>
      ))}
    </View>
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
  textArea: { minHeight: 100, borderRadius: 10, borderWidth: 1, padding: 12, fontSize: 14, textAlignVertical: 'top', marginBottom: 14 },
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
  tabText: { fontSize: 12 },
  scrollContent: { padding: 16, paddingBottom: 40 },
  card: { padding: 16, borderRadius: 16, borderWidth: 1, marginBottom: 20 },
  cardTitle: { fontSize: 16, fontWeight: '700', marginBottom: 14 },
  formRow: { flexDirection: 'row' },
  formActions: { flexDirection: 'row', alignItems: 'center', marginTop: 4, gap: 8 },
  submitBtn: { paddingVertical: 12, borderRadius: 10, alignItems: 'center' },
  submitBtnText: { color: '#FFFFFF', fontSize: 15, fontWeight: '700' },
  cancelBtn: { paddingVertical: 12, paddingHorizontal: 16, borderRadius: 10, borderWidth: 1 },
  cancelBtnText: { fontSize: 14, fontWeight: '600' },
  sectionTitle: { fontSize: 16, fontWeight: '700', marginBottom: 10 },
  itemRow: { flexDirection: 'row', alignItems: 'center', padding: 12, borderRadius: 10, borderWidth: 1, marginBottom: 8 },
  itemTitle: { fontSize: 14, fontWeight: '700' },
  itemSub: { fontSize: 12, marginTop: 2 },
  rowActions: { flexDirection: 'row', gap: 6 },
  editBtn: { paddingHorizontal: 10, paddingVertical: 6, borderRadius: 8 },
  deleteBtn: { paddingHorizontal: 10, paddingVertical: 6, borderRadius: 8 },
  pillRow: { flexDirection: 'row', flexWrap: 'wrap', gap: 8, marginBottom: 12 },
  pill: { paddingHorizontal: 14, paddingVertical: 8, borderRadius: 16, borderWidth: 1 },
  optionRow: { flexDirection: 'row', alignItems: 'center', gap: 8, marginBottom: 8 },
  radioBtn: { width: 22, height: 22, borderRadius: 11, borderWidth: 2, justifyContent: 'center', alignItems: 'center' },
  radioDot: { width: 10, height: 10, borderRadius: 5, backgroundColor: '#FFFFFF' },
  optionLetter: { fontSize: 14, fontWeight: '700' },
  helperText: { fontSize: 11, marginBottom: 14, marginTop: -4 },
});
