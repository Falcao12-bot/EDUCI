import type { RichBlock } from './richBlocks';

/** Modèles de contenu pédagogique partagés par les banques de données. */
export interface Lesson {
  id: string;
  title: string;
  subject: string;
  gradeClass: string;
  chapter: string;
  summary: string;
  content: string;
  durationMinutes: number;
  published: boolean;
  // Blocs riches créés avec l'éditeur pédagogique (optionnel).
  blocks?: RichBlock[];
}

export interface Exercise {
  id: string;
  title: string;
  subject: string;
  gradeClass: string;
  difficulty: 'Facile' | 'Moyen' | 'Difficile';
  question: string;
  options: string[];
  correctOptionIndex: number;
  explanation: string;
}

export interface Exam {
  id: string;
  title: string;
  examType: 'CEPE' | 'BEPC' | 'BAC A' | 'BAC C' | 'BAC D';
  year: number;
  subject: string;
  durationMinutes: number;
  instructions: string;
  content: string;
  solution: string;
}
