# EduCI — Plateforme Éducative Ivoirienne

> **« Apprendre. Progresser. Réussir. »**  
> Plateforme éducative complète conçue pour les élèves de Côte d'Ivoire, du Primaire au Lycée (CP1 à la Terminale), conforme aux programmes officiels de la Direction des Examens et Concours (DECO) et du Ministère de l'Éducation Nationale et de l'Alphabétisation (MENA).

---

## 1. Vue d'Ensemble & Architecture

L'application **EduCI** est développée sur une double architecture :
1. **Application Mobile Android Native (Jetpack Compose & Kotlin)** : performance maximale, animations fluides, composants Material 3, base de données locale Room (SQLite) persistante, compatibilité hors-ligne complète et intégration IA Gemini / Fallback pédagogique.
2. **Application Mobile Cross-Platform React Native & Expo (TypeScript)** : architecture modulaire pour iOS, Android et Web / PWA, avec contexte d'authentification RBAC et persistance locale.

### Structure des Niveaux Scolaires Couverts :
* **Primaire** : CP1, CP2, CE1, CE2, CM1, CM2 (Préparation CEPE).
* **Collège** : 6e, 5e, 4e, 3e (Préparation BEPC).
* **Lycée** : 2nde, 1ère, Terminale (Préparation BAC Séries A, C, D).

---

## 2. Fonctionnalités Clés Réalisées

### A. Authentification & Sécurité (RBAC Strict)
- **Inscription complète** : Prénom, Nom, Adresse e-mail, Numéro de téléphone (+225), Mot de passe, Confirmation, Niveau scolaire (CP1 à Tle), Établissement (facultatif), Clé d'administration (optionnelle).
- **Connexion flexible** : Connexion possible par **Email OU Numéro de téléphone**.
- **Sécurité des mots de passe** : Hachage cryptographique **SHA-256**.
- **Gestion des sessions** : Déconnexion sécurisée, possibilité de déconnexion de tous les appareils.
- **Contrôle strict des permissions (RBAC)** :
  - **Élève (Student)** : Consultation des cours, exercices auto-corrigés, examens blancs, tuteur IA, favoris et progression.
  - **Administrateur / Propriétaire (Admin/Owner)** : Création de cours avec éditeur par blocs, ajout d'exercices et d'annales d'examens, publication/dépublication, gestion et suspension des comptes élèves, journal d'audit.
  - **Propriétaire officiel** : `horizonprogrammeur@gmail.com` avec clé maître `EDUCI-PROPRIETAIRE-2026`.

### B. Accueil & Tableau de Bord Pédagogique
- Salutation personnalisée avec niveau et statut (Gratuit / Premium).
- **Statistiques d'apprentissage** : Taux de réussite global (%), temps d'étude, série de jours consécutifs (streak 🔥), points XP et niveau atteint.
- **Section "Continuer mon apprentissage"** : Affichage direct de la dernière leçon consultée avec bouton "Continuer".
- **Section "Mes matières"** : Cartes horizontales interactives pour la classe sélectionnée (Mathématiques, Français, Physique-Chimie, SVT, Anglais, Histoire-Géo, Philosophie).
- **Section "À découvrir"** : Nouveautés leçons, quiz et annales DECO.
- **Objectif du jour** : Barre de progression dynamique avec récompense XP.
- **Recommandations personnalisées** adaptées au niveau de l'élève.

### C. Module Cours & Éditeur Riche par Blocs (Block Editor)
- Filtres multi-critères : Niveau (Primaire, Collège, Lycée), Classe, Matière, Chapitre.
- Fiches de cours enrichies :
  - Objectifs pédagogiques conformes aux programmes officiels.
  - Titres et sous-titres hiérarchisés (H1, H2, H3).
  - Formules mathématiques et scientifiques avec KaTeX / Monospace (`$$` et `$`).
  - Tableaux comparatifs stylisés.
  - Blocs d'information visuels : **Définition**, **Exemple Concret ivoirien**, **Attention / Piège fréquent**, **Conseil du Professeur**.
  - Favoris (Ajout / Retrait avec persistance).
  - Téléchargement hors-ligne avec badge "📥 Hors-ligne".
  - Recherche par mot-clé au sein du cours.
  - Bouton "Poser une question à l'IA sur cette leçon".
  - Marquage "Cours terminé" avec attribution de points XP.

### D. Module Exercices & Auto-correction Immédiate
- Types d'exercices pris en charge :
  - QCM (Choix unique).
  - Vrai / Faux.
  - Réponse courte (Texte).
  - Réponse numérique (Calculs mathématiques et physiques).
  - Choix multiple (Plusieurs réponses correctes).
- Feedback instantané : indication de bonne/mauvaise réponse, barème de points, explication détaillée pas-à-pas et sauvegarde de la tentative.

### E. Module Examens Blancs & Annales Officielles (DECO)
- Catégories : **CEPE**, **BEPC**, **BAC (Général, Série A, Série C, Série D)**, **Devoirs et Compositions**.
- **Mode Examen en conditions réelles** :
  - Chronomètre à compte à rebours interactif (ex: 120 min).
  - Espace de brouillon et de réponses de l'élève.
  - Bouton de soumission avec validation de copie.
  - Calcul de la note sur 20 avec mention ("Très Bien", "Bien", "Assez Bien").
  - Corrigé officiel pas-à-pas et barème de notation officiel DECO.

