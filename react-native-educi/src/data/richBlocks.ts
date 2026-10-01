// Types pour le contenu pédagogique riche (éditeur de blocs).
// Chaque leçon peut contenir une liste de blocs structurés : paragraphes,
// titres, listes, tableaux, figures, graphiques, formules et encadrés
// pédagogiques.

export type RichBlock =
  | { type: 'heading'; text: string; level: 1 | 2 | 3; bold?: boolean }
  | { type: 'paragraph'; text: string; bold?: boolean; italic?: boolean }
  | { type: 'list'; items: string[]; ordered?: boolean }
  | {
      type: 'table';
      headers: string[];
      rows: string[][];
    }
  | {
      type: 'figure';
      caption?: string;
      // Figure simple : emoji/texte (pas de vraie image en RN web sans asset).
      emoji: string;
      label: string;
    }
  | {
      type: 'graph';
      title: string;
      kind: 'bar' | 'line';
      labels: string[];
      values: number[];
    }
  | { type: 'formula'; text: string }
  | { type: 'note'; variant: 'definition' | 'example' | 'warning' | 'tip'; text: string };

export const NOTE_META: Record<
  RichBlock['type'] extends never ? never : 'definition' | 'example' | 'warning' | 'tip',
  { icon: string; title: string }
> = {
  definition: { icon: '📖', title: 'Définition' },
  example: { icon: '🇨🇮', title: 'Exemple concret ivoirien' },
  warning: { icon: '⚠️', title: 'Attention / Piège fréquent' },
  tip: { icon: '💡', title: 'Conseil du Professeur' },
};

export interface LessonWithBlocks {
  // Présent lorsque la leçon a été rédigée avec l'éditeur riche.
  blocks?: RichBlock[];
}