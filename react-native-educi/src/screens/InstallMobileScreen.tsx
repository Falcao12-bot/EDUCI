import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
  Share,
  Linking,
  Alert,
  Platform,
} from 'react-native';
import { useAppTheme } from '../context/ThemeContext';

// Lien public permanent via EXPO_PUBLIC_APP_URL ; sinon, sur le web, l'adresse où l'app est ouverte.
const APP_URL =
  process.env.EXPO_PUBLIC_APP_URL ||
  (Platform.OS === 'web' && typeof window !== 'undefined' ? window.location.origin : '');

export const InstallMobileScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { colors } = useAppTheme();
  const [activeTab, setActiveTab] = useState<'qr' | 'apk' | 'pwa' | 'expo'>('qr');

  const handleShare = async () => {
    try {
      await Share.share({
        message: `📱 Installe l'application éducative ivoirienne EduCI sur ton smartphone pour préparer tes cours et examens officiels :\n${APP_URL}`,
      });
    } catch (error) {
      Alert.alert('Erreur', 'Impossible de partager le lien.');
    }
  };

  const handleOpenUrl = () => {
    Linking.openURL(APP_URL).catch(() => {
      Alert.alert('Erreur', 'Impossible d\'ouvrir l\'adresse.');
    });
  };

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
      {/* Header */}
      <View style={[styles.header, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
        <TouchableOpacity onPress={() => navigation.goBack()} style={styles.backBtn}>
          <Text style={[styles.backText, { color: colors.primary }]}>← Retour</Text>
        </TouchableOpacity>
        <Text style={[styles.headerTitle, { color: colors.textPrimary }]}>Installer sur Téléphone</Text>
        <View style={{ width: 60 }} />
      </View>

      {/* Tabs */}
      <View style={[styles.tabBar, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
        {(['qr', 'apk', 'pwa', 'expo'] as const).map(tab => {
          const active = activeTab === tab;
          const labels = {
            qr: '📲 QR Code',
            apk: '📦 APK Android',
            pwa: '🌐 PWA Web',
            expo: '🚀 Expo Go',
          };
          return (
            <TouchableOpacity
              key={tab}
              style={[
                styles.tabItem,
                { borderBottomColor: active ? colors.primary : 'transparent', borderBottomWidth: 3 },
              ]}
              onPress={() => setActiveTab(tab)}
            >
              <Text
                style={[
                  styles.tabText,
                  { color: active ? colors.primary : colors.textMuted, fontWeight: active ? '700' : '500' },
                ]}
              >
                {labels[tab]}
              </Text>
            </TouchableOpacity>
          );
        })}
      </View>

      <ScrollView contentContainerStyle={styles.scrollContent}>
        {activeTab === 'qr' && (
          <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
            <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>Flasher avec l'appareil photo</Text>
            <Text style={[styles.cardSub, { color: colors.textSecondary }]}>
              Scannez ce QR Code depuis votre smartphone pour ouvrir directement l'application et l'installer sur votre écran d'accueil.
            </Text>

            {/* Simulated QR Code box */}
            <View style={styles.qrContainer}>
              <View style={[styles.qrFrame, { borderColor: colors.primary }]}>
                <Text style={styles.qrIcon}>📱</Text>
                <Text style={[styles.qrText, { color: colors.textPrimary }]}>EduCI Mobile CI</Text>
                <Text style={[styles.qrSub, { color: colors.accent }]}>Flashez pour installer</Text>
              </View>
            </View>

            <TouchableOpacity style={[styles.actionBtn, { backgroundColor: colors.primary }]} onPress={handleShare}>
              <Text style={styles.actionBtnText}>📤 Partager le lien par WhatsApp / SMS</Text>
            </TouchableOpacity>

            <TouchableOpacity style={[styles.outlineBtn, { borderColor: colors.border }]} onPress={handleOpenUrl}>
              <Text style={[styles.outlineBtnText, { color: colors.primary }]}>Ouvrir l'adresse dans le navigateur</Text>
            </TouchableOpacity>
          </View>
        )}

        {activeTab === 'apk' && (
          <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
            <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>Installation directe du fichier APK</Text>
            <Text style={[styles.cardSub, { color: colors.textSecondary }]}>
              Pour installer le paquet natif Android sur n'importe quel smartphone Android :
            </Text>

            <View style={styles.stepsList}>
              <View style={styles.stepRow}>
                <View style={[styles.stepNum, { backgroundColor: colors.primary }]}>
                  <Text style={styles.stepNumText}>1</Text>
                </View>
                <View style={{ flex: 1 }}>
                  <Text style={[styles.stepTitle, { color: colors.textPrimary }]}>Télécharger l'APK</Text>
                  <Text style={[styles.stepDesc, { color: colors.textSecondary }]}>
                    Téléchargez le fichier `app-debug.apk` depuis le menu d'export de la plateforme.
                  </Text>
                </View>
              </View>

              <View style={styles.stepRow}>
                <View style={[styles.stepNum, { backgroundColor: colors.primary }]}>
                  <Text style={styles.stepNumText}>2</Text>
                </View>
                <View style={{ flex: 1 }}>
                  <Text style={[styles.stepTitle, { color: colors.textPrimary }]}>Autoriser les sources inconnues</Text>
                  <Text style={[styles.stepDesc, { color: colors.textSecondary }]}>
                    Dans Paramètres Android &gt; Sécurité, cochez "Autoriser l'installation d'applications inconnues".
                  </Text>
                </View>
              </View>

              <View style={styles.stepRow}>
                <View style={[styles.stepNum, { backgroundColor: colors.primary }]}>
                  <Text style={styles.stepNumText}>3</Text>
                </View>
                <View style={{ flex: 1 }}>
                  <Text style={[styles.stepTitle, { color: colors.textPrimary }]}>Lancer l'installation</Text>
                  <Text style={[styles.stepDesc, { color: colors.textSecondary }]}>
                    Cliquez sur le fichier `.apk` dans vos téléchargements et validez "Installer".
                  </Text>
                </View>
              </View>
            </View>

            <TouchableOpacity style={[styles.actionBtn, { backgroundColor: colors.primary }]} onPress={handleOpenUrl}>
              <Text style={styles.actionBtnText}>⬇️ Télécharger l'application</Text>
            </TouchableOpacity>
          </View>
        )}

        {activeTab === 'pwa' && (
          <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
            <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>Ajout à l'écran d'accueil (PWA)</Text>
            <Text style={[styles.cardSub, { color: colors.textSecondary }]}>
              Sans aucun téléchargement lourd, fonctionne sur Android (Chrome) et iPhone (Safari) :
            </Text>

            <View style={styles.stepsList}>
              <Text style={[styles.stepText, { color: colors.textPrimary }]}>
                1. Ouvrez l'adresse dans Chrome ou Safari.
              </Text>
              <Text style={[styles.stepText, { color: colors.textPrimary }]}>
                2. Appuyez sur le menu (les 3 points ⋮ sur Android ou le bouton Partager sur iPhone).
              </Text>
              <Text style={[styles.stepText, { color: colors.textPrimary }]}>
                3. Choisissez <Text style={{ fontWeight: 'bold' }}>"Ajouter à l'écran d'accueil"</Text>.
              </Text>
              <Text style={[styles.stepText, { color: colors.textPrimary }]}>
                4. L'icône EduCI s'installe sur votre bureau téléphonique et fonctionne en plein écran !
              </Text>
            </View>

            <TouchableOpacity style={[styles.actionBtn, { backgroundColor: colors.primary }]} onPress={handleOpenUrl}>
              <Text style={styles.actionBtnText}>🌐 Ouvrir sur mon téléphone</Text>
            </TouchableOpacity>
          </View>
        )}

        {activeTab === 'expo' && (
          <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
            <Text style={[styles.cardTitle, { color: colors.textPrimary }]}>Tester via Expo Go</Text>
            <Text style={[styles.cardSub, { color: colors.textSecondary }]}>
              Pour exécuter le projet React Native directement sur votre appareil physique :
            </Text>
            <View style={styles.stepsList}>
              <Text style={[styles.stepText, { color: colors.textPrimary }]}>
                1. Téléchargez l'application gratuite <Text style={{ fontWeight: 'bold' }}>Expo Go</Text> sur Google Play ou l'App Store.
              </Text>
              <Text style={[styles.stepText, { color: colors.textPrimary }]}>
                2. Lancez le serveur local avec `npx expo start` dans le dossier `react-native-educi`.
              </Text>
              <Text style={[styles.stepText, { color: colors.textPrimary }]}>
                3. Scannez le QR code affiché dans votre terminal avec l'application Expo Go.
              </Text>
            </View>
          </View>
        )}
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  header: { flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between', paddingHorizontal: 16, paddingVertical: 12, borderBottomWidth: 1 },
  backBtn: { width: 70 },
  backText: { fontSize: 14, fontWeight: '700' },
  headerTitle: { fontSize: 16, fontWeight: '800' },
  tabBar: { flexDirection: 'row', borderBottomWidth: 1 },
  tabItem: { flex: 1, paddingVertical: 12, alignItems: 'center' },
  tabText: { fontSize: 12 },
  scrollContent: { padding: 16, paddingBottom: 40 },
  card: { padding: 18, borderRadius: 16, borderWidth: 1, marginBottom: 16 },
  cardTitle: { fontSize: 17, fontWeight: '800', marginBottom: 6 },
  cardSub: { fontSize: 13, lineHeight: 18, marginBottom: 16 },
  qrContainer: { alignItems: 'center', marginVertical: 14 },
  qrFrame: { width: 180, height: 180, borderWidth: 2, borderRadius: 16, alignItems: 'center', justifyContent: 'center', backgroundColor: '#FFFFFF', padding: 16 },
  qrIcon: { fontSize: 50, marginBottom: 8 },
  qrText: { fontSize: 15, fontWeight: '800' },
  qrSub: { fontSize: 12, marginTop: 4 },
  actionBtn: { paddingVertical: 14, borderRadius: 12, alignItems: 'center', marginTop: 12 },
  actionBtnText: { color: '#FFFFFF', fontSize: 14, fontWeight: '700' },
  outlineBtn: { paddingVertical: 12, borderRadius: 12, alignItems: 'center', borderWidth: 1, marginTop: 8 },
  outlineBtnText: { fontSize: 14, fontWeight: '600' },
  stepsList: { gap: 12, marginBottom: 16 },
  stepRow: { flexDirection: 'row', gap: 12, alignItems: 'flex-start' },
  stepNum: { width: 24, height: 24, borderRadius: 12, alignItems: 'center', justifyContent: 'center' },
  stepNumText: { color: '#FFFFFF', fontSize: 12, fontWeight: '700' },
  stepTitle: { fontSize: 14, fontWeight: '700', marginBottom: 2 },
  stepDesc: { fontSize: 12, lineHeight: 16 },
  stepText: { fontSize: 13, lineHeight: 20 },
});
