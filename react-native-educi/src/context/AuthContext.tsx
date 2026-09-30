import React, { createContext, useContext, useState, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';

export const OWNER_EMAIL = 'horizonprogrammeur@gmail.com';
export const OWNER_MASTER_KEY = 'EDUCI-PROPRIETAIRE-2026';

export interface User {
  id: string;
  name: string;
  email: string;
  role: 'student' | 'teacher' | 'admin' | 'owner';
  gradeClass: string;
  schoolName: string;
  createdAt: string;
}

interface AuthContextType {
  currentUser: User | null;
  isOwnerOrAdmin: boolean;
  login: (email: string, masterKey?: string) => Promise<{ success: boolean; message: string }>;
  register: (name: string, email: string, gradeClass: string, schoolName: string, masterKey?: string) => Promise<{ success: boolean; message: string }>;
  logout: () => Promise<void>;
  claimOwnerAccess: (secretKey: string) => boolean;
}

const AuthContext = createContext<AuthContextType>({
  currentUser: null,
  isOwnerOrAdmin: false,
  login: async () => ({ success: false, message: '' }),
  register: async () => ({ success: false, message: '' }),
  logout: async () => {},
  claimOwnerAccess: () => false,
});

const AUTH_STORAGE_KEY = '@educi_current_user';

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  // Aucun compte par défaut n'est pré-chargé (conformément à la règle de sécurité EduCI)
  const [currentUser, setCurrentUser] = useState<User | null>(null);

  useEffect(() => {
    (async () => {
      try {
        const saved = await AsyncStorage.getItem(AUTH_STORAGE_KEY);
        if (saved) {
          setCurrentUser(JSON.parse(saved));
        }
      } catch (e) {
        // no user logged in
      }
    })();
  }, []);

  const saveUser = async (user: User | null) => {
    setCurrentUser(user);
    if (user) {
      await AsyncStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(user));
    } else {
      await AsyncStorage.removeItem(AUTH_STORAGE_KEY);
    }
  };

  const isOwnerOrAdmin = currentUser?.role === 'owner' || currentUser?.role === 'admin' || currentUser?.email.toLowerCase() === OWNER_EMAIL.toLowerCase();

  // Connexion sécurisée
  const login = async (email: string, masterKey?: string): Promise<{ success: boolean; message: string }> => {
    const trimmedEmail = email.trim().toLowerCase();
    
    // Détection tentative propriétaire
    if (trimmedEmail === OWNER_EMAIL.toLowerCase() || masterKey === OWNER_MASTER_KEY) {
      if (masterKey !== OWNER_MASTER_KEY) {
        return { success: false, message: "Clé Secrète Propriétaire requise ou invalide pour ce compte." };
      }
      const ownerUser: User = {
        id: 'owner_master_001',
        name: 'Propriétaire EduCI',
        email: OWNER_EMAIL,
        role: 'owner',
        gradeClass: 'Tous niveaux',
        schoolName: 'Administration Centrale EduCI',
        createdAt: new Date().toISOString(),
      };
      await saveUser(ownerUser);
      return { success: true, message: "Bienvenue Propriétaire Fondateur !" };
    }

    // Utilisateur régulier (chargé depuis les comptes enregistrés localement)
    try {
      const usersJson = await AsyncStorage.getItem('@educi_registered_users');
      const users: User[] = usersJson ? JSON.parse(usersJson) : [];
      const found = users.find(u => u.email.toLowerCase() === trimmedEmail);
      if (found) {
        await saveUser(found);
        return { success: true, message: `Bon retour, ${found.name} !` };
      }
      return { success: false, message: "Aucun compte trouvé avec cet email. Veuillez créer un compte." };
    } catch (e) {
      return { success: false, message: "Erreur lors de la connexion." };
    }
  };

  // Inscription sans compte par défaut
  const register = async (
    name: string,
    email: string,
    gradeClass: string,
    schoolName: string,
    masterKey?: string
  ): Promise<{ success: boolean; message: string }> => {
    const trimmedEmail = email.trim().toLowerCase();
    const isOwnerClaim = masterKey === OWNER_MASTER_KEY || trimmedEmail === OWNER_EMAIL.toLowerCase();

    const newUser: User = {
      id: `user_${Date.now()}`,
      name: name.trim(),
      email: trimmedEmail,
      role: isOwnerClaim ? 'owner' : 'student',
      gradeClass: gradeClass || '4e',
      schoolName: schoolName || 'Collège Moderne',
      createdAt: new Date().toISOString(),
    };

    try {
      const usersJson = await AsyncStorage.getItem('@educi_registered_users');
      const users: User[] = usersJson ? JSON.parse(usersJson) : [];
      if (users.some(u => u.email.toLowerCase() === trimmedEmail)) {
        return { success: false, message: "Cet email est déjà utilisé. Connectez-vous." };
      }
      users.push(newUser);
      await AsyncStorage.setItem('@educi_registered_users', JSON.stringify(users));
      await saveUser(newUser);
      return { success: true, message: isOwnerClaim ? "Compte Propriétaire créé avec succès !" : "Compte créé avec succès !" };
    } catch (e) {
      return { success: false, message: "Erreur lors de la création du compte." };
    }
  };

  const logout = async () => {
    await saveUser(null);
  };

  const claimOwnerAccess = (secretKey: string): boolean => {
    if (secretKey.trim() === OWNER_MASTER_KEY) {
      const ownerUser: User = {
        id: 'owner_master_001',
        name: 'Propriétaire EduCI',
        email: OWNER_EMAIL,
        role: 'owner',
        gradeClass: 'Admin',
        schoolName: 'Direction EduCI',
        createdAt: new Date().toISOString(),
      };
      saveUser(ownerUser);
      return true;
    }
    return false;
  };

  return (
    <AuthContext.Provider value={{ currentUser, isOwnerOrAdmin, login, register, logout, claimOwnerAccess }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
