import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  SafeAreaView,
} from 'react-native';
import { useAppTheme } from '../context/ThemeContext';

interface AppNotification {
  id: string;
  title: string;
  message: string;
  type: 'new_lesson' | 'exam_ready' | 'reminder' | 'announcement';
  dateString: string;
  isRead: boolean;
}

const INITIAL_NOTIFICATIONS: AppNotification[] = [
  {
    id: 'n1',
    title: 'Nouvelle Épreuve Disponible',
    message: 'L\'épreuve officielle de Mathématiques BEPC 2024 avec son corrigé détaillé a été ajoutée.',
    type: 'exam_ready',
    dateString: 'Aujourd\'hui à 09:30',
    isRead: false,
  },
  {
    id: 'n2',
    title: 'Rappel Objectif Quotidien',
    message: 'N\'oublie pas de compléter tes 20 minutes d\'entraînement pour maintenir ta série de révision active !',
    type: 'reminder',
    dateString: 'Hier à 18:00',
    isRead: false,
  },
  {
    id: 'n3',
    title: 'Nouveau cours de SVT',
    message: 'Le chapitre « La respiration chez les êtres vivants » (Classe de 5e) est en ligne.',
    type: 'new_lesson',
    dateString: 'Il y a 2 jours',
    isRead: true,
  },
  {
    id: 'n4',
    title: 'Calendrier des Examens Nationaux',
    message: 'Les dates prévisionnelles du BEPC et du BAC 2026 sont consultables dans l\'espace examens.',
    type: 'announcement',
    dateString: 'Il y a 3 jours',
    isRead: true,
  },
];

export const NotificationsScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { colors } = useAppTheme();
  const [notifications, setNotifications] = useState<AppNotification[]>(INITIAL_NOTIFICATIONS);

  const markAllRead = () => {
    setNotifications(prev => prev.map(n => ({ ...n, isRead: true })));
  };

  const toggleItemRead = (id: string) => {
    setNotifications(prev =>
      prev.map(n => (n.id === id ? { ...n, isRead: !n.isRead } : n))
    );
  };

  const getIcon = (type: AppNotification['type']) => {
    switch (type) {
      case 'exam_ready':
        return '🎓';
      case 'reminder':
        return '🔥';
      case 'new_lesson':
        return '📖';
      case 'announcement':
      default:
        return '📢';
    }
  };

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: colors.background }]}>
      {/* Header */}
      <View style={[styles.header, { backgroundColor: colors.surface, borderBottomColor: colors.border }]}>
        <View style={styles.headerLeft}>
          <TouchableOpacity onPress={() => navigation.goBack()} style={styles.backBtn}>
            <Text style={[styles.backBtnText, { color: colors.primary }]}>←</Text>
          </TouchableOpacity>
          <Text style={[styles.title, { color: colors.textPrimary }]}>Notifications</Text>
        </View>
        <TouchableOpacity onPress={markAllRead}>
          <Text style={[styles.markReadBtn, { color: colors.primary }]}>Tout marquer lu</Text>
        </TouchableOpacity>
      </View>

      <ScrollView contentContainerStyle={styles.content}>
        {notifications.length === 0 ? (
          <View style={styles.emptyBox}>
            <Text style={{ fontSize: 40, marginBottom: 12 }}>🔔</Text>
            <Text style={[styles.emptyText, { color: colors.textMuted }]}>
              Aucune notification pour le moment.
            </Text>
          </View>
        ) : (
          notifications.map(item => (
            <TouchableOpacity
              key={item.id}
              style={[
                styles.itemCard,
                {
                  backgroundColor: item.isRead ? colors.surface : colors.badgeBg,
                  borderColor: item.isRead ? colors.border : colors.primary,
                },
              ]}
              onPress={() => toggleItemRead(item.id)}
            >
              <Text style={styles.icon}>{getIcon(item.type)}</Text>
              <View style={styles.textContainer}>
                <View style={styles.itemHeader}>
                  <Text style={[styles.itemTitle, { color: colors.textPrimary }]}>{item.title}</Text>
                  {!item.isRead && (
                    <View style={[styles.unreadDot, { backgroundColor: colors.primary }]} />
                  )}
                </View>
                <Text style={[styles.itemMessage, { color: colors.textSecondary }]}>{item.message}</Text>
                <Text style={[styles.itemDate, { color: colors.textMuted }]}>{item.dateString}</Text>
              </View>
            </TouchableOpacity>
          ))
        )}
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingVertical: 12,
    borderBottomWidth: 1,
  },
  headerLeft: { flexDirection: 'row', alignItems: 'center', gap: 12 },
  backBtn: { padding: 4 },
  backBtnText: { fontSize: 22, fontWeight: '700' },
  title: { fontSize: 18, fontWeight: '700' },
  markReadBtn: { fontSize: 13, fontWeight: '600' },
  content: { padding: 16, paddingBottom: 30 },
  emptyBox: { alignItems: 'center', paddingVertical: 60 },
  emptyText: { fontSize: 14 },
  itemCard: {
    flexDirection: 'row',
    padding: 14,
    borderRadius: 12,
    borderWidth: 1,
    marginBottom: 10,
    gap: 12,
    alignItems: 'flex-start',
  },
  icon: { fontSize: 24, marginTop: 2 },
  textContainer: { flex: 1 },
  itemHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 4 },
  itemTitle: { fontSize: 14, fontWeight: '700' },
  unreadDot: { width: 8, height: 8, borderRadius: 4 },
  itemMessage: { fontSize: 13, lineHeight: 18, marginBottom: 6 },
  itemDate: { fontSize: 11 },
});
