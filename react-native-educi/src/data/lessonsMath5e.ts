// Leçons de Mathématiques 5e — Livret 2026-2027 (Côte d'Ivoire)
// 11 modules extraits du livret et structurés en blocs pédagogiques riches
// pour améliorer la compréhension des élèves.
// Chaque leçon suit le chemin pédagogique du livret :
//   1. Je découvre  2. Je retiens  3. Mes astuces  4. Je m'entraîne
//   5. Je vérifie  6. À la maison

import type { RichBlock } from './richBlocks';
import type { Lesson } from './curriculumData';

/* ------------------------------------------------------------------ */
/*  MODULE 1 — Les nombres premiers                                    */
/* ------------------------------------------------------------------ */
const module1: Lesson = {
  id: 'les_math5e_01',
  title: 'Les nombres premiers',
  subject: 'Mathématiques',
  gradeClass: '5e',
  chapter: 'Calculs algébriques',
  summary: 'Puissances, division dans ℕ, reconnaissance et décomposition en facteurs premiers.',
  content: `### Les nombres premiers
Puissances, division euclidienne, nombres premiers et décomposition en facteurs premiers.`,
  durationMinutes: 35,
  published: true,
  blocks: [
    { type: 'heading', text: 'Les nombres premiers', level: 1 },
    { type: 'paragraph', text: 'Je sais calculer avec les puissances et reconnaître les nombres premiers.', bold: true, italic: true },
    { type: 'note', variant: 'definition', text: 'À la fin de cette leçon, je saurai : calculer une puissance et appliquer la règle de priorité ; utiliser les formules (a × b)ⁿ = aⁿ × bⁿ et aⁿ × aᵐ = aⁿ⁺ᵐ ; effectuer une division dans ℕ et l\'écrire sous la forme a = b × q + r ; reconnaître un nombre premier et décomposer un nombre en produit de facteurs premiers.' },

    { type: 'heading', text: '1. Je découvre', level: 2 },
    { type: 'paragraph', text: 'La coopérative du collège de Man a produit 30 cartons d\'œufs. Chaque carton contient 30 plaquettes et chaque plaquette 30 œufs. Il a fallu investir 350 000 F. Chaque œuf est vendu 30 F. Un livre coûte 3 700 F.' },
    { type: 'paragraph', text: 'Les élèves veulent connaître le bénéfice et le nombre de livres qu\'ils pourront acheter.' },
    { type: 'list', ordered: true, items: [
      'Combien d\'œufs la coopérative a-t-elle produits ? (écris le calcul avec une puissance)',
      'Quelle somme obtient-elle en vendant tous les œufs ?',
      'Quel est le bénéfice ?',
      'Combien de livres peut-on acheter ? Que reste-t-il ?',
    ]},

    { type: 'heading', text: '2. Je retiens', level: 2 },
    { type: 'paragraph', text: 'L\'essentiel à savoir', bold: true },
    { type: 'note', variant: 'definition', text: 'Puissance : aⁿ = a × a × … × a (n facteurs). Ex. : 2⁴ = 2 × 2 × 2 × 2 = 16. On a a¹ = a et, pour a ≠ 0, a⁰ = 1.' },
    { type: 'paragraph', text: 'Priorité des opérations : 1) les parenthèses ; 2) les puissances ; 3) les multiplications et divisions ; 4) les additions et soustractions.' },
    { type: 'formula', text: 'Formule 1 : (a × b)ⁿ = aⁿ × bⁿ' },
    { type: 'paragraph', text: 'Ex. : (2 × 5)³ = 2³ × 5³ = 8 × 125 = 1 000.', italic: true },
    { type: 'formula', text: 'Formule 2 : aⁿ × aᵐ = aⁿ⁺ᵐ' },
    { type: 'paragraph', text: 'Même nombre a, on additionne les exposants. Ex. : 3² × 3⁴ = 3⁶.', italic: true },
    { type: 'note', variant: 'definition', text: 'Division dans ℕ : diviser a par b (b ≠ 0), c\'est trouver q et r tels que a = b × q + r avec r < b. q est le quotient, r le reste. Si r = 0, a est un multiple de b et b est un diviseur de a.' },
    { type: 'paragraph', text: 'Encadrer : si a n\'est pas un multiple de b, alors b × q < a < b × (q + 1). Ces deux multiples sont consécutifs.' },
    { type: 'note', variant: 'definition', text: 'Nombre premier : un nombre entier qui a exactement deux diviseurs : 1 et lui-même. 0 et 1 ne sont pas premiers. 2 est le seul nombre premier pair.' },
    { type: 'paragraph', text: 'Reconnaître un nombre premier (n < 1 000) : on essaie de diviser n par les nombres premiers 2, 3, 5, 7, 11, 13… dans l\'ordre. On s\'arrête dès qu\'une division tombe juste (n n\'est pas premier) ou quand le carré du diviseur dépasse n (n est premier).' },
    { type: 'paragraph', text: 'Décomposer en facteurs premiers : on divise par les plus petits nombres premiers possibles, jusqu\'à obtenir 1.' },
    { type: 'table', headers: ['Critère', 'Un nombre est divisible par…'], rows: [
      ['2', 's\'il se termine par 0, 2, 4, 6 ou 8'],
      ['3', 'si la somme de ses chiffres est dans la table de 3'],
      ['5', 's\'il se termine par 0 ou 5'],
      ['9', 'si la somme de ses chiffres est dans la table de 9'],
    ]},
    { type: 'note', variant: 'tip', text: 'Premiers < 50 : 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47' },

    { type: 'heading', text: '3. Mes astuces pour ne pas oublier', level: 2 },
    { type: 'note', variant: 'example', text: 'Décomposer 84 : 84 ÷ 2 = 42 ; 42 ÷ 2 = 21 ; 21 ÷ 3 = 7 ; 7 ÷ 7 = 1. Donc 84 = 2² × 3 × 7.' },
    { type: 'note', variant: 'tip', text: 'Pour tester un nombre : arrête-toi quand le carré du diviseur dépasse le nombre. Ex. : pour 97, on teste 2, 3, 5, 7 (car 11² = 121 > 97).' },
    { type: 'note', variant: 'warning', text: 'Piège : 1 n\'est pas premier. 2 est premier (c\'est même le seul pair).' },
    { type: 'note', variant: 'warning', text: 'Piège : 2³ ≠ 2 × 3. 2³ = 2 × 2 × 2 = 8.' },
    { type: 'note', variant: 'warning', text: 'Piège : 2³ × 2⁴ = 2⁷, et non 4⁷ : on n\'additionne pas les bases.' },

    { type: 'heading', text: '4. Je m\'entraîne', level: 2 },
    { type: 'list', ordered: true, items: [
      'Ex. 1 — Calcule : 2⁵ ; 10³ ; 3⁴ ; 5² + 2³.',
      'Ex. 2 — Calcule : A = 3 + 2 × 4² et B = (3 + 2) × 4².',
      'Ex. 3 — Écris sous la forme d\'une seule puissance : 5³ × 5⁴ ; 2⁴ × 2². Calcule (2 × 5)³.',
      'Ex. 4 — a) Effectue la division de 457 par 12 et écris a = b × q + r. b) Encadre 457 par deux multiples consécutifs de 12.',
      'Ex. 5 — Dis si ces nombres sont premiers et justifie : 51 ; 53 ; 91 ; 97.',
      'Ex. 6 — Décompose en produit de facteurs premiers : 180 ; 252.',
      'Ex. 7 — Une coopérative produit 10 cartons de 10 plaquettes de 10 œufs. Elle a investi 18 000 F et vend l\'œuf à 25 F. Un livre coûte 1 500 F. Combien d\'œufs ? Quel bénéfice ? Combien de livres peut-elle acheter ?',
    ]},

    { type: 'heading', text: '5. Je vérifie mes réponses', level: 2 },
    { type: 'paragraph', text: 'Essaie d\'abord seul(e), puis compare !', italic: true },
    { type: 'list', items: [
      'Je découvre : 30³ = 27 000 œufs. Vente : 810 000 F. Bénéfice : 460 000 F. 124 livres (reste 1 200 F).',
      'Ex. 1 : 32 ; 1 000 ; 81 ; 25 + 8 = 33.',
      'Ex. 2 : A = 3 + 2 × 16 = 35 ; B = 5 × 16 = 80.',
      'Ex. 3 : 5⁷ ; 2⁶ (= 64) ; (2 × 5)³ = 8 × 125 = 1 000.',
      'Ex. 4 : a) 457 = 12 × 38 + 1. b) 456 < 457 < 468.',
      'Ex. 5 : 51 = 3 × 17 (non) ; 53 (premier) ; 91 = 7 × 13 (non) ; 97 (premier).',
      'Ex. 6 : 180 = 2² × 3² × 5 ; 252 = 2² × 3² × 7.',
      'Ex. 7 : 10³ = 1 000 œufs ; vente 25 000 F ; bénéfice 7 000 F ; 4 livres (reste 1 000 F).',
    ]},

    { type: 'heading', text: '6. À la maison', level: 2 },
    { type: 'list', ordered: true, items: [
      'Devoir 1 — Liste tous les nombres premiers inférieurs à 50, puis décompose 360 et 462 en produit de facteurs premiers.',
      'Devoir 2 — Une plantation a 15 rangées de 15 pieds de manioc. Combien de pieds ? Écris le résultat avec une puissance puis calcule.',
    ]},
    { type: 'note', variant: 'tip', text: 'Apprends par cœur les nombres premiers jusqu\'à 31 : ils te serviront dans tous les exercices.' },
  ],
};

