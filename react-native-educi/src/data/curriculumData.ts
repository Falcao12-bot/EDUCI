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
  blocks?: import('./richBlocks').RichBlock[];
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

export const INITIAL_LESSONS: Lesson[] = [
  {
    id: 'les_math_01',
    title: 'Théorème de Pythagore et applications',
    subject: 'Mathématiques',
    gradeClass: '4e',
    chapter: 'Géométrie du triangle rectangle',
    summary: 'Calculer la longueur de l\'hypoténuse et démontrer qu\'un triangle est rectangle.',
    content: `### 1. Énoncé du Théorème
Dans un triangle rectangle, le carré de la longueur de l'hypoténuse est égal à la somme des carrés des longueurs des deux autres côtés.

Si le triangle ABC est rectangle en A, alors :
**BC² = AB² + AC²**

### 2. Exemple d'application
Soit un triangle rectangle en A où AB = 3 cm et AC = 4 cm.
Calcul de BC :
BC² = 3² + 4² = 9 + 16 = 25
Donc **BC = √25 = 5 cm**.

### 3. Réciproque du théorème
Si dans un triangle ABC, le plus grand côté vérifie BC² = AB² + AC², alors ce triangle est rectangle en A.`,
    durationMinutes: 25,
    published: true,
    blocks: [
      { type: 'heading', text: 'Théorème de Pythagore et applications', level: 1 },
      { type: 'paragraph', text: 'Le théorème de Pythagore est l\'un des outils géométriques les plus importants du programme ivoirien. Il permet de calculer une longueur dans un triangle rectangle et de démontrer qu\'un triangle est rectangle.', bold: true },
      { type: 'heading', text: '1. Énoncé du Théorème', level: 2 },
      { type: 'paragraph', text: 'Dans un triangle rectangle, le carré de la longueur de l\'hypoténuse (côté opposé à l\'angle droit) est égal à la somme des carrés des longueurs des deux autres côtés.' },
      { type: 'formula', text: 'BC² = AB² + AC²' },
      { type: 'note', variant: 'definition', text: 'L\'hypoténuse est toujours le côté le plus long du triangle rectangle.' },
      { type: 'paragraph', text: 'Exemple concret : un maçon d\'Abidjan veut vérifier qu\'un mur est droit. Un triangle de côtés 3m, 4m et 5m est rectangle car 3² + 4² = 9 + 16 = 25 = 5². C\'est la méthode du « cordeau 3-4-5 » !', italic: true },
      { type: 'heading', text: '2. Tableau récapitulatif des triplets usuels', level: 2 },
      {
        type: 'table',
        headers: ['Côté 1', 'Côté 2', 'Hypoténuse'],
        rows: [
          ['3', '4', '5'],
          ['6', '8', '10'],
          ['5', '12', '13'],
        ],
      },
      { type: 'heading', text: '3. Représentation d\'un triangle rectangle', level: 2 },
      { type: 'figure', emoji: '📐', label: 'Triangle rectangle ABC en A', caption: 'Triangle rectangle : l\'angle droit est au sommet A.' },
      { type: 'heading', text: '4. Application : longueur de l\'hypoténuse selon les données', level: 2 },
      {
        type: 'graph',
        title: 'Hypoténuse obtenue pour différentes données',
        kind: 'bar',
        labels: ['3×4', '6×8', '5×12'],
        values: [5, 10, 13],
      },
      { type: 'note', variant: 'warning', text: 'Ne confondez jamais l\'hypoténuse avec l\'un des côtés de l\'angle droit ! L\'hypoténuse est toujours opposée à l\'angle droit.' },
      { type: 'note', variant: 'tip', text: 'Au BEPC, le théorème de Pythagore tombe presque chaque année. Entraînez-vous sur les triplets 3-4-5, 6-8-10 et 5-12-13 pour gagner du temps.' },
    ],
  },
  {
    id: 'les_pc_01',
    title: 'La Masse Volumique des corps purs',
    subject: 'Physique-Chimie',
    gradeClass: '4e',
    chapter: 'Matière et Propriétés',
    summary: 'Définition, unité internationale kg/m³ et calculs pratiques pour les solides et liquides.',
    content: `### 1. Notion de Masse Volumique
La masse volumique ρ (rhô) d'un corps est le quotient de sa masse m par son volume V.

**Formule : ρ = m / V**
- Masse m en kilogrammes (kg) ou grammes (g)
- Volume V en mètres cubes (m³) ou centimètres cubes (cm³)

Pour l'eau pure : **ρ_eau = 1 g/cm³ = 1000 kg/m³**.`,
    durationMinutes: 20,
    published: true,
  },
  {
    id: 'les_fr_01',
    title: 'L\'accord du participe passé avec Être et Avoir',
    subject: 'Français',
    gradeClass: '3e',
    chapter: 'Grammaire et Conjugaison',
    summary: 'Règles fondamentales pour éviter les fautes courantes au BEPC.',
    content: `### 1. Auxiliaire ÊTRE
Le participe passé conjugué avec l'auxiliaire être s'accorde toujours en genre et en nombre avec le sujet du verbe.
*Exemple : Les élèves sont arrivés à l'heure.*

### 2. Auxiliaire AVOIR
Le participe passé conjugué avec l'auxiliaire avoir ne s'accorde jamais avec le sujet. Il s'accorde en genre et en nombre avec le Complément d'Objet Direct (COD) uniquement si ce COD est placé **avant** le verbe.
*Exemple : Les leçons qu'il a apprises.*`,
    durationMinutes: 30,
    published: true,
  },
  {
    id: 'les_svt_01',
    title: 'La respiration chez les êtres vivants',
    subject: 'SVT',
    gradeClass: '5e',
    chapter: 'Fonctions vitales',
    summary: 'Échanges gazeux, poumons, branchies et trachées selon le milieu de vie.',
    content: `### 1. Les échanges gazeux respiratoires
Tous les êtres vivants respirent : ils absorbent du dioxygène (O₂) et rejettent du dioxyde de carbone (CO₂).

### 2. Organes respiratoires
- Milieu aérien : Poumons (Homme, mammifères), Trachées (Criquet, insectes).
- Milieu aquatique : Branchies (Poissons comme le Tilapia et la Carpe).`,
    durationMinutes: 20,
    published: true,
  }
];

