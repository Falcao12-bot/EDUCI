import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TextInput,
  TouchableOpacity,
  SafeAreaView,
  Alert,
} from 'react-native';
import { useAppTheme } from '../context/ThemeContext';
import { useAuth, OWNER_EMAIL, OWNER_MASTER_KEY } from '../context/AuthContext';

export const AuthScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { colors } = useAppTheme();
  const { login, register } = useAuth();

  const [isRegisterMode, setIsRegisterMode] = useState(false);
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [gradeClass, setGradeClass] = useState('4e');
  const [schoolName, setSchoolName] = useState('Collège Moderne');
  const [masterKey, setMasterKey] = useState('');
  const [showMasterKeyField, setShowMasterKeyField] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    if (!email.trim()) {
      Alert.alert('Erreur', 'Veuillez saisir votre adresse email.');
      return;
    }

    setLoading(true);
    if (isRegisterMode) {
      if (!name.trim()) {
        Alert.alert('Erreur', 'Veuillez renseigner votre nom complet.');
        setLoading(false);
        return;
      }
      const res = await register(name, email, gradeClass, schoolName, masterKey);
      setLoading(false);
      Alert.alert(res.success ? 'Succès' : 'Information', res.message);
      if (res.success) {
        navigation.navigate('MainTabs');
      }
    } else {
      const res = await login(email, masterKey);
      setLoading(false);
      Alert.alert(res.success ? 'Bienvenue' : 'Erreur', res.message);
      if (res.success) {
        navigation.navigate('MainTabs');
      }
    }
  };

  const handleQuickFillOwner = () => {
    setEmail(OWNER_EMAIL);
    setMasterKey(OWNER_MASTER_KEY);
    setShowMasterKeyField(true);
  };

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
      <ScrollView contentContainerStyle={styles.scrollContent}>
        {/* Logo & Intro */}
        <View style={styles.brandHeader}>
          <Text style={[styles.brandTitle, { color: colors.primary }]}>EduCI</Text>
          <Text style={[styles.brandSub, { color: colors.textSecondary }]}>
            Portail Éducatif National de Côte d'Ivoire
          </Text>
        </View>

        {/* Card Form */}
        <View style={[styles.card, { backgroundColor: colors.surface, borderColor: colors.border }]}>
          <Text style={[styles.formTitle, { color: colors.textPrimary }]}>
            {isRegisterMode ? 'Créer un compte' : 'Connexion'}
          </Text>

          {isRegisterMode && (
            <>
              <Text style={[styles.label, { color: colors.textPrimary }]}>Nom complet :</Text>
              <TextInput
                style={[styles.input, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
                placeholder="Ex: Jean Kouassi"
                placeholderTextColor={colors.textMuted}
                value={name}
                onChangeText={setName}
              />

              <View style={styles.row}>
                <View style={{ flex: 1, marginRight: 8 }}>
                  <Text style={[styles.label, { color: colors.textPrimary }]}>Classe :</Text>
                  <TextInput
                    style={[styles.input, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
                    value={gradeClass}
                    onChangeText={setGradeClass}
                  />
                </View>
                <View style={{ flex: 1, marginLeft: 8 }}>
                  <Text style={[styles.label, { color: colors.textPrimary }]}>Établissement :</Text>
                  <TextInput
                    style={[styles.input, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
                    value={schoolName}
                    onChangeText={setSchoolName}
                  />
                </View>
              </View>
            </>
          )}

          <Text style={[styles.label, { color: colors.textPrimary }]}>Adresse Email :</Text>
          <TextInput
            style={[styles.input, { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary }]}
            placeholder="votre.email@exemple.ci"
            placeholderTextColor={colors.textMuted}
            keyboardType="email-address"
            autoCapitalize="none"
            value={email}
            onChangeText={setEmail}
          />

          {/* Master Key toggle */}
          <TouchableOpacity
            style={styles.keyToggle}
            onPress={() => setShowMasterKeyField(prev => !prev)}
          >
            <Text style={[styles.keyToggleText, { color: colors.accent }]}>
              {showMasterKeyField ? '− Masquer la clé Propriétaire' : '+ Accès Propriétaire Fondateur ?'}
            </Text>
          </TouchableOpacity>

          {showMasterKeyField && (
            <View style={{ marginBottom: 12 }}>
              <Text style={[styles.label, { color: colors.textPrimary }]}>
                Clé Secrète Propriétaire :
              </Text>
              <TextInput
                style={[styles.input, { backgroundColor: colors.surfaceVariant, borderColor: colors.primary, color: colors.textPrimary }]}
                placeholder="Entrez la clé maître..."
                placeholderTextColor={colors.textMuted}
                secureTextEntry
                value={masterKey}
                onChangeText={setMasterKey}
              />
            </View>
          )}

          {/* Submit */}
          <TouchableOpacity
            style={[styles.submitBtn, { backgroundColor: colors.primary }]}
            onPress={handleSubmit}
            disabled={loading}
          >
            <Text style={styles.submitBtnText}>
              {loading ? 'Traitement...' : isRegisterMode ? 'Créer mon Compte' : 'Se Connecter'}
            </Text>
          </TouchableOpacity>

          {/* Toggle Register/Login */}
          <TouchableOpacity
            style={styles.toggleModeBtn}
            onPress={() => setIsRegisterMode(prev => !prev)}
          >
            <Text style={[styles.toggleModeText, { color: colors.textSecondary }]}>
              {isRegisterMode
                ? 'Déjà inscrit ? Connectez-vous'
                : 'Pas encore de compte ? S\'inscrire'}
            </Text>
          </TouchableOpacity>

          {/* Quick Owner Fill helper for testing */}
          <TouchableOpacity
            style={[styles.quickOwnerBtn, { borderColor: colors.border }]}
            onPress={handleQuickFillOwner}
          >
            <Text style={[styles.quickOwnerText, { color: colors.primary }]}>
              ⚡ Remplissage rapide Propriétaire (Test)
            </Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  scrollContent: { padding: 20, justifyContent: 'center' },
  brandHeader: { alignItems: 'center', marginBottom: 24, marginTop: 20 },
  brandTitle: { fontSize: 32, fontWeight: '900', letterSpacing: 1 },
  brandSub: { fontSize: 13, marginTop: 4 },
  card: { padding: 20, borderRadius: 16, borderWidth: 1 },
  formTitle: { fontSize: 18, fontWeight: '800', marginBottom: 16 },
  label: { fontSize: 13, fontWeight: '700', marginBottom: 6 },
  input: { height: 46, borderRadius: 10, borderWidth: 1, paddingHorizontal: 12, fontSize: 14, marginBottom: 14 },
  row: { flexDirection: 'row' },
  keyToggle: { marginBottom: 12 },
  keyToggleText: { fontSize: 13, fontWeight: '700' },
  submitBtn: { paddingVertical: 14, borderRadius: 12, alignItems: 'center', marginTop: 6 },
  submitBtnText: { color: '#FFFFFF', fontSize: 15, fontWeight: '700' },
  toggleModeBtn: { alignItems: 'center', marginTop: 16 },
  toggleModeText: { fontSize: 13, fontWeight: '600' },
  quickOwnerBtn: { marginTop: 18, paddingVertical: 10, borderRadius: 10, borderWidth: 1, alignItems: 'center' },
  quickOwnerText: { fontSize: 12, fontWeight: '700' },
});