/* ------------------------------------------------------------------ */
/*  MODULE 2 — Les nombres décimaux relatifs                          */
/* ------------------------------------------------------------------ */
const module2: Lesson = {
  id: 'les_math5e_02',
  title: 'Les nombres décimaux relatifs',
  subject: 'Mathématiques',
  gradeClass: '5e',
  chapter: 'Calculs algébriques',
  summary: 'Comparer, additionner, soustraire et multiplier des nombres relatifs ; équation x + b = a.',
  content: `### Les nombres décimaux relatifs
Comparer, calculer et résoudre des équations avec des nombres positifs et négatifs.`,
  durationMinutes: 30,
  published: true,
  blocks: [
    { type: 'heading', text: 'Les nombres décimaux relatifs', level: 1 },
    { type: 'paragraph', text: 'Je sais comparer et calculer avec des nombres positifs et négatifs.', bold: true, italic: true },
    { type: 'note', variant: 'definition', text: 'À la fin de cette leçon, je saurai : comparer et ranger des nombres décimaux relatifs ; calculer une somme, une différence et un produit ; calculer une somme algébrique ; résoudre une équation du type x + b = a.' },

    { type: 'heading', text: '1. Je découvre', level: 2 },
    { type: 'paragraph', text: 'Un conférencier dit : « En 552 avant Jésus-Christ, à l\'âge de 20 ans, Pythagore a découvert une propriété importante. » Les élèves veulent savoir quel âge il aurait en 2026.' },
    { type: 'paragraph', text: 'Convention : les années avant J.-C. sont des nombres négatifs (−552), les années après J.-C. sont positives (+2026).', italic: true },
    { type: 'list', ordered: true, items: [
      'Quelle est l\'année de naissance de Pythagore (nombre relatif) ?',
      'Quel calcul donne son âge en 2026 ?',
    ]},

    { type: 'heading', text: '2. Je retiens', level: 2 },
    { type: 'paragraph', text: 'L\'essentiel à savoir', bold: true },
    { type: 'note', variant: 'definition', text: 'Un nombre relatif a un signe (+ ou −) et une distance à zéro. Ex. : −3,5 a pour signe − et pour distance à zéro 3,5. Deux nombres opposés ont la même distance à zéro et des signes contraires : +4 et −4.' },
    { type: 'paragraph', text: 'Comparer : tout nombre négatif est plus petit que 0, et 0 est plus petit que tout nombre positif. Entre deux négatifs, le plus grand est celui qui a la plus petite distance à zéro : −2 > −7.' },
    { type: 'paragraph', text: 'Ranger : dans l\'ordre croissant (du plus petit au plus grand) ou décroissant (du plus grand au plus petit).' },
    { type: 'paragraph', text: 'Somme : même signe → on additionne les distances et on garde le signe. Signes contraires → on soustrait les distances et on garde le signe du nombre qui a la plus grande distance.' },
    { type: 'paragraph', text: 'Différence : soustraire un nombre, c\'est ajouter son opposé : a − b = a + (−b).' },
    { type: 'paragraph', text: 'Somme algébrique : une suite d\'additions et de soustractions. On supprime les parenthèses : +(−3) = −3 et −(−3) = +3. On regroupe les positifs et les négatifs.' },
    { type: 'paragraph', text: 'Produit : même signe → résultat positif ; signes différents → résultat négatif. Avec plusieurs facteurs : nombre pair de facteurs négatifs → +, impair → −.' },
    { type: 'formula', text: 'Équation x + b = a : x = a − b' },
    { type: 'table', headers: ['×', '+', '−'], rows: [
      ['+', '+', '−'],
      ['−', '−', '+'],
    ]},
    { type: 'paragraph', text: 'La droite graduée : plus on va vers la droite, plus le nombre est grand.' },

    { type: 'heading', text: '3. Mes astuces pour ne pas oublier', level: 2 },
    { type: 'note', variant: 'tip', text: 'Les signes : « les amis de mes amis sont mes amis » (+ × + = +), « les ennemis de mes ennemis sont mes amis » (− × − = +), « un ami et un ennemi donnent un ennemi » (+ × − = −).' },
    { type: 'note', variant: 'warning', text: 'Piège : −7 est plus petit que −2 (même si 7 est plus grand que 2).' },
    { type: 'note', variant: 'tip', text: 'Pour vérifier une équation : remplace x par ta réponse dans l\'équation de départ.' },

    { type: 'heading', text: '4. Je m\'entraîne', level: 2 },
    { type: 'list', ordered: true, items: [
      'Ex. 1 — a) Compare −3,5 et −3,2. b) Range dans l\'ordre croissant : −4 ; 2,5 ; −0,7 ; 0 ; −4,2 ; 3.',
      'Ex. 2 — Calcule : (−7) + (+3) ; (−2,5) + (−1,5) ; (+8) + (−8).',
      'Ex. 3 — Calcule : (+5) − (+9) ; (−3) − (−7) ; (−2,5) − (+1,5).',
      'Ex. 4 — Calcule : −5 + 8 − 3 + 2.',
      'Ex. 5 — Calcule : (−4) × (+3) ; (−2) × (−5) ; (−1) × (−2) × (−3) ; (−2) × 3 × (−1,5).',
      'Ex. 6 — Résous : x + 5 = 2 ; x + (−4) = −10 ; x + 3,5 = 0.',
      'Ex. 7 — À Man, la température est de −3 °C à 5 h du matin et de +9 °C à midi. De combien de degrés a-t-elle augmenté ?',
    ]},

    { type: 'heading', text: '5. Je vérifie mes réponses', level: 2 },
    { type: 'paragraph', text: 'Essaie d\'abord seul(e), puis compare !', italic: true },
    { type: 'list', items: [
      'Je découvre : né en −572. Âge en 2026 : 2026 + 572 = 2 598 ans.',
      'Ex. 1 : a) −3,2 > −3,5. b) −4,2 < −4 < −0,7 < 0 < 2,5 < 3.',
      'Ex. 2 : −4 ; −4 ; 0.',
      'Ex. 3 : −4 ; +4 ; −4.',
      'Ex. 4 : (8 + 2) + (−5 − 3) = 10 − 8 = 2.',
      'Ex. 5 : −12 ; +10 ; −6 ; +9.',
      'Ex. 6 : x = −3 ; x = −6 ; x = −3,5.',
      'Ex. 7 : 9 − (−3) = 9 + 3 = 12 °C.',
    ]},

    { type: 'heading', text: '6. À la maison', level: 2 },
    { type: 'list', ordered: true, items: [
      'Devoir 1 — Range dans l\'ordre décroissant : −1,5 ; 0,8 ; −2 ; 1,2 ; −0,25 ; 0. Puis calcule 12 − 20 + 7 − 3.',
      'Devoir 2 — Le solde de ton compte est de −2 500 F. Tu reçois +4 000 F puis tu dépenses 1 500 F. Quel est ton nouveau solde ?',
    ]},
    { type: 'note', variant: 'tip', text: 'Avant de calculer, écris toujours le signe du résultat : c\'est l\'étape où on se trompe le plus.' },
  ],
};