export const INITIAL_EXERCISES: Exercise[] = [
  {
    id: 'ex_math_01',
    title: 'Hypoténuse d\'un triangle rectangle',
    subject: 'Mathématiques',
    gradeClass: '4e',
    difficulty: 'Facile',
    question: 'Soit un triangle rectangle dont les côtés de l\'angle droit mesurent 6 cm et 8 cm. Quelle est la longueur de l\'hypoténuse ?',
    options: ['10 cm', '14 cm', '12 cm', '48 cm'],
    correctOptionIndex: 0,
    explanation: 'D\'après le théorème de Pythagore : 6² + 8² = 36 + 64 = 100. La racine carrée de 100 est 10 cm.',
  },
  {
    id: 'ex_fr_01',
    title: 'Accord avec le COD placé avant',
    subject: 'Français',
    gradeClass: '3e',
    difficulty: 'Moyen',
    question: 'Complétez correctement : "Voici les fleurs que j\'ai ______ ce matin au marché."',
    options: ['cueilli', 'cueillies', 'cueillis', 'cueillie'],
    correctOptionIndex: 1,
    explanation: 'Le COD "les fleurs" (féminin pluriel) est placé avant l\'auxiliaire avoir, le participe passé s\'accorde donc : cueillies.',
  },
  {
    id: 'ex_pc_01',
    title: 'Masse volumique de l\'eau',
    subject: 'Physique-Chimie',
    gradeClass: '4e',
    difficulty: 'Facile',
    question: 'Quelle est la masse d\'un volume de 2 litres d\'eau pure à température ambiante ?',
    options: ['1 kg', '2 kg', '500 g', '4 kg'],
    correctOptionIndex: 1,
    explanation: 'Puisque la masse volumique de l\'eau est de 1 kg/L, 2 litres d\'eau correspondent exactement à 2 kg.',
  }
];

export const INITIAL_EXAMS: Exam[] = [
  {
    id: 'exam_bepc_math_2024',
    title: 'Épreuve Officielle BEPC - Session 2024',
    examType: 'BEPC',
    year: 2024,
    subject: 'Mathématiques',
    durationMinutes: 120,
    instructions: 'Calculatrice autorisée selon la réglementation en vigueur. Soignez la rédaction.',
    content: `### Exercice 1 (4 points) - Algèbre
Résoudre dans R l'équation : (2x - 3)(x + 4) = 0.

### Exercice 2 (6 points) - Géométrie
On considère un repère orthonormé (O, I, J).
1. Placer les points A(2, 3), B(-1, 1) et C(4, 0).
2. Démontrer que le triangle ABC est isocèle.

### Exercice 3 (10 points) - Problème de synthèse
Un planteur de cacao de Daloa souhaite clôturer sa parcelle rectangulaire de 120 m sur 80 m...`,
    solution: `### Corrigé Exercice 1 :
(2x - 3)(x + 4) = 0 équivaut à 2x - 3 = 0 ou x + 4 = 0.
Soit x = 3/2 ou x = -4.
L'ensemble des solutions est S = {-4 ; 1.5}.`,
  },
  {
    id: 'exam_bac_d_pc_2024',
    title: 'Épreuve Officielle BAC Série D - Session 2024',
    examType: 'BAC D',
    year: 2024,
    subject: 'Physique-Chimie',
    durationMinutes: 180,
    instructions: 'Traitez la chimie sur une copie séparée de la physique.',
    content: `### CHIMIE : Cinétique Chimique et Dosages acido-basiques
Étude de la réaction entre l'ion thiosulfate et les ions oxonium.

### PHYSIQUE : Mouvement dans un champ de pesanteur uniforme
Lancement d'un projectile depuis une hauteur h = 1,50 m avec une vitesse initiale v0...`,
    solution: `### Corrigé Chimie :
1. Équation bilan : S2O3²⁻ + 2H3O⁺ -> S + SO2 + 3H2O.
Le soufre précipite sous forme solide, ce qui provoque l'opacité progressive.`,
  }
];
