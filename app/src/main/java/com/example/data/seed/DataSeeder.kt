package com.example.data.seed

import com.example.data.local.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DataSeeder {
    suspend fun seedIfNeeded(db: AppDatabase) = withContext(Dispatchers.IO) {
        val userDao = db.userDao()
        val courseDao = db.courseDao()
        val exerciseDao = db.exerciseDao()
        val examDao = db.examDao()
        val notificationDao = db.notificationDao()

        // 1. Seed Demo users if empty
        val existingAdmin = userDao.getUserByEmail("admin@educi.ci")
        if (existingAdmin == null) {
            userDao.insertUser(
                UserEntity(
                    firstName = "Admin",
                    lastName = "EduCI",
                    email = "admin@educi.ci",
                    phoneNumber = "+2250700000000",
                    passwordHash = com.example.data.repository.EduRepository.hashPassword("admin123"),
                    className = "terminale",
                    schoolName = "Ministère de l'Éducation Nationale",
                    role = "admin",
                    isPremium = true
                )
            )
        }
        val existingStudent = userDao.getUserByEmail("koffi.jean@educi.ci")
        if (existingStudent == null) {
            userDao.insertUser(
                UserEntity(
                    firstName = "Koffi",
                    lastName = "Jean",
                    email = "koffi.jean@educi.ci",
                    phoneNumber = "+2250501020304",
                    passwordHash = com.example.data.repository.EduRepository.hashPassword("eleve123"),
                    className = "4e",
                    schoolName = "Lycée Classique d'Abidjan",
                    role = "student",
                    isPremium = false
                )
            )
        }

        // 2. Seed Levels
        val levels = listOf(
            LevelCategoryEntity("primaire", "Primaire", 1),
            LevelCategoryEntity("college", "Collège", 2),
            LevelCategoryEntity("lycee", "Lycée", 3)
        )
        courseDao.insertLevels(levels)

        // 3. Seed Classes
        val classes = listOf(
            GradeClassEntity("cp1", "primaire", "CP1", 1),
            GradeClassEntity("cp2", "primaire", "CP2", 2),
            GradeClassEntity("ce1", "primaire", "CE1", 3),
            GradeClassEntity("ce2", "primaire", "CE2", 4),
            GradeClassEntity("cm1", "primaire", "CM1", 5),
            GradeClassEntity("cm2", "primaire", "CM2", 6),
            GradeClassEntity("6e", "college", "6e", 7),
            GradeClassEntity("5e", "college", "5e", 8),
            GradeClassEntity("4e", "college", "4e", 9),
            GradeClassEntity("3e", "college", "3e", 10),
            GradeClassEntity("2nde", "lycee", "2nde", 11),
            GradeClassEntity("1ere", "lycee", "1ère", 12),
            GradeClassEntity("terminale", "lycee", "Terminale", 13)
        )
        courseDao.insertClasses(classes)

        // 4. Seed Subjects if empty
        val existingLesson = courseDao.getLessonByIdOnce(1L)
        if (existingLesson == null) {
            // Subjects for Collège
            val mathCollegeId = courseDao.insertSubject(
                SubjectEntity(name = "Mathématiques", classId = "college", iconName = "calculate", colorHex = "#0A7E48", isNationalExamSubject = true)
            )
            val frenchCollegeId = courseDao.insertSubject(
                SubjectEntity(name = "Français", classId = "college", iconName = "menu_book", colorHex = "#2563EB", isNationalExamSubject = true)
            )
            val pcCollegeId = courseDao.insertSubject(
                SubjectEntity(name = "Physique-Chimie", classId = "college", iconName = "science", colorHex = "#7C3AED", isNationalExamSubject = true)
            )
            val svtCollegeId = courseDao.insertSubject(
                SubjectEntity(name = "SVT", classId = "college", iconName = "eco", colorHex = "#059669", isNationalExamSubject = true)
            )
            val hgCollegeId = courseDao.insertSubject(
                SubjectEntity(name = "Histoire-Géographie", classId = "college", iconName = "public", colorHex = "#D97706", isNationalExamSubject = true)
            )
            val englishCollegeId = courseDao.insertSubject(
                SubjectEntity(name = "Anglais", classId = "college", iconName = "language", colorHex = "#DC2626", isNationalExamSubject = true)
            )

            // Lycée subjects
            val mathLyceeId = courseDao.insertSubject(
                SubjectEntity(name = "Mathématiques", classId = "lycee", iconName = "calculate", colorHex = "#0A7E48", isNationalExamSubject = true)
            )
            val philoLyceeId = courseDao.insertSubject(
                SubjectEntity(name = "Philosophie", classId = "lycee", iconName = "psychology", colorHex = "#9333EA", isNationalExamSubject = true)
            )

            // Primaire subjects
            val mathPrimaireId = courseDao.insertSubject(
                SubjectEntity(name = "Mathématiques", classId = "primaire", iconName = "calculate", colorHex = "#0A7E48", isNationalExamSubject = true)
            )
            val francaisPrimaireId = courseDao.insertSubject(
                SubjectEntity(name = "Français", classId = "primaire", iconName = "menu_book", colorHex = "#2563EB", isNationalExamSubject = true)
            )

            // 5. Seed Chapters
            val chCalculLitteral = courseDao.insertChapter(
                ChapterEntity(subjectId = mathCollegeId, classId = "4e", title = "Calcul littéral et identités remarquables", summary = "Développer, factoriser et utiliser les 3 identités remarquables", orderIndex = 1)
            )
            val chTheoremePythagore = courseDao.insertChapter(
                ChapterEntity(subjectId = mathCollegeId, classId = "4e", title = "Propriété de Pythagore", summary = "Calcul de longueurs dans un triangle rectangle", orderIndex = 2)
            )
            val chGrammaire3e = courseDao.insertChapter(
                ChapterEntity(subjectId = frenchCollegeId, classId = "3e", title = "L'argumentation et les connecteurs logiques", summary = "Défendre une thèse avec rigueur", orderIndex = 1)
            )
            val chSolutionsAcides = courseDao.insertChapter(
                ChapterEntity(subjectId = pcCollegeId, classId = "3e", title = "Les solutions acides et basiques (pH)", summary = "Mesure du pH, réactions acide-base", orderIndex = 1)
            )
            val chLogarithmeTerm = courseDao.insertChapter(
                ChapterEntity(subjectId = mathLyceeId, classId = "terminale", title = "Fonction Logarithme Népérien", summary = "Définition, propriétés algébriques et études de fonctions", orderIndex = 1)
            )
            val chFractionsPrimaire = courseDao.insertChapter(
                ChapterEntity(subjectId = mathPrimaireId, classId = "cm2", title = "Les Fractions et Nombres Décimaux", summary = "Comprendre les fractions simples et les pourcentages", orderIndex = 1)
            )

            // 6. Seed Rich Lessons
            val lesson1Content = """
# I. Définitions et Développements

Le calcul littéral consiste à utiliser des lettres pour représenter des nombres quelconques.

:::definition
**Développer** une expression, c'est transformer un produit de facteurs en une somme ou une différence de termes.
:::

## 1. La règle de distributivité simple
Pour tous nombres réels §a§, §b§ et §k§ :
§§k(a + b) = ka + kb§§
§§k(a - b) = ka - kb§§

:::exemple
Développons l'expression §A = 3(2x + 5)§ :
§A = 3 \times 2x + 3 \times 5 = 6x + 15§
:::

# II. Les Trois Identités Remarquables

Ce sont des formules fondamentales du programme de 4e et 3e en Côte d'Ivoire.

| Identité Remarquable | Forme Développée | Nom Usuel |
| --- | --- | --- |
| §(a + b)^2§ | §a^2 + 2ab + b^2§ | Carré d'une somme |
| §(a - b)^2§ | §a^2 - 2ab + b^2§ | Carré d'une différence |
| §(a - b)(a + b)§ | §a^2 - b^2§ | Produit de la somme par la différence |

:::attention
Erreur très fréquente chez les élèves :
§(a + b)^2 \neq a^2 + b^2§ !
N'oubliez jamais le double produit : **§2ab§** !
:::

:::conseil
**Méthode de factorisation :**
Pour factoriser une expression du type §9x^2 - 25§, reconnaissez la forme §a^2 - b^2 = (a - b)(a + b)§ avec §a = 3x§ et §b = 5§ :
§9x^2 - 25 = (3x - 5)(3x + 5)§
:::

# III. Exercices d'application
1. Développer §(2x + 3)^2§
2. Factoriser §4x^2 - 12x + 9§
            """.trimIndent().replace("§", "$")

            courseDao.insertLesson(
                LessonEntity(
                    chapterId = chCalculLitteral,
                    title = "Développement et Identités Remarquables",
                    summary = "Maîtriser les formules des 3 identités remarquables et les calculs littéraux.",
                    pedagogicalObjectives = "1. Connaître par cœur les 3 identités remarquables.\n2. Savoir développer et réduire une expression littérale.\n3. Savoir reconnaître un facteur commun.",
                    content = lesson1Content,
                    durationMinutes = 25,
                    difficulty = "Moyen",
                    isPremium = false,
                    isDraft = false
                )
            )

            val lesson2Content = """
# I. Énoncé du Théorème de Pythagore

Dans un triangle rectangle, le carré de la longueur de l'hypoténuse est égal à la somme des carrés des longueurs des deux autres côtés.

:::definition
Soit un triangle §ABC§ rectangle en §A§ :
§§BC^2 = AB^2 + AC^2§§
L'hypoténuse est le côté opposé à l'angle droit (ici le segment §[BC]§).
:::

| Élément | Définition géométrique | Formule |
| --- | --- | --- |
| Hypoténuse | Plus grand côté du triangle rectangle | §BC = \sqrt{AB^2 + AC^2}§ |
| Côté de l'angle droit | Segment adjacent à l'angle à 90° | §AB = \sqrt{BC^2 - AC^2}§ |

:::exemple
Soit un triangle §EFG§ rectangle en §E§ tel que §EF = 3\text{ cm}§ et §EG = 4\text{ cm}§.
Calculons l'hypoténuse §FG§ :
§FG^2 = EF^2 + EG^2 = 3^2 + 4^2 = 9 + 16 = 25§
§FG = \sqrt{25} = 5\text{ cm}§.
:::

:::conseil
Vérifiez toujours que l'hypoténuse trouvée est bien la plus grande longueur du triangle.
:::
            """.trimIndent().replace("§", "$")

            courseDao.insertLesson(
                LessonEntity(
                    chapterId = chTheoremePythagore,
                    title = "Théorème de Pythagore et Réciproque",
                    summary = "Calculer l'hypoténuse et prouver qu'un triangle est rectangle.",
                    pedagogicalObjectives = "1. Énoncer la propriété de Pythagore.\n2. Calculer une longueur manquante dans un triangle rectangle.\n3. Utiliser la réciproque pour démontrer qu'un triangle est rectangle.",
                    content = lesson2Content,
                    durationMinutes = 20,
                    difficulty = "Facile",
                    isPremium = false,
                    isDraft = false
                )
            )

            val lessonPhContent = """
# I. Définition et Échelle de pH

Le pH (potentiel Hydrogène) mesure la concentration en ions hydrogène §H^+§ dans une solution aqueuse.

:::definition
L'échelle de pH s'étend de 0 à 14 à 25°C :
- **Solution acide :** 0 <= pH < 7 (excès d'ions §H^+§)
- **Solution neutre :** pH = 7 (eau pure, [H+] = [OH-])
- **Solution basique :** 7 < pH <= 14 (excès d'ions hydroxyde §OH^-§)
:::

| Solution courante | Valeur approximative du pH | Nature |
| --- | --- | --- |
| Jus de citron | 2.4 | Très acide |
| Vinaigre blanc | 3.0 | Acide |
| Eau minérale de Côte d'Ivoire | 7.0 | Neutre |
| Savon traditionnel noir | 9.5 | Basique |
| Eau de javel | 12.0 | Très basique |

:::attention
Ne versez jamais de l'eau dans un acide concentré ! Risque de projections violentes. Versez toujours **l'acide dans l'eau** avec précaution.
:::
            """.trimIndent().replace("§", "$")

            courseDao.insertLesson(
                LessonEntity(
                    chapterId = chSolutionsAcides,
                    title = "Solutions Acides, Basiques et Mesure du pH",
                    summary = "Comprendre l'échelle de pH et la dangerosité des acides et bases en laboratoire.",
                    pedagogicalObjectives = "1. Savoir utiliser le papier pH et le pH-mètre.\n2. Identifier si une solution est acide, neutre ou basique.\n3. Respecter les règles de sécurité en chimie.",
                    content = lessonPhContent,
                    durationMinutes = 30,
                    difficulty = "Moyen",
                    isPremium = true,
                    isDraft = false
                )
            )

            val lessonLogContent = """
# I. Définition de la Fonction Logarithme Népérien

:::definition
La fonction logarithme népérien, notée ln, est l'unique primitive sur ]0 ; +infini[ de la fonction x -> 1/x qui s'annule en 1 :
§§\ln(1) = 0 \quad \text{et} \quad \ln'(x) = \frac{1}{x}§§
Le nombre d'Euler e ~= 2,718 vérifie ln(e) = 1.
:::

## Propriétés fondamentales
Pour tous réels §a > 0§ et §b > 0§ :
§§\ln(a \times b) = \ln(a) + \ln(b)§§
§§\ln(a / b) = \ln(a) - \ln(b)§§
§§\ln(a^n) = n\ln(a) \quad (n \in \mathbb{Z})§§
§§\ln(\sqrt{a}) = \frac{1}{2}\ln(a)§§

| Limite usuelle | Résultat | Interprétation |
| --- | --- | --- |
| lim x->0+ ln(x) | -infini | Asymptote verticale d'équation x = 0 |
| lim x->+inf ln(x) | +infini | Branche parabolique de direction (Ox) |
| lim x->+inf ln(x)/x | 0 | Croissance comparée |
| lim x->0+ x ln(x) | 0 | Croissance comparée en 0 |
            """.trimIndent().replace("§", "$")

            courseDao.insertLesson(
                LessonEntity(
                    chapterId = chLogarithmeTerm,
                    title = "Étude Complète de la Fonction Logarithme Népérien",
                    summary = "Propriétés algébriques, limites, dérivée et étude de la fonction ln.",
                    pedagogicalObjectives = "1. Connaître les relations fondamentales du logarithme.\n2. Résoudre des équations et inéquations avec ln.\n3. Calculer les dérivées et limites au BAC C et D.",
                    content = lessonLogContent,
                    durationMinutes = 45,
                    difficulty = "Difficile",
                    isPremium = true,
                    isDraft = false
                )
            )

            // 7. Seed Interactive Exercises
            exerciseDao.insertExercises(
                listOf(
                    ExerciseEntity(
                        classId = "4e",
                        subjectId = mathCollegeId,
                        chapterId = chCalculLitteral,
                        title = "Développement d'une identité remarquable",
                        instructions = "Choisis la bonne réponse parmi les options proposées.",
                        question = "Quel est le développement correct de (3x + 2)² ?",
                        type = "qcm",
                        optionsJson = "9x² + 4;;9x² + 6x + 4;;9x² + 12x + 4;;6x² + 12x + 4",
                        correctAnswer = "9x² + 12x + 4",
                        explanation = "On applique (a+b)² = a² + 2ab + b² avec a = 3x et b = 2. Donc (3x)² + 2(3x)(2) + 2² = 9x² + 12x + 4.",
                        points = 10,
                        durationMinutes = 3,
                        difficulty = "Moyen",
                        isPremium = false
                    ),
                    ExerciseEntity(
                        classId = "4e",
                        subjectId = mathCollegeId,
                        chapterId = chTheoremePythagore,
                        title = "Calcul d'hypoténuse",
                        instructions = "Sélectionne la bonne longueur.",
                        question = "Dans un triangle rectangle dont les côtés adjacents mesurent 6 cm et 8 cm, quelle est la longueur de l'hypoténuse ?",
                        type = "qcm",
                        optionsJson = "10 cm;;14 cm;;12 cm;;100 cm",
                        correctAnswer = "10 cm",
                        explanation = "D'après Pythagore : c² = 6² + 8² = 36 + 64 = 100. Donc c = √100 = 10 cm.",
                        points = 10,
                        durationMinutes = 3,
                        difficulty = "Facile",
                        isPremium = false
                    ),
                    ExerciseEntity(
                        classId = "3e",
                        subjectId = pcCollegeId,
                        chapterId = chSolutionsAcides,
                        title = "Classification du pH",
                        instructions = "Réponds par Vrai ou Faux.",
                        question = "Une solution dont le pH est égal à 2,5 est qualifiée de basique.",
                        type = "true_false",
                        optionsJson = "Vrai;;Faux",
                        correctAnswer = "Faux",
                        explanation = "Une solution est acide si son pH est inférieur à 7. Comme 2,5 < 7, elle est acide.",
                        points = 10,
                        durationMinutes = 2,
                        difficulty = "Facile",
                        isPremium = false
                    )
                )
            )

            // 8. Seed Official National Exams (CEPE, BEPC, BAC)
            val bepcContent = """
# MINISTÈRE DE L'ÉDUCATION NATIONALE ET DE L'ALPHABÉTISATION
## EXAMEN DU BEPC — SESSION 2024 — CÔTE D'IVOIRE
### Épreuve de Mathématiques (Durée : 2 heures - Coeff : 3)

---

#### EXERCICE 1 (3 points)
Écris sur ta copie le numéro de chaque affirmation suivi de **V** si elle est vraie ou **F** si elle est fausse :
1. Pour tous réels §a§ et §b§, §(a - b)^2 = a^2 - b^2§.
2. Le nombre §\sqrt{49}§ est égal à 7.
3. Si un triangle §ABC§ est rectangle en §A§, alors cos(B) = AC/BC.

---

#### EXERCICE 2 (5 points)
On donne l'expression littérale :
§§E = (2x - 3)^2 - (x + 1)(2x - 3)§§
1. Développe et réduis l'expression §E§.
2. Factorise l'expression §E§.
3. Résous dans R l'équation : §(2x - 3)(x - 4) = 0§.

---

#### EXERCICE 3 (6 points)
Dans le plan muni d'un repère orthonormé (O, I, J) :
On donne les points §A(1 ; 2)§, §B(4 ; 6)§ et §C(-3 ; 5)§.
1. Calcule les coordonnées des vecteurs AB et AC.
2. Démontre que le triangle §ABC§ est rectangle en §A§.
3. Détermine les coordonnées du point §D§ tel que §ABDC§ soit un parallélogramme.

---

#### SITUATION D'ÉVALUATION (6 points)
Un jeune planteur de cacao de Soubré souhaite clôturer une parcelle triangulaire pour protéger ses fèves. Il dispose d'un budget de 150 000 FCFA pour acheter du grillage à 2 500 FCFA le mètre linéaire.
Détermine si son budget sera suffisant.
            """.trimIndent().replace("§", "$")

            val bepcSolution = """
### CORRIGÉ OFFICIEL TYPE & BARÈME

#### EXERCICE 1 (3 points - 1 pt par item)
1. **Faux** : §(a-b)^2 = a^2 - 2ab + b^2§.
2. **Vrai** : §\sqrt{49} = 7§.
3. **Faux** : cos(B) = AB/BC (côté adjacent / hypoténuse).

#### EXERCICE 2 (5 points)
1. **Développement :**
§E = (4x^2 - 12x + 9) - (2x^2 - 3x + 2x - 3)§
§E = 4x^2 - 12x + 9 - 2x^2 + x + 3§
§E = 2x^2 - 11x + 12§ *(2 pts)*

2. **Factorisation :**
§E = (2x - 3)[(2x - 3) - (x + 1)] = (2x - 3)(x - 4)§ *(2 pts)*

3. **Équation produit nul :**
§2x - 3 = 0 \implies x = 3/2§ ou §x - 4 = 0 \implies x = 4§.
§S = {3/2 ; 4}§ *(1 pt)*
            """.trimIndent().replace("§", "$")

            val bacContent = """
# BACCALAURÉAT DE L'ENSEIGNEMENT DU SECOND DEGRÉ
## SESSION 2024 — SÉRIE D — CÔTE D'IVOIRE
### Épreuve de Sciences Physiques (Durée : 3 heures)

#### CHIMIE (6 points) : Dosage acide fort - base forte
On dose un volume §V_A = 20\text{ mL}§ d'une solution d'acide chlorhydrique de concentration §C_A§ inconnue par une solution d'hydroxyde de sodium de concentration §C_B = 0,1\text{ mol/L}§.
Le volume équivalent obtenu est §V_{BE} = 15\text{ mL}§.
1. Écris l'équation bilan de la réaction du dosage.
2. Détermine la concentration §C_A§ de la solution acide.
3. Définis l'équivalence acido-basique et précise le pH à l'équivalence à 25°C.

#### PHYSIQUE (14 points) : Mouvement d'un projectile et Circuit RLC
1. Étude du mouvement d'un ballon tiré au stade Félix Houphouët-Boigny avec une vitesse initiale §v_0 = 20\text{ m/s}§ inclinée d'un angle §\alpha = 30^\circ§ avec l'horizontale.
2. Équations horaires et équation de la trajectoire dans le champ de pesanteur.
            """.trimIndent().replace("§", "$")

            val bacSolution = """
### CORRIGÉ BAC SÉRIE D 2024
1. Équation de dosage : H3O+ + OH- -> 2H2O.
2. À l'équivalence : §n_A = n_B \implies C_A V_A = C_B V_{BE}§.
§C_A = \frac{C_B \times V_{BE}}{V_A} = \frac{0,1 \times 15}{20} = 0,075\text{ mol/L}§.
3. À l'équivalence acido-basique, les réactifs sont introduits dans les proportions stœchiométriques. Pour un acide fort et une base forte à 25°C, pH = 7,0.
            """.trimIndent().replace("§", "$")

            examDao.insertExams(
                listOf(
                    ExamEntity(
                        title = "BEPC 2024 — Mathématiques (Épreuve Officielle)",
                        examType = "BEPC",
                        series = "Général",
                        year = 2024,
                        subject = "Mathématiques",
                        durationMinutes = 120,
                        instructions = "L'usage de la calculatrice est autorisé. Rédigez avec clarté et précision.",
                        content = bepcContent,
                        solution = bepcSolution,
                        gradingScale = "Exercice 1 : 3 pts | Exercice 2 : 5 pts | Exercice 3 : 6 pts | Problème : 6 pts",
                        isPremium = false
                    ),
                    ExamEntity(
                        title = "BAC 2024 — Sciences Physiques (Série D)",
                        examType = "BAC",
                        series = "Série D",
                        year = 2024,
                        subject = "Physique-Chimie",
                        durationMinutes = 180,
                        instructions = "Session de Juin 2024. Toutes les réponses doivent être justifiées numériquement.",
                        content = bacContent,
                        solution = bacSolution,
                        gradingScale = "Chimie : 6 pts | Mécanique : 8 pts | Électricité : 6 pts",
                        isPremium = true
                    ),
                    ExamEntity(
                        title = "CEPE 2024 — Exploitation de Texte & Mathématiques",
                        examType = "CEPE",
                        series = "Général",
                        year = 2024,
                        subject = "Mathématiques",
                        durationMinutes = 60,
                        instructions = "Examen d'entrée en 6e. Écris lisiblement sur ta feuille de composition.",
                        content = """
# DIRECTION DES EXAMENS ET CONCOURS (DECO)
## EXAMEN DU CEPE ET ENTRÉE EN 6e — SESSION 2024
### Épreuve de Mathématiques (Durée : 1 heure)

#### OPÉRATIONS (4 points)
Pose et effectue les opérations suivantes :
1. 14 560 + 8 745
2. 35 000 - 18 425
3. 425 x 36

#### PROBLÈME (6 points)
La coopérative scolaire d'une école de Yamoussoukro récolte 450 kg de manioc.
Elle vend le kilogramme à 250 FCFA.
1. Calcule la somme totale rapportée par la vente du manioc.
2. Avec cet argent, le directeur achète 15 dictionnaires à 5 000 FCFA l'unité. Calcule le montant dépensé.
3. Quelle somme reste-t-il dans la caisse de la coopérative ?
                        """.trimIndent(),
                        solution = """
### CORRIGÉ CEPE 2024
1. Somme rapportée : 450 x 250 = 112 500 FCFA.
2. Montant dépensé : 15 x 5 000 = 75 000 FCFA.
3. Reste en caisse : 112 500 - 75 000 = 37 500 FCFA.
                        """.trimIndent(),
                        gradingScale = "Opérations : 4 pts | Problème : 6 pts (2 pts par question)",
                        isPremium = false
                    )
                )
            )

            // 9. Seed Notifications
            notificationDao.insertNotifications(
                listOf(
                    NotificationEntity(
                        title = "Bienvenue sur EduCI !",
                        message = "Explore tes cours, fais des exercices et prépare tes examens nationaux en toute simplicité.",
                        type = "announcement"
                    ),
                    NotificationEntity(
                        title = "Sujets Officiels 2024 en ligne",
                        message = "Les épreuves du CEPE, BEPC et BAC 2024 avec corrigés types sont disponibles dans l'onglet Examens.",
                        type = "exam_ready"
                    ),
                    NotificationEntity(
                        title = "Continue ta série !",
                        message = "Atteins ton objectif quotidien de 5 exercices pour garder ta flamme active 🔥.",
                        type = "reminder"
                    )
                )
            )
        }
    }
}
