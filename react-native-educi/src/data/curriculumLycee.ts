import type { Lesson } from './types';

/**
 * Programme du lycée ivoirien (2nde, 1ère, Tle) : mathématiques, physique-chimie,
 * SVT, français, philosophie et histoire.
 */
export const LYCEE_LESSONS: Lesson[] = [
  /* ------------------------------ 2nde ------------------------------ */
  {
    id: 'les_ly_2nde_math_01',
    title: 'Généralités sur les fonctions',
    subject: 'Mathématiques',
    gradeClass: '2nde',
    chapter: 'Fonctions et variations',
    summary: 'Lire un ensemble de définition, calculer des images et étudier les variations.',
    content: `### 1. Notion de fonction
Une fonction f associe à chaque réel x d'un ensemble D un unique réel noté f(x).
D est le **domaine de définition** de f.

### 2. Image et antécédent
Si f(x) = 3x − 1 :
- l'image de 2 est f(2) = 3 × 2 − 1 = **5** ;
- l'antécédent de 8 vérifie 3x − 1 = 8, donc x = **3**.

### 3. Représentation graphique
La courbe représentative de f est l'ensemble des points de coordonnées (x ; f(x)).

### 4. Sens de variation
- f est **croissante** sur un intervalle I si pour tous a < b de I, f(a) ≤ f(b).
- f est **décroissante** si pour tous a < b de I, f(a) ≥ f(b).

### 5. Tableau de variations
On résume les variations dans un tableau où les flèches indiquent si la fonction monte ou descend, et où l'on place les extremums (maximum, minimum).

### 6. Exercice
Soit f(x) = x² − 4.
Calcule f(−3) et les antécédents de 0.
f(−3) = 9 − 4 = **5** ; x² − 4 = 0 donne x = **2** ou x = **−2**.`,
    durationMinutes: 50,
    published: true,
  },
  {
    id: 'les_ly_2nde_pc_01',
    title: 'Les solutions aqueuses et la concentration',
    subject: 'Physique-Chimie',
    gradeClass: '2nde',
    chapter: 'Chimie des solutions',
    summary: 'Préparer une solution et calculer une concentration molaire ou massique.',
    content: `### 1. Solution, soluté, solvant
Une solution est obtenue en dissolvant un **soluté** dans un **solvant**. Lorsque le solvant est l'eau, on parle de **solution aqueuse**.
Exemple : l'eau sucrée = sucre (soluté) + eau (solvant).

### 2. Concentration massique
**Cₘ = m / V**
- m : masse du soluté en grammes (g)
- V : volume de solution en litres (L)
- Cₘ : concentration en g/L

Exemple : 20 g de sel dans 2 L d'eau → Cₘ = 20/2 = **10 g/L**.

### 3. Concentration molaire
**C = n / V** avec n = m / M
- n : quantité de matière en moles (mol)
- M : masse molaire en g/mol
- C : concentration en mol/L

Exemple : 5,85 g de NaCl (M = 58,5 g/mol) dans 1 L :
n = 5,85/58,5 = 0,1 mol → C = **0,1 mol/L**.

### 4. Dissolution et dilution
- Dissoudre : ajouter du soluté au solvant.
- Diluer : ajouter de l'eau à une solution. La quantité de matière reste la même : **C₁V₁ = C₂V₂**.

### 5. Exercice
On dilue 10 mL d'une solution à 0,5 mol/L dans 100 mL d'eau.
C₂ = (0,5 × 10) / 100 = **0,05 mol/L**.`,
    durationMinutes: 50,
    published: true,
    blocks: [
      { type: 'heading', text: 'Concentration massique et concentration molaire', level: 1 },
      { type: 'formula', text: 'Cₘ = m / V          C = n / V          n = m / M' },
      {
        type: 'table',
        headers: ['Grandeur', 'Symbole', 'Unité'],
        rows: [
          ['Masse', 'm', 'gramme (g)'],
          ['Volume', 'V', 'litre (L)'],
          ['Quantité de matière', 'n', 'mole (mol)'],
          ['Concentration massique', 'Cₘ', 'g/L'],
          ['Concentration molaire', 'C', 'mol/L'],
        ],
      },
      { type: 'note', variant: 'warning', text: 'Toujours exprimer le volume en litres avant de calculer une concentration.' },
      { type: 'note', variant: 'tip', text: 'Lors d\'une dilution, la quantité de matière ne change pas : C₁V₁ = C₂V₂.' },
    ],
  },
  {
    id: 'les_ly_2nde_fr_01',
    title: 'Méthode du commentaire composé',
    subject: 'Français',
    gradeClass: '2nde',
    chapter: 'Techniques d\'expression écrite',
    summary: 'Analyser un texte littéraire et organiser un commentaire en trois parties.',
    content: `### 1. Objectif
Le commentaire composé explique **comment** un texte produit du sens et des effets sur le lecteur. Il ne raconte pas l'histoire.

### 2. Les étapes au brouillon
1. Lire le texte deux fois, repérer le genre, le thème et le ton.
2. Relever des citations précises (guillemets) avec les procédés : figures de style, temps verbaux, ponctuation, champs lexicaux.
3. Regrouper les remarques en **deux ou trois axes** de lecture.
4. Rédiger une introduction et une conclusion.

### 3. L'introduction
- Présenter l'auteur, l'œuvre et l'extrait.
- Dégager l'idée principale du texte.
- Annoncer les axes du commentaire.

### 4. Le développement
Chaque paragraphe suit le même schéma :
**idée → citation → procédé → interprétation**.

### 5. La conclusion
Elle résume les axes et propose un bilan sur l'écriture du texte.

### 6. Conseil
Évite la paraphrase (« l'auteur dit que… ») : cite le texte et explique l'effet produit par les mots choisis.`,
    durationMinutes: 45,
    published: true,
  },
  {
    id: 'les_ly_2nde_hg_01',
    title: 'La Première Guerre mondiale et ses conséquences',
    subject: 'Histoire-Géographie',
    gradeClass: '2nde',
    chapter: 'Guerres mondiales et paix fragile',
    summary: 'Comprendre les causes, le déroulement et les conséquences du premier conflit mondial.',
    content: `### 1. Les causes
- Rivalités économiques et coloniales entre puissances européennes.
- Course aux armements et systèmes d'alliances (Triple-Alliance / Triple-Entente).
- Attentat de Sarajevo (28 juin 1914).
- Nationalismes dans les Balkans.

### 2. Le déroulement
- **Guerre de mouvement** (1914) : invasion de la Belgique, bataille de la Marne.
- **Guerre de position** (1915-1917) : tranchées, Verdun, la Somme.
- **1917** : entrée en guerre des États-Unis et révolution russe.
- **Armistice** : 11 novembre 1918.

### 3. Une guerre totale
Mobilisation des soldats et des civils, économie tournée vers l'armement, propagande, génocide arménien.

### 4. Les conséquences
- Bilan humain : près de 10 millions de morts.
- Traité de Versailles (1919) : l'Allemagne jugée responsable.
- Disparition des empires austro-hongrois et ottoman.
- Création de la Société des Nations (SDN).

### 5. Conséquence pour l'Afrique
Les colonies africaines ont fourni des soldats et des matières premières. Les tirailleurs sénégalais ont combattu en Europe.`,
    durationMinutes: 50,
    published: true,
  },

  /* ------------------------------ 1ère ------------------------------ */
  {
    id: 'les_ly_1ere_math_01',
    title: 'Les suites arithmétiques',
    subject: 'Mathématiques',
    gradeClass: '1ère',
    chapter: 'Suites numériques',
    summary: 'Reconnaître, exprimer et sommer une suite arithmétique.',
    content: `### 1. Définition
Une suite (uₙ) est arithmétique de raison r si pour tout n :
**uₙ₊₁ = uₙ + r**

Exemple : 3 ; 7 ; 11 ; 15… est arithmétique de premier terme u₀ = 3 et de raison r = 4.

### 2. Terme général
**uₙ = u₀ + n × r**
Avec u₀ = 3 et r = 4 : u₅ = 3 + 5 × 4 = **23**.

### 3. Sens de variation
- r > 0 : la suite est croissante.
- r < 0 : la suite est décroissante.
- r = 0 : la suite est constante.

### 4. Somme des termes
**S = (nombre de termes) × (premier terme + dernier terme) / 2**

Somme des 6 premiers termes (3, 7, 11, 15, 19, 23) :
S = 6 × (3 + 23)/2 = **78**.

### 5. Application concrète
Un élève économise 2 500 F CFA la première semaine, puis 500 F CFA de plus chaque semaine : 2 500 ; 3 000 ; 3 500…
C'est une suite arithmétique de raison r = 500.

### 6. Exercice
u₀ = 12, r = −3. Calcule u₄.
u₄ = 12 + 4 × (−3) = **0**.`,
    durationMinutes: 50,
    published: true,
  },
  {
    id: 'les_ly_1ere_pc_01',
    title: 'Énergie mécanique et travail',
    subject: 'Physique-Chimie',
    gradeClass: '1ère',
    chapter: 'Travail et énergie',
    summary: 'Calculer le travail d\'une force et appliquer la conservation de l\'énergie mécanique.',
    content: `### 1. Le travail d'une force
Le travail d'une force constante F dont le point d'application se déplace de d s'écrit :
**W = F × d × cos α**
- W en joules (J), F en newtons (N), d en mètres (m), α angle entre la force et le déplacement.

### 2. Énergie cinétique
**Ec = ½ × m × v²**
Une voiture de 1 000 kg roulant à 20 m/s possède Ec = ½ × 1 000 × 400 = **200 000 J**.

### 3. Énergie potentielle de pesanteur
**Ep = m × g × h** avec g ≈ 9,8 N/kg.
Une masse de 2 kg à 5 m : Ep = 2 × 9,8 × 5 = **98 J**.

### 4. Énergie mécanique
**Em = Ec + Ep**

En l'absence de frottements, l'énergie mécanique se **conserve** : ce qui est perdu en énergie potentielle est gagné en énergie cinétique.

### 5. Exercice
Une balle de 0,2 kg tombe d'une hauteur de 10 m. Calcule sa vitesse au sol (g = 10 N/kg, sans frottement).
mgh = ½mv² → v = √(2gh) = √(2 × 10 × 10) = **14,1 m/s**.`,
    durationMinutes: 50,
    published: true,
  },
  {
    id: 'les_ly_1ere_svt_01',
    title: 'La transmission des caractères héréditaires',
    subject: 'SVT',
    gradeClass: '1ère',
    chapter: 'Génétique',
    summary: 'Comprendre les lois de Mendel, les croisements et la notion de lignée pure.',
    content: `### 1. Les caractères héréditaires
Un caractère héréditaire est transmis des parents aux enfants par les **gènes**, portés par les chromosomes. Chaque gène existe en deux versions : les **allèles**.

### 2. Les allèles dominants et récessifs
- Un allèle **dominant** s'exprime même s'il est présent une seule fois.
- Un allèle **récessif** ne s'exprime que s'il est présent deux fois.

On note A l'allèle dominant et a l'allèle récessif.

### 3. Génotype et phénotype
- **Génotype** : la composition génétique (AA, Aa ou aa).
- **Phénotype** : le caractère observable.

### 4. Premier croisement de Mendel
Deux lignées pures (AA × aa) donnent une génération F1 **100 % Aa**, donc 100 % de phénotype dominant.

### 5. Deuxième croisement
F1 × F1 (Aa × Aa) donne :
- 1/4 AA, 2/4 Aa, 1/4 aa
- soit **3/4 de phénotype dominant** et **1/4 de phénotype récessif**.

### 6. Exercice
Deux parents Aa et Aa ont un enfant. Quelle est la probabilité qu'il exprime le caractère récessif ?
Réponse : **1/4 soit 25 %**.`,
    durationMinutes: 50,
    published: true,
    blocks: [
      { type: 'heading', text: 'Le croisement Aa × Aa', level: 1 },
      { type: 'paragraph', text: 'Chaque parent transmet un seul allèle à son enfant. Les combinaisons possibles sont AA, Aa, aA et aa.' },
      {
        type: 'table',
        headers: ['', 'A (père)', 'a (père)'],
        rows: [
          ['A (mère)', 'AA', 'Aa'],
          ['a (mère)', 'Aa', 'aa'],
        ],
      },
      { type: 'note', variant: 'definition', text: 'Un allèle est dominant lorsqu\'il s\'exprime chez l\'hétérozygote Aa. L\'allèle a est récessif.' },
      { type: 'note', variant: 'example', text: 'Les groupes sanguins, la couleur des yeux ou la drépanocytose chez l\'enfant suivent cette logique de transmission.' },
    ],
  },
  {
    id: 'les_ly_1ere_hg_01',
    title: 'La décolonisation de l\'Afrique',
    subject: 'Histoire-Géographie',
    gradeClass: '1ère',
    chapter: 'Décolonisation et construction des États',
    summary: 'Analyser les causes, les formes et les étapes de l\'émancipation africaine.',
    content: `### 1. Les causes
- Rôle des élites africaines formées à l'école coloniale.
- Impact de la Seconde Guerre mondiale et de la Charte des Nations unies.
- Affaiblissement des puissances coloniales.
- Pression internationale et guerre froide.

### 2. Les étapes
- **1951** : indépendance de la Libye.
- **1956** : Maroc et Tunisie.
- **1957** : Ghana (Kwame Nkrumah).
- **1958** : la Guinée dit « non » au référendum et devient indépendante.
- **1960** : 17 pays africains deviennent indépendants, dont la Côte d'Ivoire (7 août).

### 3. Les formes d'accès à l'indépendance
- Négociée : Côte d'Ivoire, Sénégal, Nigeria.
- Par la lutte armée : Algérie, Angola, Mozambique, Guinée-Bissau.

### 4. Les défis des nouveaux États
- Construction de l'unité nationale dans des frontières héritées de la colonisation.
- Développement économique et alphabétisation.
- Création de l'OUA en 1963, remplacée par l'Union africaine en 2002.

### 5. À retenir
L'indépendance politique n'a pas mis fin à la dépendance économique : c'est le défi du **néocolonialisme**.`,
    durationMinutes: 50,
    published: true,
  },
  {
    id: 'les_ly_1ere_fr_01',
    title: 'Méthode de la dissertation littéraire',
    subject: 'Français',
    gradeClass: '1ère',
    chapter: 'Techniques d\'expression écrite',
    summary: 'Construire une problématique et un plan en trois parties pour la dissertation.',
    content: `### 1. Comprendre le sujet
Un sujet de dissertation comporte généralement une **citation** et une **consigne**. Repère :
- le thème (la littérature, le roman, le théâtre…) ;
- la thèse défendue par la citation ;
- le mot de consigne : *discutez*, *pensez-vous que*, *dans quelle mesure*.

### 2. Formuler une problématique
La problématique transforme le sujet en question à résoudre.
Exemple : « Le roman doit-il seulement divertir le lecteur ? »
Problématique : « La fonction du roman se limite-t-elle au divertissement ou peut-il aussi instruire et faire réfléchir ? »

### 3. Construire le plan
Plan dialectique en trois parties :
1. **Thèse** : oui, le roman divertit (intrigue, évasion, émotion).
2. **Antithèse** : mais il instruit et dénonce (réalisme, satire, engagement).
3. **Synthèse** : le grand roman unit plaisir et réflexion.

### 4. L'introduction
Amorce → présentation de la citation → problématique → annonce du plan.

### 5. Le développement
Chaque sous-partie : idée → argument → exemple littéraire précis (titre, auteur, personnage).

### 6. La conclusion
Bilan des trois parties puis ouverture.`,
    durationMinutes: 45,
    published: true,
  },

  /* ------------------------------- Tle ------------------------------ */
  {
    id: 'les_ly_tle_math_01',
    title: 'Dérivation et étude de fonction',
    subject: 'Mathématiques',
    gradeClass: 'Tle',
    chapter: 'Analyse',
    summary: 'Calculer une dérivée et dresser le tableau de variations d\'une fonction.',
    content: `### 1. Nombre dérivé
Le nombre dérivé f'(a) est le coefficient directeur de la tangente à la courbe au point d'abscisse a :
**f'(a) = lim (f(a + h) − f(a)) / h quand h → 0**

### 2. Dérivées usuelles
- (xⁿ)' = n·xⁿ⁻¹
- (√x)' = 1/(2√x)
- (1/x)' = −1/x²
- (eˣ)' = eˣ
- (ln x)' = 1/x

### 3. Opérations
- (u + v)' = u' + v'
- (uv)' = u'v + uv'
- (u/v)' = (u'v − uv')/v²

### 4. Lien avec les variations
- Si f' > 0 sur un intervalle, f est **croissante**.
- Si f' < 0, f est **décroissante**.
- Si f' s'annule en changeant de signe en a, f admet un **extremum** en a.

### 5. Méthode d'étude
1. Domaine de définition.
2. Calcul de f'.
3. Signe de f'.
4. Tableau de variations.
5. Limites et asymptotes éventuelles.
6. Tracé de la courbe.

### 6. Exercice
f(x) = x³ − 3x. f'(x) = 3x² − 3 = 3(x − 1)(x + 1).
f' > 0 sur ]−∞ ; −1[ et ]1 ; +∞[, f' < 0 sur ]−1 ; 1[.
Maximum local en x = −1, minimum local en x = 1.`,
    durationMinutes: 55,
    published: true,
  },
  {
    id: 'les_ly_tle_pc_01',
    title: 'Acides, bases et dosage acido-basique',
    subject: 'Physique-Chimie',
    gradeClass: 'Tle',
    chapter: 'Chimie des solutions',
    summary: 'Calculer un pH, identifier un acide fort ou faible et réaliser un dosage.',
    content: `### 1. Acides et bases
- Un **acide** libère des ions H₃O⁺ dans l'eau.
- Une **base** libère des ions OH⁻ dans l'eau.
- Un acide fort est totalement dissocié ; un acide faible l'est partiellement.

### 2. Le pH
**pH = −log[H₃O⁺]** et **[H₃O⁺] = 10^(−pH)**
À 25 °C : pH < 7 → acide ; pH = 7 → neutre ; pH > 7 → basique.

### 3. Produit ionique de l'eau
**Ke = [H₃O⁺][OH⁻] = 10⁻¹⁴** à 25 °C.

### 4. Dosage acido-basique
À l'équivalence, les quantités de matière sont dans les proportions stoechiométriques :
**CₐVₐ = C_bV_b** (pour un monoacide et une monobase).

### 5. Exemple
On dose 20 mL d'acide chlorhydrique par une solution de soude à 0,1 mol/L. L'équivalence est atteinte pour Vb = 15 mL.
Ca = (0,1 × 15)/20 = **0,075 mol/L**.

### 6. Indicateurs colorés
L'hélianthine vire du rouge au jaune (pH 3,1-4,4), le bleu de bromothymol du jaune au bleu (pH 6,0-7,6), la phénolphtaléine de l'incolore au rose (pH 8,2-10).

### 7. Exercice
Une solution a [H₃O⁺] = 10⁻³ mol/L. Son pH est **3** : elle est acide.`,
    durationMinutes: 55,
    published: true,
    blocks: [
      { type: 'heading', text: 'Acides, bases et pH', level: 1 },
      { type: 'formula', text: 'pH = −log[H₃O⁺]          [H₃O⁺] = 10^(−pH)          Ke = 10⁻¹⁴' },
      {
        type: 'table',
        headers: ['Solution', 'pH', 'Nature'],
        rows: [
          ['Jus de citron', '2,2', 'Acide'],
          ['Eau pure', '7,0', 'Neutre'],
          ['Eau de savon', '9,5', 'Basique'],
          ['Eau de javel', '12,0', 'Très basique'],
        ],
      },
      { type: 'note', variant: 'warning', text: 'Le pH n\'a de sens qu\'à une température donnée : Ke dépend de la température.' },
      { type: 'note', variant: 'tip', text: 'À l\'équivalence d\'un dosage, n(acide) = n(base) si le rapport stoechiométrique est 1:1.' },
    ],
  },
  {
    id: 'les_ly_tle_philo_01',
    title: 'La conscience : méthode de dissertation philosophique',
    subject: 'Philosophie',
    gradeClass: 'Tle',
    chapter: 'Le sujet et la conscience',
    summary: 'Problématiser la notion de conscience et construire une dissertation en trois temps.',
    content: `### 1. La notion
La **conscience** est la connaissance immédiate que le sujet a de lui-même et du monde. On distingue la conscience spontanée (percevoir) et la conscience réfléchie (penser sa propre pensée).

### 2. Les auteurs à connaître
- **Descartes** : le *cogito* — « Je pense donc je suis ». La conscience est le fondement de la certitude.
- **Locke** : la conscience fonde l'identité personnelle.
- **Freud** : l'inconscient montre que le sujet n'est pas totalement transparent à lui-même.
- **Sartre** : la conscience est toujours conscience de quelque chose (intentionnalité).

### 3. Problématiser
Un sujet comme « Suis-je ce que j'ai conscience d'être ? » pose le problème de la transparence du sujet à lui-même.

### 4. Plan possible
1. **Thèse** : oui, la conscience révèle le sujet à lui-même (Descartes).
2. **Antithèse** : mais l'inconscient freudien limite cette transparence.
3. **Synthèse** : la conscience n'est pas donnée mais à conquérir par le travail de la réflexion.

### 5. Méthode
- Introduction : définition des termes → problématique → annonce du plan.
- Développement : chaque partie avec argument + référence philosophique.
- Conclusion : bilan et réponse claire à la problématique.

### 6. Conseil
Ne raconte pas la vie des philosophes : utilise leurs idées pour répondre à la question posée.`,
    durationMinutes: 55,
    published: true,
  },
  {
    id: 'les_ly_tle_hg_01',
    title: 'La Côte d\'Ivoire et l\'Afrique contemporaine',
    subject: 'Histoire-Géographie',
    gradeClass: 'Tle',
    chapter: 'Le monde depuis 1945',
    summary: 'Analyser l\'insertion de la Côte d\'Ivoire dans les grands enjeux africains et mondiaux.',
    content: `### 1. Les organisations régionales
- **CEDEAO** (1975) : communauté économique des États de l'Afrique de l'Ouest, siège à Abuja.
- **UEMOA** (1994) : union économique et monétaire, siège à Ouagadougou, monnaie : le franc CFA.
- **Union africaine** (2002) : succède à l'OUA, siège à Addis-Abeba.

### 2. Les enjeux de développement
- Diversifier l'économie au-delà du cacao et du café.
- Développer l'industrie et les services.
- Réduire le chômage des jeunes et l'analphabétisme.
- Moderniser les infrastructures et l'énergie.

### 3. Les défis sécuritaires
Le Sahel connaît des attaques terroristes. La Côte d'Ivoire participe aux forces conjointes (Accra Initiative) et renforce la surveillance de ses frontières nord.

### 4. Les défis environnementaux
Déforestation, érosion côtière à Abidjan, gestion des déchets, changement climatique et agriculture durable.

### 5. Le rôle international
La Côte d'Ivoire est un acteur diplomatique majeur : elle accueille des institutions internationales (BID à Abidjan) et participe aux missions de maintien de la paix.

### 6. Exercice de synthèse
« La Côte d'Ivoire est un pays émergent. » Discutez cette affirmation en montrant les atouts et les limites.`,
    durationMinutes: 55,
    published: true,
  },
  {
    id: 'les_ly_tle_svt_01',
    title: 'Brassage génétique et diversité des êtres vivants',
    subject: 'SVT',
    gradeClass: 'Tle',
    chapter: 'Génétique et évolution',
    summary: 'Expliquer la méiose, la fécondation et l\'origine de la diversité génétique.',
    content: `### 1. La méiose
La méiose est une division cellulaire qui produit **quatre cellules haploïdes** (n chromosomes) à partir d'une cellule diploïde (2n). Elle comporte deux divisions successives : réductionnelle puis équationnelle.

### 2. Les sources de diversité
- **Brassage interchromosomique** : répartition aléatoire des chromosomes homologues en anaphase I.
- **Brassage intrachromosomique** : crossing-over en prophase I.
- **Fécondation** : rencontre aléatoire des gamètes.

### 3. Conséquence
Pour l'espèce humaine (n = 23), le nombre de combinaisons chromosomiques possibles par gamète est 2²³, soit plus de 8 millions : chaque individu est génétiquement unique.

### 4. Anomalies chromosomiques
Une mauvaise séparation des chromosomes (non-disjonction) peut provoquer la trisomie 21.

### 5. Application : l'hérédité des maladies
La drépanocytose est une maladie génétique récessive : elle ne se déclare que si l'enfant reçoit l'allèle muté des deux parents.

### 6. Exercice
Explique pourquoi deux frères non jumeaux ne sont jamais génétiquement identiques.
Réponse : brassages inter et intrachromosomique + fécondation aléatoire.`,
    durationMinutes: 55,
    published: true,
  },
];