/* ------------------------------------------------------------------ */
/*  MODULE 3 — Les fractions                                           */
/* ------------------------------------------------------------------ */
const module3: Lesson = {
  id: 'les_math5e_03',
  title: 'Les fractions',
  subject: 'Mathématiques',
  gradeClass: '5e',
  chapter: 'Calculs algébriques',
  summary: 'Puissance, addition, soustraction, produit de fractions et encadrement par des décimaux.',
  content: `### Les fractions
Calculer avec des fractions et les encadrer par des nombres décimaux.`,
  durationMinutes: 30,
  published: true,
  blocks: [
    { type: 'heading', text: 'Les fractions', level: 1 },
    { type: 'paragraph', text: 'Je sais calculer avec des fractions et les encadrer par des décimaux.', bold: true, italic: true },
    { type: 'note', variant: 'definition', text: 'À la fin de cette leçon, je saurai : calculer la puissance d\'une fraction ; additionner, soustraire et multiplier des fractions ; encadrer une fraction par deux nombres décimaux consécutifs ; résoudre une situation de partage.' },

    { type: 'heading', text: '1. Je découvre', level: 2 },
    { type: 'paragraph', text: 'Un papa partage une tablette de chocolat entre ses trois enfants et leur maman : le cadet reçoit le quart de la tablette ; l\'aîné reçoit deux fois la part du cadet ; la benjamine reçoit le quart de la part du cadet ; le reste revient à la maman.' },
    { type: 'paragraph', text: 'La benjamine dit que la part de sa maman est plus grande que la sienne et plus petite que celle de l\'aîné.' },
    { type: 'list', ordered: true, items: [
      'Écris la part de chaque enfant sous forme de fraction de la tablette.',
      'Quelle fraction reste pour la maman ?',
      'La benjamine a-t-elle raison ?',
    ]},

    { type: 'heading', text: '2. Je retiens', level: 2 },
    { type: 'paragraph', text: 'L\'essentiel à savoir', bold: true },
    { type: 'note', variant: 'definition', text: 'Une fraction a/b représente a parts d\'un tout partagé en b parts égales (b ≠ 0).' },
    { type: 'paragraph', text: 'Même dénominateur : on additionne ou on soustrait les numérateurs : 5/8 − 2/8 = 3/8.' },
    { type: 'paragraph', text: 'Dénominateurs différents : on réduit au même dénominateur d\'abord. Ex. : 3/4 − 1/6 = 9/12 − 2/12 = 7/12.' },
    { type: 'formula', text: 'Produit : a/b × c/d = (a × c) / (b × d)' },
    { type: 'formula', text: 'Puissance d\'une fraction : (a/b)ⁿ = aⁿ / bⁿ' },
    { type: 'paragraph', text: 'Ex. : (2/3)³ = 2³/3³ = 8/27.', italic: true },
    { type: 'paragraph', text: 'Encadrer une fraction à l\'ordre n : on trouve deux nombres décimaux consécutifs qui l\'entourent. À l\'ordre 1 : deux nombres qui diffèrent de 0,1 ; à l\'ordre 2 : de 0,01. Méthode : on calcule la valeur décimale (a ÷ b), puis on garde les chiffres demandés.' },
    { type: 'table', headers: ['Fraction', 'Valeur décimale', 'Ordre 1', 'Ordre 2'], rows: [
      ['7/3', '2,3333…', '2,3 < 7/3 < 2,4', '2,33 < 7/3 < 2,34'],
      ['5/8', '0,625', '0,6 < 5/8 < 0,7', '0,62 < 5/8 < 0,63'],
    ]},

    { type: 'heading', text: '3. Mes astuces pour ne pas oublier', level: 2 },
    { type: 'note', variant: 'warning', text: 'Piège : (2/3)³ ≠ 2/3 × 3. L\'exposant s\'applique au numérateur ET au dénominateur.' },
    { type: 'note', variant: 'tip', text: 'Réduire : divise le numérateur et le dénominateur par le même nombre (18/30 = 3/5).' },
    { type: 'note', variant: 'tip', text: 'Encadrer : lis la valeur décimale et coupe-la au bon endroit ; ajoute 1 au dernier chiffre pour le deuxième nombre.' },

    { type: 'heading', text: '4. Je m\'entraîne', level: 2 },
    { type: 'list', ordered: true, items: [
      'Ex. 1 — Calcule : (2/3)³ ; (5/4)² ; (1/2)⁴.',
      'Ex. 2 — Calcule 3/4 − 1/6.',
      'Ex. 3 — Calcule 2/3 × 9/10 et simplifie.',
      'Ex. 4 — Encadre par deux décimaux consécutifs : a) 7/3 à l\'ordre 1 ; b) 5/8 à l\'ordre 2 ; c) 22/7 à l\'ordre 2.',
      'Ex. 5 — Awa a mangé 1/3 d\'un gâteau, Mariam 1/4 du même gâteau. Quelle fraction du gâteau reste-t-il ?',
    ]},

    { type: 'heading', text: '5. Je vérifie mes réponses', level: 2 },
    { type: 'paragraph', text: 'Essaie d\'abord seul(e), puis compare !', italic: true },
    { type: 'list', items: [
      'Je découvre : cadet 1/4 ; aîné 1/2 ; benjamine 1/16 ; maman 3/16. La benjamine a raison (1/16 < 3/16 < 8/16).',
      'Ex. 1 : 8/27 ; 25/16 ; 1/16.',
      'Ex. 2 : 9/12 − 2/12 = 7/12.',
      'Ex. 3 : 18/30 = 3/5.',
      'Ex. 4 : a) 2,3 < 7/3 < 2,4 ; b) 0,62 < 5/8 < 0,63 ; c) 3,14 < 22/7 < 3,15.',
      'Ex. 5 : 1/3 + 1/4 = 7/12 mangés. Il reste 5/12 du gâteau.',
    ]},

    { type: 'heading', text: '6. À la maison', level: 2 },
    { type: 'list', ordered: true, items: [
      'Devoir 1 — Calcule (3/5)² ; (1/10)³ ; 5/6 − 1/4 ; 3/7 × 14/9.',
      'Devoir 2 — Une bouteille d\'eau de 1 L est bue ainsi : 1/2 le matin, 1/5 à midi. Quelle fraction reste-t-il ?',
    ]},
    { type: 'note', variant: 'tip', text: 'Écris toujours les étapes (même dénominateur, calcul, simplification) l\'une sous l\'autre.' },
  ],
};

/* ------------------------------------------------------------------ */
/*  MODULE 4 — La proportionnalité                                    */
/* ------------------------------------------------------------------ */
const module4: Lesson = {
  id: 'les_math5e_04',
  title: 'La proportionnalité',
  subject: 'Mathématiques',
  gradeClass: '5e',
  chapter: 'Organisation et traitement de données',
  summary: 'Reconnaître la proportionnalité, lire des coordonnées, calculer vitesse, débit et masse volumique.',
  content: `### La proportionnalité
Reconnaître une situation de proportionnalité et calculer vitesse, débit et masse volumique.`,
  durationMinutes: 30,
  published: true,
  blocks: [
    { type: 'heading', text: 'La proportionnalité', level: 1 },
    { type: 'paragraph', text: 'Je sais reconnaître une situation de proportionnalité et calculer vitesse, débit et masse volumique.', bold: true, italic: true },
    { type: 'note', variant: 'definition', text: 'À la fin de cette leçon, je saurai : reconnaître une situation de proportionnalité à partir d\'un tableau ou d\'un graphique ; lire les coordonnées d\'un point et représenter des points dans un quadrillage ; déterminer le coefficient de proportionnalité ; calculer une vitesse moyenne, un débit moyen et une masse volumique.' },

    { type: 'heading', text: '1. Je découvre', level: 2 },
    { type: 'paragraph', text: 'Le graphique ci-dessous montre la distance parcourue par un véhicule en fonction de la durée du trajet. Le professeur affirme : « Ce graphique traduit une situation de proportionnalité. »' },
    { type: 'list', ordered: true, items: [
      'Quelle distance le véhicule a-t-il parcourue en 10 min ? en 20 min ?',
      'Les points sont-ils alignés ? La droite passe-t-elle par l\'origine (0 ; 0) ?',
      'Quelle distance parcourt-il en 1 minute ?',
    ]},

    { type: 'heading', text: '2. Je retiens', level: 2 },
    { type: 'paragraph', text: 'L\'essentiel à savoir', bold: true },
    { type: 'note', variant: 'definition', text: 'Proportionnalité : deux grandeurs sont proportionnelles quand on passe de l\'une à l\'autre en multipliant toujours par le même nombre k, appelé coefficient de proportionnalité.' },
    { type: 'paragraph', text: 'Reconnaître avec un tableau : tous les quotients y ÷ x sont égaux.' },
    { type: 'paragraph', text: 'Reconnaître avec un graphique : les points sont alignés sur une droite qui passe par l\'origine.' },
    { type: 'paragraph', text: 'Coordonnées d\'un point : on lit d\'abord l\'abscisse (axe horizontal), puis l\'ordonnée (axe vertical). On écrit M (15 ; 22,5).' },
    { type: 'paragraph', text: 'Coefficient avec un graphique : k = ordonnée ÷ abscisse d\'un point (ou l\'ordonnée du point d\'abscisse 1).' },
    { type: 'formula', text: 'Vitesse moyenne : v = d ÷ t' },
    { type: 'paragraph', text: 'On en déduit d = v × t et t = d ÷ v. Unités : km/h, m/s.', italic: true },
    { type: 'formula', text: 'Débit moyen : D = V ÷ t' },
    { type: 'paragraph', text: 'Unités : L/min, m³/h.', italic: true },
    { type: 'formula', text: 'Masse volumique : ρ = m ÷ V' },
    { type: 'paragraph', text: 'Unités : g/cm³, kg/m³.', italic: true },
    { type: 'table', headers: ['Grandeur', 'Formule', 'Exemple'], rows: [
      ['Vitesse moyenne', 'v = d ÷ t', '150 km en 2 h : v = 75 km/h'],
      ['Débit moyen', 'D = V ÷ t', '360 L en 12 min : D = 30 L/min'],
      ['Masse volumique', 'ρ = m ÷ V', '540 g pour 200 cm³ : ρ = 2,7 g/cm³'],
    ]},
    { type: 'figure', emoji: '📈', label: 'Droite passant par l\'origine', caption: 'Distance parcourue en fonction de la durée : une droite qui passe par l\'origine.' },

    { type: 'heading', text: '3. Mes astuces pour ne pas oublier', level: 2 },
    { type: 'note', variant: 'tip', text: 'Triangle des formules : retiens d = v × t. Si tu cherches v, tu fais d ÷ t ; si tu cherches t, tu fais d ÷ v.' },
    { type: 'note', variant: 'warning', text: 'Piège : les unités doivent être compatibles (minutes avec minutes, litres avec litres).' },
    { type: 'note', variant: 'warning', text: 'Piège : une droite qui ne passe pas par l\'origine ne représente pas une situation de proportionnalité.' },
    { type: 'note', variant: 'tip', text: 'Pour tracer : place les points un par un, puis relie-les à la règle.' },

    { type: 'heading', text: '4. Je m\'entraîne', level: 2 },
    { type: 'list', ordered: true, items: [
      'Ex. 1 — Sur le graphique du véhicule, donne les coordonnées des points d\'abscisse 10 min, 25 min et 30 min.',
      'Ex. 2 — a) Durée 10, 20, 30 min → Distance 15, 30, 45 km. b) Durée 10, 20 min → Distance 12, 25 km. Dans quel cas y a-t-il proportionnalité ? Donne le coefficient.',
      'Ex. 3 — Un car parcourt 150 km en 2 h. Calcule sa vitesse moyenne. Quelle distance parcourt-il en 3 h ?',
      'Ex. 4 — Un robinet remplit un seau de 12 L en 4 min. Calcule son débit moyen.',
      'Ex. 5 — Un morceau de métal de 200 cm³ a une masse de 540 g. Calcule sa masse volumique.',
      'Ex. 6 — Place les points (1;2), (2;4), (3;6), (4;8). Que remarques-tu ?',
      'Ex. 7 — Le fût de la famille Coulibaly est rempli avec un débit de 7 L/min en 8 min 30 s. On augmente le débit à 12 L/min. Quel temps faut-il maintenant ?',
    ]},

    { type: 'heading', text: '5. Je vérifie mes réponses', level: 2 },
    { type: 'paragraph', text: 'Essaie d\'abord seul(e), puis compare !', italic: true },
    { type: 'list', items: [
      'Je découvre : 15 km en 10 min ; 30 km en 20 min. k = 1,5 km/min (90 km/h).',
      'Ex. 1 : (10;15) ; (25;37,5) ; (30;45).',
      'Ex. 2 : a) oui, k = 1,5. b) non (1,2 ≠ 1,25).',
      'Ex. 3 : v = 75 km/h ; d = 225 km.',
      'Ex. 4 : D = 3 L/min.',
      'Ex. 5 : ρ = 2,7 g/cm³.',
      'Ex. 6 : points alignés sur une droite passant par l\'origine, k = 2.',
      'Ex. 7 : volume 59,5 L ; nouveau temps ≈ 4 min 58 s.',
    ]},

    { type: 'heading', text: '6. À la maison', level: 2 },
    { type: 'list', ordered: true, items: [
      'Devoir 1 — Un cycliste roule à 18 km/h. Quelle distance parcourt-il en 2 h 30 min ?',
      'Devoir 2 — Un robinet donne 40 L en 5 min. En combien de temps remplit-il un bidon de 100 L ?',
    ]},
    { type: 'note', variant: 'tip', text: 'Avant de calculer, écris la formule, remplace par les nombres, puis note l\'unité du résultat.' },
  ],
};

