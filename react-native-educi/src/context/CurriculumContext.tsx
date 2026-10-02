import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {
  Lesson,
  Exercise,
  Exam,
  INITIAL_LESSONS,
  INITIAL_EXERCISES,
  INITIAL_EXAMS,
} from '../data/curriculumData';

const LESSONS_KEY = '@educi_lessons';
const EXERCISES_KEY = '@educi_exercises';
const EXAMS_KEY = '@educi_exams';

interface CurriculumContextType {
  lessons: Lesson[];
  exercises: Exercise[];
  exams: Exam[];
  addLesson: (lesson: Lesson) => void;
  updateLesson: (lesson: Lesson) => void;
  deleteLesson: (id: string) => void;
  addExercise: (exercise: Exercise) => void;
  updateExercise: (exercise: Exercise) => void;
  deleteExercise: (id: string) => void;
  addExam: (exam: Exam) => void;
  updateExam: (exam: Exam) => void;
  deleteExam: (id: string) => void;
}

const CurriculumContext = createContext<CurriculumContextType>({
  lessons: [],
  exercises: [],
  exams: [],
  addLesson: () => {},
  updateLesson: () => {},
  deleteLesson: () => {},
  addExercise: () => {},
  updateExercise: () => {},
  deleteExercise: () => {},
  addExam: () => {},
  updateExam: () => {},
  deleteExam: () => {},
});

export const CurriculumProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [lessons, setLessons] = useState<Lesson[]>(INITIAL_LESSONS);
  const [exercises, setExercises] = useState<Exercise[]>(INITIAL_EXERCISES);
  const [exams, setExams] = useState<Exam[]>(INITIAL_EXAMS);
  const [loaded, setLoaded] = useState(false);

  // Charger les données persistées au démarrage
  useEffect(() => {
    (async () => {
      try {
        const [savedLessons, savedExercises, savedExams] = await Promise.all([
          AsyncStorage.getItem(LESSONS_KEY),
          AsyncStorage.getItem(EXERCISES_KEY),
          AsyncStorage.getItem(EXAMS_KEY),
        ]);
        if (savedLessons) setLessons(JSON.parse(savedLessons));
        if (savedExercises) setExercises(JSON.parse(savedExercises));
        if (savedExams) setExams(JSON.parse(savedExams));
      } catch (e) {
        // garde les valeurs initiales en cas d'erreur
      }
      setLoaded(true);
    })();
  }, []);

  // Persister automatiquement à chaque changement (après le chargement initial)
  useEffect(() => {
    if (loaded) AsyncStorage.setItem(LESSONS_KEY, JSON.stringify(lessons)).catch(() => {});
  }, [lessons, loaded]);

  useEffect(() => {
    if (loaded) AsyncStorage.setItem(EXERCISES_KEY, JSON.stringify(exercises)).catch(() => {});
  }, [exercises, loaded]);

  useEffect(() => {
    if (loaded) AsyncStorage.setItem(EXAMS_KEY, JSON.stringify(exams)).catch(() => {});
  }, [exams, loaded]);

  // --- Leçons ---
  const addLesson = useCallback((lesson: Lesson) => {
    setLessons(prev => [lesson, ...prev]);
  }, []);

  const updateLesson = useCallback((lesson: Lesson) => {
    setLessons(prev => prev.map(l => (l.id === lesson.id ? lesson : l)));
  }, []);

  const deleteLesson = useCallback((id: string) => {
    setLessons(prev => prev.filter(l => l.id !== id));
  }, []);

  // --- Exercices ---
  const addExercise = useCallback((exercise: Exercise) => {
    setExercises(prev => [exercise, ...prev]);
  }, []);

  const updateExercise = useCallback((exercise: Exercise) => {
    setExercises(prev => prev.map(e => (e.id === exercise.id ? exercise : e)));
  }, []);

  const deleteExercise = useCallback((id: string) => {
    setExercises(prev => prev.filter(e => e.id !== id));
  }, []);

  // --- Examens ---
  const addExam = useCallback((exam: Exam) => {
    setExams(prev => [exam, ...prev]);
  }, []);

  const updateExam = useCallback((exam: Exam) => {
    setExams(prev => prev.map(e => (e.id === exam.id ? exam : e)));
  }, []);

  const deleteExam = useCallback((id: string) => {
    setExams(prev => prev.filter(e => e.id !== id));
  }, []);

  return (
    <CurriculumContext.Provider
      value={{
        lessons,
        exercises,
        exams,
        addLesson,
        updateLesson,
        deleteLesson,
        addExercise,
        updateExercise,
        deleteExercise,
        addExam,
        updateExam,
        deleteExam,
      }}
    >
      {children}
    </CurriculumContext.Provider>
  );
};

export const useCurriculum = () => useContext(CurriculumContext);
