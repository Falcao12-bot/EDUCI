import React, { createContext, useContext, useEffect, useState } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { INITIAL_LESSONS, Lesson } from '../data/curriculumData';

export interface ExerciseResult {
  selected: number;
  correct: boolean;
}

export interface Progress {
  completedLessons: string[];
  exerciseResults: Record<string, ExerciseResult>;
}

const STORAGE_KEYS = {
  customLessons: '@educi_custom_lessons',
  deletedLessons: '@educi_deleted_lessons',
  progress: '@educi_progress',
  selectedClass: '@educi_selected_class',
  readNotifications: '@educi_read_notifications',
};

const EMPTY_PROGRESS: Progress = { completedLessons: [], exerciseResults: {} };

interface AppDataContextType {
  ready: boolean;
  /** Leçons du programme + leçons publiées par le propriétaire. */
  lessons: Lesson[];
  progress: Progress;
  selectedClass: string | null;
  setSelectedClass: (gradeClass: string | null) => void;
  addLesson: (lesson: Lesson) => void;
  deleteLesson: (lessonId: string) => void;
  toggleLessonCompleted: (lessonId: string) => void;
  recordExerciseResult: (exerciseId: string, result: ExerciseResult) => void;
  clearExerciseResult: (exerciseId: string) => void;
  readNotificationIds: string[];
  setReadNotificationIds: (ids: string[]) => void;
}

const AppDataContext = createContext<AppDataContextType>({
  ready: false,
  lessons: INITIAL_LESSONS,
  progress: EMPTY_PROGRESS,
  selectedClass: null,
  setSelectedClass: () => {},
  addLesson: () => {},
  deleteLesson: () => {},
  toggleLessonCompleted: () => {},
  recordExerciseResult: () => {},
  clearExerciseResult: () => {},
  readNotificationIds: [],
  setReadNotificationIds: () => {},
});

const persist = (key: string, value: unknown) => {
  AsyncStorage.setItem(key, JSON.stringify(value)).catch(() => {
    // stockage local indisponible : on garde l'état en mémoire
  });
};

export const AppDataProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [customLessons, setCustomLessons] = useState<Lesson[]>([]);
  const [deletedLessonIds, setDeletedLessonIds] = useState<string[]>([]);
  const [progress, setProgress] = useState<Progress>(EMPTY_PROGRESS);
  const [selectedClass, setSelectedClassState] = useState<string | null>(null);
  const [readNotificationIds, setReadNotificationIdsState] = useState<string[]>([]);
  const [ready, setReady] = useState(false);

  useEffect(() => {
    (async () => {
      const read = async (key: string) => {
        try {
          const raw = await AsyncStorage.getItem(key);
          return raw ? JSON.parse(raw) : null;
        } catch {
          return null;
        }
      };

      const [storedLessons, storedDeleted, storedProgress, storedClass, storedNotifications] =
        await Promise.all([
          read(STORAGE_KEYS.customLessons),
          read(STORAGE_KEYS.deletedLessons),
          read(STORAGE_KEYS.progress),
          read(STORAGE_KEYS.selectedClass),
          read(STORAGE_KEYS.readNotifications),
        ]);

      if (Array.isArray(storedLessons)) setCustomLessons(storedLessons);
      if (Array.isArray(storedDeleted)) setDeletedLessonIds(storedDeleted);
      if (storedProgress && typeof storedProgress === 'object') {
        setProgress({
          completedLessons: Array.isArray(storedProgress.completedLessons) ? storedProgress.completedLessons : [],
          exerciseResults:
            storedProgress.exerciseResults && typeof storedProgress.exerciseResults === 'object'
              ? storedProgress.exerciseResults
              : {},
        });
      }
      if (typeof storedClass === 'string') setSelectedClassState(storedClass);
      if (Array.isArray(storedNotifications)) setReadNotificationIdsState(storedNotifications);

      setReady(true);
    })();
  }, []);

  const lessons: Lesson[] = [
    ...customLessons,
    ...INITIAL_LESSONS.filter(l => !deletedLessonIds.includes(l.id)),
  ];

  const setSelectedClass = (gradeClass: string | null) => {
    setSelectedClassState(gradeClass);
    persist(STORAGE_KEYS.selectedClass, gradeClass);
  };

  const addLesson = (lesson: Lesson) => {
    setCustomLessons(prev => {
      const next = [lesson, ...prev];
      persist(STORAGE_KEYS.customLessons, next);
      return next;
    });
  };

  const deleteLesson = (lessonId: string) => {
    if (customLessons.some(l => l.id === lessonId)) {
      setCustomLessons(prev => {
        const next = prev.filter(l => l.id !== lessonId);
        persist(STORAGE_KEYS.customLessons, next);
        return next;
      });
      return;
    }
    setDeletedLessonIds(prev => {
      if (prev.includes(lessonId)) return prev;
      const next = [...prev, lessonId];
      persist(STORAGE_KEYS.deletedLessons, next);
      return next;
    });
  };

  const toggleLessonCompleted = (lessonId: string) => {
    setProgress(prev => {
      const isDone = prev.completedLessons.includes(lessonId);
      const next: Progress = {
        ...prev,
        completedLessons: isDone
          ? prev.completedLessons.filter(id => id !== lessonId)
          : [...prev.completedLessons, lessonId],
      };
      persist(STORAGE_KEYS.progress, next);
      return next;
    });
  };

  const recordExerciseResult = (exerciseId: string, result: ExerciseResult) => {
    setProgress(prev => {
      const next: Progress = {
        ...prev,
        exerciseResults: { ...prev.exerciseResults, [exerciseId]: result },
      };
      persist(STORAGE_KEYS.progress, next);
      return next;
    });
  };

  const clearExerciseResult = (exerciseId: string) => {
    setProgress(prev => {
      const exerciseResults = { ...prev.exerciseResults };
      delete exerciseResults[exerciseId];
      const next: Progress = { ...prev, exerciseResults };
      persist(STORAGE_KEYS.progress, next);
      return next;
    });
  };

  const setReadNotificationIds = (ids: string[]) => {
    setReadNotificationIdsState(ids);
    persist(STORAGE_KEYS.readNotifications, ids);
  };

  return (
    <AppDataContext.Provider
      value={{
        ready,
        lessons,
        progress,
        selectedClass,
        setSelectedClass,
        addLesson,
        deleteLesson,
        toggleLessonCompleted,
        recordExerciseResult,
        clearExerciseResult,
        readNotificationIds,
        setReadNotificationIds,
      }}
    >
      {children}
    </AppDataContext.Provider>
  );
};

export const useAppData = () => useContext(AppDataContext);