/* ------------------------------------------------------------------ */
/*  MODULE 5 — La statistique                                          */
/* ------------------------------------------------------------------ */
const module5: Lesson = {
  id: 'les_math5e_05',
  title: 'La statistique',
  subject: 'Mathématiques',
  gradeClass: '5e',
  chapter: 'Organisation et traitement de données',
  summary: 'Vocabulaire, effectifs, fréquences, diagrammes en bâtons et à bandes.',
  content: `### La statistique
Construire et lire des diagrammes pour interpréter des données.`,
  durationMinutes: 25,
  published: true,
  blocks: [
    { type: 'heading', text: 'La statistique', level: 1 },
    { type: 'paragraph', text: 'Je sais construire et lire des diagrammes pour interpréter des données.', bold: true, italic: true },
    { type: 'note', variant: 'definition', text: 'À la fin de cette leçon, je saurai : utiliser le vocabulaire de la statistique ; construire un diagramme en bâtons et un diagramme à bandes ; lire un effectif, l\'effectif total et une fréquence ; interpréter un diagramme.' },

    { type: 'heading', text: '1. Je découvre', level: 2 },
    { type: 'paragraph', text: 'Le président du club « les cracks » a interrogé les 60 élèves de la 5e 1 sur leur loisir préféré. Résultats : lecture 25 %, musique 40 %, cinéma 15 %, sport 20 %.' },
    { type: 'paragraph', text: 'Pour mieux voir les résultats, les élèves veulent construire un diagramme.' },
    { type: 'list', ordered: true, items: [
      'Combien d\'élèves préfèrent chaque loisir ?',
      'Que vérifies-tu en additionnant les pourcentages ?',
      'Quel loisir est le plus choisi ?',
    ]},

    { type: 'heading', text: '2. Je retiens', level: 2 },
    { type: 'paragraph', text: 'L\'essentiel à savoir', bold: true },
    { type: 'note', variant: 'definition', text: 'Population : l\'ensemble des personnes interrogées. Caractère : ce qu\'on étudie (ici le loisir préféré). Effectif : le nombre d\'individus pour une valeur du caractère. Effectif total : la somme de tous les effectifs.' },
    { type: 'formula', text: 'Fréquence : f = effectif ÷ effectif total' },
    { type: 'paragraph', text: 'On l\'écrit en fraction, en décimal ou en pourcentage.', italic: true },
    { type: 'paragraph', text: 'Diagramme en bâtons : chaque valeur est représentée par un bâton (un trait vertical) dont la hauteur est proportionnelle à l\'effectif.' },
    { type: 'paragraph', text: 'Diagramme à bandes : chaque valeur est représentée par une bande (un rectangle) de même largeur, dont la hauteur est proportionnelle à l\'effectif.' },
    { type: 'paragraph', text: 'Pour construire : 1) un titre ; 2) deux axes ; 3) une échelle régulière ; 4) les bâtons ou les bandes ; 5) les noms des valeurs.' },
    { type: 'paragraph', text: 'Pour interpréter : je lis les données, je compare les valeurs, puis je conclus par une phrase.' },
    { type: 'table', headers: ['Loisir', 'Fréquence', 'Effectif (sur 60)'], rows: [
      ['Lecture', '25 % = 0,25', '15'],
      ['Musique', '40 % = 0,40', '24'],
      ['Cinéma', '15 % = 0,15', '9'],
      ['Sport', '20 % = 0,20', '12'],
      ['Total', '100 %', '60'],
    ]},
    { type: 'graph', title: 'Loisirs préférés des 60 élèves', kind: 'bar', labels: ['Lecture', 'Musique', 'Cinéma', 'Sport'], values: [15, 24, 9, 12] },

    { type: 'heading', text: '3. Mes astuces pour ne pas oublier', level: 2 },
    { type: 'note', variant: 'tip', text: 'Vérification : la somme des effectifs est égale à l\'effectif total ; la somme des fréquences vaut 1 (ou 100 %).' },
    { type: 'note', variant: 'tip', text: 'Échelle : choisis une échelle qui tient dans la page (par exemple 1 cm pour 2 élèves) et garde-la partout.' },
    { type: 'note', variant: 'warning', text: 'Piège : un diagramme sans titre ni légende est incomplet.' },

    { type: 'heading', text: '4. Je m\'entraîne', level: 2 },
    { type: 'list', ordered: true, items: [
      'Ex. 1 — Lecture 15 ; musique 24 ; cinéma 9 ; sport 12. Calcule l\'effectif total puis la fréquence (en %) de chaque loisir.',
      'Ex. 2 — Avec le diagramme en bâtons, donne l\'effectif de chaque sport et l\'effectif total.',
      'Ex. 3 — Quelle est la fréquence des élèves qui pratiquent le football ? (fraction, décimal, pourcentage)',
      'Ex. 4 — Construis un diagramme à bandes pour les loisirs (échelle : 1 cm pour 3 élèves).',
      'Ex. 5 — Rédige deux phrases qui interprètent le diagramme des sports.',
    ]},

    { type: 'heading', text: '5. Je vérifie mes réponses', level: 2 },
    { type: 'paragraph', text: 'Essaie d\'abord seul(e), puis compare !', italic: true },
    { type: 'list', items: [
      'Je découvre : lecture 15, musique 24, cinéma 9, sport 12. La musique est le loisir le plus choisi.',
      'Ex. 1 : total 60. Fréquences : 25 % ; 40 % ; 15 % ; 20 %.',
      'Ex. 2 : football 18 ; handball 8 ; athlétisme 6 ; danse 8. Total : 40.',
      'Ex. 3 : 18/40 = 9/20 = 0,45 = 45 %.',
      'Ex. 4 : hauteurs des bandes : lecture 5 cm, musique 8 cm, cinéma 3 cm, sport 4 cm.',
      'Ex. 5 : Le football est le plus pratiqué (45 %). Le handball et la danse ont le même effectif (8 élèves).',
    ]},

    { type: 'heading', text: '6. À la maison', level: 2 },
    { type: 'list', ordered: true, items: [
      'Devoir 1 — Fais une enquête auprès de 20 camarades sur leur matière préférée, présente les résultats dans un tableau puis construis un diagramme en bâtons.',
      'Devoir 2 — Dans un village, 120 familles utilisent : puits 30, forage 54, robinet 36. Calcule les fréquences en % et construis un diagramme à bandes.',
    ]},
    { type: 'note', variant: 'tip', text: 'Pour un diagramme propre : règle, crayon bien taillé et quadrillage de ton cahier.' },
  ],
};

