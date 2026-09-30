# EduCI Mobile - Version React Native / Expo

Plateforme éducative ivoirienne pour apprendre, progresser et réussir du primaire au lycée.

## 🌟 Fonctionnalités Implémentées

1. **Thème Vert-Blanc & Mode Sombre :**
   - Palette claire avec fond vert-blanc doux (`#EDF7F0`), cartes blanches (`#FFFFFF`) et liseré menthe (`#CFE8D7`).
   - Palette sombre émeraude nocturne (`#0B1912`, `#13281E`, `#34D399`).
   - Bascule fluide entre Mode Clair, Mode Sombre et Mode Système avec persistance (`AsyncStorage`).

2. **Sécurité et Droits Exclusifs du Propriétaire :**
   - **Aucun compte utilisateur par défaut** dans la base de données.
   - Propriétaire désigné : `horizonprogrammeur@gmail.com`.
   - Clé Maître Secrète : `EDUCI-PROPRIETAIRE-2026`.
   - Seul l'administrateur / propriétaire authentifié peut ajouter, modifier ou supprimer des leçons, des exercices et gérer les comptes.
   - Les autres utilisateurs ont uniquement un rôle apprenant (lecture, quiz, révisions).

3. **Modules Pédagogiques :**
   - **Niveaux & Classes** : Primaire (CI à CM2), Collège (6e à 3e), Lycée (2nde à Tle).
   - **Cours & Leçons** : Fiches de cours synthétiques conformes au programme ivoirien.
   - **Exercices Auto-corrigés** : QCM interactifs avec rétroaction immédiate et explications.
   - **Examens Nationaux** : Sujets réels et corrigés officiels (CEPE, BEPC, BAC A, C, D).
   - **Tuteur IA** : Assistant virtuel pour l'aide aux devoirs et la méthodologie.

## 🚀 Démarrage du Projet React Native

```bash
# Se placer dans le dossier
cd react-native-educi

# Installer les dépendances
npm install

# Démarrer avec Expo
npx expo start
```
