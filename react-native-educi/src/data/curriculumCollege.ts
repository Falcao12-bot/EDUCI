import type { Lesson } from './types';

/**
 * Programme du collège ivoirien (6e → 3e), d'après les programmes
 * éducatifs officiels : mathématiques, français, histoire-géographie,
 * physique-chimie et SVT.
 */
export const COLLEGE_LESSONS: Lesson[] = [
  /* ------------------------------- 6e ------------------------------- */
  {
    id: 'les_cl_6e_math_01',
    title: 'Les nombres décimaux : comparer, ranger, calculer',
    subject: 'Mathématiques',
    gradeClass: '6e',
    chapter: 'Nombres et calculs',
    summary: 'Lire, écrire, comparer et ordonner les nombres décimaux.',
    content: `### 1. Écriture décimale
Un nombre décimal s'écrit avec une partie entière et une partie décimale séparées par une virgule.
Exemple : **12,45** → 12 unités, 4 dixièmes et 5 centièmes.

### 2. Comparer deux décimaux
1. On compare les parties entières.
2. Si elles sont égales, on compare chiffre par chiffre après la virgule.

Ainsi **7,4 > 7,35** car 4 dixièmes > 3 dixièmes.

### 3. Ranger dans l'ordre croissant
2,5 < 2,56 < 3,04 < 3,4

### 4. Calculs
- Addition : 4,25 + 3,6 = **7,85** (on aligne les virgules)
- Multiplication par 10, 100 : 3,7 × 100 = **370**
- Division par 10 : 45 ÷ 10 = **4,5**

### 5. Exercice
Range dans l'ordre croissant : 5,7 ; 5,07 ; 5,75 ; 5,007.
Réponse : **5,007 < 5,07 < 5,7 < 5,75**.`,
    durationMinutes: 40,
    published: true,
  },
  {
    id: 'les_cl_6e_fr_01',
    title: 'Le conte : structure et personnages',
    subject: 'Français',
    gradeClass: '6e',
    chapter: 'Le récit : le conte',
    summary: 'Reconnaître la structure du conte et le rôle des personnages.',
    content: `### 1. Qu'est-ce qu'un conte ?
Un conte est un récit court, souvent oral, qui se transmet de génération en génération. Il commence par une formule (« Il était une fois… ») et se termine par une morale.

### 2. La structure
1. **Situation initiale** : on présente le héros et son quotidien.
2. **Élément perturbateur** : un problème survient (manque, interdit, malheur).
3. **Péripéties** : le héros affronte des épreuves, souvent au nombre de trois.
4. **Dénouement** : le problème est résolu.
5. **Situation finale** : le héros est récompensé ou puni.

### 3. Les personnages
- Le **héros** : souvent petit, pauvre ou mal aimé (le cadet, l'orphelin).
- Les **adjuvants** : ils aident le héros (un vieillard, un animal).
- Les **opposants** : ils gênent le héros (le roi jaloux, la marâtre).

### 4. Les contes ivoiriens
Les contes d'**Anansi l'araignée** ou du lièvre rusé transmettent des leçons de sagesse. Ils utilisent des animaux pour représenter les qualités et les défauts humains.

### 5. Exercice
Choisis un conte que tu connais et identifie sa situation initiale, son élément perturbateur et sa morale.`,
    durationMinutes: 35,
    published: true,
  },
  {
    id: 'les_cl_6e_hg_01',
    title: 'La Préhistoire et la naissance de l\'écriture',
    subject: 'Histoire-Géographie',
    gradeClass: '6e',
    chapter: 'Les débuts de l\'humanité',
    summary: 'Comprendre les grandes étapes de la Préhistoire et l\'invention de l\'écriture.',
    content: `### 1. La Préhistoire
La Préhistoire commence avec les premiers hommes, il y a environ 3 millions d'années, et se termine avec l'invention de l'écriture, vers **3 300 avant Jésus-Christ**.

### 2. Les grandes étapes
- **Paléolithique** : les hommes sont nomades, chasseurs et cueilleurs. Ils taillent la pierre et maîtrisent le feu.
- **Néolithique** : ils deviennent sédentaires, cultivent la terre et élèvent des animaux. Ils polissent la pierre et fabriquent des poteries.

### 3. L'invention de l'écriture
L'écriture apparaît en **Mésopotamie** (écriture cunéiforme) puis en Égypte (hiéroglyphes). Elle sert d'abord à compter les récoltes et les troupeaux, puis à transmettre des lois et des récits.

### 4. En Afrique de l'Ouest
Des sites archéologiques témoignent d'une métallurgie ancienne du fer, notamment dans la région de la boucle du Niger.

### 5. À retenir
Préhistoire = avant l'écriture. Histoire = après l'écriture.`,
    durationMinutes: 35,
    published: true,
  },

  /* ------------------------------- 5e ------------------------------- */
  {
    id: 'les_cl_5e_math_01',
    title: 'Les nombres relatifs',
    subject: 'Mathématiques',
    gradeClass: '5e',
    chapter: 'Nombres et calculs',
    summary: 'Utiliser les nombres positifs et négatifs, additionner et soustraire.',
    content: `### 1. Définition
Un nombre relatif est un nombre précédé d'un signe **+** ou **−**.
- +5 (positif) et −5 (négatif) sont des nombres **opposés**.
- 0 est le seul nombre à la fois positif et négatif.

### 2. Utilisation concrète
- Température : −3 °C signifie 3 degrés en dessous de zéro.
- Argent : −2 000 F CFA signifie une dette de 2 000 F CFA.
- Altitude : −10 m signifie 10 m sous le niveau de la mer.

### 3. Addition
- Mêmes signes : on additionne les distances à zéro et on garde le signe.
  (−4) + (−6) = **−10**
- Signes contraires : on soustrait et on garde le signe du plus grand.
  (−7) + (+3) = **−4**

### 4. Soustraction
Soustraire un nombre revient à ajouter son opposé :
5 − (−3) = 5 + 3 = **8**

### 5. Exercice
Un plongeur descend de 8 m, remonte de 3 m, puis redescend de 5 m.
Sa position finale est −8 + 3 − 5 = **−10 m**.`,
    durationMinutes: 40,
    published: true,
  },
  {
    id: 'les_cl_5e_fr_01',
    title: 'Le récit et le roman d\'aventure',
    subject: 'Français',
    gradeClass: '5e',
    chapter: 'Le récit',
    summary: 'Étudier le point de vue narratif et le schéma du récit d\'aventure.',
    content: `### 1. Le narrateur
Le narrateur est celui qui raconte l'histoire.
- **Narrateur interne** : il dit « je » et participe aux événements.
- **Narrateur externe** : il raconte à la 3e personne, comme un témoin.
- **Narrateur omniscient** : il connaît les pensées de tous les personnages.

### 2. Le schéma du récit d'aventure
1. Le héros part de son quotidien.
2. Une mission ou un danger l'oblige à partir.
3. Il affronte des épreuves (naufrage, forêt, animaux sauvages).
4. Il est aidé par ses compagnons.
5. Il revient transformé et plus mûr.

### 3. Les temps du récit
- **Imparfait** : décrit le décor et les habitudes.
- **Passé simple** : raconte les actions de premier plan.

### 4. Exercice
Écris le début (5 lignes) d'un récit d'aventure qui se déroule dans la forêt de Taï. Utilise la 3e personne et alterne imparfait et passé simple.`,
    durationMinutes: 40,
    published: true,
  },
  {
    id: 'les_cl_5e_hg_01',
    title: 'Les grands empires de l\'Ouest africain',
    subject: 'Histoire-Géographie',
    gradeClass: '5e',
    chapter: 'L\'Afrique des Empires',
    summary: 'Découvrir les empires du Ghana, du Mali et du Songhaï et leur commerce Transsaharien.',
    content: `### 1. L'empire du Ghana (VIIIe – XIe siècle)
Situé entre les actuels Mali et Mauritanie, il prospère grâce au commerce de l'**or** et du **sel** avec les caravaniers arabes du Sahara. Sa capitale, Kumbi Saleh, est une grande ville marchande.

### 2. L'empire du Mali (XIIIe – XVe siècle)
Fondé par Soundiata Keïta après la bataille de Kirina (1235), il atteint son apogée sous **Kankou Moussa**, célèbre pour son pèlerinage à La Mecque et sa richesse. Tombouctou devient un grand centre intellectuel.

### 3. L'empire du Songhaï (XVe – XVIe siècle)
Sous **Sonni Ali Ber** puis Askia Mohamed, il s'étend autour du fleuve Niger. La ville de Gao est sa capitale. Il s'effondre après la bataille de Tondibi en 1591.

### 4. Le commerce transsaharien
Les caravanes échangent :
- sel du Sahara, dattes et chevaux vers le sud ;
- or, noix de kola et esclaves vers le nord.

### 5. À retenir
Ces empires ont diffusé l'**islam**, développé les villes et les écoles coraniques, et laissé un héritage culturel important.`,
    durationMinutes: 40,
    published: true,
    blocks: [
      { type: 'heading', text: 'Les trois grands empires de l\'Ouest africain', level: 1 },
      { type: 'paragraph', text: 'Du VIIIe au XVIe siècle, trois empires se succèdent dans la boucle du Niger et vivent du commerce transsaharien.' },
      {
        type: 'table',
        headers: ['Empire', 'Période', 'Capitale', 'Richesse'],
        rows: [
          ['Ghana', 'VIIIe – XIe s.', 'Kumbi Saleh', 'Or et sel'],
          ['Mali', 'XIIIe – XVe s.', 'Niani / Tombouctou', 'Or et commerce'],
          ['Songhaï', 'XVe – XVIe s.', 'Gao', 'Or, sel, kola'],
        ],
      },
      { type: 'figure', emoji: '🐫', label: 'Caravane transsaharienne', caption: 'Les caravanes reliaient les villes du Sahel au Maghreb.' },
      { type: 'note', variant: 'definition', text: 'Un empire est un vaste ensemble de territoires réunis sous un même souverain.' },
      { type: 'note', variant: 'tip', text: 'Retiens la chronologie dans l\'ordre : Ghana → Mali → Songhaï. C\'est la question la plus fréquente au BEPC.' },
    ],
  },

  /* ------------------------------- 4e ------------------------------- */
  {
    id: 'les_cl_4e_math_01',
    title: 'Le calcul littéral : développer et réduire',
    subject: 'Mathématiques',
    gradeClass: '4e',
    chapter: 'Calcul littéral',
    summary: 'Développer une expression, réduire et factoriser grâce à la distributivité.',
    content: `### 1. Que signifie « calcul littéral » ?
On utilise des **lettres** pour représenter des nombres inconnus ou variables.
Exemple : le périmètre d'un rectangle de longueur L et de largeur l s'écrit **P = 2 × (L + l)**.

### 2. La distributivité
Pour tous nombres a, b, c : **a × (b + c) = a × b + a × c**

Exemple : 3(x + 4) = 3x + 12.

### 3. Développer puis réduire
A = 2(x + 5) + 3(x − 1)
A = 2x + 10 + 3x − 3
A = **5x + 7**

### 4. Factoriser
Factoriser, c'est transformer une somme en produit :
6x + 9 = 3(2x + 3)

### 5. Les identités remarquables
- (a + b)² = a² + 2ab + b²
- (a − b)² = a² − 2ab + b²
- (a + b)(a − b) = a² − b²

### 6. Exercice
Développe et réduis : (x + 3)(x + 2).
Réponse : x² + 2x + 3x + 6 = **x² + 5x + 6**.`,
    durationMinutes: 45,
    published: true,
  },
  {
    id: 'les_cl_4e_fr_01',
    title: 'Le texte argumentatif',
    subject: 'Français',
    gradeClass: '4e',
    chapter: 'Argumenter et convaincre',
    summary: 'Construire une argumentation avec thèse, arguments et exemples.',
    content: `### 1. Les trois piliers de l'argumentation
- La **thèse** : l'idée que l'on défend.
- Les **arguments** : les raisons qui la justifient.
- Les **exemples** : les faits, chiffres ou situations qui illustrent l'argument.

### 2. La structure du texte
1. **Introduction** : on présente le thème et on annonce la thèse.
2. **Développement** : un paragraphe par argument, chaque argument suivi d'un exemple.
3. **Conclusion** : on rappelle la thèse et on ouvre le débat.

### 3. Les connecteurs logiques
- Pour ajouter : *d'abord, ensuite, enfin, de plus*
- Pour opposer : *cependant, en revanche, néanmoins*
- Pour conclure : *donc, par conséquent, en définitive*

### 4. Exemple de sujet
« Faut-il interdire les téléphones portables à l'école ? »
Argument : ils distraient les élèves pendant les cours.
Exemple : en Côte d'Ivoire, la loi encadre l'usage du téléphone dans les établissements.

### 5. Exercice
Rédige un paragraphe argumenté de 10 lignes pour défendre ou refuser la présence du téléphone au collège.`,
    durationMinutes: 40,
    published: true,
  },
  {
    id: 'les_cl_4e_hg_01',
    title: 'La colonisation de l\'Afrique de l\'Ouest et la Côte d\'Ivoire',
    subject: 'Histoire-Géographie',
    gradeClass: '4e',
    chapter: 'Le fait colonial',
    summary: 'Comprendre la conquête coloniale, ses motivations et ses conséquences en Côte d\'Ivoire.',
    content: `### 1. Le partage de l'Afrique
La **conférence de Berlin** (1884-1885) organise le partage du continent entre puissances européennes. La France obtient l'Afrique Occidentale Française (AOF), dont la Côte d'Ivoire fait partie.

### 2. La conquête de la Côte d'Ivoire
La conquête commence en 1880 et se heurte à une forte résistance :
- **Samory Touré** dans le Nord-Est ;
- **Agyeman Niamien** et d'autres chefs dans l'Est et le Sud.
La Côte d'Ivoire devient **colonie française en 1893**. Bingerville est d'abord capitale, puis Abidjan en 1934.

### 3. L'économie coloniale
- Cultures de rente obligatoires : **cacao, café, hévéa**.
- Travail forcé, impôt de capitation et cultures obligatoires.
- Construction du chemin de fer Abidjan-Niger.

### 4. Les conséquences
- Déstructuration des sociétés traditionnelles ;
- économie tournée vers l'exportation ;
- diffusion de la langue française et de l'école coloniale ;
- éveil des mouvements de revendication (syndicats, élites africaines).

### 5. À retenir
En 1946, la Côte d'Ivoire devient un **territoire d'outre-mer** ; la colonisation s'achève en 1960 avec l'indépendance.`,
    durationMinutes: 45,
    published: true,
  },

  /* ------------------------------- 3e ------------------------------- */
  {
    id: 'les_cl_3e_math_01',
    title: 'Le théorème de Thalès et sa réciproque',
    subject: 'Mathématiques',
    gradeClass: '3e',
    chapter: 'Géométrie plane',
    summary: 'Calculer des longueurs avec Thalès et démontrer que des droites sont parallèles.',
    content: `### 1. Configuration
Soit deux droites sécantes en A, avec M et B sur la première, N et C sur la seconde. Si les droites (MN) et (BC) sont **parallèles**, alors :
**AM / AB = AN / AC = MN / BC**

### 2. Application directe
On donne AM = 3 cm, AB = 9 cm et MN = 4 cm. Calculons BC.
3/9 = 4/BC → BC = (4 × 9) / 3 = **12 cm**.

### 3. Réciproque
Si les points sont alignés dans le même ordre et si **AM/AB = AN/AC**, alors les droites (MN) et (BC) sont parallèles.

### 4. Méthode de rédaction
1. Citer les alignements (A, M, B) et (A, N, C).
2. Citer les droites parallèles.
3. Appliquer le théorème et faire un produit en croix.
4. Conclure avec l'unité.

### 5. Attention
Le théorème de Thalès ne s'applique que si **(MN) et (BC) sont parallèles**. Ne pas confondre avec le théorème de Pythagore, qui concerne l'angle droit.

### 6. Exercice
Avec AM = 2 cm, AB = 6 cm et AC = 9 cm, calcule AN.
Réponse : AN = (2 × 9) / 6 = **3 cm**.`,
    durationMinutes: 45,
    published: true,
  },
  {
    id: 'les_cl_3e_pc_01',
    title: "La loi d'Ohm et les circuits électriques",
    subject: 'Physique-Chimie',
    gradeClass: '3e',
    chapter: 'Électricité',
    summary: 'Utiliser la loi d\'Ohm pour calculer tension, intensité et résistance.',
    content: `### 1. Les grandeurs électriques
- La **tension** U se mesure en volts (V) avec un voltmètre branché en dérivation.
- L'**intensité** I se mesure en ampères (A) avec un ampèremètre branché en série.
- La **résistance** R se mesure en ohms (Ω) avec un ohmmètre.

### 2. La loi d'Ohm
Pour un conducteur ohmique : **U = R × I**

On en déduit :
- R = U / I
- I = U / R

### 3. Exemple
Un conducteur de résistance R = 20 Ω est parcouru par un courant I = 0,25 A.
U = 20 × 0,25 = **5 V**.

### 4. Le circuit série
L'intensité est la même partout. Les tensions s'additionnent :
U = U₁ + U₂

### 5. Le circuit en dérivation
La tension est la même aux bornes de chaque branche. Les intensités s'additionnent :
I = I₁ + I₂

### 6. Sécurité
Un court-circuit fait circuler une intensité très forte : le **disjoncteur** et le **fusible** protègent l'installation.

### 7. Exercice
U = 12 V et I = 0,3 A. Calcule R.
Réponse : R = 12 / 0,3 = **40 Ω**.`,
    durationMinutes: 45,
    published: true,
  },
  {
    id: 'les_cl_3e_svt_01',
    title: 'La reproduction humaine et la santé',
    subject: 'SVT',
    gradeClass: '3e',
    chapter: 'Reproduction et éducation à la santé',
    summary: 'Connaître les organes reproducteurs, le cycle féminin et les moyens de protection.',
    content: `### 1. Les organes reproducteurs
- Chez l'**homme** : les testicules produisent les spermatozoïdes et l'hormone testostérone.
- Chez la **femme** : les ovaires produisent les ovules et les hormones œstrogène et progestérone. L'utérus accueille l'embryon.

### 2. Le cycle féminin
Il dure en moyenne **28 jours**. Il commence le premier jour des règles et se termine la veille des règles suivantes. L'**ovulation** se produit vers le 14e jour.

### 3. La fécondation
La fécondation a lieu dans le tiers supérieur de la trompe. Elle donne une cellule œuf qui se divise et s'implante dans l'utérus : c'est la **nidation**.

### 4. La grossesse
Elle dure environ 9 mois. Le placenta assure les échanges entre la mère et l'enfant.

### 5. Protéger sa santé
- Le préservatif protège des infections sexuellement transmissibles et des grossesses non désirées.
- Le dépistage du VIH est gratuit et confidentiel en Côte d'Ivoire.
- La consultation prénatale protège la mère et l'enfant.

### 6. À retenir
La reproduction est une fonction biologique : elle doit être abordée avec respect, information et protection.`,
    durationMinutes: 40,
    published: true,
  },
  {
    id: 'les_cl_3e_hg_01',
    title: 'La Côte d\'Ivoire de 1960 à nos jours',
    subject: 'Histoire-Géographie',
    gradeClass: '3e',
    chapter: 'L\'histoire récente de la Côte d\'Ivoire',
    summary: 'Suivre les grandes étapes de l\'indépendance à la reconstruction nationale.',
    content: `### 1. L'indépendance (1960)
La Côte d'Ivoire devient indépendante le **7 août 1960**. **Félix Houphouët-Boigny** en est le premier président. Il choisit une voie libérale et des liens forts avec la France.

### 2. Le « miracle ivoirien » (1960-1980)
L'économie repose sur le cacao, le café et le bois. La population augmente et les infrastructures se développent (écoles, routes, barrages).

### 3. La crise des années 1980-1990
La chute des cours du cacao et du café provoque une crise économique. En 1990, le multipartisme est rétabli.

### 4. Les crises politiques (1999-2011)
- Coup d'État de 1999 ;
- conflit armé de 2002 ;
- crise post-électorale de 2010-2011.

### 5. La reconstruction
Depuis 2011, le pays se reconstruit : réconciliation nationale, relance économique, modernisation des villes, développement de l'école obligatoire jusqu'à 16 ans.

### 6. Exercice de synthèse
Cite deux réussites et deux défis actuels de la Côte d'Ivoire.
Réussites : croissance économique, développement des infrastructures.
Défis : chômage des jeunes, inégalités entre les régions.`,
    durationMinutes: 45,
    published: true,
  },
];
