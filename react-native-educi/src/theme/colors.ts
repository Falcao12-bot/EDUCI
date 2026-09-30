export interface ThemeColors {
  background: string;
  surface: string;
  surfaceVariant: string;
  primary: string;
  primaryLight: string;
  primaryDark: string;
  accent: string;       // Orange Côte d'Ivoire
  accentLight: string;
  textPrimary: string;
  textSecondary: string;
  textMuted: string;
  border: string;
  divider: string;
  error: string;
  success: string;
  badgeBg: string;
  cardShadow: string;
}

export const lightTheme: ThemeColors = {
  // Fond Vert-Blanc caractéristique EduCI demandé
  background: '#EDF7F0',
  surface: '#FFFFFF',
  surfaceVariant: '#F3FAF5',
  primary: '#008751',      // Vert officiel Côte d'Ivoire
  primaryLight: '#E8F5E9',
  primaryDark: '#005A36',
  accent: '#FF8200',       // Orange officiel Côte d'Ivoire
  accentLight: '#FFF4E6',
  textPrimary: '#1A2E22',
  textSecondary: '#3F5B4B',
  textMuted: '#6B8074',
  border: '#CFE8D7',       // Liseré menthe clair
  divider: '#E2EFE7',
  error: '#DC2626',
  success: '#10B981',
  badgeBg: '#DFF3E5',
  cardShadow: 'rgba(0, 75, 40, 0.06)',
};

export const darkTheme: ThemeColors = {
  // Mode Sombre émeraude nocturne optimisé
  background: '#0B1912',
  surface: '#13281E',
  surfaceVariant: '#193327',
  primary: '#34D399',
  primaryLight: '#1B4734',
  primaryDark: '#059669',
  accent: '#F59E0B',
  accentLight: '#3D2808',
  textPrimary: '#E8F5E9',
  textSecondary: '#A7C9B6',
  textMuted: '#6F8C7C',
  border: '#1F3D2E',
  divider: '#1A3326',
  error: '#EF4444',
  success: '#34D399',
  badgeBg: '#1A3F2E',
  cardShadow: 'rgba(0, 0, 0, 0.4)',
};