/* ------------------------------------------------------------------ */
/*  MODULE 6 — Les angles                                              */
/* ------------------------------------------------------------------ */
const module6: Lesson = {
  id: 'les_math5e_06',
  title: 'Les angles',
  subject: 'Mathématiques',
  gradeClass: '5e',
  chapter: 'Géométrie du plan',
  summary: 'Angles adjacents, complémentaires, supplémentaires, opposés par le sommet et somme des angles d\'un triangle.',
  content: `### Les angles
Identifier, calculer et construire des angles.`,
  durationMinutes: 25,
  published: true,
  blocks: [
    { type: 'heading', text: 'Les angles', level: 1 },
    { type: 'paragraph', text: 'Je sais identifier, calculer et construire des angles.', bold: true, italic: true },
    { type: 'note', variant: 'definition', text: 'À la fin de cette leçon, je saurai : reconnaître des angles adjacents, complémentaires, supplémentaires et opposés par le sommet ; calculer la mesure d\'un angle complémentaire ou supplémentaire ; utiliser la somme des angles d\'un triangle ; justifier avec des propriétés.' },

    { type: 'heading', text: '1. Je découvre', level: 2 },
    { type: 'paragraph', text: 'Un agent de l\'agriculture trace le triangle ABE d\'un champ : l\'angle en A est droit (90°) et AB = AE. Yao affirme que l\'angle ABE mesure 45°.' },
    { type: 'paragraph', text: 'Pour vérifier, les élèves calculent chaque angle du triangle.' },
    { type: 'list', ordered: true, items: [
      'Quelle est la somme des angles d\'un triangle ?',
      'Comme AB = AE, que peux-tu dire des angles en B et en E ?',
      'Yao a-t-il raison ?',
    ]},

    { type: 'heading', text: '2. Je retiens', level: 2 },
    { type: 'paragraph', text: 'L\'essentiel à savoir', bold: true },
    { type: 'note', variant: 'definition', text: 'Angles adjacents : ils ont le même sommet, un côté commun, et sont de part et d\'autre de ce côté.' },
    { type: 'note', variant: 'definition', text: 'Angles complémentaires : leur somme est 90°. Angles supplémentaires : leur somme est 180°.' },
    { type: 'note', variant: 'definition', text: 'Angles opposés par le sommet : leurs côtés sont dans le prolongement l\'un de l\'autre. Ils ont la même mesure.' },
    { type: 'formula', text: 'Somme des angles d\'un triangle : 180°' },
    { type: 'paragraph', text: 'Conséquence : dans un triangle rectangle, les deux angles aigus sont complémentaires.', italic: true },
    { type: 'table', headers: ['Je cherche…', 'Je fais…', 'Exemple'], rows: [
      ['le complémentaire de x', '90° − x', 'Complémentaire de 35° : 55°'],
      ['le supplémentaire de x', '180° − x', 'Supplémentaire de 112° : 68°'],
      ['le 3e angle d\'un triangle', '180° − (angle 1 + angle 2)', '48° et 67° : 65°'],
    ]},
    { type: 'figure', emoji: '📐', label: 'Les quatre familles d\'angles', caption: 'Angles complémentaires, supplémentaires, adjacents et opposés par le sommet.' },

    { type: 'heading', text: '3. Mes astuces pour ne pas oublier', level: 2 },
    { type: 'note', variant: 'tip', text: 'Complémentaire → Coin (90°, l\'équerre). Supplémentaire → Segment (180°, un angle plat).' },
    { type: 'note', variant: 'tip', text: 'Pour construire un angle complémentaire : méthode 1, angle adjacent (on trace une perpendiculaire au côté) ; méthode 2, angle non adjacent (au rapporteur, avec 90° − x).' },
    { type: 'note', variant: 'tip', text: 'Justifier : « Les angles ont pour somme 90°, donc ils sont complémentaires. »' },

    { type: 'heading', text: '4. Je m\'entraîne', level: 2 },
    { type: 'list', ordered: true, items: [
      'Ex. 1 — Calcule le complémentaire de 35° puis le supplémentaire de 112°.',
      'Ex. 2 — Un triangle a deux angles de 48° et 67°. Calcule le troisième.',
      'Ex. 3 — Deux angles mesurent 58° et 32°. Sont-ils complémentaires ? Justifie.',
      'Ex. 4 — Deux droites se coupent en O. Un angle mesure 70°. Donne l\'opposé par le sommet et l\'adjacent.',
      'Ex. 5 — Un triangle ABC est isocèle en A et l\'angle en A mesure 40°. Calcule les angles B et C.',
      'Ex. 6 — Écris un programme de construction d\'un angle complémentaire (adjacent) de xOy = 40°.',
    ]},

    { type: 'heading', text: '5. Je vérifie mes réponses', level: 2 },
    { type: 'paragraph', text: 'Essaie d\'abord seul(e), puis compare !', italic: true },
    { type: 'list', items: [
      'Je découvre : B = E = (180° − 90°) ÷ 2 = 45°. Yao a raison.',
      'Ex. 1 : 55° ; 68°.',
      'Ex. 2 : 180° − 115° = 65°.',
      'Ex. 3 : 58° + 32° = 90° → complémentaires.',
      'Ex. 4 : opposé 70° ; adjacent 110°.',
      'Ex. 5 : (180° − 40°) ÷ 2 = 70°. B = C = 70°.',
      'Ex. 6 : trace xOy = 40°, puis la perpendiculaire à [Oy) en O. L\'angle yOz = 50° (complémentaire adjacent).',
    ]},

    { type: 'heading', text: '6. À la maison', level: 2 },
    { type: 'list', ordered: true, items: [
      'Devoir 1 — Un triangle ABC est rectangle en B et l\'angle A mesure 37°. Calcule l\'angle C et justifie.',
      'Devoir 2 — Trace un angle de 65°, puis son supplémentaire adjacent. Écris le programme de construction.',
    ]},
    { type: 'note', variant: 'tip', text: 'Avant de répondre, fais un petit schéma à main levée : l\'angle se voit souvent mieux qu\'il ne se calcule.' },
  ],
};

/* ------------------------------------------------------------------ */
/*  MODULE 7 — Les triangles                                           */
/* ------------------------------------------------------------------ */
const module7: Lesson = {
  id: 'les_math5e_07',
  title: 'Les triangles',
  subject: 'Mathématiques',
  gradeClass: '5e',
  chapter: 'Géométrie du plan',
  summary: 'Triangles particuliers, droites particulières, inégalité triangulaire et construction.',
  content: `### Les triangles
Reconnaître, construire et justifier la nature des triangles.`,
  durationMinutes: 30,
  published: true,
  blocks: [
    { type: 'heading', text: 'Les triangles', level: 1 },
    { type: 'paragraph', text: 'Je sais reconnaître, construire et justifier la nature des triangles.', bold: true, italic: true },
    { type: 'note', variant: 'definition', text: 'À la fin de cette leçon, je saurai : connaître les triangles particuliers et leurs propriétés ; reconnaître les droites particulières d\'un triangle ; utiliser l\'inégalité triangulaire ; construire un triangle et la bissectrice d\'un angle.' },

    { type: 'heading', text: '1. Je découvre', level: 2 },
    { type: 'paragraph', text: 'Les élèves de la 5e 1 veulent créer un jardin botanique. Ils dessinent un triangle ABC dans lequel AB = AC et l\'angle en A mesure 60°.' },
    { type: 'paragraph', text: 'Ils veulent connaître la nature du triangle et les mesures de ses angles.' },
    { type: 'list', ordered: true, items: [
      'Que peux-tu dire des angles en B et en C ?',
      'Calcule leur mesure.',
      'Quelle est la nature du triangle ABC ?',
    ]},

    { type: 'heading', text: '2. Je retiens', level: 2 },
    { type: 'paragraph', text: 'L\'essentiel à savoir', bold: true },
    { type: 'note', variant: 'definition', text: 'Inégalité triangulaire : dans un triangle, chaque côté est plus petit que la somme des deux autres. On peut construire un triangle si le plus grand côté est strictement plus petit que la somme des deux autres.' },
    { type: 'table', headers: ['Triangle', 'Définition et propriétés', 'Axes de symétrie'], rows: [
      ['Isocèle (en A)', '2 côtés égaux (AB = AC). Les 2 angles à la base sont égaux.', '1 axe : la médiatrice de la base'],
      ['Équilatéral', '3 côtés égaux. Les 3 angles mesurent 60°.', '3 axes'],
      ['Rectangle (en A)', '1 angle droit. Les 2 autres angles sont complémentaires.', 'aucun en général'],
    ]},
    { type: 'table', headers: ['Droite particulière', 'Définition'], rows: [
      ['Médiatrice d\'un côté', 'perpendiculaire à ce côté en son milieu'],
      ['Médiane', 'relie un sommet au milieu du côté opposé'],
      ['Hauteur', 'passe par un sommet et est perpendiculaire au côté opposé'],
      ['Bissectrice d\'un angle', 'partage l\'angle en deux angles de même mesure'],
    ]},

    { type: 'heading', text: '3. Mes astuces pour ne pas oublier', level: 2 },
    { type: 'note', variant: 'tip', text: 'Triangle isocèle en A : l\'axe de symétrie est à la fois médiatrice de [BC], médiane, hauteur et bissectrice issues de A.' },
    { type: 'note', variant: 'tip', text: 'Pour justifier : isocèle → 2 côtés égaux ou 2 angles égaux ; équilatéral → 3 côtés égaux ou 3 angles de 60° ; rectangle → un angle de 90° ou deux angles qui font 90°.' },
    { type: 'note', variant: 'tip', text: 'Bissectrice à la règle et au compas : 1) arc de cercle de centre O qui coupe les côtés en M et N ; 2) de M et N, deux arcs de même rayon qui se coupent en P ; 3) trace [OP) : c\'est la bissectrice.' },
    { type: 'note', variant: 'warning', text: 'Piège : 4 + 5 = 9, donc des côtés de 4 cm, 5 cm et 9 cm donnent un triangle aplati : impossible à construire.' },

    { type: 'heading', text: '4. Je m\'entraîne', level: 2 },
    { type: 'list', ordered: true, items: [
      'Ex. 1 — Peut-on construire un triangle de côtés : a) 3, 4, 8 cm ? b) 5, 6, 7 cm ? c) 4, 5, 9 cm ?',
      'Ex. 2 — Donne la nature du triangle dont les angles mesurent : a) 50°, 65°, 65° b) 60°, 60°, 60° c) 90°, 35°, 55°.',
      'Ex. 3 — ABC est isocèle en A et l\'angle en A mesure 100°. Calcule les angles B et C.',
      'Ex. 4 — Justifie qu\'un triangle ayant deux angles de 45° et 45° est rectangle et isocèle.',
      'Ex. 5 — Écris un programme de construction d\'un triangle équilatéral de côté 5 cm.',
    ]},

    { type: 'heading', text: '5. Je vérifie mes réponses', level: 2 },
    { type: 'paragraph', text: 'Essaie d\'abord seul(e), puis compare !', italic: true },
    { type: 'list', items: [
      'Je découvre : B = C = (180° − 60°) ÷ 2 = 60°. ABC est équilatéral.',
      'Ex. 1 : a) non (8 > 3+4) b) oui (7 < 5+6) c) non (9 = 4+5, aplati).',
      'Ex. 2 : a) isocèle b) équilatéral c) rectangle.',
      'Ex. 3 : (180° − 100°) ÷ 2 = 40°. B = C = 40°.',
      'Ex. 4 : deux angles égaux → isocèle. 3e angle = 180° − 90° = 90° → rectangle.',
      'Ex. 5 : trace [AB] de 5 cm, arc de centre A rayon 5, arc de centre B rayon 5, C = intersection, trace [AC] et [BC].',
    ]},

    { type: 'heading', text: '6. À la maison', level: 2 },
    { type: 'list', ordered: true, items: [
      'Devoir 1 — Construis un triangle isocèle de base 6 cm et de côtés égaux 5 cm. Trace son axe de symétrie.',
      'Devoir 2 — Construis la bissectrice d\'un angle de 70° à la règle et au compas.',
    ]},
    { type: 'note', variant: 'tip', text: 'Garde toujours tes traits de construction : ils prouvent que tu as respecté le programme.' },
  ],
};

