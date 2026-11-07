/** Ordre officiel des classes du système éducatif ivoirien (CP1 → Terminale). */
export const CLASS_ORDER = [
  'CI', 'CP1', 'CP2', 'CE1', 'CE2', 'CM1', 'CM2',
  '6e', '5e', '4e', '3e', '2nde', '1ère', 'Tle',
];

const rank = (gradeClass: string) => {
  const index = CLASS_ORDER.indexOf(gradeClass);
  return index === -1 ? CLASS_ORDER.length : index;
};

/** Déduplique et trie une liste de classes dans l'ordre du programme. */
export const sortClasses = (classes: string[]): string[] =>
  Array.from(new Set(classes.filter(Boolean))).sort((a, b) => rank(a) - rank(b));