### F. Tuteur IA Pédagogique (Professeur EduCI)
- Pédagogie structurée en 4 étapes : **Explication → Méthode → Raisonnement → Réponse**.
- Adaptation intelligente au niveau de l'élève (vocabulaire et complexité adaptés du CP1 à la Terminale).
- Intégration de l'API **Gemini 3.5 Flash** (via `BuildConfig.GEMINI_API_KEY`) avec **moteur pédagogique hors-ligne riche et interactif** en cas d'absence de connexion ou de clé.
- Exemples concrets du quotidien ivoirien (culture du cacao/café, marché d'Adjamé, lagune Ébrié, fleuve Bandama, etc.).

### G. Espace Premium & Couche de Paiement Ivoirienne
- Formules d'abonnement :
  - **Mensuel** : 2 500 FCFA / mois
  - **Trimestriel** : 6 000 FCFA / trimestre
  - **Annuel (Promo)** : 18 000 FCFA / an
- Intégration des moyens de paiement mobiles adaptés à la Côte d'Ivoire :
  - 🌊 **Wave Côte d'Ivoire** (0% de frais)
  - 🍊 **Orange Money CI** (#144*82#)
  - 🟡 **MTN Mobile Money CI** (*133#)
  - 🔵 **Moov Money CI** (*155#)
  - 💳 **Carte bancaire (Visa, Mastercard, Djamo)**
- Enregistrement des transactions (référence unique, date, montant, expiration), génération de reçu et activation instantanée côté base de données.

### H. Mode Hors-Ligne & Gestion du Stockage
- Téléchargement des cours pour révision sans connexion Internet.
- Indicateur de taille mémoire occupée (en Mo) dans le profil élève.
- Fonction "Vider le cache" pour libérer l'espace disque du téléphone.

### I. Espace Administrateur Sécurisé
- Tableau de bord statistique (élèves inscrits, leçons publiées, exercices, examens).
- **Éditeur de leçon WYSIWYG par blocs** avec sauvegarde automatique du brouillon et prévisualisation élève en direct.
- Module de gestion des élèves avec **barre de recherche**, **suspension temporaire** et **réactivation de compte**.
- Module de création d'exercices et d'examens avec barème.
- **Journal d'audit administratif** horodaté (traçabilité de toutes les actions).

---

## 3. Guide d'Installation & Déploiement

### Option 1 : Application Android Native (APK / AAB)
1. **Compilation locale** :
   ```bash
   gradle assembleDebug
   ```
   Le fichier APK prêt à être installé se trouve dans `.build-outputs/app-debug.apk`.
2. **Génération AAB pour Google Play Store** :
   ```bash
   gradle bundleRelease
   ```
3. **Installation sur un smartphone Android** :
   - Transférer le fichier `app-debug.apk` sur le téléphone ou le télécharger via l'écran "Installer sur mon téléphone".
   - Autoriser l'installation depuis des sources inconnues dans les paramètres de sécurité Android.
   - Ouvrir le fichier APK et valider l'installation.

### Option 2 : Application React Native & Expo
1. **Accéder au sous-dossier** :
   ```bash
   cd react-native-educi
   ```
2. **Installer les dépendances** :
   ```bash
   npm install
   ```
3. **Lancer le serveur de développement** :
   ```bash
   npx expo start
   ```
4. **Tester sur mobile** :
   - Scanner le QR code avec l'application **Expo Go** (Android) ou l'appareil photo (iPhone).
   - Lancer sur le web avec `npx expo start --web`.

---

## 4. Configuration Backend & Base de Données

- **Base de données Android** : Room Database SQLite (`educi_database`, version 2).
  - Tables : `users`, `level_categories`, `grade_classes`, `subjects`, `chapters`, `lessons`, `exercises`, `exams`, `user_progress`, `user_exercise_attempts`, `notifications`, `admin_logs`, `draft_backups`, `favorites`, `offline_downloads`, `payment_transactions`, `exam_submissions`.
- **Variables d'environnement** (`.env`) :
  ```env
  GEMINI_API_KEY=votre_cle_gemini_ici
  ```
- **Configuration Propriétaire** :
  - Email : `horizonprogrammeur@gmail.com`
  - Clé Maître Secrète : `EDUCI-PROPRIETAIRE-2026`

---

## 5. Documentation Utilisateur & Administrateur

### Pour l'Élève :
1. Crée ton compte en renseignant ton prénom, nom, numéro de téléphone, email et classe.
2. Découvre tes cours par matière, lis les leçons et marque-les comme terminées pour gagner des XP.
3. Teste tes connaissances avec les exercices auto-corrigés et consulte les explications en cas d'erreur.
4. Prépare ton examen (CEPE, BEPC ou BAC) avec les annales officielles et lance le chronomètre pour t'entraîner en conditions réelles.
5. Pose tes questions scolaires au Professeur EduCI pour obtenir une méthode pas-à-pas.

### Pour l'Administrateur :
1. Connecte-toi avec l'adresse email propriétaire ou clique sur « Accès Réservé au Propriétaire ».
2. Saisis la clé secrète d'administration `EDUCI-PROPRIETAIRE-2026`.
3. Utilise l'éditeur riche par blocs pour rédiger ou mettre à jour des cours sans toucher au code source.
4. Gère les comptes élèves (recherche rapide, suspension ou réactivation en cas de besoin).
5. Consulte le journal d'audit pour vérifier l'historique des modifications pédagogiques.