/* ------------------------------------------------------------------ */
/*  MODULE 8 — Cercles et disques                                      */
/* ------------------------------------------------------------------ */
const module8: Lesson = {
  id: 'les_math5e_08',
  title: 'Cercles et disques',
  subject: 'Mathématiques',
  gradeClass: '5e',
  chapter: 'Géométrie du plan',
  summary: 'Cercle, disque, position d\'un point, cercle circonscrit, périmètre et aire.',
  content: `### Cercles et disques
Utiliser le cercle, le disque et le cercle circonscrit à un triangle.`,
  durationMinutes: 30,
  published: true,
  blocks: [
    { type: 'heading', text: 'Cercles et disques', level: 1 },
    { type: 'paragraph', text: 'Je sais utiliser le cercle, le disque et le cercle circonscrit à un triangle.', bold: true, italic: true },
    { type: 'note', variant: 'definition', text: 'À la fin de cette leçon, je saurai : identifier les éléments d\'un cercle et d\'un disque ; traduire la position d\'un point par une égalité ou une inégalité ; construire le cercle circonscrit à un triangle ; calculer le périmètre d\'un cercle et l\'aire d\'un disque.' },

    { type: 'heading', text: '1. Je découvre', level: 2 },
    { type: 'paragraph', text: 'Un technicien doit installer le pied d\'un pylône d\'antenne à égale distance des villages A, B et C. Le réseau couvre tous les points situés à moins de la distance AO du pylône O. Les chefs des villages D et E veulent savoir s\'ils seront couverts.' },
    { type: 'paragraph', text: 'Les élèves du village E décident de construire le cercle de couverture.' },
    { type: 'list', ordered: true, items: [
      'Comment trouver le point O équidistant de A, B et C ?',
      'Que représente le cercle de centre O qui passe par A, B et C ?',
      'Comment savoir si D est couvert ?',
    ]},

    { type: 'heading', text: '2. Je retiens', level: 2 },
    { type: 'paragraph', text: 'L\'essentiel à savoir', bold: true },
    { type: 'note', variant: 'definition', text: 'Cercle C(A ; r) : ensemble des points situés à la distance r du centre A. Disque D(A ; r) : le cercle et toute la surface à l\'intérieur.' },
    { type: 'paragraph', text: 'Rayon : segment qui joint le centre à un point du cercle. Diamètre : segment qui passe par le centre et joint deux points du cercle (d = 2r). Corde : segment qui joint deux points du cercle.' },
    { type: 'paragraph', text: 'Position d\'un point M : sur le cercle si AM = r ; à l\'intérieur si AM < r ; à l\'extérieur si AM > r. M appartient au disque D(A ; r) si AM = r ou AM < r.' },
    { type: 'formula', text: 'Périmètre du cercle : P = 2 × π × r = π × d' },
    { type: 'formula', text: 'Aire du disque : A = π × r²' },
    { type: 'note', variant: 'definition', text: 'Cercle circonscrit à un triangle : cercle qui passe par ses trois sommets. Son centre est le point d\'intersection des médiatrices des côtés.' },
    { type: 'note', variant: 'definition', text: 'Cas du triangle rectangle : le centre du cercle circonscrit est le milieu de l\'hypoténuse.' },
    { type: 'figure', emoji: '⭕', label: 'Positions d\'un point par rapport à un cercle', caption: 'M sur le cercle (AM = r), à l\'intérieur (AM < r) ou à l\'extérieur (AM > r).' },

    { type: 'heading', text: '3. Mes astuces pour ne pas oublier', level: 2 },
    { type: 'note', variant: 'tip', text: 'Valeur approchée : π ≈ 3,14. Écris d\'abord le résultat exact (ex. 10π), puis la valeur approchée.' },
    { type: 'note', variant: 'warning', text: 'Piège : le périmètre est en cm (longueur), l\'aire en cm² (surface).' },
    { type: 'note', variant: 'tip', text: 'Pour construire le cercle circonscrit : 1) deux médiatrices ; 2) leur intersection O ; 3) compas en O ouvert jusqu\'à un sommet ; 4) trace le cercle.' },

    { type: 'heading', text: '4. Je m\'entraîne', level: 2 },
    { type: 'list', ordered: true, items: [
      'Ex. 1 — Soit le cercle C(O ; 4 cm). Où se trouvent les points A, B et C tels que OA = 3 cm, OB = 4 cm et OC = 5 cm ?',
      'Ex. 2 — a) Traduis : « M appartient au cercle C(A ; 3 cm) ». b) Traduis : « N est à l\'intérieur du disque D(A ; 3 cm) ».',
      'Ex. 3 — Un cercle a un rayon de 7 cm. Calcule son périmètre et l\'aire du disque (π ≈ 3,14).',
      'Ex. 4 — Un disque a un diamètre de 10 cm. Donne le périmètre et l\'aire en fonction de π, puis les valeurs approchées.',
      'Ex. 5 — ABC est rectangle en A et BC = 10 cm. Où est le centre du cercle circonscrit ? Quel est son rayon ?',
      'Ex. 6 — Une table ronde a un diamètre de 1,2 m. Calcule son aire (π ≈ 3,14).',
    ]},

    { type: 'heading', text: '5. Je vérifie mes réponses', level: 2 },
    { type: 'paragraph', text: 'Essaie d\'abord seul(e), puis compare !', italic: true },
    { type: 'list', items: [
      'Je découvre : O = intersection des médiatrices. Le cercle de centre O passant par A, B, C est le cercle circonscrit. D est couvert si OD ≤ OA.',
      'Ex. 1 : A intérieur ; B sur le cercle ; C extérieur.',
      'Ex. 2 : a) AM = 3 cm. b) AN < 3 cm.',
      'Ex. 3 : P = 43,96 cm ; A = 153,86 cm².',
      'Ex. 4 : P = 10π ≈ 31,4 cm ; A = 25π ≈ 78,5 cm².',
      'Ex. 5 : centre = milieu de [BC] ; rayon = 5 cm.',
      'Ex. 6 : r = 0,6 m ; A = 0,36π ≈ 1,13 m².',
    ]},

    { type: 'heading', text: '6. À la maison', level: 2 },
    { type: 'list', ordered: true, items: [
      'Devoir 1 — Construis un triangle ABC avec AB = 5 cm, AC = 6 cm, BC = 7 cm, puis son cercle circonscrit. Écris le programme de construction.',
      'Devoir 2 — Une roue de vélo a un rayon de 35 cm. Calcule la distance parcourue en un tour (π ≈ 3,14).',
    ]},
    { type: 'note', variant: 'tip', text: 'Utilise un compas bien serré : un compas qui bouge fausse tout le cercle.' },
  ],
};

