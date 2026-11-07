import type { Lesson } from './types';

/**
 * Programme du primaire ivoirien (CI → CM2).
 * Contenus rédigés d'après les progressions officielles : lecture, calcul,
 * français, histoire-géographie, sciences et éducation civique.
 */
export const PRIMAIRE_LESSONS: Lesson[] = [
  /* ------------------------------- CI ------------------------------- */
  {
    id: 'les_pr_ci_fr_01',
    title: 'Les voyelles : a, e, i, o, u, y',
    subject: 'Français',
    gradeClass: 'CI',
    chapter: 'Lecture : les sons et les lettres',
    summary: 'Reconnaître, nommer et écrire les six voyelles du français.',
    content: `### 1. Qu'est-ce qu'une voyelle ?
Une voyelle est une lettre que l'on prononce la bouche ouverte, sans obstacle. Le français compte **six voyelles** : a, e, i, o, u, y.

### 2. Entendre la voyelle dans un mot
- **a** comme dans *avion*, *arachide*
- **e** comme dans *école*, *élève*
- **i** comme dans *ici*, *igname*
- **o** comme dans *orange*, *orange*
- **u** comme dans *usine*, *uniforme*
- **y** comme dans *yaourt*, *yoyo*

### 3. Exercice oral
Prononce lentement : « Papa a acheté une arachide à Abidjan. »
Combien de fois entends-tu le son **a** ? Réponse : six fois.

### 4. À retenir
- Minuscules : **a e i o u y**
- Majuscules : **A E I O U Y**
- Chaque voyelle peut s'écrire seule ou dans une syllabe : *ma*, *pi*, *lo*.`,
    durationMinutes: 20,
    published: true,
  },
  {
    id: 'les_pr_ci_math_01',
    title: 'Compter de 0 à 10 et reconnaître les chiffres',
    subject: 'Mathématiques',
    gradeClass: 'CI',
    chapter: 'Les nombres de 0 à 10',
    summary: 'Dénombrer une collection, lire et écrire les chiffres de 0 à 10.',
    content: `### 1. Les chiffres de 0 à 10
0 – 1 – 2 – 3 – 4 – 5 – 6 – 7 – 8 – 9 – 10

### 2. Compter des objets
Pour compter, je touche chaque objet une seule fois et je dis un nombre :
un, deux, trois, quatre, cinq…

Exemple : sur la table, il y a **4 mangues**. Je dis : « une, deux, trois, quatre ».

### 3. Le nombre et sa quantité
- 0 veut dire « aucun objet »
- 5 veut dire « cinq objets »
- 10 est le plus grand nombre que nous connaissons aujourd'hui

### 4. Exercice
Compte les doigts d'une seule main : tu trouves **5**.
Compte les doigts des deux mains : tu trouves **10**.`,
    durationMinutes: 20,
    published: true,
  },

  /* ------------------------------- CP1 ------------------------------ */
  {
    id: 'les_pr_cp1_fr_01',
    title: 'Lire mes premières syllabes : ma, me, mi, mo, mu',
    subject: 'Français',
    gradeClass: 'CP1',
    chapter: 'Lecture : former des syllabes',
    summary: 'Associer une consonne et une voyelle pour lire des syllabes simples.',
    content: `### 1. La consonne m
La lettre **m** se prononce en fermant les lèvres. Avec les voyelles, elle forme des syllabes :

- **ma** comme dans *maman*
- **me** comme dans *merci*
- **mi** comme dans *midi*
- **mo** comme dans *moto*
- **mu** comme dans *murmure*

### 2. Lire des mots
- ma – man – **maman**
- mo – to – **moto**
- mi – li – **mili** (dans *mil*)

### 3. Méthode du lecteur débutant
1. Je regarde la consonne.
2. Je regarde la voyelle.
3. Je prononce les deux sons d'un seul trait : *mmmm-a* → **ma**.

### 4. Exercice
Lis à voix haute : *ma, me, mi, mo, mu*. Puis entoure la syllabe que tu entends au début de « mardi » (réponse : **ma**).`,
    durationMinutes: 20,
    published: true,
  },
  {
    id: 'les_pr_cp1_math_01',
    title: "L'addition jusqu'à 20",
    subject: 'Mathématiques',
    gradeClass: 'CP1',
    chapter: 'Premiers calculs',
    summary: "Comprendre le sens de l'addition et calculer des sommes jusqu'à 20.",
    content: `### 1. Le sens de l'addition
Additionner, c'est **réunir** deux quantités. Le signe de l'addition est **+**.

Exemple : 7 mangues + 5 mangues = **12 mangues**.

### 2. Calculer
- 6 + 4 = **10**
- 8 + 7 = **15** (je fais 8 + 2 = 10, puis 10 + 5 = 15)
- 9 + 9 = **18**

### 3. Les doubles à connaître
1 + 1 = 2 ; 2 + 2 = 4 ; 3 + 3 = 6 ; 4 + 4 = 8 ; 5 + 5 = 10
6 + 6 = 12 ; 7 + 7 = 14 ; 8 + 8 = 16 ; 9 + 9 = 18 ; 10 + 10 = 20

### 4. Problème
Awa a 9 crayons. Son frère lui donne 6 crayons.
Combien de crayons a-t-elle en tout ?
Calcul : 9 + 6 = **15 crayons**.`,
    durationMinutes: 25,
    published: true,
  },

  /* ------------------------------- CP2 ------------------------------ */
  {
    id: 'les_pr_cp2_math_01',
    title: 'La soustraction : enlever, retirer',
    subject: 'Mathématiques',
    gradeClass: 'CP2',
    chapter: 'Les quatre opérations',
    summary: 'Comprendre le sens de la soustraction et calculer des différences.',
    content: `### 1. Le sens de la soustraction
Soustraire, c'est **enlever** une quantité à une autre. Le signe est **−**.

Exemple : j'ai 12 oranges, j'en donne 5.
Il me reste : 12 − 5 = **7 oranges**.

### 2. Trois situations qui utilisent la soustraction
- Je retire : 15 − 6 = 9
- Je cherche ce qui manque : pour aller de 8 à 20, il manque 12
- Je compare : 14 est plus grand que 9 de 5

### 3. Calculer mentalement
- 10 − 3 = **7**
- 16 − 8 = **8**
- 20 − 12 = **8**

### 4. Problème
Un vendeur d'attiéké avait 30 sachets. Il en a vendu 17.
Combien lui en reste-t-il ?
30 − 17 = **13 sachets**.`,
    durationMinutes: 25,
    published: true,
  },
  {
    id: 'les_pr_cp2_fr_01',
    title: 'Le nom et le verbe',
    subject: 'Français',
    gradeClass: 'CP2',
    chapter: 'Grammaire : les classes de mots',
    summary: 'Distinguer le nom (qui désigne) du verbe (qui exprime une action).',
    content: `### 1. Le nom
Le nom désigne une personne, un animal, une chose ou une idée.
On peut le faire précéder de **un, une, le, la**.
- une **école**, un **enfant**, la **route**

### 2. Le verbe
Le verbe exprime ce que l'on **fait** ou ce qui **se passe**.
On peut le faire précéder de **il** ou **elle**.
- il **joue**, elle **écrit**, il **pleut**

### 3. Reconnaître dans une phrase
« Koffi **achète** du pain. »
- Koffi = nom propre
- achète = verbe
- pain = nom commun

### 4. Exercice
Souligne le verbe dans chaque phrase :
1. Les élèves chantent.
2. Le chat dort.
3. Maman prépare le riz.

Corrigé : **chantent**, **dort**, **prépare**.`,
    durationMinutes: 25,
    published: true,
  },

  /* ------------------------------- CE1 ------------------------------ */
  {
    id: 'les_pr_ce1_math_01',
    title: 'Les tables de multiplication de 2, 3, 4 et 5',
    subject: 'Mathématiques',
    gradeClass: 'CE1',
    chapter: 'La multiplication',
    summary: 'Mémoriser les tables et les utiliser pour calculer rapidement.',
    content: `### 1. La multiplication, c'est une addition répétée
4 × 3 signifie 4 + 4 + 4 = **12**.
On lit « quatre fois trois ».

### 2. Les tables à connaître
- Table de 2 : 2, 4, 6, 8, 10, 12, 14, 16, 18, 20
- Table de 3 : 3, 6, 9, 12, 15, 18, 21, 24, 27, 30
- Table de 4 : 4, 8, 12, 16, 20, 24, 28, 32, 36, 40
- Table de 5 : 5, 10, 15, 20, 25, 30, 35, 40, 45, 50

### 3. Astuces
- Dans la table de 5, le résultat finit toujours par **0** ou **5**.
- Dans la table de 2, tous les résultats sont **pairs**.

### 4. Exercice
Une classe achète 6 paquets de 5 cahiers.
Combien de cahiers au total ?
6 × 5 = **30 cahiers**.`,
    durationMinutes: 25,
    published: true,
    blocks: [
      { type: 'heading', text: 'Les tables de multiplication de 2 à 5', level: 1 },
      { type: 'paragraph', text: 'La multiplication est une addition répétée : 4 × 3 = 4 + 4 + 4 = 12.' },
      {
        type: 'table',
        headers: ['×', '2', '3', '4', '5'],
        rows: [
          ['2', '4', '6', '8', '10'],
          ['3', '6', '9', '12', '15'],
          ['4', '8', '12', '16', '20'],
          ['5', '10', '15', '20', '25'],
        ],
      },
      { type: 'note', variant: 'tip', text: 'Récitez la table de 5 en tapant dans tes mains : 5, 10, 15, 20… le rythme aide la mémoire.' },
      { type: 'note', variant: 'warning', text: 'Attention : 4 × 3 et 3 × 4 donnent le même résultat (12), mais 4 + 3 = 7. Ne confonds pas les deux opérations.' },
    ],
  },
  {
    id: 'les_pr_ce1_fr_01',
    title: 'Le présent des verbes en -er',
    subject: 'Français',
    gradeClass: 'CE1',
    chapter: 'Conjugaison : le présent',
    summary: 'Conjuguer les verbes du premier groupe comme chanter ou jouer.',
    content: `### 1. Les terminaisons au présent
Pour les verbes en **-er** (chanter, jouer, parler), on ajoute :
- je chant**e**
- tu chant**es**
- il / elle chant**e**
- nous chant**ons**
- vous chant**ez**
- ils / elles chant**ent**

### 2. Exemple complet
**Jouer** : je joue, tu joues, il joue, nous jouons, vous jouez, elles jouent.

### 3. Piège à éviter
Les terminaisons **-e**, **-es** et **-ent** ne s'entendent pas à l'oral, mais elles s'écrivent !
« Les enfants jou**ent** » : on entend « jou », mais on écrit **-ent**.

### 4. Exercice
Complète avec le verbe *parler* au présent :
1. Nous ______ français. → **parlons**
2. Tu ______ à ton ami. → **parles**
3. Elles ______ au téléphone. → **parlent**`,
    durationMinutes: 25,
    published: true,
  },

  /* ------------------------------- CE2 ------------------------------ */
  {
    id: 'les_pr_ce2_hg_01',
    title: "Se repérer sur la carte de Côte d'Ivoire",
    subject: 'Histoire-Géographie',
    gradeClass: 'CE2',
    chapter: 'La carte et ses outils',
    summary: 'Lire une carte, utiliser la rose des vents et localiser les grandes villes.',
    content: `### 1. Les outils de la carte
- Le **titre** indique ce que représente la carte.
- La **légende** explique les couleurs et les symboles.
- La **rose des vents** donne le nord, le sud, l'est et l'ouest.
- L'**échelle** permet de mesurer les distances réelles.

### 2. La Côte d'Ivoire
La Côte d'Ivoire est un pays d'Afrique de l'Ouest. Sa capitale politique est **Yamoussoukro** et sa capitale économique est **Abidjan**. Ses voisins sont le Mali et le Burkina Faso au nord, le Ghana à l'est, la Guinée et le Liberia à l'ouest.

### 3. Océan et relief
Le pays est bordé au sud par l'**océan Atlantique** (golfe de Guinée). Le nord est occupé par des plateaux et des savanes, le sud par la forêt.

### 4. Exercice
Sur une carte muette, place : Abidjan, Yamoussoukro, Bouaké, Korhogo, Man.
Colorie en bleu l'océan Atlantique.`,
    durationMinutes: 30,
    published: true,
  },
  {
    id: 'les_pr_ce2_sc_01',
    title: "L'eau : usages, propreté et cycle",
    subject: 'Sciences et Technologie',
    gradeClass: 'CE2',
    chapter: "L'eau dans notre environnement",
    summary: "Connaître les usages de l'eau, les gestes d'hygiène et le cycle de l'eau.",
    content: `### 1. Les usages de l'eau
Nous utilisons l'eau pour boire, cuisiner, nous laver, laver le linge, arroser les cultures et abreuver les animaux.

### 2. Une eau propre pour la santé
- L'eau de robinet doit être potable.
- L'eau de puits ou de marigot doit être **bouillie** ou **traitée** avant d'être bue.
- Je me lave les mains au savon avant de manger et après les toilettes.

### 3. Le cycle de l'eau
1. Le soleil chauffe l'eau : elle **s'évapore**.
2. La vapeur monte, refroidit et forme les **nuages**.
3. L'eau retombe en **pluie**.
4. Elle ruisselle vers les rivières, les lacs et la mer.

### 4. À retenir
L'eau est précieuse : je ne la gaspille pas. Je ferme le robinet quand je me brosse les dents.`,
    durationMinutes: 30,
    published: true,
  },

  /* ------------------------------- CM1 ------------------------------ */
  {
    id: 'les_pr_cm1_math_01',
    title: 'Les fractions : partager un tout',
    subject: 'Mathématiques',
    gradeClass: 'CM1',
    chapter: 'Les fractions',
    summary: 'Lire, écrire et comparer des fractions simples à partir de partages.',
    content: `### 1. Qu'est-ce qu'une fraction ?
Une fraction représente une ou plusieurs parts égales d'un tout.
Dans **3/4**, le **4** indique le nombre de parts égales (le dénominateur) et le **3** le nombre de parts prises (le numérateur).

### 2. Exemple concret
Un attiéké est coupé en 4 parts égales. Koffi en mange 3 parts : il a mangé **3/4** de l'attiéké. Il reste **1/4**.

### 3. Fractions à connaître
- 1/2 = la moitié
- 1/4 = le quart
- 3/4 = trois quarts
- 1/10 = un dixième

### 4. Comparer
Quand le dénominateur est le même, la fraction la plus grande est celle qui a le plus grand numérateur :
**3/5 > 2/5**.

### 5. Exercice
Un champ est partagé en 8 parcelles égales. Le planteur cultive 5 parcelles.
Quelle fraction du champ cultive-t-il ? Réponse : **5/8**.`,
    durationMinutes: 30,
    published: true,
  },
  {
    id: 'les_pr_cm1_fr_01',
    title: 'Le passé composé avec avoir et être',
    subject: 'Français',
    gradeClass: 'CM1',
    chapter: 'Conjugaison : les temps du passé',
    summary: "Former le passé composé et choisir le bon auxiliaire.",
    content: `### 1. Formation
Le passé composé = **auxiliaire (avoir ou être) au présent + participe passé**.
- J'**ai mangé** du foutou.
- Je **suis parti** à l'école.

### 2. Avec l'auxiliaire avoir
La plupart des verbes se conjuguent avec **avoir** :
j'ai parlé, tu as fini, il a vendu, nous avons couru.

### 3. Avec l'auxiliaire être
Les verbes de mouvement et les verbes pronominaux se conjuguent avec **être** :
je suis allé, tu es venu, elle est arrivée, nous sommes restés.

### 4. Accord
Avec **être**, le participe passé s'accorde avec le sujet :
- Elle est arriv**ée**.
- Ils sont part**is**.

### 5. Exercice
Conjugue au passé composé :
1. Nous (finir) le devoir. → **avons fini**
2. Les filles (partir) tôt. → **sont parties**`,
    durationMinutes: 30,
    published: true,
  },

  /* ------------------------------- CM2 ------------------------------ */
  {
    id: 'les_pr_cm2_math_01',
    title: 'La proportionnalité : prix et quantités',
    subject: 'Mathématiques',
    gradeClass: 'CM2',
    chapter: 'Organisation et gestion de données',
    summary: 'Reconnaître une situation de proportionnalité et calculer avec le prix unitaire.',
    content: `### 1. Reconnaître la proportionnalité
Deux grandeurs sont proportionnelles lorsque l'on multiplie toujours la première par le même nombre pour obtenir la seconde. Ce nombre s'appelle le **coefficient de proportionnalité**.

Exemple : 1 kilo de riz coûte 500 F CFA.
- 2 kg → 1 000 F CFA
- 3 kg → 1 500 F CFA
Le prix est proportionnel à la quantité.

### 2. Le prix unitaire
Prix total ÷ quantité = prix d'une unité.
2 500 F CFA pour 5 kg → 2 500 ÷ 5 = **500 F CFA le kilo**.

### 3. La règle de trois
3 cahiers coûtent 1 200 F CFA. Combien coûtent 7 cahiers ?
1. Prix d'un cahier : 1 200 ÷ 3 = 400 F CFA
2. Prix de 7 cahiers : 400 × 7 = **2 800 F CFA**

### 4. Attention
Toutes les situations ne sont pas proportionnelles : le prix du transport en taxi ne double pas toujours quand la distance double.`,
    durationMinutes: 35,
    published: true,
    blocks: [
      { type: 'heading', text: 'La proportionnalité en pratique', level: 1 },
      { type: 'paragraph', text: 'Deux grandeurs sont proportionnelles si l\'on passe de l\'une à l\'autre en multipliant toujours par le même nombre.' },
      {
        type: 'table',
        headers: ['Quantité de riz (kg)', '1', '2', '5', '10'],
        rows: [['Prix (F CFA)', '500', '1 000', '2 500', '5 000']],
      },
      { type: 'graph', title: 'Prix du riz selon la quantité', kind: 'line', labels: ['1 kg', '2 kg', '5 kg', '10 kg'], values: [500, 1000, 2500, 5000] },
      { type: 'note', variant: 'definition', text: 'Le coefficient de proportionnalité est le prix d\'un kilogramme : 500 F CFA.' },
      { type: 'note', variant: 'example', text: 'Au marché d\'Adjamé, 3 kg de bananes plantain coûtent 1 500 F CFA. Un kilo coûte donc 500 F CFA.' },
    ],
  },
  {
    id: 'les_pr_cm2_fr_01',
    title: "L'accord du verbe avec son sujet",
    subject: 'Français',
    gradeClass: 'CM2',
    chapter: 'Orthographe grammaticale',
    summary: 'Repérer le sujet et accorder correctement le verbe en nombre et en personne.',
    content: `### 1. La règle
Le verbe s'accorde toujours avec son **sujet** en personne et en nombre.
- L'élève **écrit**. (sujet singulier)
- Les élèves **écrivent**. (sujet pluriel)

### 2. Trouver le sujet
Je pose la question : **« Qui est-ce qui… ? »**
« Les enfants jouent dans la cour. » → Qui est-ce qui joue ? **Les enfants** → 3e personne du pluriel.

### 3. Cas difficiles
- Sujet inversé : « Où **vont** les élèves ? »
- Sujet éloigné : « Les cahiers de Koffi **sont** neufs. »
- Plusieurs sujets = pluriel : « Awa et Koffi **arrivent**. »

### 4. Exercice
Accorde les verbes :
1. Les filles (chanter) dans la chorale. → **chantent**
2. Mon frère (regarder) la télévision. → **regarde**
3. Le maître et les élèves (entrer) en classe. → **entrent**`,
    durationMinutes: 30,
    published: true,
  },
  {
    id: 'les_pr_cm2_hg_01',
    title: 'Le relief et les climats de la Côte d\'Ivoire',
    subject: 'Histoire-Géographie',
    gradeClass: 'CM2',
    chapter: 'Géographie de la Côte d\'Ivoire',
    summary: 'Décrire les régions naturelles, le relief et les deux grandes zones climatiques.',
    content: `### 1. Le relief
La Côte d'Ivoire est un pays de **plaines** et de **plateaux**. On distingue :
- les plaines du sud, basses et humides ;
- les plateaux du centre et du nord ;
- les montagnes de l'ouest, avec le **mont Nimba** (1 752 m), point culminant.

### 2. Les climats
- Le **climat équatorial** au sud : chaud et très pluvieux (forêt dense).
- Le **climat tropical** au nord : plus sec, avec une saison des pluies et une saison sèche (savane).

### 3. Les fleuves
Les principaux fleuves sont le **Bandama**, le **Sassandra**, le **Cavally** et la **Comoé**.

### 4. Activités humaines
Le sud forestier favorise le **cacao**, le café et l'hévéa. Le nord de savane accueille le coton, l'anacarde et l'élevage.

### 5. Exercice
Relie chaque région à son activité : forêt du sud → cacao ; savane du nord → coton et élevage.`,
    durationMinutes: 30,
    published: true,
  },
  {
    id: 'les_pr_cm2_ecm_01',
    title: "Les droits et les devoirs de l'enfant",
    subject: 'Éducation civique et morale',
    gradeClass: 'CM2',
    chapter: 'Le citoyen et la loi',
    summary: "Connaître les droits fondamentaux de l'enfant et les devoirs qui les accompagnent.",
    content: `### 1. Les droits de l'enfant
La **Convention internationale des droits de l'enfant** (1989) protège chaque enfant. Il a droit :
- à la vie, à la santé et à une alimentation suffisante ;
- à l'éducation et à l'école ;
- à la protection contre les violences et le travail dangereux ;
- à un nom et à une nationalité ;
- au repos et au jeu.

### 2. Les devoirs
À chaque droit correspond un devoir :
- droit à l'école → devoir d'étudier et de respecter les enseignants ;
- droit à la protection → devoir de respecter les autres ;
- droit à la parole → devoir d'écouter.

### 3. À l'école et à la maison
Je respecte les règles de la classe, je prends soin du matériel, j'aide les plus jeunes et je dénonce les violences à un adulte de confiance.

### 4. Exercice
Cite deux droits de l'enfant et le devoir qui correspond à chacun.`,
    durationMinutes: 25,
    published: true,
  },
];
