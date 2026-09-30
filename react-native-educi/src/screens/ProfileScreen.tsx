import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
  Alert,
} from 'react-native';
import { useAppTheme, ThemeMode } from '../context/ThemeContext';
import { useAuth, OWNER_EMAIL } from '../context/AuthContext';

export const ProfileScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { colors, mode, setMode } = useAppTheme();
  const { currentUser, isOwnerOrAdmin, logout } = useAuth();

  const handleLogout = () => {
    Alert.alert(
      'Déconnexion',
      'Voulez-vous vraiment vous déconnecter ?',
      [
        { text: 'Annuler', style: 'cancel' },
        {
          text: 'Déconnexion',
          style: 'destructive',
          onPress: async () => {
            await logout();
            navigation.navigate('Auth');
          },
        },
      ]
    );
  };

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
      <ScrollView contentContainerStyle={styles.scrollContent}>
        {/* User Card */}
        <View style={[styles.profileCard, { backgroundColor: colors.surface, borderColor: colors.border }]}>
          <View style={[styles.avatarCircle, { backgroundColor: colors.badgeBg, borderColor: colors.primary }]}>
            <Text style={[styles.avatarText, { color: colors.primary }]}>
              {currentUser ? currentUser.name.charAt(0).toUpperCase() : '👤'}
            </Text>
          </View>
          <Text style={[styles.userName, { color: colors.textPrimary }]}>
            {currentUser ? currentUser.name : 'Utilisateur Invité'}
          </Text>
          <Text style={[styles.userEmail, { color: colors.textSecondary }]}>
            {currentUser ? currentUser.email : 'Aucun compte connecté'}
          </Text>
          {isOwnerOrAdmin && (
            <View style={[styles.ownerBadge, { backgroundColor: colors.badgeBg, borderColor: colors.primary }]}>
              <Text style={[styles.ownerBadgeText, { color: colors.primary }]}>
                👑 Propriétaire & Administrateur
              </Text>
            </View>
          )}
        </View>

        {/* Theme Settings (Mode Clair Vert-Blanc / Mode Sombre / Système) */}
        <Text style={[styles.sectionTitle, { color: colors.textPrimary }]}>Apparence & Thème</Text>
        <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
          <Text style={[styles.cardSub, { color: colors.textSecondary }]}>
            Choisissez votre mode d'affichage préféré pour un confort visuel optimal :
          </Text>

          <View style={styles.themeRow}>
            {(['light', 'dark', 'system'] as ThemeMode[]).map(tMode => {
              const active = mode === tMode;
              const labels = {
                light: '☀️ Mode Clair (Vert-Blanc)',
                dark: '🌙 Mode Sombre',
                system: '📱 Système',
              };
              return (
                <TouchableOpacity
                  key={tMode}
                  style={[
                    styles.themeOption,
                    {
                      backgroundColor: active ? colors.badgeBg : colors.surfaceVariant,
                      borderColor: active ? colors.primary : colors.border,
                    },
                  ]}
                  onPress={() => setMode(tMode)}
                >
                  <Text
                    style={[
                      styles.themeOptionText,
                      { color: active ? colors.primary : colors.textPrimary, fontWeight: active ? '700' : '500' },
                    ]}
                  >
                    {labels[tMode]}
                  </Text>
                </TouchableOpacity>
              );
            })}
          </View>
        </View>

        {/* Owner Management Link */}
        <Text style={[styles.sectionTitle, { color: colors.textPrimary }]}>Administration</Text>
        <TouchableOpacity
          style={[styles.cardAction, { backgroundColor: colors.surface, borderColor: colors.border }]}
          onPress={() => navigation.navigate('Admin')}
        >
          <View style={{ flex: 1 }}>
            <Text style={[styles.actionTitle, { color: colors.textPrimary }]}>
              {isOwnerOrAdmin ? '⚙️ Tableau de Bord Propriétaire' : '🔒 Accès Réservé au Propriétaire'}
            </Text>
            <Text style={[styles.actionSub, { color: colors.textMuted }]}>
              {isOwnerOrAdmin
                ? 'Gérer les cours, exercices, épreuves et utilisateurs'
                : 'Réservé exclusivement à horizonprogrammeur@gmail.com'}
            </Text>
          </View>
          <Text style={{ fontSize: 18, color: colors.textMuted }}>➔</Text>
        </TouchableOpacity>

        {/* Auth / Account Action */}
        <View style={{ marginTop: 24 }}>
          {currentUser ? (
            <TouchableOpacity
              style={[styles.logoutBtn, { backgroundColor: '#FEE2E2', borderColor: colors.error }]}
              onPress={handleLogout}
            >
              <Text style={[styles.logoutBtnText, { color: colors.error }]}>Se Déconnecter</Text>
            </TouchableOpacity>
          ) : (
            <TouchableOpacity
              style={[styles.loginBtn, { backgroundColor: colors.primary }]}
              onPress={() => navigation.navigate('Auth')}
            >
              <Text style={styles.loginBtnText}>Se Connecter / S'inscrire</Text>
            </TouchableOpacity>
          )}
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  scrollContent: { padding: 16, paddingBottom: 40 },
  profileCard: { padding: 20, borderRadius: 16, borderWidth: 1, alignItems: 'center', marginBottom: 20 },
  avatarCircle: { width: 72, height: 72, borderRadius: 36, borderWidth: 2, alignItems: 'center', justifyContent: 'center', marginBottom: 12 },
  avatarText: { fontSize: 30, fontWeight: '800' },
  userName: { fontSize: 18, fontWeight: '800', marginBottom: 4 },
  userEmail: { fontSize: 13, marginBottom: 8 },
  ownerBadge: { paddingHorizontal: 12, paddingVertical: 4, borderRadius: 12, borderWidth: 1, marginTop: 4 },
  ownerBadgeText: { fontSize: 12, fontWeight: '700' },
  sectionTitle: { fontSize: 16, fontWeight: '700', marginBottom: 10 },
  card: { padding: 16, borderRadius: 14, borderWidth: 1, marginBottom: 20 },
  cardSub: { fontSize: 13, marginBottom: 14, lineHeight: 18 },
  themeRow: { gap: 10 },
  themeOption: { paddingVertical: 12, paddingHorizontal: 14, borderRadius: 10, borderWidth: 1 },
  themeOptionText: { fontSize: 14 },
  cardAction: { flexDirection: 'row', alignItems: 'center', padding: 16, borderRadius: 14, borderWidth: 1 },
  actionTitle: { fontSize: 15, fontWeight: '700', marginBottom: 2 },
  actionSub: { fontSize: 12 },
  logoutBtn: { paddingVertical: 14, borderRadius: 12, borderWidth: 1, alignItems: 'center' },
  logoutBtnText: { fontSize: 15, fontWeight: '700' },
  loginBtn: { paddingVertical: 14, borderRadius: 12, alignItems: 'center' },
  loginBtnText: { color: '#FFFFFF', fontSize: 15, fontWeight: '700' },
});