/* ------------------------------------------------------------------ */
/*  MODULE 9 — Les parallélogrammes particuliers                       */
/* ------------------------------------------------------------------ */
const module9: Lesson = {
  id: 'les_math5e_09',
  title: 'Les parallélogrammes particuliers',
  subject: 'Mathématiques',
  gradeClass: '5e',
  chapter: 'Géométrie du plan',
  summary: 'Rectangle, losange, carré : propriétés, justification, périmètre et aire du losange.',
  content: `### Les parallélogrammes particuliers
Reconnaître, construire et justifier un rectangle, un losange et un carré.`,
  durationMinutes: 30,
  published: true,
  blocks: [
    { type: 'heading', text: 'Les parallélogrammes particuliers', level: 1 },
    { type: 'paragraph', text: 'Je sais reconnaître, construire et justifier un rectangle, un losange et un carré.', bold: true, italic: true },
    { type: 'note', variant: 'definition', text: 'À la fin de cette leçon, je saurai : connaître les propriétés des angles d\'un parallélogramme ; connaître les définitions et propriétés du rectangle, du losange et du carré ; justifier qu\'un quadrilatère est un rectangle, un losange ou un carré ; calculer le périmètre et l\'aire d\'un losange.' },

    { type: 'heading', text: '1. Je découvre', level: 2 },
    { type: 'paragraph', text: 'En arts plastiques, les élèves de la 5e 4 découvrent une frise faite de quadrilatères dont les quatre côtés mesurent tous 3 cm.' },
    { type: 'paragraph', text: 'Ils veulent identifier les quadrilatères de la frise et noter les informations dont ils sont sûrs.' },
    { type: 'list', ordered: true, items: [
      'Un quadrilatère qui a ses 4 côtés égaux : comment s\'appelle-t-il ?',
      'Que peux-tu dire de ses diagonales ?',
    ]},

    { type: 'heading', text: '2. Je retiens', level: 2 },
    { type: 'paragraph', text: 'L\'essentiel à savoir', bold: true },
    { type: 'note', variant: 'definition', text: 'Parallélogramme : quadrilatère dont les côtés opposés sont parallèles. Ses angles opposés ont la même mesure ; deux angles consécutifs sont supplémentaires (leur somme est 180°).' },
    { type: 'note', variant: 'definition', text: 'Rectangle : parallélogramme qui a un angle droit (donc 4 angles droits). Ses diagonales ont la même longueur et se coupent en leur milieu.' },
    { type: 'note', variant: 'definition', text: 'Losange : parallélogramme qui a deux côtés consécutifs égaux (donc 4 côtés égaux). Ses diagonales sont perpendiculaires et se coupent en leur milieu.' },
    { type: 'note', variant: 'definition', text: 'Carré : à la fois rectangle et losange (4 angles droits et 4 côtés égaux).' },
    { type: 'formula', text: 'Périmètre du losange : P = 4 × c' },
    { type: 'formula', text: 'Aire du losange : A = (D × d) ÷ 2' },
    { type: 'paragraph', text: 'D et d : les diagonales.', italic: true },
    { type: 'table', headers: ['Pour prouver que c\'est un…', 'Je montre que…'], rows: [
      ['Rectangle', 'un parallélogramme a un angle droit, OU ses diagonales ont la même longueur, OU un quadrilatère a 3 angles droits'],
      ['Losange', 'un parallélogramme a deux côtés consécutifs égaux, OU ses diagonales sont perpendiculaires, OU un quadrilatère a 4 côtés égaux'],
      ['Carré', 'un rectangle a deux côtés consécutifs égaux (ou des diagonales perpendiculaires), OU un losange a un angle droit (ou des diagonales égales)'],
    ]},

    { type: 'heading', text: '3. Mes astuces pour ne pas oublier', level: 2 },
    { type: 'note', variant: 'tip', text: 'Rectangle = diagonales ÉGALES. Losange = diagonales PERPENDICULAIRES. Carré = les deux.' },
    { type: 'note', variant: 'tip', text: 'Les deux sens : si c\'est un rectangle, alors les diagonales sont égales (propriété directe) ; si un parallélogramme a des diagonales égales, alors c\'est un rectangle (réciproque).' },
    { type: 'note', variant: 'warning', text: 'Piège : un quadrilatère qui n\'a que des diagonales égales n\'est pas forcément un rectangle : il doit d\'abord être un parallélogramme.' },

    { type: 'heading', text: '4. Je m\'entraîne', level: 2 },
    { type: 'list', ordered: true, items: [
      'Ex. 1 — ABCD est un parallélogramme et l\'angle A mesure 65°. Calcule les angles B, C et D.',
      'Ex. 2 — Donne le nom le plus précis : a) 4 côtés égaux et 4 angles droits ; b) parallélogramme aux diagonales égales ; c) parallélogramme aux diagonales perpendiculaires.',
      'Ex. 3 — ABCD est un parallélogramme avec AC = BD. Justifie que c\'est un rectangle.',
      'Ex. 4 — Un losange a pour côté 5 cm et pour diagonales 8 cm et 6 cm. Calcule son périmètre et son aire.',
      'Ex. 5 — Écris un programme de construction d\'un carré de côté 4 cm.',
    ]},

    { type: 'heading', text: '5. Je vérifie mes réponses', level: 2 },
    { type: 'paragraph', text: 'Essaie d\'abord seul(e), puis compare !', italic: true },
    { type: 'list', items: [
      'Je découvre : c\'est un losange. Ses diagonales sont perpendiculaires et se coupent en leur milieu.',
      'Ex. 1 : C = 65° ; B = 115° ; D = 115°.',
      'Ex. 2 : a) un carré b) un rectangle c) un losange.',
      'Ex. 3 : un parallélogramme dont les diagonales ont la même longueur est un rectangle.',
      'Ex. 4 : P = 20 cm ; A = 24 cm².',
      'Ex. 5 : trace [AB] de 4 cm, perpendiculaire en A, place D à 4 cm, arcs de rayon 4 cm de B et D → C, trace [BC] et [DC].',
    ]},

    { type: 'heading', text: '6. À la maison', level: 2 },
    { type: 'list', ordered: true, items: [
      'Devoir 1 — Construis un rectangle de longueur 6 cm et largeur 3 cm. Trace ses diagonales et mesure-les.',
      'Devoir 2 — Construis un losange de côté 4 cm dont une diagonale mesure 6 cm. Calcule son périmètre.',
    ]},
    { type: 'note', variant: 'tip', text: 'Fais un tableau « rectangle / losange / carré » avec les propriétés et relis-le chaque soir.' },
  ],
};

/* ------------------------------------------------------------------ */
/*  MODULE 10 — Prisme droit et cylindre droit                         */
/* ------------------------------------------------------------------ */
const module10: Lesson = {
  id: 'les_math5e_10',
  title: 'Prisme droit et cylindre droit',
  subject: 'Mathématiques',
  gradeClass: '5e',
  chapter: 'Géométrie de l\'espace',
  summary: 'Description, patron, aire latérale, aire totale et volume du prisme et du cylindre.',
  content: `### Prisme droit et cylindre droit
Décrire un prisme droit et un cylindre droit et calculer leurs aires et volumes.`,
  durationMinutes: 35,
  published: true,
  blocks: [
    { type: 'heading', text: 'Prisme droit et cylindre droit', level: 1 },
    { type: 'paragraph', text: 'Je sais décrire un prisme droit et un cylindre droit et calculer leurs aires et volumes.', bold: true, italic: true },
    { type: 'note', variant: 'definition', text: 'À la fin de cette leçon, je saurai : reconnaître et décrire un prisme droit et un cylindre droit ; construire un patron ; calculer l\'aire latérale, l\'aire totale et le volume.' },

    { type: 'heading', text: '1. Je découvre', level: 2 },
    { type: 'paragraph', text: 'Le professeur dessine au tableau un solide : deux triangles ABC et DEF identiques et parallèles, reliés par trois rectangles.' },
    { type: 'paragraph', text: 'Il donne 0,5 point pour chaque information juste.' },
    { type: 'list', ordered: true, items: [
      'Combien ce solide a-t-il de faces ? de sommets ? d\'arêtes ?',
      'Quelle est la forme des bases ? des faces latérales ?',
    ]},

    { type: 'heading', text: '2. Je retiens', level: 2 },
    { type: 'paragraph', text: 'L\'essentiel à savoir', bold: true },
    { type: 'note', variant: 'definition', text: 'Prisme droit : solide dont les deux bases sont des polygones identiques et parallèles, et dont les faces latérales sont des rectangles. Hauteur h : la longueur d\'une arête latérale.' },
    { type: 'note', variant: 'definition', text: 'Cylindre droit : solide formé de deux disques identiques (les bases) et d\'une surface latérale qui, dépliée, est un rectangle.' },
    { type: 'paragraph', text: 'Patron : figure plane qu\'on plie pour fabriquer le solide.' },
    { type: 'table', headers: ['', 'Prisme droit', 'Cylindre droit'], rows: [
      ['Aire d\'une base', 'Ab (selon la base)', 'π × r²'],
      ['Aire latérale', 'Al = périmètre de la base × h', 'Al = 2 × π × r × h'],
      ['Aire totale', 'At = Al + 2 × Ab', 'At = Al + 2 × π × r²'],
      ['Volume', 'V = Ab × h', 'V = π × r² × h'],
    ]},

    { type: 'heading', text: '3. Mes astuces pour ne pas oublier', level: 2 },
    { type: 'note', variant: 'tip', text: 'Retiens : volume = aire de la base × hauteur, pour les deux solides.' },
    { type: 'note', variant: 'tip', text: 'Patron du cylindre : 2 disques + 1 rectangle de longueur 2πr (le périmètre de la base) et de largeur h.' },
    { type: 'note', variant: 'warning', text: 'Piège : n\'oublie pas d\'ajouter les deux bases pour l\'aire totale ; et mets l\'unité (cm², cm³).' },
    { type: 'note', variant: 'tip', text: 'Prisme à base triangulaire : 5 faces, 9 arêtes, 6 sommets. Pavé droit : 6 faces, 12 arêtes, 8 sommets.' },

    { type: 'heading', text: '4. Je m\'entraîne', level: 2 },
    { type: 'list', ordered: true, items: [
      'Ex. 1 — Combien de faces, d\'arêtes et de sommets a un prisme droit à base triangulaire ?',
      'Ex. 2 — Base : triangle rectangle de côtés 3, 4, 5 cm (côtés de l\'angle droit : 3 et 4), hauteur 10 cm. Calcule Al, Ab, At et V.',
      'Ex. 3 — Cylindre de rayon 3 cm et hauteur 10 cm. Calcule Ab, Al, At et V (exact en π, puis π ≈ 3,14).',
      'Ex. 4 — Un bidon cylindrique a un rayon de 10 cm et une hauteur de 30 cm. Quel est son volume en cm³ puis en litres ?',
      'Ex. 5 — Décris le patron d\'un cylindre de rayon 3 cm et hauteur 10 cm.',
    ]},

    { type: 'heading', text: '5. Je vérifie mes réponses', level: 2 },
    { type: 'paragraph', text: 'Essaie d\'abord seul(e), puis compare !', italic: true },
    { type: 'list', items: [
      'Je découvre : 5 faces (2 triangles + 3 rectangles), 6 sommets, 9 arêtes.',
      'Ex. 1 : 5 faces ; 9 arêtes ; 6 sommets.',
      'Ex. 2 : Al = 12 × 10 = 120 cm² ; Ab = 6 cm² ; At = 132 cm² ; V = 60 cm³.',
      'Ex. 3 : Ab = 9π ≈ 28,26 cm² ; Al = 60π ≈ 188,4 cm² ; At = 78π ≈ 244,92 cm² ; V = 90π ≈ 282,6 cm³.',
      'Ex. 4 : V = 9 420 cm³ = 9,42 L.',
      'Ex. 5 : 2 disques de rayon 3 cm + 1 rectangle de longueur 6π ≈ 18,84 cm et largeur 10 cm.',
    ]},

    { type: 'heading', text: '6. À la maison', level: 2 },
    { type: 'list', ordered: true, items: [
      'Devoir 1 — Fabrique un cylindre en carton à partir d\'un patron (rayon 3 cm, hauteur 8 cm) et calcule son volume.',
      'Devoir 2 — Un prisme droit a pour base un triangle d\'aire 15 cm² et de périmètre 20 cm ; sa hauteur est 8 cm. Calcule Al, At et V.',
    ]},
    { type: 'note', variant: 'tip', text: 'Écris toujours : formule → remplacement → résultat avec l\'unité.' },
  ],
};

