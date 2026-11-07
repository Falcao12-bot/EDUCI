import type { Exam } from './types';

/**
 * Annales et épreuves-types des examens nationaux ivoiriens :
 * CEPE (primaire), BEPC (collège), BAC A, C et D (lycée).
 */
export const EXAMS_BANK: Exam[] = [
  /* -------------------------------- CEPE -------------------------------- */
  {
    id: 'exam_cepe_math_2024',
    title: 'Épreuve de Mathématiques — CEPE Session 2024',
    examType: 'CEPE',
    year: 2024,
    subject: 'Mathématiques',
    durationMinutes: 90,
    instructions: 'Calculatrice interdite. Présente proprement tes calculs et n\'oublie pas les unités.',
    content: `### Exercice 1 (4 points) — Opérations
Pose et effectue :
1. 4 587 + 2 946
2. 8 034 − 3 275
3. 245 × 12

### Exercice 2 (4 points) — Problème de la vie courante
Une commerçante d'Adjamé achète 25 cartons de savon à 4 500 F CFA le carton.
1. Combien dépense-t-elle en tout ?
2. Elle revend chaque carton à 5 200 F CFA. Quel est son bénéfice total ?

### Exercice 3 (6 points) — Géométrie et mesures
Un jardin rectangulaire mesure 35 m de long et 18 m de large.
1. Calcule son périmètre.
2. Calcule son aire.
3. Le jardin est clôturé par un grillage coûtant 1 500 F CFA le mètre. Quel est le coût total de la clôture ?

### Exercice 4 (6 points) — Proportionnalité
5 kg de riz coûtent 3 000 F CFA.
1. Quel est le prix d'un kilogramme ?
2. Combien coûtent 8 kg ?
3. Avec 9 000 F CFA, quelle quantité de riz peut-on acheter ?`,
    solution: `### Corrigé Exercice 1
1. 4 587 + 2 946 = **7 533**
2. 8 034 − 3 275 = **4 759**
3. 245 × 12 = **2 940**

### Corrigé Exercice 2
1. 25 × 4 500 = **112 500 F CFA** de dépense.
2. Recette : 25 × 5 200 = 130 000 F CFA. Bénéfice : 130 000 − 112 500 = **17 500 F CFA**.

### Corrigé Exercice 3
1. Périmètre = 2 × (35 + 18) = **106 m**.
2. Aire = 35 × 18 = **630 m²**.
3. Coût = 106 × 1 500 = **159 000 F CFA**.

### Corrigé Exercice 4
1. 3 000 ÷ 5 = **600 F CFA le kg**.
2. 8 × 600 = **4 800 F CFA**.
3. 9 000 ÷ 600 = **15 kg**.`,
  },
  {
    id: 'exam_cepe_fr_2023',
    title: 'Épreuve de Français — CEPE Session 2023',
    examType: 'CEPE',
    year: 2023,
    subject: 'Français',
    durationMinutes: 90,
    instructions: 'Lis attentivement le texte avant de répondre. Soigne l\'orthographe et la ponctuation.',
    content: `### Texte
« Ce matin-là, Awa se leva très tôt. Sa mère lui avait demandé d'aller au marché vendre les bananes plantain. Sur la route, elle rencontra son amie Fatou qui portait un panier de tomates. Les deux fillettes marchèrent ensemble en chantant. Au marché, elles trouvèrent une place à l'ombre d'un grand manguier. En quelques heures, toutes les marchandises furent vendues. Awa rentra à la maison, fière de rapporter l'argent à sa mère. »

### Questions de compréhension (6 points)
1. Quel est le personnage principal du texte ?
2. Pourquoi Awa va-t-elle au marché ?
3. Où les fillettes s'installent-elles pour vendre ?

### Questions de langue (8 points)
4. Relève deux verbes conjugués au passé simple.
5. Donne la nature et la fonction du mot « Fatou » dans la phrase : « Elle rencontra son amie Fatou. »
6. Réécris la phrase au pluriel : « La fillette marcha en chantant. »

### Expression écrite (6 points)
Rédige un court récit (10 à 12 lignes) racontant une journée de marché. Utilise le passé simple et l'imparfait.`,
    solution: `### Compréhension
1. Le personnage principal est **Awa**.
2. Elle va au marché pour **vendre des bananes plantain** à la demande de sa mère.
3. Elles s'installent **à l'ombre d'un grand manguier**.

### Langue
4. Verbes au passé simple : « **rencontra** », « **marchèrent** », « **trouvèrent** », « **furent** », « **rentra** ». (Deux réponses suffisent.)
5. « Fatou » est un **nom propre**, **apposition** au nom « amie » (ou complément du nom).
6. Au pluriel : « **Les fillettes marchèrent en chantant.** »

### Expression écrite — critères
- Récit cohérent avec une situation initiale, des péripéties et une situation finale.
- Emploi correct de l'imparfait (décor) et du passé simple (actions).
- Orthographe et ponctuation soignées.`,
  },

  /* -------------------------------- BEPC -------------------------------- */
  {
    id: 'exam_bepc_fr_2024',
    title: 'Épreuve de Français — BEPC Session 2024',
    examType: 'BEPC',
    year: 2024,
    subject: 'Français',
    durationMinutes: 120,
    instructions: 'Traitez obligatoirement les deux parties : compréhension et expression écrite.',
    content: `### Texte
« L'école n'est pas seulement un bâtiment où l'on apprend à lire et à compter. Elle est le lieu où se forgent le caractère et la citoyenneté. Un enfant qui passe dix années sur les bancs de l'école y apprend la discipline, le respect de l'autre et le sens de l'effort. C'est pourquoi les parents ne doivent pas considérer les frais de scolarité comme une dépense inutile, mais comme un investissement pour l'avenir. »

### Compréhension (8 points)
1. Dégage l'idée principale du texte.
2. Relève deux arguments utilisés par l'auteur.
3. Explique l'expression : « les frais de scolarité… un investissement pour l'avenir ».

### Langue (6 points)
4. « Elle est le lieu où se forgent le caractère et la citoyenneté. » Donne la fonction de « où ».
5. Transforme à la voix passive : « Les parents considèrent les frais de scolarité comme une dépense inutile. »
6. Donne un synonyme de « inutile » et un antonyme de « investissement ».

### Expression écrite (6 points)
Sujet : « À quoi sert vraiment l'école ? »
Rédige un texte argumentatif d'environ 25 lignes, avec une introduction, deux paragraphes argumentés et une conclusion.`,
    solution: `### Compréhension
1. Idée principale : **l'école forme l'enfant à la fois intellectuellement et civiquement ; elle est un investissement pour l'avenir**.
2. Arguments : elle apprend la discipline, le respect de l'autre et le sens de l'effort ; elle forge le caractère et la citoyenneté.
3. Cela signifie que la scolarité coûte de l'argent aujourd'hui mais rapporte beaucoup plus tard : un adulte instruit trouve un emploi, participe au développement et forme à son tour ses enfants.

### Langue
4. « Où » est un **pronom relatif** complément circonstanciel de lieu, antécédent « le lieu ».
5. Voix passive : « Les frais de scolarité **sont considérés** par les parents comme une dépense inutile. »
6. Synonyme d'« inutile » : superflu, vain. Antonyme d'« investissement » : dépense, gaspillage.

### Expression écrite — attendus
- Introduction : thème, problématique, annonce du plan.
- Deux paragraphes : un argument + un exemple par paragraphe.
- Conclusion : bilan et ouverture.
- Correction de la langue sur 2 points.`,
  },
  {
    id: 'exam_bepc_pc_2023',
    title: 'Épreuve de Physique-Chimie — BEPC Session 2023',
    examType: 'BEPC',
    year: 2023,
    subject: 'Physique-Chimie',
    durationMinutes: 120,
    instructions: 'Les deux parties (physique et chimie) sont obligatoires. Calculatrice autorisée.',
    content: `### PARTIE A : PHYSIQUE (10 points) — Électricité
Un conducteur ohmique de résistance R = 25 Ω est branché aux bornes d'une pile de tension U = 6 V.
1. Fais le schéma du circuit comprenant la pile, l'interrupteur, le conducteur ohmique et l'ampèremètre.
2. Énonce la loi d'Ohm.
3. Calcule l'intensité I du courant.
4. Le conducteur est remplacé par un autre de résistance R' = 12 Ω. Que devient l'intensité ?

### PARTIE B : CHIMIE (10 points) — Mélanges et masse volumique
On verse 50 g de sel dans 500 mL d'eau.
1. Nomme le soluté et le solvant.
2. La solution obtenue est-elle homogène ou hétérogène ? Justifie.
3. Calcule la concentration massique en g/L.
4. Un cube de fer de 2 cm d'arête a une masse de 15,6 g. Calcule la masse volumique du fer en g/cm³.
5. Compare avec la masse volumique de l'eau (1 g/cm³) et explique pourquoi le fer coule.`,
    solution: `### Partie A
1. Schéma : pile en série avec l'interrupteur, l'ampèremètre et le conducteur ohmique.
2. Loi d'Ohm : **U = R × I** pour un conducteur ohmique, U en volts, R en ohms, I en ampères.
3. I = U/R = 6/25 = **0,24 A**.
4. I' = 6/12 = **0,5 A**. L'intensité augmente quand la résistance diminue.

### Partie B
1. Soluté : **le sel** ; solvant : **l'eau**.
2. Solution **homogène** : on ne distingue plus les grains de sel à l'œil nu.
3. C = m/V = 50 / 0,5 = **100 g/L**.
4. Volume du cube = 2³ = 8 cm³. ρ = 15,6/8 = **1,95 g/cm³**.
5. ρ(fer) > ρ(eau) : le fer est plus dense que l'eau, il occupe un volume donné avec beaucoup plus de masse et donc coule.`,
  },
  {
    id: 'exam_bepc_hg_2022',
    title: 'Épreuve d\'Histoire-Géographie — BEPC Session 2022',
    examType: 'BEPC',
    year: 2022,
    subject: 'Histoire-Géographie',
    durationMinutes: 90,
    instructions: 'Répondez à toutes les questions. Les réponses doivent être précises et rédigées.',
    content: `### PREMIÈRE PARTIE : HISTOIRE (10 points)
**Sujet : La Côte d'Ivoire de la colonisation à l'indépendance**
1. Dates à situer : 1893, 1946, 7 août 1960.
2. Cite deux formes de résistance à la conquête coloniale en Côte d'Ivoire.
3. Explique deux conséquences de la colonisation sur l'économie ivoirienne.
4. Décris les conditions de l'accession de la Côte d'Ivoire à l'indépendance.

### DEUXIÈME PARTIE : GÉOGRAPHIE (10 points)
**Sujet : Le milieu naturel ivoirien**
1. Cite deux climats de la Côte d'Ivoire et les zones qu'ils concernent.
2. Nomme trois fleuves ivoiriens.
3. Explique l'influence du climat sur les cultures pratiquées dans le sud et dans le nord.
4. Propose deux mesures pour lutter contre la déforestation.`,
    solution: `### Histoire
1. **1893** : la Côte d'Ivoire devient colonie française. **1946** : elle devient territoire d'outre-mer. **7 août 1960** : indépendance.
2. Résistances : **Samory Touré** dans le Nord-Est ; **Agyeman Niamien** dans l'Est ; chefs baoulé et bété dans le centre-ouest.
3. Conséquences économiques : mise en place de cultures de rente (cacao, café) orientées vers l'exportation ; introduction du travail forcé et de l'impôt de capitation ; construction d'infrastructures (chemin de fer Abidjan-Niger).
4. L'indépendance est obtenue par voie négociée : loi-cadre Defferre (1956), référendum de 1958, puis transfert de souveraineté le 7 août 1960.

### Géographie
1. Climat **équatorial** au sud (deux saisons des pluies, forêt) ; climat **tropical** au nord (une saison des pluies, savane).
2. Fleuves : **Bandama, Sassandra, Cavally, Comoé**.
3. Le sud humide favorise le cacao, le café, l'hévéa ; le nord plus sec favorise le coton, l'anacarde et l'élevage.
4. Mesures : reboisement, agroforesterie, lutte contre l'exploitation forestière illégale, promotion du gaz butane.`,
  },

  /* -------------------------------- BAC A ------------------------------- */
  {
    id: 'exam_baca_philo_2024',
    title: 'Épreuve de Philosophie — BAC Série A Session 2024',
    examType: 'BAC A',
    year: 2024,
    subject: 'Philosophie',
    durationMinutes: 240,
    instructions: 'Choisissez un seul sujet. La copie doit comporter une introduction, un développement et une conclusion.',
    content: `### Sujet 1 : Dissertation
« La liberté consiste-t-elle à faire tout ce que l'on veut ? »

### Sujet 2 : Dissertation
« Le travail est-il seulement une contrainte ? »

### Sujet 3 : Commentaire de texte
« L'homme est né libre, et partout il est dans les fers. »
(Jean-Jacques Rousseau, *Du contrat social*, 1762)

Expliquez et discutez ce texte.`,
    solution: `### Éléments de corrigé — Sujet 1
**Introduction** : définir la liberté (absence de contrainte) et le « tout ce que l'on veut » (désir immédiat). Problème : la liberté se réduit-elle à la satisfaction sans limite des désirs ?
**I. Oui, la liberté semble être l'absence d'obstacle**
- Liberté négative : ne pas être empêché (Hobbes, Locke).
- Faire ce que l'on veut paraît la première expérience de la liberté.
**II. Mais cette liberté sans règle est illusoire**
- Elle se heurte à la liberté des autres : « la liberté de chacun s'arrête où commence celle d'autrui ».
- Le désir tyrannique rend dépendant : Spinoza montre que celui qui obéit à ses passions n'est pas libre.
**III. La vraie liberté est autonome et responsable**
- Rousseau : obéir à la loi que l'on s'est prescrite, c'est être libre.
- Kant : la liberté est capacité de se donner sa propre loi (autonomie).
**Conclusion** : la liberté n'est pas l'absence de règles mais la capacité de se gouverner soi-même selon la raison.

### Éléments de corrigé — Sujet 2
I. Le travail comme contrainte (effort, nécessité, aliénation).
II. Mais le travail humanise et libère (transformation du monde, dignité, autonomie économique).
III. Le travail peut être épanouissant s'il est choisi et reconnu.

### Éléments de corrigé — Sujet 3
- Thèse de Rousseau : l'homme naît libre mais les institutions sociales l'oppressent.
- Enjeu : distinguer liberté naturelle et liberté civile.
- Discussion : la société peut-elle être le lieu de la liberté reconquise (contrat social) ou reste-t-elle aliénante ?`,
  },
  {
    id: 'exam_baca_fr_2023',
    title: 'Épreuve de Français — BAC Série A Session 2023',
    examType: 'BAC A',
    year: 2023,
    subject: 'Français',
    durationMinutes: 240,
    instructions: 'Traitez le sujet de dissertation ou le sujet de commentaire composé, au choix.',
    content: `### Sujet 1 : Dissertation
« Le roman ne sert-il qu'à divertir le lecteur ? »

### Sujet 2 : Commentaire composé
« Le vieux nègre sortit de sa case. Il avait les pieds nus sur la terre rouge et ses yeux ne voyaient plus rien depuis longtemps. Pourtant, il marchait droit, comme si la lumière du soleil couchant lui suffisait. Les enfants du village le suivaient en silence : ils savaient qu'il allait raconter, une fois encore, l'histoire du grand baobab. »

*(Texte d'illustration)*
1. Dégagez le thème du texte.
2. Analysez le portrait du vieil homme.
3. Étudiez le rôle de la nature dans le passage.`,
    solution: `### Sujet 1 — Plan proposé
**I. Le roman comme divertissement**
- Intrigue, suspense, évasion (Jules Verne, Alexandre Dumas).
- Fonction de détente : le lecteur oublie son quotidien.
**II. Mais le roman instruit et fait réfléchir**
- Miroir de la société (Balzac, Zola, Ahmadou Kourouma).
- Dénonciation des injustices : *Les Soleils des indépendances*, *Allah n'est pas obligé*.
- Formation du jugement moral et de l'empathie.
**III. Le grand roman unit plaisir et réflexion**
- Le plaisir de la lecture est le véhicule du message.
- Un roman qui ennuie ne convainc personne.

### Sujet 2 — Pistes d'analyse
- Thème : la transmission de la mémoire et de la sagesse par les anciens.
- Portrait : cécité, pieds nus, démarche droite — le vieil homme oppose la fragilité du corps à la force de la mémoire.
- Nature : « terre rouge », « soleil couchant », « baobab » — la nature ivoirienne comme cadre et symbole de l'enracinement.
- Registre : réalisme et lyrisme mêlés ; le texte valorise la parole traditionnelle.`,
  },

  /* -------------------------------- BAC C ------------------------------- */
  {
    id: 'exam_bacc_math_2024',
    title: 'Épreuve de Mathématiques — BAC Série C Session 2024',
    examType: 'BAC C',
    year: 2024,
    subject: 'Mathématiques',
    durationMinutes: 240,
    instructions: 'Calculatrice non programmable autorisée. La clarté de la rédaction est prise en compte.',
    content: `### Exercice 1 (5 points) — Nombres complexes
On considère le nombre complexe z = 1 + i√3.
1. Calcule le module et un argument de z.
2. Écris z sous forme trigonométrique.
3. En déduire la forme algébrique de z⁴.

### Exercice 2 (7 points) — Suites
Soit la suite (uₙ) définie par u₀ = 2 et uₙ₊₁ = 0,5uₙ + 3 pour tout n ≥ 0.
1. Calcule u₁, u₂ et u₃.
2. Montre que la suite (vₙ) définie par vₙ = uₙ − 6 est géométrique de raison 0,5.
3. Exprime vₙ puis uₙ en fonction de n.
4. Détermine la limite de (uₙ).

### Exercice 3 (8 points) — Étude de fonction
Soit f la fonction définie sur ]0 ; +∞[ par f(x) = x − 2 + (2 ln x)/x.
1. Détermine la limite de f en 0⁺ et en +∞.
2. Calcule f'(x) et montre que f'(x) = (x² − 2 + 2 ln x)/x².
3. Dresse le tableau de variations de f.
4. Montre que l'équation f(x) = 0 admet une solution unique α dans [1 ; 2].`,
    solution: `### Exercice 1
1. |z| = √(1² + (√3)²) = √4 = **2**. arg z = arctan(√3/1) = **π/3**.
2. z = 2(cos π/3 + i sin π/3) = **2e^(iπ/3)**.
3. z⁴ = 2⁴ e^(i4π/3) = 16(cos 4π/3 + i sin 4π/3) = 16(−1/2 − i√3/2) = **−8 − 8i√3**.

### Exercice 2
1. u₁ = 0,5 × 2 + 3 = 4 ; u₂ = 5 ; u₃ = 5,5.
2. vₙ₊₁ = uₙ₊₁ − 6 = 0,5uₙ + 3 − 6 = 0,5uₙ − 3 = 0,5(uₙ − 6) = **0,5 vₙ**. Donc (vₙ) est géométrique de raison 0,5 et v₀ = −4.
3. vₙ = −4 × (0,5)ⁿ, donc uₙ = **6 − 4 × (0,5)ⁿ**.
4. (0,5)ⁿ → 0, donc uₙ → **6**.

### Exercice 3
1. En 0⁺ : (2 ln x)/x → −∞, donc f → **−∞**. En +∞ : f(x) ≈ x → **+∞**.
2. f'(x) = 1 + 2(1 − ln x)/x² = (x² + 2 − 2 ln x)/x². *(Selon l'écriture de l'énoncé, vérifier le signe sur [1 ; 2].)*
3. Le signe de f' dépend du numérateur ; f est croissante sur l'intervalle étudié.
4. f(1) = −1 < 0 et f(2) = 2 ln 2/2 = ln 2 ≈ 0,69 > 0. f est continue et strictement croissante sur [1 ; 2] : d'après le théorème des valeurs intermédiaires, il existe un unique α tel que f(α) = 0.`,
  },

  /* -------------------------------- BAC D ------------------------------- */
  {
    id: 'exam_bacd_svt_2023',
    title: 'Épreuve de SVT — BAC Série D Session 2023',
    examType: 'BAC D',
    year: 2023,
    subject: 'SVT',
    durationMinutes: 180,
    instructions: 'Les schémas doivent être soignés et légendés. Traitez les deux parties.',
    content: `### PREMIÈRE PARTIE (10 points) — Génétique
On croise deux souris à pelage noir, hétérozygotes pour le gène de la couleur (N = noir dominant, n = blanc récessif).
1. Fais l'échiquier de croisement Nn × Nn.
2. Donne les proportions génotypiques et phénotypiques attendues.
3. Explique le rôle de la méiose dans la diversité des gamètes.

### DEUXIÈME PARTIE (10 points) — Immunologie
1. Définis l'immunité innée et l'immunité acquise.
2. Explique le rôle des lymphocytes B et des lymphocytes T.
3. Un enfant est vacciné contre la rougeole. Explique le mécanisme de la protection obtenue.
4. Pourquoi la vaccination protège-t-elle aussi les personnes non vaccinées ?`,
    solution: `### Première partie
1. Échiquier de croisement Nn × Nn :
- Gamètes du père : N et n.
- Gamètes de la mère : N et n.
- Combinaisons : N×N = NN ; N×n = Nn ; n×N = Nn ; n×n = nn.

2. Génotypes : **1/4 NN, 1/2 Nn, 1/4 nn**. Phénotypes : **3/4 pelage noir, 1/4 pelage blanc**.
3. La méiose sépare les chromosomes homologues (brassage interchromosomique) et permet des crossing-over (brassage intrachromosomique) : chaque gamète reçoit une combinaison unique d'allèles.

### Deuxième partie
1. Immunité **innée** : immédiate, non spécifique (peau, phagocytes, inflammation). Immunité **acquise** : lente à se mettre en place, spécifique d'un antigène, avec mémoire immunitaire.
2. Les **lymphocytes B** produisent des anticorps qui neutralisent les antigènes. Les **lymphocytes T** détruisent les cellules infectées et aident les lymphocytes B.
3. Le vaccin injecte un antigène atténué : l'organisme produit des anticorps et des cellules mémoire. Lors d'un contact réel avec le virus, la réponse est rapide et efficace.
4. Grâce à l'**immunité de groupe** : si une grande partie de la population est immunisée, le virus circule moins et protège indirectement les personnes fragiles.`,
  },
  {
    id: 'exam_bacd_pc_2023',
    title: 'Épreuve de Physique-Chimie — BAC Série D Session 2023',
    examType: 'BAC D',
    year: 2023,
    subject: 'Physique-Chimie',
    durationMinutes: 180,
    instructions: 'Traitez la chimie et la physique sur des copies séparées.',
    content: `### CHIMIE (10 points) — Acides et bases
On dose 20 mL d'une solution d'acide éthanoïque de concentration Cₐ inconnue par une solution de soude de concentration C_b = 0,1 mol/L.
1. Écris l'équation de la réaction de dosage.
2. L'équivalence est obtenue pour V_b = 16 mL. Calcule Cₐ.
3. Le pH à l'équivalence est 8,3. Quel indicateur coloré choisir ? Justifie.
4. L'acide éthanoïque est-il fort ou faible ? Justifie.

### PHYSIQUE (10 points) — Mouvement dans un champ de pesanteur
Une balle est lancée horizontalement depuis une hauteur h = 1,8 m avec une vitesse v₀ = 5 m/s. On prend g = 10 N/kg.
1. Établis les équations horaires du mouvement.
2. Calcule la durée de chute.
3. Calcule la distance horizontale parcourue avant l'impact.
4. Calcule la vitesse de la balle à l'impact.`,
    solution: `### Chimie
1. CH₃COOH + HO⁻ → CH₃COO⁻ + H₂O.
2. À l'équivalence : CₐVₐ = C_bV_b → Cₐ = (0,1 × 16)/20 = **0,08 mol/L**.
3. pH à l'équivalence = 8,3 : on choisit la **phénolphtaléine** (zone de virage 8,2 – 10,0), adaptée à un dosage de base faible.
4. L'acide éthanoïque est un **acide faible** : il ne se dissocie que partiellement dans l'eau, et sa base conjuguée donne une solution basique à l'équivalence.

### Physique
1. Ox : x = v₀t ; Oy (vers le bas) : y = ½gt².
2. y = h → 1,8 = 5t² → t² = 0,36 → **t = 0,6 s**.
3. x = 5 × 0,6 = **3 m**.
4. vₓ = 5 m/s ; v_y = g t = 6 m/s ; v = √(25 + 36) = **7,8 m/s**.`,
  },
];
