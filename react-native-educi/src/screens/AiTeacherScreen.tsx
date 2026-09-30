import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TextInput,
  TouchableOpacity,
  SafeAreaView,
  ActivityIndicator,
} from 'react-native';
import { useAppTheme } from '../context/ThemeContext';

interface Message {
  id: string;
  sender: 'ai' | 'user';
  text: string;
}

export const AiTeacherScreen: React.FC = () => {
  const { colors } = useAppTheme();
  const [messages, setMessages] = useState<Message[]>([
    {
      id: 'm1',
      sender: 'ai',
      text: 'Bonjour ! Je suis le Professeur EduCI, ton tuteur personnel. Quelle notion du programme ivoirien souhaites-tu travailler aujourd\'hui ?',
    },
  ]);
  const [inputText, setInputText] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSend = () => {
    if (!inputText.trim()) return;

    const userMsg: Message = {
      id: `u_${Date.now()}`,
      sender: 'user',
      text: inputText.trim(),
    };

    setMessages(prev => [...prev, userMsg]);
    setInputText('');
    setLoading(true);

    setTimeout(() => {
      let reply = "Excellente question ! Selon le programme national, voici la méthode pas-à-pas pour réussir : identifie d'abord les données de l'énoncé, applique la formule du cours, et formule clairement la conclusion.";
      if (userMsg.text.toLowerCase().includes('pythagore')) {
        reply = "Pour le Théorème de Pythagore : vérifie d'abord que le triangle est bien rectangle. L'hypoténuse est le côté opposé à l'angle droit (le plus long). La formule magique est : Hypoténuse² = Côté1² + Côté2² !";
      } else if (userMsg.text.toLowerCase().includes('bepc') || userMsg.text.toLowerCase().includes('bac')) {
        reply = "Pour les examens nationaux (BEPC/BAC) en Côte d'Ivoire : la régularité et la rigueur de présentation font la différence. Révise les annales des 5 dernières années dans notre onglet 'Examens' !";
      }

      const aiMsg: Message = {
        id: `a_${Date.now()}`,
        sender: 'ai',
        text: reply,
      };
      setMessages(prev => [...prev, aiMsg]);
      setLoading(false);
    }, 1000);
  };

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
      <View style={[styles.header, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
        <View>
          <Text style={[styles.headerTitle, { color: colors.primary }]}>Professeur EduCI 🤖</Text>
          <Text style={[styles.headerSub, { color: colors.textMuted }]}>
            Tuteur pédagogique intelligent aligné au programme CI
          </Text>
        </View>
      </View>

      <ScrollView contentContainerStyle={styles.messagesContainer}>
        {messages.map(msg => {
          const isUser = msg.sender === 'user';
          return (
            <View
              key={msg.id}
              style={[
                styles.messageBubble,
                isUser
                  ? [styles.userBubble, { backgroundColor: colors.primary }]
                  : [styles.aiBubble, { backgroundColor: colors.surface, borderColor: colors.border }],
              ]}
            >
              <Text
                style={[
                  styles.messageText,
                  { color: isUser ? '#FFFFFF' : colors.textPrimary },
                ]}
              >
                {msg.text}
              </Text>
            </View>
          );
        })}

        {loading && (
          <View style={[styles.loadingBox, { backgroundColor: colors.surface, borderColor: colors.border }]}>
            <ActivityIndicator size="small" color={colors.primary} />
            <Text style={[styles.loadingText, { color: colors.textSecondary }]}>
              Professeur EduCI réfléchit...
            </Text>
          </View>
        )}
      </ScrollView>

      {/* Input bar */}
      <View style={[styles.inputRow, { backgroundColor: colors.surface, borderTopColor: colors.border }]}>
        <TextInput
          style={[
            styles.textInput,
            { backgroundColor: colors.surfaceVariant, borderColor: colors.border, color: colors.textPrimary },
          ]}
          placeholder="Pose ta question au Professeur..."
          placeholderTextColor={colors.textMuted}
          value={inputText}
          onChangeText={setInputText}
        />
        <TouchableOpacity
          style={[styles.sendBtn, { backgroundColor: colors.primary }]}
          onPress={handleSend}
          disabled={loading || !inputText.trim()}
        >
          <Text style={styles.sendBtnText}>➤</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  header: { padding: 14, borderBottomWidth: 1 },
  headerTitle: { fontSize: 17, fontWeight: '800' },
  headerSub: { fontSize: 11, marginTop: 2 },
  messagesContainer: { padding: 16, paddingBottom: 20 },
  messageBubble: { maxWidth: '82%', padding: 12, borderRadius: 14, marginBottom: 12 },
  userBubble: { alignSelf: 'flex-end', borderBottomRightRadius: 2 },
  aiBubble: { alignSelf: 'flex-start', borderBottomLeftRadius: 2, borderWidth: 1 },
  messageText: { fontSize: 14, lineHeight: 20 },
  loadingBox: { flexDirection: 'row', alignItems: 'center', padding: 10, borderRadius: 10, alignSelf: 'flex-start', borderWidth: 1, gap: 8 },
  loadingText: { fontSize: 12 },
  inputRow: { flexDirection: 'row', padding: 12, borderTopWidth: 1, gap: 8, alignItems: 'center' },
  textInput: { flex: 1, height: 44, borderRadius: 22, borderWidth: 1, paddingHorizontal: 16, fontSize: 14 },
  sendBtn: { width: 44, height: 44, borderRadius: 22, alignItems: 'center', justifyContent: 'center' },
  sendBtnText: { color: '#FFFFFF', fontSize: 16, fontWeight: '700' },
});