/* ------------------------------------------------------------------ */
/*  MODULE 11 — Figures symétriques par rapport à un point             */
/* ------------------------------------------------------------------ */
const module11: Lesson = {
  id: 'les_math5e_11',
  title: 'Figures symétriques par rapport à un point',
  subject: 'Mathématiques',
  gradeClass: '5e',
  chapter: 'Transformations du plan',
  summary: 'Symétrique d\'un point, d\'un segment, d\'une droite, d\'un cercle ; propriétés de conservation ; centre de symétrie.',
  content: `### Figures symétriques par rapport à un point
Construire le symétrique d\'une figure par rapport à un point et trouver un centre de symétrie.`,
  durationMinutes: 30,
  published: true,
  blocks: [
    { type: 'heading', text: 'Figures symétriques par rapport à un point', level: 1 },
    { type: 'paragraph', text: 'Je sais construire le symétrique d\'une figure par rapport à un point et trouver un centre de symétrie.', bold: true, italic: true },
    { type: 'note', variant: 'definition', text: 'À la fin de cette leçon, je saurai : construire le symétrique d\'un point, d\'un segment, d\'une droite et d\'un cercle ; connaître les propriétés de conservation ; reconnaître le centre de symétrie d\'une figure.' },

    { type: 'heading', text: '1. Je découvre', level: 2 },
    { type: 'paragraph', text: 'À Grand-Bassam, un vitrail circulaire de 2 m de diamètre est brisé. Il est symétrique par rapport à son centre O. Les élèves veulent reconstituer la partie manquante à partir de la partie conservée.' },
    { type: 'paragraph', text: 'Ils décident de construire les symétriques des points du vitrail par rapport à O.' },
    { type: 'list', ordered: true, items: [
      'Comment trouver le symétrique d\'un point A du vitrail par rapport à O ?',
      'Que peut-on dire de O pour le segment [AA′] ?',
    ]},

    { type: 'heading', text: '2. Je retiens', level: 2 },
    { type: 'paragraph', text: 'L\'essentiel à savoir', bold: true },
    { type: 'note', variant: 'definition', text: 'Symétrique d\'un point : A′ est le symétrique de A par rapport à O si O est le milieu de [AA′]. Le symétrique de O est O lui-même.' },
    { type: 'paragraph', text: 'Construction : on trace la droite (AO), puis on reporte au compas la longueur OA de l\'autre côté de O : OA′ = OA.' },
    { type: 'note', variant: 'definition', text: 'Propriétés de conservation : la symétrie par rapport à un point conserve les alignements, les longueurs, les mesures d\'angles et les milieux.' },
    { type: 'paragraph', text: 'Symétrique d\'un segment : un segment de même longueur. D\'une demi-droite : une demi-droite. D\'une droite : une droite parallèle (confondue si elle passe par O).' },
    { type: 'paragraph', text: 'Deux droites parallèles ont pour symétriques deux droites parallèles ; deux droites perpendiculaires ont pour symétriques deux droites perpendiculaires.' },
    { type: 'paragraph', text: 'Symétrique d\'un cercle : un cercle de même rayon, dont le centre est le symétrique du centre.' },
    { type: 'note', variant: 'definition', text: 'Centre de symétrie d\'une figure : point O tel que le symétrique de la figure par rapport à O est la figure elle-même.' },
    { type: 'table', headers: ['Figure', 'Centre de symétrie'], rows: [
      ['Segment', 'son milieu'],
      ['Cercle', 'son centre'],
      ['Parallélogramme (rectangle, losange, carré)', 'le point d\'intersection des diagonales'],
      ['Triangle', 'aucun'],
    ]},
    { type: 'figure', emoji: '🔄', label: 'Triangle A′B′C′ symétrique de ABC par rapport à O', caption: 'La symétrie centrale fait tourner la figure d\'un demi-tour autour de O.' },

    { type: 'heading', text: '3. Mes astuces pour ne pas oublier', level: 2 },
    { type: 'note', variant: 'tip', text: 'Pour construire A′ : « J\'avance de A jusqu\'à O, puis je continue de la même longueur. »' },
    { type: 'note', variant: 'tip', text: 'Pour justifier : « O est le milieu de [AA′], donc A′ est le symétrique de A par rapport à O. »' },
    { type: 'note', variant: 'warning', text: 'Piège : un symétrique par rapport à un point n\'est pas un symétrique par rapport à une droite (miroir). Ici, la figure tourne d\'un demi-tour.' },

    { type: 'heading', text: '4. Je m\'entraîne', level: 2 },
    { type: 'list', ordered: true, items: [
      'Ex. 1 — O est le milieu de [MN]. Que peut-on dire de M et N ? Que peut-on dire de la longueur de [M′N′] ?',
      'Ex. 2 — [AB] mesure 6 cm et l\'angle ABC mesure 40°. Donne la longueur de [A′B′] et la mesure de l\'angle A′B′C′.',
      'Ex. 3 — Parmi ces figures, lesquelles ont un centre de symétrie : cercle, segment, triangle isocèle, carré, lettres H, A, N, E ?',
      'Ex. 4 — (AB) et (CD) sont parallèles. Que peux-tu dire de leurs symétriques par rapport à O ?',
      'Ex. 5 — Écris un programme de construction du symétrique du triangle ABC par rapport à O.',
    ]},

    { type: 'heading', text: '5. Je vérifie mes réponses', level: 2 },
    { type: 'paragraph', text: 'Essaie d\'abord seul(e), puis compare !', italic: true },
    { type: 'list', items: [
      'Je découvre : on trace (AO) et on reporte OA de l\'autre côté de O (OA′ = OA). O est le milieu de [AA′].',
      'Ex. 1 : M et N sont symétriques par rapport à O. [M′N′] = [NM], de même longueur que [MN].',
      'Ex. 2 : A′B′ = 6 cm (longueurs conservées) ; angle A′B′C′ = 40° (angles conservés).',
      'Ex. 3 : cercle, segment, carré, lettres H et N. Le triangle isocèle et les lettres A, E n\'en ont pas.',
      'Ex. 4 : leurs symétriques sont deux droites parallèles.',
      'Ex. 5 : trace (AO) et place A′ tel que O soit le milieu de [AA′]. Fais de même pour B (B′) et C (C′). Trace le triangle A′B′C′.',
    ]},

    { type: 'heading', text: '6. À la maison', level: 2 },
    { type: 'list', ordered: true, items: [
      'Devoir 1 — Trace un triangle ABC et un point O hors du triangle. Construis le symétrique de ABC par rapport à O.',
      'Devoir 2 — Cherche dans ton environnement (logos, tissus, carrelages) 3 figures qui ont un centre de symétrie et dessine-les.',
    ]},
    { type: 'note', variant: 'tip', text: 'Après chaque point construit, vérifie avec la règle que O est bien le milieu de [AA′].' },
  ],
};

export const LESSONS_MATH_5E: Lesson[] = [
  module1, module2, module3, module4, module5,
  module6, module7, module8, module9, module10, module11,
];
